package br.com.usinasantafe.pcp.external.room.datasource.stable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pcp.external.room.AppDatabaseRoom
import br.com.usinasantafe.pcp.external.room.dao.stable.FluxoDao
import br.com.usinasantafe.pcp.infra.models.room.stable.FluxoRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class IFluxoRoomDatasourceTest {

    private lateinit var fluxoDao: FluxoDao
    private lateinit var db: AppDatabaseRoom

    @Before
    fun before() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabaseRoom::class.java).allowMainThreadQueries().build()
        fluxoDao = db.fluxoDao()
    }

    @After
    fun after() {
        db.close()
    }

    @Test
    fun `AddAll - Check return failure if have row repeated`() =
        runTest {
            val datasource = IFluxoRoomDatasource(fluxoDao)
            val result = datasource.addAll(
                listOf(
                    FluxoRoomModel(
                        idFluxo = 1,
                        descrFluxo = "Mov. Equip. Próprio"
                    ),

                    FluxoRoomModel(
                        idFluxo = 1,
                        descrFluxo = "Mov. Equip. Próprio"
                    ),
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IFluxoRoomDatasource.addAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "android.database.sqlite.SQLiteConstraintException: Cannot execute for last inserted row ID"
            )
        }

    @Test
    fun `addAll - Check return true and check data inserted`() =
        runTest {
            val datasource = IFluxoRoomDatasource(fluxoDao)
            val result = datasource.addAll(
                listOf(
                    FluxoRoomModel(
                        idFluxo = 1,
                        descrFluxo = "Mov. Equip. Próprio"
                    ),

                    FluxoRoomModel(
                        idFluxo = 2,
                        descrFluxo = "Mov. Equip. Visitante/Terceiro"
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
            val list = fluxoDao.listAll()
            assertEquals(
                list.size,
                2
            )
            val roomModel = list[1]
            assertEquals(
                roomModel.idFluxo,
                2
            )
            assertEquals(
                roomModel.descrFluxo,
                "Mov. Equip. Visitante/Terceiro"
            )
        }

    @Test
    fun `deleteAll - Check return true and check data deleted`() =
        runTest {
            val datasource = IFluxoRoomDatasource(fluxoDao)
            val resultAdd = datasource.addAll(
                listOf(
                    FluxoRoomModel(
                        idFluxo = 1,
                        descrFluxo = "Mov. Equip. Próprio"
                    ),

                    FluxoRoomModel(
                        idFluxo = 2,
                        descrFluxo = "Mov. Equip. Visitante/Terceiro"
                    ),
                )
            )
            assertEquals(
                resultAdd.isSuccess,
                true
            )
            val listBefore = fluxoDao.listAll()
            assertEquals(
                listBefore.size,
                2
            )
            val resultDelete = datasource.deleteAll()
            assertEquals(
                resultDelete.isSuccess,
                true
            )
            val listAfter = fluxoDao.listAll()
            assertEquals(
                listAfter.size,
                0
            )
        }

    @Test
    fun `get - Check return entity if FluxoRoomDatasourceImpl get execute successfully`() =
        runTest {
            val datasource = IFluxoRoomDatasource(fluxoDao)
            val resultAdd = datasource.addAll(
                listOf(
                    FluxoRoomModel(
                        idFluxo = 1,
                        descrFluxo = "Mov. Equip. Próprio"
                    ),

                    FluxoRoomModel(
                        idFluxo = 2,
                        descrFluxo = "Mov. Equip. Visitante/Terceiro"
                    ),
                )
            )
            assertEquals(
                resultAdd.isSuccess,
                true
            )
            val result = datasource.get(1)
            val roomModel = result.getOrNull()!!
            assertEquals(
                roomModel.idFluxo,
                1
            )
            assertEquals(
                roomModel.descrFluxo,
                "Mov. Equip. Próprio"
            )
        }

}