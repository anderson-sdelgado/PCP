package br.com.usinasantafe.pcp.external.room.datasource.variable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pcp.external.room.AppDatabaseRoom
import br.com.usinasantafe.pcp.external.room.dao.variable.MovEquipVisitTercPassagDao
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipVisitTercPassagRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class IMovEquipVisitTercPassagRoomDatasourceTest {

    private lateinit var movEquipVisitTercPassagDao: MovEquipVisitTercPassagDao
    private lateinit var db: AppDatabaseRoom

    @Before
    fun before() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabaseRoom::class.java).allowMainThreadQueries().build()
        movEquipVisitTercPassagDao = db.movEquipVisitTercPassagDao()
    }

    @After
    fun after() {
        db.close()
    }

    @Test
    fun `Check success addAll if have row is correct`() = runTest {
        val datasource = IMovEquipVisitTercPassagRoomDatasource(movEquipVisitTercPassagDao)
        val result = datasource.addAll(
            listOf(
                MovEquipVisitTercPassagRoomModel(
                    idMovEquipVisitTerc = 1,
                    idVisitTerc = 10
                ),
                MovEquipVisitTercPassagRoomModel(
                    idMovEquipVisitTerc = 1,
                    idVisitTerc = 20
                ),
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
    fun `Check success list if have MovEquipVisitTercPassagRoomModel list`() = runTest {
        val datasource = IMovEquipVisitTercPassagRoomDatasource(movEquipVisitTercPassagDao)
        datasource.addAll(
            listOf(
                MovEquipVisitTercPassagRoomModel(
                    idMovEquipVisitTerc = 1,
                    idVisitTerc = 10
                ),
                MovEquipVisitTercPassagRoomModel(
                    idMovEquipVisitTerc = 1,
                    idVisitTerc = 20
                ),
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
            model.idMovEquipVisitTercPassag,
            2
        )
        assertEquals(
            model.idVisitTerc,
            20
        )
    }

    @Test
    fun  `Check success add if have row is correct`() = runTest {
        val datasource = IMovEquipVisitTercPassagRoomDatasource(movEquipVisitTercPassagDao)
        val result = datasource.add(10, 1)
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
            model.idMovEquipVisitTercPassag,
            1
        )
        assertEquals(
            model.idVisitTerc,
            10
        )
    }

    @Test
    fun `Check success delete if process execute successfully`() = runTest {
        val datasource = IMovEquipVisitTercPassagRoomDatasource(movEquipVisitTercPassagDao)
        datasource.addAll(
            listOf(
                MovEquipVisitTercPassagRoomModel(
                    idMovEquipVisitTerc = 1,
                    idVisitTerc = 10
                ),
                MovEquipVisitTercPassagRoomModel(
                    idMovEquipVisitTerc = 1,
                    idVisitTerc = 20
                ),
                MovEquipVisitTercPassagRoomModel(
                    idMovEquipVisitTerc = 1,
                    idVisitTerc = 30
                ),
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
            modelBefore.idMovEquipVisitTercPassag,
            2
        )
        assertEquals(
            modelBefore.idVisitTerc,
            20
        )
        val resultDelete = datasource.delete(20, 1)
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
            modelAfter.idMovEquipVisitTercPassag,
            3
        )
        assertEquals(
            modelAfter.idVisitTerc,
            30
        )
    }

    @Test
    fun `Check success delete Mov All if process execute successfully`() = runTest {
        val datasource = IMovEquipVisitTercPassagRoomDatasource(movEquipVisitTercPassagDao)
        datasource.addAll(
            listOf(
                MovEquipVisitTercPassagRoomModel(
                    idMovEquipVisitTerc = 1,
                    idVisitTerc = 10
                ),
                MovEquipVisitTercPassagRoomModel(
                    idMovEquipVisitTerc = 1,
                    idVisitTerc = 20
                ),
                MovEquipVisitTercPassagRoomModel(
                    idMovEquipVisitTerc = 1,
                    idVisitTerc = 30
                ),
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