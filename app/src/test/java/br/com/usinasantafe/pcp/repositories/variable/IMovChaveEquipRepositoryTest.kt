package br.com.usinasantafe.pcp.infra.repositories.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovChave
import br.com.usinasantafe.pcp.domain.entities.variable.MovChaveEquip
import br.com.usinasantafe.pcp.domain.errors.DatasourceException
import br.com.usinasantafe.pcp.infra.datasource.retrofit.variable.MovChaveEquipRetrofitDatasource
import br.com.usinasantafe.pcp.infra.datasource.room.variable.MovChaveEquipRoomDatasource
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.MovChaveEquipSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.MovChaveEquipRetrofitModelInput
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.MovChaveRetrofitModelInput
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
                    DatasourceException(
                        function = "IMovChaveEquipRepository.listInside",
                        cause = Exception()
                    )
                )
            )
            val result = repository.listInside()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> IMovChaveEquipRepository.listInside"
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
                    DatasourceException(
                        function = "IMovChaveEquipRepository.setIdEquip",
                        cause = Exception()
                    )
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
                "Failure Datasource -> IMovChaveEquipRepository.setIdEquip"
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
                    DatasourceException(
                        function = "MovChaveEquipSharePreferenceDatasource.setMatricColab",
                        cause = Exception()
                    )
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
                "Failure Datasource -> MovChaveEquipSharePreferenceDatasource.setMatricColab"
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
                    DatasourceException(
                        function = "MovChaveEquipSharePreferenceDatasource.setObserv",
                        cause = Exception()
                    )
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
                "Failure Datasource -> MovChaveEquipSharePreferenceDatasource.setObserv"
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
                    DatasourceException(
                        function = "MovChaveEquipSharePreferenceDatasource.get",
                        cause = Exception()
                    )
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
                "Failure Datasource -> MovChaveEquipSharePreferenceDatasource.get"
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
                    DatasourceException(
                        function = "MovChaveEquipRoomDatasource.save",
                        cause = Exception()
                    )
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
                "Failure Datasource -> MovChaveEquipRoomDatasource.save"
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
                "Failure Repository -> IMovChaveEquipRepository.save"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause!!.message,
                "Id is 0"
            )
        }

    @Test
    fun `save - Check return failure if have error in MovChaveEquipSharedPreferencesDatasource clear`() =
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
                movChaveEquipSharedPreferencesDatasource.clear()
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovChaveEquipSharedPreferenceDatasource.clear",
                        cause = Exception()
                    )
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
                "Failure Datasource -> MovChaveEquipSharedPreferenceDatasource.clear"
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
                movChaveEquipSharedPreferencesDatasource.clear()
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
                    DatasourceException(
                        function = "MovChaveEquipRoomDatasource.get",
                        cause = Exception()
                    )
                )
            )
            val result = repository.get(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovChaveEquipRoomDatasource.get"
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
                    DatasourceException(
                        function = "MovChaveEquipSharePreferenceDatasource.start",
                        cause = Exception()
                    )
                )
            )
            val result = repository.start(entity)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovChaveEquipSharePreferenceDatasource.start"
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
                    DatasourceException(
                        function = "MovChaveEquipRoomDatasource.setOutside",
                        cause = Exception()
                    )
                )
            )
            val result = repository.setOutside(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovChaveEquipRoomDatasource.setOutside"
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
                    DatasourceException(
                        function = "IMovChaveEquipRepository.listOpen",
                        cause = Exception()
                    )
                )
            )
            val result = repository.listOpen()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> IMovChaveEquipRepository.listOpen"
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
                    DatasourceException(
                        function = "MovChaveEquipRoomDatasource.setClose",
                        cause = Exception()
                    )
                )
            )
            val result = repository.setClose(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovChaveEquipRoomDatasource.setClose"
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
                    DatasourceException(
                        function = "MovChaveEquipRoomDatasource.setIdEquip",
                        cause = Exception()
                    )
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
                "Failure Datasource -> MovChaveEquipRoomDatasource.setIdEquip"
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
                    DatasourceException(
                        function = "MovChaveEquipRoomDatasource.setMatricColab",
                        cause = Exception()
                    )
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
                "Failure Datasource -> MovChaveEquipRoomDatasource.setMatricColab"
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
                    DatasourceException(
                        function = "MovChaveEquipRoomDatasource.setObserv",
                        cause = Exception()
                    )
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
                "Failure Datasource -> MovChaveEquipRoomDatasource.setObserv"
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
                    DatasourceException(
                        function = "MovChaveEquipRoomDatasource.checkSend",
                        cause = Exception()
                    )
                )
            )
            val result = repository.checkSend()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovChaveEquipRoomDatasource.checkSend"
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
                    DatasourceException(
                        function = "MovChaveEquipRoomDatasource.listSend",
                        cause = Exception()
                    )
                )
            )
            val result = repository.listSend()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovChaveEquipRoomDatasource.listSend"
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
            val retrofitModelOutputList = entityList.map { it.entityToRetrofitModelOutput(16997417840) }
            whenever(
                movChaveEquipRetrofitDatasource.send(
                    list = retrofitModelOutputList,
                    token = "TOKEN"
                )
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovChaveEquipRetrofitDatasource.send",
                        cause = Exception()
                    )
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
                "Failure Datasource -> MovChaveEquipRetrofitDatasource.send"
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
            val retrofitModelOutputList = entityOutputList.map { it.entityToRetrofitModelOutput(16997417840) }
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
                    DatasourceException(
                        function = "MovChaveEquipRoomDatasource.setSent",
                        cause = Exception()
                    )
                )
            )
            val result = repository.setSent(entityInputList)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovChaveEquipRoomDatasource.setSent"
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
                    DatasourceException(
                        function = "MovChaveEquipRoomDatasource.checkOpen",
                        cause = Exception()
                    )
                )
            )
            val result = repository.checkOpen()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovChaveEquipRoomDatasource.checkOpen"
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