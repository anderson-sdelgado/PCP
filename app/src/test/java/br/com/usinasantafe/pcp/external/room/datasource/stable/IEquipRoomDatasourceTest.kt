package br.com.usinasantafe.pcp.external.room.datasource.stable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pcp.external.room.AppDatabaseRoom
import br.com.usinasantafe.pcp.external.room.dao.stable.EquipDao
import br.com.usinasantafe.pcp.infra.models.room.stable.EquipRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class IEquipRoomDatasourceTest {

    private lateinit var equipDao: EquipDao
    private lateinit var db: AppDatabaseRoom

    @Before
    fun before() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabaseRoom::class.java).allowMainThreadQueries().build()
        equipDao = db.equipDao()
    }

    @After
    fun after() {
        db.close()
    }

    @Test
    fun `Check execution correct deleteAll`() = runTest {
        val datasource = IEquipRoomDatasource(equipDao)
        val result = datasource.deleteAll()
        assertEquals(
            result.isSuccess,
            true
        )
    }

    @Test
    fun `Check failure addAll if have row repeated`() = runTest {
        val datasource = IEquipRoomDatasource(equipDao)
        val result = datasource.addAll(
            listOf(
                EquipRoomModel(
                    idEquip = 1,
                    nroEquip = 10,
                    descrEquip = "teste"
                ),
                EquipRoomModel(
                    idEquip = 1,
                    nroEquip = 10,
                    descrEquip = "teste"
                )
            )
        )
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "IEquipRoomDatasource.addAll"
        )
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            "android.database.sqlite.SQLiteConstraintException: Cannot execute for last inserted row ID"
        )
    }

    @Test
    fun `Check success addAll if have row is correct`() = runTest {
        val datasource = IEquipRoomDatasource(equipDao)
        val result = datasource.addAll(
            listOf(
                EquipRoomModel(
                    idEquip = 1,
                    nroEquip = 10,
                    descrEquip = "teste"
                ),
                EquipRoomModel(
                    idEquip = 2,
                    nroEquip = 20,
                    descrEquip = "teste"
                )
            )
        )
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!,
            true
        )
    }

    @Test
    fun `Check return false if not exist Equip`() = runTest {
        val datasource = IEquipRoomDatasource(equipDao)
        val result = datasource.checkNro(100)
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!,
            false
        )
    }

    @Test
    fun `Check return true if exist Equip`() = runTest {
        val datasource = IEquipRoomDatasource(equipDao)
        datasource.addAll(
            listOf(
                EquipRoomModel(
                    idEquip = 10,
                    nroEquip = 100,
                    descrEquip = "teste"
                ),
            )
        )
        val result = datasource.checkNro(100)
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!,
            true
        )
    }

    @Test
    fun `Check return failure getNro if not have data researched`() = runTest {
        val datasource = IEquipRoomDatasource(equipDao)
        val exception = try {
            datasource.getNro(1)
            null
        } catch (exception: Exception){
            exception
        }
        assertEquals(
            exception,
            null
        )
    }

    @Test
    fun `Check return getNro if have data researched`() = runTest {
        val datasource = IEquipRoomDatasource(equipDao)
        datasource.addAll(
            listOf(
                EquipRoomModel(
                    idEquip = 10,
                    nroEquip = 100,
                    descrEquip = "teste"
                ),
            )
        )
        val result = datasource.getNro(10)
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!,
            100
        )
    }

    @Test
    fun `Check return failure getId if not have data researched`() = runTest {
        val datasource = IEquipRoomDatasource(equipDao)
        val exception = try {
            datasource.getId(10)
            null
        } catch (exception: Exception){
            exception
        }
        assertEquals(
            exception,
            null
        )
    }

    @Test
    fun `Check return getId if have data researched`() = runTest {
        val datasource = IEquipRoomDatasource(equipDao)
        datasource.addAll(
            listOf(
                EquipRoomModel(
                    idEquip = 10,
                    nroEquip = 100,
                    descrEquip = "teste"
                ),
            )
        )
        val result = datasource.getId(100)
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!,
            10
        )
    }

}