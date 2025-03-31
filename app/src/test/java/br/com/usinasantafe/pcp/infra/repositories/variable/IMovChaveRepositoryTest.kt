package br.com.usinasantafe.pcp.infra.repositories.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovChave
import br.com.usinasantafe.pcp.infra.datasource.retrofit.variable.MovChaveRetrofitDatasource
import br.com.usinasantafe.pcp.infra.datasource.room.variable.MovChaveRoomDatasource
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.MovChaveSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.MovChaveRetrofitModelInput
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.entityToRetrofitModelOutput
import br.com.usinasantafe.pcp.infra.models.room.variable.MovChaveRoomModel
import br.com.usinasantafe.pcp.infra.models.room.variable.entityToRoomModel
import br.com.usinasantafe.pcp.infra.models.room.variable.roomModelToEntity
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.MovChaveSharedPreferencesModel
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.entityToSharedPreferencesModel
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.StatusData
import br.com.usinasantafe.pcp.utils.StatusForeigner
import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.utils.TypeMovKey
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.Date
import kotlin.test.assertEquals

class IMovChaveRepositoryTest {

    private val movChaveRoomDatasource = mock<MovChaveRoomDatasource>()
    private val movChaveSharedPreferencesDatasource = mock<MovChaveSharedPreferencesDatasource>()
    private val movChaveRetrofitDatasource = mock<MovChaveRetrofitDatasource>()
    private val repository = IMovChaveRepository(
        movChaveRoomDatasource = movChaveRoomDatasource,
        movChaveSharedPreferencesDatasource = movChaveSharedPreferencesDatasource,
        movChaveRetrofitDatasource = movChaveRetrofitDatasource
    )

    @Test
    fun `listInside - Check return failure if have error in MovChaveRoomDatasource listInside`() =
        runTest {
            whenever(
                movChaveRoomDatasource.listInside()
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.listInside()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovChaveRepository.listInside -> Unknown Error"
            )
        }

