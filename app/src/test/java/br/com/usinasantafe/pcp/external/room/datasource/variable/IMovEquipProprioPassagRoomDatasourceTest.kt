package br.com.usinasantafe.pcp.external.room.datasource.variable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pcp.external.room.AppDatabaseRoom
import br.com.usinasantafe.pcp.external.room.dao.variable.MovEquipProprioPassagDao
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipProprioPassagRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class IMovEquipProprioPassagRoomDatasourceTest {

    private lateinit var movEquipProprioPassagDao: MovEquipProprioPassagDao
    private lateinit var db: AppDatabaseRoom

    @Before
    fun before() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabaseRoom::class.java).allowMainThreadQueries().build()
        movEquipProprioPassagDao = db.movEquipProprioPassagDao()
    }

    @After
    fun after() {
        db.close()
    }

    @Test
    fun `Check success addAll if have row is correct`() = runTest {
        val datasource = IMovEquipProprioPassagRoomDatasource(movEquipProprioPassagDao)
        val result = datasource.addAll(
            listOf(
                MovEquipProprioPassagRoomModel(
                    idMovEquipProprio = 1,
                    matricColab = 19759
                ),
                MovEquipProprioPassagRoomModel(
                    idMovEquipProprio = 1,
                    matricColab = 19035
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
    fun `Check success list if have MovEquipProprioPassagRoomModel list`() = runTest {
        val datasource = IMovEquipProprioPassagRoomDatasource(movEquipProprioPassagDao)
        datasource.addAll(
            listOf(
                MovEquipProprioPassagRoomModel(
                    idMovEquipProprio = 1,
                    matricColab = 19759
                ),
                MovEquipProprioPassagRoomModel(
                    idMovEquipProprio = 1,
                    matricColab = 19035
                )
            )
        )
        val result = datasource.list(1)
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!.size,
            2
        )
        val model = result.getOrNull()!![1]
        assertEquals(
            model.idMovEquipProprioPassag,
            2
        )
        assertEquals(
            model.matricColab,
            19035
        )
    }

    @Test
    fun  `Check success add if have row is correct`() = runTest {
        val datasource = IMovEquipProprioPassagRoomDatasource(movEquipProprioPassagDao)
        val result = datasource.add(19759, 1)
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!,
            true
        )
        val resultList = datasource.list(1)
        assertEquals(
            resultList.isSuccess,
            true
        )
        assertEquals(
            resultList.getOrNull()!!.size,
            1
        )
        val model = resultList.getOrNull()!![0]
        assertEquals(
            model.idMovEquipProprioPassag,
            1
        )
        assertEquals(
            model.matricColab,
            19759
        )
    }

    @Test
    fun `Check success delete if process execute successfully`() = runTest {
        val datasource = IMovEquipProprioPassagRoomDatasource(movEquipProprioPassagDao)
        datasource.addAll(
            listOf(
                MovEquipProprioPassagRoomModel(
                    idMovEquipProprio = 1,
                    matricColab = 19759
                ),
                MovEquipProprioPassagRoomModel(
                    idMovEquipProprio = 1,
                    matricColab = 19035
                ),
                MovEquipProprioPassagRoomModel(
                    idMovEquipProprio = 1,
                    matricColab = 1807
                )
            )
        )
        val result = datasource.list(1)
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!.size,
            3
        )
        val modelBefore = result.getOrNull()!![1]
        assertEquals(
            modelBefore.idMovEquipProprioPassag,
            2
        )
        assertEquals(
            modelBefore.matricColab,
            19035
        )
        val resultDelete = datasource.delete(19035, 1)
        assertEquals(
            resultDelete.isSuccess,
            true
        )
        assertEquals(
            resultDelete.getOrNull()!!,
            true
        )
        val resultList = datasource.list(1)
        assertEquals(
            resultList.getOrNull()!!.size,
            2
        )
        val modelAfter = resultList.getOrNull()!![1]
        assertEquals(
            modelAfter.idMovEquipProprioPassag,
            3
        )
        assertEquals(
            modelAfter.matricColab,
            1807
        )
    }

    @Test
    fun `Check success delete Mov all if process execute successfully`() = runTest {
        val datasource = IMovEquipProprioPassagRoomDatasource(movEquipProprioPassagDao)
        datasource.addAll(
            listOf(
                MovEquipProprioPassagRoomModel(
                    idMovEquipProprio = 1,
                    matricColab = 19759
                ),
                MovEquipProprioPassagRoomModel(
                    idMovEquipProprio = 1,
                    matricColab = 19035
                ),
                MovEquipProprioPassagRoomModel(
                    idMovEquipProprio = 1,
                    matricColab = 1807
                )
            )
        )
        val result = datasource.list(1)
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!.size,
            3
        )
        val resultDelete = datasource.delete(1)
        assertEquals(
            resultDelete.isSuccess,
            true
        )
        assertEquals(
            resultDelete.getOrNull()!!,
            true
        )
        val resultList = datasource.list(1)
        assertEquals(
            resultList.getOrNull()!!.size,
            0
        )
    }

}