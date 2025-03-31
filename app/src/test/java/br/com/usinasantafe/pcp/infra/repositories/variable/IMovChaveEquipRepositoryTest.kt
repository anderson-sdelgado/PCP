package br.com.usinasantafe.pcp.infra.repositories.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovChaveEquip
import br.com.usinasantafe.pcp.infra.datasource.retrofit.variable.MovChaveEquipRetrofitDatasource
import br.com.usinasantafe.pcp.infra.datasource.room.variable.MovChaveEquipRoomDatasource
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.MovChaveEquipSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.MovChaveEquipRetrofitModelInput
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.entityToRetrofitModelOutput
import br.com.usinasantafe.pcp.infra.models.room.variable.MovChaveEquipRoomModel
import br.com.usinasantafe.pcp.infra.models.room.variable.entityToRoomModel
import br.com.usinasantafe.pcp.infra.models.room.variable.roomModelToEntity
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.MovChaveEquipSharedPreferencesModel
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

class IMovChaveEquipRepositoryTest {

    private val movChaveEquipRoomDatasource = mock<MovChaveEquipRoomDatasource>()
    private val movChaveEquipSharedPreferencesDatasource = mock<MovChaveEquipSharedPreferencesDatasource>()
    private val movChaveEquipRetrofitDatasource = mock<MovChaveEquipRetrofitDatasource>()
    private val repository = IMovChaveEquipRepository(
        movChaveEquipRoomDatasource,
        movChaveEquipSharedPreferencesDatasource,
        movChaveEquipRetrofitDatasource
    )

    @Test
    fun `listInside - Check return failure if have error in MovChaveEquipRoomDatasource listInside`() =
        runTest {
            whenever(
                movChaveEquipRoomDatasource.listInside()
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
                "IMovChaveEquipRepository.listInside -> Unknown Error"
            )
        }