    @Test
    fun `listInside - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                movChaveRoomDatasource.listInside()
            ).thenReturn(
                Result.success(
                    listOf(
                        MovChaveRoomModel(
                            idMovChave = 1,
                            matricVigiaMovChave = 19035,
                            idLocalMovChave = 1,
                            dthrMovChave = Date().time,
                            tipoMovChave = TypeMovKey.REMOVE,
                            idChaveMovChave = 1,
                            matricColabMovChave = 19759,
                            observMovChave = "OBSERV",
                            statusMovChave = StatusData.OPEN,
                            statusSendMovChave = StatusSend.SEND,
                            statusForeignerMovChave = StatusForeigner.INSIDE,
                            uuidMainMovChave = "UUID"
                        )
                    )
                )
            )
            val result = repository.listInside()
            assertEquals(
                result.isSuccess,
                true
            )
            val entityList = result.getOrNull()!!
            assertEquals(
                entityList.size,
                1
            )
            val entity = entityList[0]
            assertEquals(
                entity.idMovChave,
                1
            )
            assertEquals(
                entity.matricVigiaMovChave,
                19035
            )
            assertEquals(
                entity.idLocalMovChave,
                1
            )
        }

    @Test
    fun `setIdChave - Check return failure if have error in MovChaveSharePreferenceDatasource setIdChave - ADD`() =
        runTest {
            whenever(
                movChaveSharedPreferencesDatasource.setIdChave(1)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.setIdChave(
                idChave = 1,
                flowApp = FlowApp.ADD,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovChaveRepository.setIdChave -> Unknown Error"
            )
        }

    @Test
    fun `setIdChave - Check return correct if function execute successfully - ADD`() =
        runTest {
            whenever(
                movChaveSharedPreferencesDatasource.setIdChave(1)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setIdChave(
                idChave = 1,
                flowApp = FlowApp.ADD,
                id = 1
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
    fun `setMatricColab - Check return failure if have error in MovChaveSharePreferenceDatasource setMatricColab - ADD`() =
        runTest {
            whenever(
                movChaveSharedPreferencesDatasource.setMatricColab(19759)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.setMatricColab(
                matricColab = 19759,
                flowApp = FlowApp.ADD,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovChaveRepository.setMatricColab -> Unknown Error"
            )
        }

    @Test
    fun `setMatricColab - Check return correct if function execute successfully - ADD`() =
        runTest {
            whenever(
                movChaveSharedPreferencesDatasource.setMatricColab(19759)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setMatricColab(
                matricColab = 19759,
                flowApp = FlowApp.ADD,
                id = 1
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
    fun `setObserv - Check return failure if have error in MovChaveSharePreferenceDatasource setObserv - ADD`() =
        runTest {
            whenever(
                movChaveSharedPreferencesDatasource.setObserv("OBSERV")
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.setObserv(
                observ = "OBSERV",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovChaveRepository.setObserv -> Unknown Error"
            )
        }

    @Test
    fun `setObserv - Check return correct if function execute successfully - ADD`() =
        runTest {
            whenever(
                movChaveSharedPreferencesDatasource.setObserv("OBSERV")
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setObserv(
                observ = "OBSERV",
                flowApp = FlowApp.ADD,
                id = 0
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
    fun `save - Check return failure if have error in MovChaveSharedPreferencesDatasource get`() =
        runTest {
            whenever(
                movChaveSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.save(
                matricVigia = 19035,
                idLocal = 1,
                uuid = "UUID"
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovChaveRepository.save -> Unknown Error"
            )
        }

    @Test
    fun `save - Check return failure if have error in MovChaveRoomDatasource save`() =
        runTest {
            val sharedPreferencesModel = MovChaveSharedPreferencesModel(
                dthrMovChave = Date(),
                tipoMovChave = TypeMovKey.REMOVE,
                idChaveMovChave = 1,
                matricColabMovChave = 19759,
                observMovChave = "OBSERV",
            )
            whenever(
                movChaveSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(sharedPreferencesModel)
            )
            whenever(
                movChaveRoomDatasource.save(
                    sharedPreferencesModel
                        .entityToSharedPreferencesModel()
                        .entityToRoomModel(
                            matricVigia = 19035,
                            idLocal = 1,
                            uuid = "UUID"
                        )
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.save(
                matricVigia = 19035,
                idLocal = 1,
                uuid = "UUID"
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovChaveRepository.save -> Unknown Error"
            )
        }

    @Test
    fun `save - Check return failure if MovChaveRoomDatasource save execute successfully but return zero`() =
        runTest {
            val sharedPreferencesModel = MovChaveSharedPreferencesModel(
                dthrMovChave = Date(),
                tipoMovChave = TypeMovKey.REMOVE,
                idChaveMovChave = 1,
                matricColabMovChave = 19759,
                observMovChave = "OBSERV",
            )
            whenever(
                movChaveSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(sharedPreferencesModel)
            )
            whenever(
                movChaveRoomDatasource.save(
                    sharedPreferencesModel
                        .entityToSharedPreferencesModel()
                        .entityToRoomModel(
                            matricVigia = 19035,
                            idLocal = 1,
                            uuid = "UUID"
                        )
                )
            ).thenReturn(
                Result.success(0)
            )
            val result = repository.save(
                matricVigia = 19035,
                idLocal = 1,
                uuid = "UUID"
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovChaveRepository.save"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause!!.message,
                "Id is 0"
            )
        }

    @Test
    fun `save - Check return failure if have error in MovChaveSharedPreferencesDatasource clean`() =
        runTest {
            val sharedPreferencesModel = MovChaveSharedPreferencesModel(
                dthrMovChave = Date(),
                tipoMovChave = TypeMovKey.REMOVE,
                idChaveMovChave = 1,
                matricColabMovChave = 19759,
                observMovChave = "OBSERV",
            )
            whenever(
                movChaveSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(sharedPreferencesModel)
            )
            whenever(
                movChaveRoomDatasource.save(
                    sharedPreferencesModel
                        .entityToSharedPreferencesModel()
                        .entityToRoomModel(
                            matricVigia = 19035,
                            idLocal = 1,
                            uuid = "UUID"
                        )
                )
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                movChaveSharedPreferencesDatasource.clean()
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.save(
                matricVigia = 19035,
                idLocal = 1,
                uuid = "UUID"
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovChaveRepository.save -> Unknown Error"
            )
        }

    @Test
    fun `save - Check return correct if function execute successfully`() =
        runTest {
            val sharedPreferencesModel = MovChaveSharedPreferencesModel(
                dthrMovChave = Date(),
                tipoMovChave = TypeMovKey.REMOVE,
                idChaveMovChave = 1,
                matricColabMovChave = 19759,
                observMovChave = "OBSERV",
            )
            whenever(
                movChaveSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(sharedPreferencesModel)
            )
            whenever(
                movChaveRoomDatasource.save(
                    sharedPreferencesModel
                        .entityToSharedPreferencesModel()
                        .entityToRoomModel(
                            matricVigia = 19035,
                            idLocal = 1,
                            uuid = "UUID"
                        )
                )
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                movChaveSharedPreferencesDatasource.clean()
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.save(
                matricVigia = 19035,
                idLocal = 1,
                uuid = "UUID"
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                1
            )
        }

    @Test
    fun `get - Check return failure if have error in MovChaveRoomDatasource get`() =
        runTest {
            whenever(
                movChaveRoomDatasource.get(1)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.get(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovChaveRepository.get -> Unknown Error"
            )
        }

    @Test
    fun `get - Check return correct if function execute successfully`() =
        runTest {
            val roomModel = MovChaveRoomModel(
                idMovChave = 1,
                matricVigiaMovChave = 19035,
                idLocalMovChave = 1,
                dthrMovChave = Date().time,
                tipoMovChave = TypeMovKey.REMOVE,
                idChaveMovChave = 1,
                matricColabMovChave = 19759,
                observMovChave = "OBSERV",
                statusMovChave = StatusData.OPEN,
                statusSendMovChave = StatusSend.SEND,
                statusForeignerMovChave = StatusForeigner.INSIDE,
                uuidMainMovChave = "UUID"
            )
            whenever(
                movChaveRoomDatasource.get(1)
            ).thenReturn(
                Result.success(roomModel)
            )
            val result = repository.get(1)
            assertEquals(
                result.isSuccess,
                true
            )
            val entity = result.getOrNull()!!
            assertEquals(
                entity,
                roomModel.roomModelToEntity()
            )
            assertEquals(
                entity.idMovChave,
                1
            )
            assertEquals(
                entity.matricVigiaMovChave,
                19035
            )
        }

    @Test
    fun `start - Check return failure if have error in MovChaveSharedPreferencesDatasource start`() =
        runTest {
            val entity = MovChave(
                idMovChave = 1,
                matricVigiaMovChave = 19035,
                idLocalMovChave = 1,
                dthrMovChave = Date(),
                tipoMovChave = TypeMovKey.RECEIPT,
                idChaveMovChave = 1,
                matricColabMovChave = 19759,
                observMovChave = "OBSERV",
                statusMovChave = StatusData.OPEN,
                statusSendMovChave = StatusSend.SEND,
                statusForeignerMovChave = StatusForeigner.INSIDE
            )
            whenever(
                movChaveSharedPreferencesDatasource.start(
                    entity.entityToSharedPreferencesModel()
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.start(entity)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovChaveRepository.start(OUTSIDE) -> Unknown Error"
            )
        }

    @Test
    fun `start - Check return correct if function execute successfully`() =
        runTest {
            val entity = MovChave(
                idMovChave = 1,
                matricVigiaMovChave = 19035,
                idLocalMovChave = 1,
                dthrMovChave = Date(),
                tipoMovChave = TypeMovKey.RECEIPT,
                idChaveMovChave = 1,
                matricColabMovChave = 19759,
                observMovChave = "OBSERV",
                statusMovChave = StatusData.OPEN,
                statusSendMovChave = StatusSend.SEND,
                statusForeignerMovChave = StatusForeigner.INSIDE
            )
            whenever(
                movChaveSharedPreferencesDatasource.start(
                    entity.entityToSharedPreferencesModel()
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.start(entity)
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
    fun `setOutside - Check return failure if have error in MovChaveRoomDatasource setOutside`() =
        runTest {
            whenever(
                movChaveRoomDatasource.setOutside(1)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.setOutside(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovChaveRepository.setOutside -> Unknown Error"
            )
        }

    @Test
    fun `setOutside - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                movChaveRoomDatasource.setOutside(1)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setOutside(1)
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
    fun `listOpen - Check return failure if have error in MovChaveRoomDatasource listOpen`() =
        runTest {
            whenever(
                movChaveRoomDatasource.listOpen()
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.listOpen()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovChaveRepository.listOpen -> Unknown Error"
            )
        }

