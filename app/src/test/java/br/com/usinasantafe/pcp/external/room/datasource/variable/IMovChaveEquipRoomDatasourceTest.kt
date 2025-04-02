package br.com.usinasantafe.pcp.external.room.datasource.variable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pcp.external.room.AppDatabaseRoom
import br.com.usinasantafe.pcp.external.room.dao.variable.MovChaveEquipDao
import br.com.usinasantafe.pcp.infra.models.room.variable.MovChaveEquipRoomModel
import br.com.usinasantafe.pcp.infra.models.room.variable.MovChaveRoomModel
import br.com.usinasantafe.pcp.utils.StatusData
import br.com.usinasantafe.pcp.utils.StatusForeigner
import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.utils.TypeMovKey
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.robolectric.RobolectricTestRunner
import java.util.Date

@RunWith(RobolectricTestRunner::class)
class IMovChaveEquipRoomDatasourceTest{

    private lateinit var movChaveEquipDao: MovChaveEquipDao
    private lateinit var db: AppDatabaseRoom

    @Before
    fun before() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        movChaveEquipDao = db.movChaveEquipDao()
    }

    @After
    fun after() {
        db.close()
    }

    @Test
    fun `listInside - Check return failure if have error in MovChaveEquipDao listInside`() =
        runTest {
            val datasource = IMovChaveEquipRoomDatasource(movChaveEquipDao)
            val exception = try {
                datasource.listInside()
                null
            } catch (exception: Exception) {
                exception
            }
            assertEquals(
                exception,
                null
            )
        }

    @Test
    fun `listRemove - Check return correct if function execute successfully`() =
        runTest {
            movChaveEquipDao.insert(
                MovChaveEquipRoomModel(
                    dthrMovChaveEquip = Date().time,
                    tipoMovChaveEquip = TypeMovKey.RECEIPT,
                    matricVigiaMovChaveEquip = 19759,
                    idLocalMovChaveEquip = 1,
                    idEquipMovChaveEquip = 1,
                    matricColabMovChaveEquip = 19035,
                    observMovChaveEquip = "OBSERV",
                    statusMovChaveEquip = StatusData.OPEN,
                    statusSendMovChaveEquip = StatusSend.SEND,
                    statusForeignerMovChaveEquip = StatusForeigner.INSIDE,
                    uuidMainMovChaveEquip = "UUID"
                )
            )
            movChaveEquipDao.insert(
                MovChaveEquipRoomModel(
                    dthrMovChaveEquip = Date().time,
                    tipoMovChaveEquip = TypeMovKey.RECEIPT,
                    matricVigiaMovChaveEquip = 19759,
                    idLocalMovChaveEquip = 2,
                    idEquipMovChaveEquip = 2,
                    matricColabMovChaveEquip = 19035,
                    observMovChaveEquip = "OBSERV",
                    statusMovChaveEquip = StatusData.OPEN,
                    statusSendMovChaveEquip = StatusSend.SEND,
                    statusForeignerMovChaveEquip = StatusForeigner.OUTSIDE,
                    uuidMainMovChaveEquip = "UUID"
                )
            )
            val datasource = IMovChaveEquipRoomDatasource(movChaveEquipDao)
            val result = datasource.listInside()
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                1
            )
            val modelRoom = list[0]
            assertEquals(
                modelRoom.idLocalMovChaveEquip,
                1
            )
        }

    @Test
    fun `save - Check return correct if function execute successfully`() =
        runTest {
            val roomModel = MovChaveEquipRoomModel(
                idMovChaveEquip = 1,
                dthrMovChaveEquip = Date().time,
                tipoMovChaveEquip = TypeMovKey.RECEIPT,
                matricVigiaMovChaveEquip = 19759,
                idLocalMovChaveEquip = 1,
                idEquipMovChaveEquip = 1,
                matricColabMovChaveEquip = 19035,
                observMovChaveEquip = "OBSERV",
                statusMovChaveEquip = StatusData.OPEN,
                statusSendMovChaveEquip = StatusSend.SEND,
                statusForeignerMovChaveEquip = StatusForeigner.INSIDE,
                uuidMainMovChaveEquip = "UUID"
            )
            val modelList = movChaveEquipDao.listStatusForeigner(StatusForeigner.INSIDE)
            assertEquals(
                modelList.size,
                0
            )
            val datasource = IMovChaveEquipRoomDatasource(movChaveEquipDao)
            val result = datasource.save(roomModel)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                1L
            )
            val modelListAfter = movChaveEquipDao.listStatusForeigner(StatusForeigner.INSIDE)
            assertEquals(
                modelListAfter.size,
                1
            )
        }

    @Test
    fun `get - Check return correct if function execute successfully`() =
        runTest {
            val roomModel = MovChaveEquipRoomModel(
                idMovChaveEquip = 1,
                dthrMovChaveEquip = Date().time,
                tipoMovChaveEquip = TypeMovKey.RECEIPT,
                matricVigiaMovChaveEquip = 19759,
                idLocalMovChaveEquip = 1,
                idEquipMovChaveEquip = 1,
                matricColabMovChaveEquip = 19035,
                observMovChaveEquip = "OBSERV",
                statusMovChaveEquip = StatusData.OPEN,
                statusSendMovChaveEquip = StatusSend.SEND,
                statusForeignerMovChaveEquip = StatusForeigner.INSIDE,
                uuidMainMovChaveEquip = "UUID"
            )
            movChaveEquipDao.insert(roomModel)
            val datasource = IMovChaveEquipRoomDatasource(movChaveEquipDao)
            val result = datasource.get(1)
            assertEquals(
                result.isSuccess,
                true
            )
            val model = result.getOrNull()!!
            assertEquals(
                model,
                roomModel
            )
            assertEquals(
                model.idMovChaveEquip,
                1
            )
            assertEquals(
                model.idLocalMovChaveEquip,
                1
            )
        }

    @Test
    fun `setOutside - Check return correct if function execute successfully`() =
        runTest {
            val roomModel = MovChaveEquipRoomModel(
                idMovChaveEquip = 1,
                dthrMovChaveEquip = Date().time,
                tipoMovChaveEquip = TypeMovKey.RECEIPT,
                matricVigiaMovChaveEquip = 19759,
                idLocalMovChaveEquip = 1,
                idEquipMovChaveEquip = 1,
                matricColabMovChaveEquip = 19035,
                observMovChaveEquip = "OBSERV",
                statusMovChaveEquip = StatusData.OPEN,
                statusSendMovChaveEquip = StatusSend.SEND,
                statusForeignerMovChaveEquip = StatusForeigner.INSIDE,
                uuidMainMovChaveEquip = "UUID"
            )
            movChaveEquipDao.insert(roomModel)
            val listOpenBefore = movChaveEquipDao.listStatusData(StatusData.OPEN)
            assertEquals(
                listOpenBefore.size,
                1
            )
            assertEquals(
                listOpenBefore[0].idMovChaveEquip,
                1
            )
            assertEquals(
                listOpenBefore[0].statusForeignerMovChaveEquip,
                StatusForeigner.INSIDE
            )
            val datasource = IMovChaveEquipRoomDatasource(movChaveEquipDao)
            val result = datasource.setOutside(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val model = movChaveEquipDao.get(1)
            assertEquals(
                model.idMovChaveEquip,
                1
            )
            assertEquals(
                model.statusForeignerMovChaveEquip,
                StatusForeigner.OUTSIDE
            )
            val listOpenAfter = movChaveEquipDao.listStatusData(StatusData.OPEN)
            assertEquals(
                listOpenAfter.size,
                1
            )
            assertEquals(
                listOpenAfter[0].idMovChaveEquip,
                1
            )
            assertEquals(
                listOpenAfter[0].statusForeignerMovChaveEquip,
                StatusForeigner.OUTSIDE
            )
        }

    @Test
    fun `listOpen - Check return correct if function execute successfully`() =
        runTest {
            movChaveEquipDao.insert(
                MovChaveEquipRoomModel(
                    dthrMovChaveEquip = Date().time,
                    tipoMovChaveEquip = TypeMovKey.RECEIPT,
                    matricVigiaMovChaveEquip = 19759,
                    idLocalMovChaveEquip = 1,
                    idEquipMovChaveEquip = 1,
                    matricColabMovChaveEquip = 19035,
                    observMovChaveEquip = "OBSERV",
                    statusMovChaveEquip = StatusData.OPEN,
                    statusSendMovChaveEquip = StatusSend.SEND,
                    statusForeignerMovChaveEquip = StatusForeigner.INSIDE,
                    uuidMainMovChaveEquip = "UUID"
                )
            )
            movChaveEquipDao.insert(
                MovChaveEquipRoomModel(
                    dthrMovChaveEquip = Date().time,
                    tipoMovChaveEquip = TypeMovKey.RECEIPT,
                    matricVigiaMovChaveEquip = 19759,
                    idLocalMovChaveEquip = 1,
                    idEquipMovChaveEquip = 1,
                    matricColabMovChaveEquip = 19035,
                    observMovChaveEquip = "OBSERV",
                    statusMovChaveEquip = StatusData.CLOSE,
                    statusSendMovChaveEquip = StatusSend.SEND,
                    statusForeignerMovChaveEquip = StatusForeigner.INSIDE,
                    uuidMainMovChaveEquip = "UUID"
                )
            )
            val datasource = IMovChaveEquipRoomDatasource(movChaveEquipDao)
            val result = datasource.listOpen()
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                1
            )
            val modelRoom = list[0]
            assertEquals(
                modelRoom.idLocalMovChaveEquip,
                1
            )
        }

    @Test
    fun `setClose - Check return correct if function execute successfully`() =
        runTest {
            val roomModel = MovChaveEquipRoomModel(
                idMovChaveEquip = 1,
                dthrMovChaveEquip = Date().time,
                tipoMovChaveEquip = TypeMovKey.RECEIPT,
                matricVigiaMovChaveEquip = 19759,
                idLocalMovChaveEquip = 1,
                idEquipMovChaveEquip = 1,
                matricColabMovChaveEquip = 19035,
                observMovChaveEquip = "OBSERV",
                statusMovChaveEquip = StatusData.OPEN,
                statusSendMovChaveEquip = StatusSend.SEND,
                statusForeignerMovChaveEquip = StatusForeigner.INSIDE,
                uuidMainMovChaveEquip = "UUID"
            )
            movChaveEquipDao.insert(roomModel)
            val modelBefore = movChaveEquipDao.get(1)
            assertEquals(
                modelBefore.idMovChaveEquip,
                1
            )
            assertEquals(
                modelBefore.statusForeignerMovChaveEquip,
                StatusForeigner.INSIDE
            )
            assertEquals(
                modelBefore.statusMovChaveEquip,
                StatusData.OPEN
            )
            val datasource = IMovChaveEquipRoomDatasource(movChaveEquipDao)
            val result = datasource.setClose(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val modelAfter = movChaveEquipDao.get(1)
            assertEquals(
                modelAfter.idMovChaveEquip,
                1
            )
            assertEquals(
                modelAfter.statusForeignerMovChaveEquip,
                StatusForeigner.INSIDE
            )
            assertEquals(
                modelAfter.statusMovChaveEquip,
                StatusData.CLOSE
            )
        }

    @Test
    fun `setIdEquip - Check return correct if function execute successfully`() =
        runTest {
            val roomModel = MovChaveEquipRoomModel(
                idMovChaveEquip = 1,
                dthrMovChaveEquip = Date().time,
                tipoMovChaveEquip = TypeMovKey.RECEIPT,
                matricVigiaMovChaveEquip = 19759,
                idLocalMovChaveEquip = 1,
                idEquipMovChaveEquip = 1,
                matricColabMovChaveEquip = 19035,
                observMovChaveEquip = "OBSERV",
                statusMovChaveEquip = StatusData.OPEN,
                statusSendMovChaveEquip = StatusSend.SEND,
                statusForeignerMovChaveEquip = StatusForeigner.INSIDE,
                uuidMainMovChaveEquip = "UUID"
            )
            movChaveEquipDao.insert(roomModel)
            val modelBefore = movChaveEquipDao.get(1)
            assertEquals(
                modelBefore.idEquipMovChaveEquip,
                1
            )
            val datasource = IMovChaveEquipRoomDatasource(movChaveEquipDao)
            val result = datasource.setIdEquip(2, 1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val modelAfter = movChaveEquipDao.get(1)
            assertEquals(
                modelAfter.idEquipMovChaveEquip,
                2
            )
        }

    @Test
    fun `setMatricColab - Check return correct if function execute successfully`() =
        runTest {
            val roomModel = MovChaveEquipRoomModel(
                idMovChaveEquip = 1,
                dthrMovChaveEquip = Date().time,
                tipoMovChaveEquip = TypeMovKey.RECEIPT,
                matricVigiaMovChaveEquip = 19759,
                idLocalMovChaveEquip = 1,
                idEquipMovChaveEquip = 1,
                matricColabMovChaveEquip = 19035,
                observMovChaveEquip = "OBSERV",
                statusMovChaveEquip = StatusData.OPEN,
                statusSendMovChaveEquip = StatusSend.SEND,
                statusForeignerMovChaveEquip = StatusForeigner.INSIDE,
                uuidMainMovChaveEquip = "UUID"
            )
            movChaveEquipDao.insert(roomModel)
            val modelBefore = movChaveEquipDao.get(1)
            assertEquals(
                modelBefore.matricColabMovChaveEquip,
                19035
            )
            val datasource = IMovChaveEquipRoomDatasource(movChaveEquipDao)
            val result = datasource.setMatricColab(18017, 1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val modelAfter = movChaveEquipDao.get(1)
            assertEquals(
                modelAfter.matricColabMovChaveEquip,
                18017
            )
        }

    @Test
    fun `setObserv - Check return correct if function execute successfully`() =
        runTest {
            val roomModel = MovChaveEquipRoomModel(
                idMovChaveEquip = 1,
                dthrMovChaveEquip = Date().time,
                tipoMovChaveEquip = TypeMovKey.RECEIPT,
                matricVigiaMovChaveEquip = 19759,
                idLocalMovChaveEquip = 1,
                idEquipMovChaveEquip = 1,
                matricColabMovChaveEquip = 19035,
                observMovChaveEquip = "OBSERV",
                statusMovChaveEquip = StatusData.OPEN,
                statusSendMovChaveEquip = StatusSend.SEND,
                statusForeignerMovChaveEquip = StatusForeigner.INSIDE,
                uuidMainMovChaveEquip = "UUID"
            )
            movChaveEquipDao.insert(roomModel)
            val modelBefore = movChaveEquipDao.get(1)
            assertEquals(
                modelBefore.observMovChaveEquip,
                "OBSERV"
            )
            val datasource = IMovChaveEquipRoomDatasource(movChaveEquipDao)
            val result = datasource.setObserv("OBSERV ALTERADO", 1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val modelAfter = movChaveEquipDao.get(1)
            assertEquals(
                modelAfter.observMovChaveEquip,
                "OBSERV ALTERADO"
            )
        }

    @Test
    fun `checkSend - Check return false if not have mov to send`() =
        runTest {
            movChaveEquipDao.insert(
                MovChaveEquipRoomModel(
                    idMovChaveEquip = 1,
                    dthrMovChaveEquip = Date().time,
                    tipoMovChaveEquip = TypeMovKey.RECEIPT,
                    matricVigiaMovChaveEquip = 19759,
                    idLocalMovChaveEquip = 1,
                    idEquipMovChaveEquip = 1,
                    matricColabMovChaveEquip = 19035,
                    observMovChaveEquip = "OBSERV",
                    statusMovChaveEquip = StatusData.OPEN,
                    statusSendMovChaveEquip = StatusSend.SENT,
                    statusForeignerMovChaveEquip = StatusForeigner.INSIDE,
                    uuidMainMovChaveEquip = "UUID"
                )
            )
            val datasource = IMovChaveEquipRoomDatasource(movChaveEquipDao)
            val result = datasource.checkSend()
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
    fun `checkSend - Check return true if have mov to send`() =
        runTest {
            movChaveEquipDao.insert(
                MovChaveEquipRoomModel(
                    idMovChaveEquip = 1,
                    dthrMovChaveEquip = Date().time,
                    tipoMovChaveEquip = TypeMovKey.RECEIPT,
                    matricVigiaMovChaveEquip = 19759,
                    idLocalMovChaveEquip = 1,
                    idEquipMovChaveEquip = 1,
                    matricColabMovChaveEquip = 19035,
                    observMovChaveEquip = "OBSERV",
                    statusMovChaveEquip = StatusData.OPEN,
                    statusSendMovChaveEquip = StatusSend.SEND,
                    statusForeignerMovChaveEquip = StatusForeigner.INSIDE,
                    uuidMainMovChaveEquip = "UUID"
                )
            )
            val datasource = IMovChaveEquipRoomDatasource(movChaveEquipDao)
            val result = datasource.checkSend()
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
    fun `listSend - Check return empty list if not have mov to send`() =
        runTest {
            movChaveEquipDao.insert(
                MovChaveEquipRoomModel(
                    idMovChaveEquip = 1,
                    dthrMovChaveEquip = Date().time,
                    tipoMovChaveEquip = TypeMovKey.RECEIPT,
                    matricVigiaMovChaveEquip = 19759,
                    idLocalMovChaveEquip = 1,
                    idEquipMovChaveEquip = 1,
                    matricColabMovChaveEquip = 19035,
                    observMovChaveEquip = "OBSERV",
                    statusMovChaveEquip = StatusData.OPEN,
                    statusSendMovChaveEquip = StatusSend.SENT,
                    statusForeignerMovChaveEquip = StatusForeigner.INSIDE,
                    uuidMainMovChaveEquip = "UUID"
                )
            )
            val datasource = IMovChaveEquipRoomDatasource(movChaveEquipDao)
            val result = datasource.listSend()
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                0
            )
        }

    @Test
    fun `listSend - Check return list if have mov to send`() =
        runTest {
            movChaveEquipDao.insert(
                MovChaveEquipRoomModel(
                    idMovChaveEquip = 1,
                    dthrMovChaveEquip = Date().time,
                    tipoMovChaveEquip = TypeMovKey.RECEIPT,
                    matricVigiaMovChaveEquip = 19759,
                    idLocalMovChaveEquip = 1,
                    idEquipMovChaveEquip = 1,
                    matricColabMovChaveEquip = 19035,
                    observMovChaveEquip = "OBSERV",
                    statusMovChaveEquip = StatusData.OPEN,
                    statusSendMovChaveEquip = StatusSend.SEND,
                    statusForeignerMovChaveEquip = StatusForeigner.INSIDE,
                    uuidMainMovChaveEquip = "UUID"
                )
            )
            val datasource = IMovChaveEquipRoomDatasource(movChaveEquipDao)
            val result = datasource.listSend()
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                1
            )
            val modelRoom = list[0]
            assertEquals(
                modelRoom.idMovChaveEquip,
                1
            )
        }

    @Test
    fun `setSent - Check return correct if function execute successfully`() =
        runTest {
            val roomModel = MovChaveEquipRoomModel(
                idMovChaveEquip = 1,
                dthrMovChaveEquip = Date().time,
                tipoMovChaveEquip = TypeMovKey.RECEIPT,
                matricVigiaMovChaveEquip = 19759,
                idLocalMovChaveEquip = 1,
                idEquipMovChaveEquip = 1,
                matricColabMovChaveEquip = 19035,
                observMovChaveEquip = "OBSERV",
                statusMovChaveEquip = StatusData.OPEN,
                statusSendMovChaveEquip = StatusSend.SEND,
                statusForeignerMovChaveEquip = StatusForeigner.INSIDE,
                uuidMainMovChaveEquip = "UUID"
            )
            movChaveEquipDao.insert(roomModel)
            val modelBefore = movChaveEquipDao.get(1)
            assertEquals(
                modelBefore.statusSendMovChaveEquip,
                StatusSend.SEND
            )
            val datasource = IMovChaveEquipRoomDatasource(movChaveEquipDao)
            val result = datasource.setSent(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val modelAfter = movChaveEquipDao.get(1)
            assertEquals(
                modelAfter.statusSendMovChaveEquip,
                StatusSend.SENT
            )
        }

    @Test
    fun `checkOpen - Check return false if not have mov`() =
        runTest {
            movChaveEquipDao.insert(
                MovChaveEquipRoomModel(
                    idMovChaveEquip = 1,
                    dthrMovChaveEquip = Date().time,
                    tipoMovChaveEquip = TypeMovKey.RECEIPT,
                    matricVigiaMovChaveEquip = 19759,
                    idLocalMovChaveEquip = 1,
                    idEquipMovChaveEquip = 1,
                    matricColabMovChaveEquip = 19035,
                    observMovChaveEquip = "OBSERV",
                            statusMovChaveEquip = StatusData.CLOSE,
                    statusSendMovChaveEquip = StatusSend.SENT,
                    statusForeignerMovChaveEquip = StatusForeigner.INSIDE,
                    uuidMainMovChaveEquip = "UUID"
                )
            )
            val datasource = IMovChaveEquipRoomDatasource(movChaveEquipDao)
            val result = datasource.checkOpen()
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
    fun `checkOpen - Check return true if have mov`() =
        runTest {
            movChaveEquipDao.insert(
                MovChaveEquipRoomModel(
                    idMovChaveEquip = 1,
                    dthrMovChaveEquip = Date().time,
                    tipoMovChaveEquip = TypeMovKey.RECEIPT,
                    matricVigiaMovChaveEquip = 19759,
                    idLocalMovChaveEquip = 1,
                    idEquipMovChaveEquip = 1,
                    matricColabMovChaveEquip = 19035,
                    observMovChaveEquip = "OBSERV",
                    statusMovChaveEquip = StatusData.OPEN,
                    statusSendMovChaveEquip = StatusSend.SEND,
                    statusForeignerMovChaveEquip = StatusForeigner.INSIDE,
                    uuidMainMovChaveEquip = "UUID"
                )
            )
            val datasource = IMovChaveEquipRoomDatasource(movChaveEquipDao)
            val result = datasource.checkSend()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }

}