    @Test
    fun `listInside - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                movChaveEquipRoomDatasource.listInside()
            ).thenReturn(
                Result.success(
                    listOf(
                        MovChaveEquipRoomModel(
                            idMovChaveEquip = 1,
                            matricVigiaMovChaveEquip = 19035,
                            idLocalMovChaveEquip = 1,
                            dthrMovChaveEquip = Date().time,
                            tipoMovChaveEquip = TypeMovKey.REMOVE,
                            idEquipMovChaveEquip = 1,
                            matricColabMovChaveEquip = 19759,
                            observMovChaveEquip = "OBSERV",
                            statusMovChaveEquip = StatusData.OPEN,
                            statusSendMovChaveEquip = StatusSend.SEND,
                            statusForeignerMovChaveEquip = StatusForeigner.INSIDE,
                            uuidMainMovChaveEquip = "UUID"
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
                entity.idMovChaveEquip,
                1
            )
            assertEquals(
                entity.matricVigiaMovChaveEquip,
                19035
            )
            assertEquals(
                entity.idLocalMovChaveEquip,
                1
            )
        }

    @Test
    fun `setIdEquip - Check return failure if have error in MovChaveEquipSharePreferenceDatasource setIdEquip - ADD`() =
        runTest {
            whenever(
                movChaveEquipSharedPreferencesDatasource.setIdEquip(1)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.setIdEquip(
                idEquip = 1,
                flowApp = FlowApp.ADD,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovChaveEquipRepository.setIdEquip -> Unknown Error"
            )
        }

    @Test
    fun `setIdEquip - Check return correct if function execute successfully - ADD`() =
        runTest {
            whenever(
                movChaveEquipSharedPreferencesDatasource.setIdEquip(1)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setIdEquip(
                idEquip = 1,
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
    fun `setMatricColab - Check return failure if have error in MovChaveEquipSharePreferenceDatasource setMatricColab - ADD`() =
        runTest {
            whenever(
                movChaveEquipSharedPreferencesDatasource.setMatricColab(19759)
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
                "IMovChaveEquipRepository.setMatricColab -> Unknown Error"
            )
        }

    @Test
    fun `setMatricColab - Check return correct if function execute successfully - ADD`() =
        runTest {
            whenever(
                movChaveEquipSharedPreferencesDatasource.setMatricColab(19759)
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
                movChaveEquipSharedPreferencesDatasource.setObserv("OBSERV")
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
                "IMovChaveEquipRepository.setObserv -> Unknown Error"
            )
        }

    @Test
    fun `setObserv - Check return correct if function execute successfully - ADD`() =
        runTest {
            whenever(
                movChaveEquipSharedPreferencesDatasource.setObserv("OBSERV")
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
    fun `save - Check return failure if have error in MovChaveEquipSharedPreferencesDatasource get`() =
        runTest {
            whenever(
                movChaveEquipSharedPreferencesDatasource.get()
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
                "IMovChaveEquipRepository.save -> Unknown Error"
            )
        }

    @Test
    fun `save - Check return failure if have error in MovChaveEquipRoomDatasource save`() =
        runTest {
            val sharedPreferencesModel = MovChaveEquipSharedPreferencesModel(
                dthrMovChaveEquip = Date(),
                tipoMovChaveEquip = TypeMovKey.RECEIPT,
                idEquipMovChaveEquip = 1,
                matricColabMovChaveEquip = 19759,
                observMovChaveEquip = "OBSERV",
            )
            whenever(
                movChaveEquipSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(sharedPreferencesModel)
            )
            whenever(
                movChaveEquipRoomDatasource.save(
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
                "IMovChaveEquipRepository.save -> Unknown Error"
            )
        }

    @Test
    fun `save - Check return failure if MovChaveEquipRoomDatasource save execute successfully but return zero`() =
        runTest {
            val sharedPreferencesModel = MovChaveEquipSharedPreferencesModel(
                dthrMovChaveEquip = Date(),
                tipoMovChaveEquip = TypeMovKey.RECEIPT,
                idEquipMovChaveEquip = 1,
                matricColabMovChaveEquip = 19759,
                observMovChaveEquip = "OBSERV",
            )
            whenever(
                movChaveEquipSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(sharedPreferencesModel)
            )
            whenever(
                movChaveEquipRoomDatasource.save(
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
                "IMovChaveEquipRepository.save"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause!!.message,
                "Id is 0"
            )
        }

    @Test
    fun `save - Check return failure if have error in MovChaveEquipSharedPreferencesDatasource clean`() =
        runTest {
            val sharedPreferencesModel = MovChaveEquipSharedPreferencesModel(
                dthrMovChaveEquip = Date(),
                tipoMovChaveEquip = TypeMovKey.RECEIPT,
                idEquipMovChaveEquip = 1,
                matricColabMovChaveEquip = 19759,
                observMovChaveEquip = "OBSERV",
            )
            whenever(
                movChaveEquipSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(sharedPreferencesModel)
            )
            whenever(
                movChaveEquipRoomDatasource.save(
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
                movChaveEquipSharedPreferencesDatasource.clean()
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
                "IMovChaveEquipRepository.save -> Unknown Error"
            )
        }

    @Test
    fun `save - Check return correct if function execute successfully`() =
        runTest {
            val sharedPreferencesModel = MovChaveEquipSharedPreferencesModel(
                dthrMovChaveEquip = Date(),
                tipoMovChaveEquip = TypeMovKey.RECEIPT,
                idEquipMovChaveEquip = 1,
                matricColabMovChaveEquip = 19759,
                observMovChaveEquip = "OBSERV",
            )
            whenever(
                movChaveEquipSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(sharedPreferencesModel)
            )
            whenever(
                movChaveEquipRoomDatasource.save(
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
                movChaveEquipSharedPreferencesDatasource.clean()
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
    fun `get - Check return failure if have error in MovChaveEquipRoomDatasource get`() =
        runTest {
            whenever(
                movChaveEquipRoomDatasource.get(1)
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
                "IMovChaveEquipRepository.get -> Unknown Error"
            )
        }

    @Test
    fun `get - Check return correct if function execute successfully`() =
        runTest {
            val roomModel = MovChaveEquipRoomModel(
                idMovChaveEquip = 1,
                matricVigiaMovChaveEquip = 19035,
                idLocalMovChaveEquip = 1,
                dthrMovChaveEquip = Date().time,
                tipoMovChaveEquip = TypeMovKey.RECEIPT,
                idEquipMovChaveEquip = 1,
                matricColabMovChaveEquip = 19759,
                observMovChaveEquip = "OBSERV",
                statusMovChaveEquip = StatusData.OPEN,
                statusSendMovChaveEquip = StatusSend.SEND,
                statusForeignerMovChaveEquip = StatusForeigner.INSIDE,
                uuidMainMovChaveEquip = "UUID"
            )
            whenever(
                movChaveEquipRoomDatasource.get(1)
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
                entity.idMovChaveEquip,
                1
            )
            assertEquals(
                entity.matricVigiaMovChaveEquip,
                19035
            )
        }

    @Test
    fun `start - Check return failure if have error in MovChaveEquipSharedPreferencesDatasource start`() =
        runTest {
            val entity = MovChaveEquip(
                idMovChaveEquip = 1,
                matricVigiaMovChaveEquip = 19035,
                idLocalMovChaveEquip = 1,
                dthrMovChaveEquip = Date(),
                tipoMovChaveEquip = TypeMovKey.REMOVE,
                idEquipMovChaveEquip = 1,
                matricColabMovChaveEquip = 19759,
                observMovChaveEquip = "OBSERV",
                statusMovChaveEquip = StatusData.OPEN,
                statusSendMovChaveEquip = StatusSend.SEND,
                statusForeignerMovChaveEquip = StatusForeigner.INSIDE
            )
            whenever(
                movChaveEquipSharedPreferencesDatasource.start(
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
                "IMovChaveEquipRepository.start(OUTSIDE) -> Unknown Error"
            )
        }

    @Test
    fun `start - Check return correct if function execute successfully`() =
        runTest {
            val entity = MovChaveEquip(
                idMovChaveEquip = 1,
                matricVigiaMovChaveEquip = 19035,
                idLocalMovChaveEquip = 1,
                dthrMovChaveEquip = Date(),
                tipoMovChaveEquip = TypeMovKey.REMOVE,
                idEquipMovChaveEquip = 1,
                matricColabMovChaveEquip = 19759,
                observMovChaveEquip = "OBSERV",
                statusMovChaveEquip = StatusData.OPEN,
                statusSendMovChaveEquip = StatusSend.SEND,
                statusForeignerMovChaveEquip = StatusForeigner.INSIDE
            )
            whenever(
                movChaveEquipSharedPreferencesDatasource.start(
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
    fun `setOutside - Check return failure if have error in MovChaveEquipRoomDatasource setOutside`() =
        runTest {
            whenever(
                movChaveEquipRoomDatasource.setOutside(1)
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
                "IMovChaveEquipRepository.setOutside -> Unknown Error"
            )
        }

    @Test
    fun `setOutside - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                movChaveEquipRoomDatasource.setOutside(1)
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
    fun `listOpen - Check return failure if have error in MovChaveEquipRoomDatasource listOpen`() =
        runTest {
            whenever(
                movChaveEquipRoomDatasource.listOpen()
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
                "IMovChaveEquipRepository.listOpen -> Unknown Error"
            )
        }