    @Test
    fun `listOpen - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                movChaveRoomDatasource.listOpen()
            ).thenReturn(
                Result.success(
                    listOf(
                        MovChaveRoomModel(
                            idMovChave = 1,
                            matricVigiaMovChave = 19035,
                            idLocalMovChave = 1,
                            dthrMovChave = Date().time,
                            tipoMovChave = TypeMovKey.REMOVE,
                            idChaveMovChave = 1,
                            matricColabMovChave = 19759,
                            observMovChave = "OBSERV",
                            statusMovChave = StatusData.OPEN,
                            statusSendMovChave = StatusSend.SEND,
                            statusForeignerMovChave = StatusForeigner.INSIDE,
                            uuidMainMovChave = "UUID"
                        )
                    )
                )
            )
            val result = repository.listOpen()
            assertEquals(
                result.isSuccess,
                true
            )
            val entityList = result.getOrNull()!!
            assertEquals(
                entityList.size,
                1
            )
            val entity = entityList[0]
            assertEquals(
                entity.idMovChave,
                1
            )
            assertEquals(
                entity.matricVigiaMovChave,
                19035
            )
            assertEquals(
                entity.idLocalMovChave,
                1
            )
        }

    @Test
    fun `setClose - Check return failure if have error in MovChaveRoomDatasource setClose`() =
        runTest {
            whenever(
                movChaveRoomDatasource.setClose(1)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.setClose(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovChaveRepository.setClose -> Unknown Error"
            )
        }

    @Test
    fun `setClose - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                movChaveRoomDatasource.setClose(1)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setClose(1)
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
    fun `setIdChave - Check return failure if have error in MovChaveRoomDatasource setIdChave - CHANGE`() =
        runTest {
            whenever(
                movChaveRoomDatasource.setIdChave(
                    idChave = 1,
                    id = 1
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.setIdChave(
                idChave = 1,
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovChaveRepository.setIdChave -> Unknown Error"
            )
        }

    @Test
    fun `setIdChave - Check return correct if function execute successfully - CHANGE`() =
        runTest {
            whenever(
                movChaveRoomDatasource.setIdChave(
                    idChave = 1,
                    id = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setIdChave(
                idChave = 1,
                flowApp = FlowApp.CHANGE,
                id = 1
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
    fun `getMatricColab - Check return correct if function execute successfully`() =
        runTest {
            val roomModel = MovChaveRoomModel(
                idMovChave = 1,
                matricVigiaMovChave = 19035,
                idLocalMovChave = 1,
                dthrMovChave = Date().time,
                tipoMovChave = TypeMovKey.REMOVE,
                idChaveMovChave = 1,
                matricColabMovChave = 19759,
                observMovChave = "OBSERV",
                statusMovChave = StatusData.OPEN,
                statusSendMovChave = StatusSend.SEND,
                statusForeignerMovChave = StatusForeigner.INSIDE,
                uuidMainMovChave = "UUID"
            )
            whenever(
                movChaveRoomDatasource.get(1)
            ).thenReturn(
                Result.success(roomModel)
            )
            val result = repository.getMatricColab(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                19759
            )
        }

    @Test
    fun `setMatricColab - Check return failure if have error in MovChaveRoomDatasource setMatricColab - CHANGE`() =
        runTest {
            whenever(
                movChaveRoomDatasource.setMatricColab(
                    matric = 19759,
                    id = 1
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.setMatricColab(
                matricColab = 19759,
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovChaveRepository.setMatricColab -> Unknown Error"
            )
        }

    @Test
    fun `setMatricColab - Check return correct if function execute successfully - CHANGE`() =
        runTest {
            whenever(
                movChaveRoomDatasource.setMatricColab(
                    matric = 19759,
                    id = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setMatricColab(
                matricColab = 19759,
                flowApp = FlowApp.CHANGE,
                id = 1
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
    fun `setObserv - Check return failure if have error in MovChaveRoomDatasource setObserv - CHANGE`() =
        runTest {
            whenever(
                movChaveRoomDatasource.setObserv("OBSERV", 1)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.setObserv(
                observ = "OBSERV",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovChaveRepository.setObserv -> Unknown Error"
            )
        }

    @Test
    fun `setObserv - Check return correct if function execute successfully - CHANGE`() =
        runTest {
            whenever(
                movChaveRoomDatasource.setObserv("OBSERV", 1)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setObserv(
                observ = "OBSERV",
                flowApp = FlowApp.CHANGE,
                id = 1
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
    fun `getObserv - Check return correct if function execute successfully`() =
        runTest {
            val roomModel = MovChaveRoomModel(
                idMovChave = 1,
                matricVigiaMovChave = 19035,
                idLocalMovChave = 1,
                dthrMovChave = Date().time,
                tipoMovChave = TypeMovKey.REMOVE,
                idChaveMovChave = 1,
                matricColabMovChave = 19759,
                observMovChave = "OBSERV",
                statusMovChave = StatusData.OPEN,
                statusSendMovChave = StatusSend.SEND,
                statusForeignerMovChave = StatusForeigner.INSIDE,
                uuidMainMovChave = "UUID"
            )
            whenever(
                movChaveRoomDatasource.get(1)
            ).thenReturn(
                Result.success(roomModel)
            )
            val result = repository.getObserv(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                "OBSERV"
            )
        }

    @Test
    fun `checkSend - Check return failure if have error in MovChaveRoomDatasource checkSend`() =
        runTest {
            whenever(
                movChaveRoomDatasource.checkSend()
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.checkSend()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovChaveRepository.checkSend -> Unknown Error"
            )
        }

    @Test
    fun `checkSend - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                movChaveRoomDatasource.checkSend()
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.checkSend()
            assertEquals(
                result.getOrNull()!!,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }

    @Test
    fun `listSend - Check return failure if have error in MovChaveRoomDatasource listSend`() =
        runTest {
            whenever(
                movChaveRoomDatasource.listSend()
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.listSend()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovChaveRepository.listSend -> Unknown Error"
            )
        }

    @Test
    fun `listSend - Check return correct if function execute successfully`() =
        runTest {
            val list = listOf(
                MovChaveRoomModel(
                    idMovChave = 1,
                    matricVigiaMovChave = 19759,
                    dthrMovChave = 1723213270250,
                    tipoMovChave = TypeMovKey.REMOVE,
                    idLocalMovChave = 1,
                    idChaveMovChave = 1,
                    matricColabMovChave = 19035,
                    statusMovChave = StatusData.OPEN,
                    statusSendMovChave = StatusSend.SEND,
                    statusForeignerMovChave = StatusForeigner.INSIDE,
                    observMovChave = "Teste",
                    uuidMainMovChave = "UUID"
                )
            )
            whenever(
                movChaveRoomDatasource.listSend()
            ).thenReturn(
                Result.success(list)
            )
            val result = repository.listSend()
            assertEquals(
                result.isSuccess,
                true
            )
            val entityList = result.getOrNull()!!
            assertEquals(
                entityList.size,
                1
            )
            val entity = entityList[0]
            assertEquals(
                entity.idMovChave,
                1
            )
        }

    @Test
    fun `send - Check return failure if have error in MovChaveRetrofitDatasource send`() =
        runTest {
            val entityList = listOf(
                MovChave(
                    uuidMainMovChave = "UUID",
                    idMovChave = 1,
                    matricVigiaMovChave = 19759,
                    dthrMovChave = Date(),
                    tipoMovChave = TypeMovKey.REMOVE,
                    idLocalMovChave = 1,
                    idChaveMovChave = 1,
                    matricColabMovChave = 19035,
                    statusMovChave = StatusData.OPEN,
                    statusSendMovChave = StatusSend.SEND,
                    statusForeignerMovChave = StatusForeigner.INSIDE,
                    observMovChave = "Teste"
                )
            )
            val retrofitModelOutputList = entityList.map {
                it.entityToRetrofitModelOutput(16997417840)
            }
            whenever(
                movChaveRetrofitDatasource.send(
                    list = retrofitModelOutputList,
                    token = "TOKEN"
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.send(
                list = entityList,
                number = 16997417840,
                token = "TOKEN"
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovChaveRepository.send -> Unknown Error"
            )
        }

    @Test
    fun `send - Check return correct if function execute successfully`() =
        runTest {
            val entityOutputList = listOf(
                MovChave(
                    uuidMainMovChave = "UUID",
                    idMovChave = 1,
                    matricVigiaMovChave = 19759,
                    dthrMovChave = Date(),
                    tipoMovChave = TypeMovKey.REMOVE,
                    idLocalMovChave = 1,
                    idChaveMovChave = 1,
                    matricColabMovChave = 19035,
                    statusMovChave = StatusData.OPEN,
                    statusSendMovChave = StatusSend.SEND,
                    statusForeignerMovChave = StatusForeigner.INSIDE,
                    observMovChave = "Teste"
                )
            )
            val retrofitModelInputList = listOf(
                MovChaveRetrofitModelInput(
                    idMovChave = 1,
                )
            )
            val retrofitModelOutputList = entityOutputList.map {
                it.entityToRetrofitModelOutput(16997417840)
            }
            whenever(
                movChaveRetrofitDatasource.send(
                    list = retrofitModelOutputList,
                    token = "TOKEN"
                )
            ).thenReturn(
                Result.success(
                    retrofitModelInputList
                )
            )
            val result = repository.send(
                list = entityOutputList,
                number = 16997417840,
                token = "TOKEN"
            )
            assertEquals(
                result.isSuccess,
                true
            )
            val entityList = result.getOrNull()!!
            assertEquals(
                entityList.size,
                1
            )
            val entity = entityList[0]
            assertEquals(
                entity.idMovChave,
                1
            )
        }

    @Test
    fun `setSent - Check return failure if have error in MovChaveRepository setSent`() =
        runTest {
            val entityInputList = listOf(
                MovChave(
                    idMovChave = 1,
                )
            )
            whenever(
                movChaveRoomDatasource.setSent(1)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.setSent(entityInputList)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovChaveRepository.setSent -> Unknown Error"
            )
        }

    @Test
    fun `setSent - Check return correct if function execute successfully`() =
        runTest {
            val entityInputList = listOf(
                MovChave(
                    idMovChave = 1,
                )
            )
            whenever(
                movChaveRoomDatasource.setSent(1)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setSent(entityInputList)
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
    fun `checkOpen - Check return failure if have error in MovChaveRoomDatasource checkOpen`() =
        runTest {
            whenever(
                movChaveRoomDatasource.checkOpen()
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.checkOpen()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovChaveRepository.checkOpen -> Unknown Error"
            )
        }

    @Test
    fun `checkOpen - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                movChaveRoomDatasource.checkOpen()
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.checkOpen()
            assertEquals(
                result.getOrNull()!!,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }

}