    @Test
    fun `listOpen - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                movChaveEquipRoomDatasource.listOpen()
            ).thenReturn(
                Result.success(
                    listOf(
                        MovChaveEquipRoomModel(
                            idMovChaveEquip = 1,
                            matricVigiaMovChaveEquip = 19035,
                            idLocalMovChaveEquip = 1,
                            dthrMovChaveEquip = Date().time,
                            tipoMovChaveEquip = TypeMovKey.RECEIPT,
                            idEquipMovChaveEquip = 1,
                            matricColabMovChaveEquip = 19759,
                            observMovChaveEquip = "OBSERV",
                            statusMovChaveEquip = StatusData.OPEN,
                            statusSendMovChaveEquip = StatusSend.SEND,
                            statusForeignerMovChaveEquip = StatusForeigner.INSIDE,
                            uuidMainMovChaveEquip = "UUID"
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
                entity.idMovChaveEquip,
                1
            )
            assertEquals(
                entity.matricVigiaMovChaveEquip,
                19035
            )
            assertEquals(
                entity.idLocalMovChaveEquip,
                1
            )
        }

    @Test
    fun `setClose - Check return failure if have error in MovChaveEquipRoomDatasource setClose`() =
        runTest {
            whenever(
                movChaveEquipRoomDatasource.setClose(1)
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
                "IMovChaveEquipRepository.setClose -> Unknown Error"
            )
        }

    @Test
    fun `setClose - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                movChaveEquipRoomDatasource.setClose(1)
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
    fun `setIdEquip - Check return failure if have error in MovChaveEquipRoomDatasource setIdEquip - CHANGE`() =
        runTest {
            whenever(
                movChaveEquipRoomDatasource.setIdEquip(
                    idEquip = 1,
                    id = 1
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.setIdEquip(
                idEquip = 1,
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovChaveEquipRepository.setIdEquip -> Unknown Error"
            )
        }

    @Test
    fun `setIdEquip- Check return correct if function execute successfully - CHANGE`() =
        runTest {
            whenever(
                movChaveEquipRoomDatasource.setIdEquip(
                    idEquip = 1,
                    id = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setIdEquip(
                idEquip = 1,
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
            val roomModel = MovChaveEquipRoomModel(
                idMovChaveEquip = 1,
                matricVigiaMovChaveEquip = 19035,
                idLocalMovChaveEquip = 1,
                dthrMovChaveEquip = Date().time,
                tipoMovChaveEquip = TypeMovKey.RECEIPT,
                idEquipMovChaveEquip = 1,
                matricColabMovChaveEquip = 19759,
                observMovChaveEquip = "OBSERV",
                statusMovChaveEquip = StatusData.OPEN,
                statusSendMovChaveEquip = StatusSend.SEND,
                statusForeignerMovChaveEquip = StatusForeigner.INSIDE,
                uuidMainMovChaveEquip = "UUID"
            )
            whenever(
                movChaveEquipRoomDatasource.get(1)
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
    fun `setMatricColab - Check return failure if have error in MovChaveEquipRoomDatasource setMatricColab - CHANGE`() =
        runTest {
            whenever(
                movChaveEquipRoomDatasource.setMatricColab(
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
                "IMovChaveEquipRepository.setMatricColab -> Unknown Error"
            )
        }

    @Test
    fun `setMatricColab - Check return correct if function execute successfully - CHANGE`() =
        runTest {
            whenever(
                movChaveEquipRoomDatasource.setMatricColab(
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
    fun `setObserv - Check return failure if have error in MovChaveEquipRoomDatasource setObserv - CHANGE`() =
        runTest {
            whenever(
                movChaveEquipRoomDatasource.setObserv("OBSERV", 1)
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
                "IMovChaveEquipRepository.setObserv -> Unknown Error"
            )
        }

    @Test
    fun `setObserv - Check return correct if function execute successfully - CHANGE`() =
        runTest {
            whenever(
                movChaveEquipRoomDatasource.setObserv("OBSERV", 1)
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
            val roomModel = MovChaveEquipRoomModel(
                idMovChaveEquip = 1,
                matricVigiaMovChaveEquip = 19035,
                idLocalMovChaveEquip = 1,
                dthrMovChaveEquip = Date().time,
                tipoMovChaveEquip = TypeMovKey.RECEIPT,
                idEquipMovChaveEquip = 1,
                matricColabMovChaveEquip = 19759,
                observMovChaveEquip = "OBSERV",
                statusMovChaveEquip = StatusData.OPEN,
                statusSendMovChaveEquip = StatusSend.SEND,
                statusForeignerMovChaveEquip = StatusForeigner.INSIDE,
                uuidMainMovChaveEquip = "UUID"
            )
            whenever(
                movChaveEquipRoomDatasource.get(1)
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
    fun `getIdEquip - Check return correct if function execute successfully`() =
        runTest {
            val roomModel = MovChaveEquipRoomModel(
                idMovChaveEquip = 1,
                matricVigiaMovChaveEquip = 19035,
                idLocalMovChaveEquip = 1,
                dthrMovChaveEquip = Date().time,
                tipoMovChaveEquip = TypeMovKey.RECEIPT,
                idEquipMovChaveEquip = 1,
                matricColabMovChaveEquip = 19759,
                observMovChaveEquip = "OBSERV",
                statusMovChaveEquip = StatusData.OPEN,
                statusSendMovChaveEquip = StatusSend.SEND,
                statusForeignerMovChaveEquip = StatusForeigner.INSIDE,
                uuidMainMovChaveEquip = "UUID"
            )
            whenever(
                movChaveEquipRoomDatasource.get(1)
            ).thenReturn(
                Result.success(roomModel)
            )
            val result = repository.getIdEquip(1)
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
    fun `checkSend - Check return failure if have error in MovChaveEquipRoomDatasource checkSend`() =
        runTest {
            whenever(
                movChaveEquipRoomDatasource.checkSend()
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
                "IMovChaveEquipRepository.checkSend -> Unknown Error"
            )
        }

    @Test
    fun `checkSend - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                movChaveEquipRoomDatasource.checkSend()
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
    fun `listSend - Check return failure if have error in MovChaveEquipRoomDatasource listSend`() =
        runTest {
            whenever(
                movChaveEquipRoomDatasource.listSend()
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
                "IMovChaveEquipRepository.listSend -> Unknown Error"
            )
        }

    @Test
    fun `listSend - Check return correct if function execute successfully`() =
        runTest {
            val roomModelList = listOf(
                MovChaveEquipRoomModel(
                    idMovChaveEquip = 1,
                    matricVigiaMovChaveEquip = 19035,
                    idLocalMovChaveEquip = 1,
                    dthrMovChaveEquip = Date().time,
                    tipoMovChaveEquip = TypeMovKey.RECEIPT,
                    idEquipMovChaveEquip = 1,
                    matricColabMovChaveEquip = 19759,
                    observMovChaveEquip = "OBSERV",
                    statusMovChaveEquip = StatusData.OPEN,
                    statusSendMovChaveEquip = StatusSend.SEND,
                    statusForeignerMovChaveEquip = StatusForeigner.INSIDE,
                    uuidMainMovChaveEquip = "UUID"
                )
            )
            whenever(
                movChaveEquipRoomDatasource.listSend()
            ).thenReturn(
                Result.success(
                    roomModelList
                )
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
                entity.idMovChaveEquip,
                1
            )
        }

    @Test
    fun `send - Check return failure if have error in MovChaveRetrofitDatasource send`() =
        runTest {
            val entityList = listOf(
                MovChaveEquip(
                    idMovChaveEquip = 1,
                    matricVigiaMovChaveEquip = 19035,
                    idLocalMovChaveEquip = 1,
                    dthrMovChaveEquip = Date(),
                    tipoMovChaveEquip = TypeMovKey.RECEIPT,
                    idEquipMovChaveEquip = 1,
                    matricColabMovChaveEquip = 19759,
                    observMovChaveEquip = "OBSERV",
                    statusMovChaveEquip = StatusData.OPEN,
                    statusSendMovChaveEquip = StatusSend.SEND,
                    statusForeignerMovChaveEquip = StatusForeigner.INSIDE,
                    uuidMainMovChaveEquip = "UUID"
                )
            )
            val retrofitModelOutputList = entityList.map {
                it.entityToRetrofitModelOutput(16997417840)
            }
            whenever(
                movChaveEquipRetrofitDatasource.send(
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
                "IMovChaveEquipRepository.send -> Unknown Error"
            )
        }

    @Test
    fun `send - Check return correct if function execute successfully`() =
        runTest {
            val entityOutputList = listOf(
                MovChaveEquip(
                    idMovChaveEquip = 1,
                    matricVigiaMovChaveEquip = 19035,
                    idLocalMovChaveEquip = 1,
                    dthrMovChaveEquip = Date(),
                    tipoMovChaveEquip = TypeMovKey.RECEIPT,
                    idEquipMovChaveEquip = 1,
                    matricColabMovChaveEquip = 19759,
                    observMovChaveEquip = "OBSERV",
                    statusMovChaveEquip = StatusData.OPEN,
                    statusSendMovChaveEquip = StatusSend.SEND,
                    statusForeignerMovChaveEquip = StatusForeigner.INSIDE,
                    uuidMainMovChaveEquip = "UUID"
                )
            )
            val retrofitModelInputList = listOf(
                MovChaveEquipRetrofitModelInput(
                    idMovChaveEquip = 1,
                )
            )
            val retrofitModelOutputList = entityOutputList.map {
                it.entityToRetrofitModelOutput(16997417840)
            }
            whenever(
                movChaveEquipRetrofitDatasource.send(
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
                entity.idMovChaveEquip,
                1
            )
        }

    @Test
    fun `setSent - Check return failure if have error in MovChaveEquipRepository setSent`() =
        runTest {
            val entityInputList = listOf(
                MovChaveEquip(
                    idMovChaveEquip = 1,
                )
            )
            whenever(
                movChaveEquipRoomDatasource.setSent(1)
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
                "IMovChaveEquipRepository.setSent -> Unknown Error"
            )
        }

    @Test
    fun `setSent - Check return correct if function execute successfully`() =
        runTest {

            val entityInputList = listOf(
                MovChaveEquip(
                    idMovChaveEquip = 1,
                )
            )
            whenever(
                movChaveEquipRoomDatasource.setSent(1)
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
    fun `checkOpen - Check return failure if have error in MovChaveEquipRoomDatasource checkOpen`() =
        runTest {
            whenever(
                movChaveEquipRoomDatasource.checkOpen()
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
                "IMovChaveEquipRepository.checkOpen -> Unknown Error"
            )
        }

    @Test
    fun `checkOpen - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                movChaveEquipRoomDatasource.checkOpen()
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