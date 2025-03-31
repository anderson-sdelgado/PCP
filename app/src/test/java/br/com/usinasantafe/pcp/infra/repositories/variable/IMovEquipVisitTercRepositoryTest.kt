package br.com.usinasantafe.pcp.infra.repositories.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipVisitTerc
import br.com.usinasantafe.pcp.infra.datasource.retrofit.variable.MovEquipVisitTercRetrofitDatasource
import br.com.usinasantafe.pcp.infra.datasource.room.variable.MovEquipVisitTercRoomDatasource
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.MovEquipVisitTercSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.MovEquipVisitTercRetrofitModelInput
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.MovEquipVisitTercRetrofitModelOutput
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipVisitTercRoomModel
import br.com.usinasantafe.pcp.infra.models.room.variable.entityToRoomModel
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.MovEquipVisitTercSharedPreferencesModel
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.entityToSharedPreferencesModel
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.StatusData
import br.com.usinasantafe.pcp.utils.StatusForeigner
import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.utils.TypeMovEquip
import br.com.usinasantafe.pcp.utils.TypeVisitTerc
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class IMovEquipVisitTercRepositoryTest {

    private val movEquipVisitTercSharedPreferencesDatasource =
        mock<MovEquipVisitTercSharedPreferencesDatasource>()
    private val movEquipVisitTercRoomDatasource =
        mock<MovEquipVisitTercRoomDatasource>()
    private val movEquipVisitTercRetrofitDatasource =
        mock<MovEquipVisitTercRetrofitDatasource>()
    private val repository = IMovEquipVisitTercRepository(
        movEquipVisitTercSharedPreferencesDatasource,
        movEquipVisitTercRoomDatasource,
        movEquipVisitTercRetrofitDatasource
    )

    @Test
    fun `Check return failure Datasource in MovEquipVisitTercRoomDatasource listOpen`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.listOpen()
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
                "IMovEquipVisitTercRepository.listOpen -> Unknown Error"
            )
        }

    @Test
    fun `Check return list if have success in MovEquipVisitTercRoomDatasource listOpen`() =
        runTest {
            val movEquipVisitTercRoomModel = MovEquipVisitTercRoomModel(
                idMovEquipVisitTerc = 1,
                nroMatricVigiaMovEquipVisitTerc = 19759,
                idLocalMovEquipVisitTerc = 1,
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                dthrMovEquipVisitTerc = 1723213270250,
                veiculoMovEquipVisitTerc = "TESTE",
                placaMovEquipVisitTerc = "TESTE",
                destinoMovEquipVisitTerc = "TESTE",
                observMovEquipVisitTerc = "TESTE",
                statusMovEquipVisitTerc = StatusData.OPEN,
                statusSendMovEquipVisitTerc = StatusSend.SEND,
                statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
            )
            val movEquipVisitTerc = MovEquipVisitTerc(
                idMovEquipVisitTerc = 1,
                nroMatricVigiaMovEquipVisitTerc = 19759,
                idLocalMovEquipVisitTerc = 1,
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                dthrMovEquipVisitTerc = Date(1723213270250),
                veiculoMovEquipVisitTerc = "TESTE",
                placaMovEquipVisitTerc = "TESTE",
                destinoMovEquipVisitTerc = "TESTE",
                observMovEquipVisitTerc = "TESTE",
                statusMovEquipVisitTerc = StatusData.OPEN,
                statusSendMovEquipVisitTerc = StatusSend.SEND,
                statusMovEquipForeignerVisitTerc = StatusForeigner.INSIDE,
            )
            whenever(
                movEquipVisitTercRoomDatasource.listOpen()
            ).thenReturn(
                Result.success(
                    listOf(
                        movEquipVisitTercRoomModel
                    )
                )
            )
            val result = repository.listOpen()
            assertEquals(
                result.isSuccess,
                true
            )
            val resultList = result.getOrNull()!!
            assertEquals(
                resultList[0],
                movEquipVisitTerc
            )
        }

    @Test
    fun `Check return failure Datasource in MovEquipVisitTercRoomDatasource setClose`() = runTest {
        whenever(
            movEquipVisitTercRoomDatasource.setClose(1)
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
            "IMovEquipVisitTercRepository.setClose -> Unknown Error"
        )
    }

    @Test
    fun `Check return true if have success in MovEquipVisitTercRoomDatasource setClose`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.setClose(1)
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
    fun `Get - Check return failure if have error in MovEquipVisitTercRoomDatasource Get`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.get(1)
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
                "IMovEquipVisitTercRepository.get -> Unknown Error"
            )
        }

    @Test
    fun `Get - Check return entity if Get execute successfully`() = runTest {
        val movEquipVisitTercRoomModel = MovEquipVisitTercRoomModel(
            idMovEquipVisitTerc = 1,
            nroMatricVigiaMovEquipVisitTerc = 19759,
            idLocalMovEquipVisitTerc = 1,
            tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
            idVisitTercMovEquipVisitTerc = 1000,
            tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
            dthrMovEquipVisitTerc = 1723213270250,
            veiculoMovEquipVisitTerc = "TESTE",
            placaMovEquipVisitTerc = "TESTE",
            destinoMovEquipVisitTerc = "TESTE",
            observMovEquipVisitTerc = "TESTE",
            statusMovEquipVisitTerc = StatusData.OPEN,
            statusSendMovEquipVisitTerc = StatusSend.SEND,
            statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
        )
        val movEquipVisitTerc = MovEquipVisitTerc(
            idMovEquipVisitTerc = 1,
            nroMatricVigiaMovEquipVisitTerc = 19759,
            idLocalMovEquipVisitTerc = 1,
            tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
            idVisitTercMovEquipVisitTerc = 1000,
            tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
            dthrMovEquipVisitTerc = Date(1723213270250),
            veiculoMovEquipVisitTerc = "TESTE",
            placaMovEquipVisitTerc = "TESTE",
            destinoMovEquipVisitTerc = "TESTE",
            observMovEquipVisitTerc = "TESTE",
            statusMovEquipVisitTerc = StatusData.OPEN,
            statusSendMovEquipVisitTerc = StatusSend.SEND,
            statusMovEquipForeignerVisitTerc = StatusForeigner.INSIDE,
        )
        whenever(
            movEquipVisitTercRoomDatasource.get(1)
        ).thenReturn(
            Result.success(
                movEquipVisitTercRoomModel
            )
        )
        val result = repository.get(1)
        assertEquals(
            result.isSuccess,
            true
        )
        val resultEntity = result.getOrNull()!!
        assertEquals(
            resultEntity,
            movEquipVisitTerc
        )
    }

    @Test
    fun `GetDestino - Check return failure if have error in MovEquipVisitTercRoomDatasource Get`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.get(1)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.getDestino(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipVisitTercRepository.getDestino -> Unknown Error"
            )
        }

    @Test
    fun `GetDestino - Check return true if MovEquipVisitTercRoomDatasource Get execute successfully`() =
        runTest {
            val movEquipVisitTercRoomModel = MovEquipVisitTercRoomModel(
                idMovEquipVisitTerc = 1,
                nroMatricVigiaMovEquipVisitTerc = 19759,
                idLocalMovEquipVisitTerc = 1,
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                dthrMovEquipVisitTerc = 1723213270250,
                veiculoMovEquipVisitTerc = "TESTE VEICULO",
                placaMovEquipVisitTerc = "TESTE PLACA",
                destinoMovEquipVisitTerc = "TESTE DESTINO",
                observMovEquipVisitTerc = "TESTE OBSERV",
                statusMovEquipVisitTerc = StatusData.OPEN,
                statusSendMovEquipVisitTerc = StatusSend.SEND,
                statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
            )
            whenever(
                movEquipVisitTercRoomDatasource.get(1)
            ).thenReturn(
                Result.success(movEquipVisitTercRoomModel)
            )
            val result = repository.getDestino(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                "TESTE DESTINO"
            )
        }

    @Test
    fun `GetIdVisitTerc - Check return failure if have error in MovEquipVisitTercRoomDatasource Get`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.get(1)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.getIdVisitTerc(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipVisitTercRepository.getIdVisitTerc -> Unknown Error"
            )
        }

    @Test
    fun `GetIdVisitTerc - Check return true if MovEquipVisitTercRoomDatasource Get execute successfully`() =
        runTest {
            val movEquipVisitTercRoomModel = MovEquipVisitTercRoomModel(
                idMovEquipVisitTerc = 1,
                nroMatricVigiaMovEquipVisitTerc = 19759,
                idLocalMovEquipVisitTerc = 1,
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                dthrMovEquipVisitTerc = 1723213270250,
                veiculoMovEquipVisitTerc = "TESTE VEICULO",
                placaMovEquipVisitTerc = "TESTE PLACA",
                destinoMovEquipVisitTerc = "TESTE DESTINO",
                observMovEquipVisitTerc = "TESTE OBSERV",
                statusMovEquipVisitTerc = StatusData.OPEN,
                statusSendMovEquipVisitTerc = StatusSend.SEND,
                statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
            )
            whenever(
                movEquipVisitTercRoomDatasource.get(1)
            ).thenReturn(
                Result.success(movEquipVisitTercRoomModel)
            )
            val result = repository.getIdVisitTerc(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                1000
            )
        }

    @Test
    fun `GetObserv - Check return failure if have error in MovEquipVisitTercRoomDatasource Get`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.get(1)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.getObserv(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipVisitTercRepository.getObserv -> Unknown Error"
            )
        }

    @Test
    fun `GetObserv - Check return true if MovEquipVisitTercRoomDatasource Get execute successfully`() =
        runTest {
            val movEquipVisitTercRoomModel = MovEquipVisitTercRoomModel(
                idMovEquipVisitTerc = 1,
                nroMatricVigiaMovEquipVisitTerc = 19759,
                idLocalMovEquipVisitTerc = 1,
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                dthrMovEquipVisitTerc = 1723213270250,
                veiculoMovEquipVisitTerc = "TESTE VEICULO",
                placaMovEquipVisitTerc = "TESTE PLACA",
                destinoMovEquipVisitTerc = "TESTE DESTINO",
                observMovEquipVisitTerc = "TESTE OBSERV",
                statusMovEquipVisitTerc = StatusData.OPEN,
                statusSendMovEquipVisitTerc = StatusSend.SEND,
                statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
            )
            whenever(
                movEquipVisitTercRoomDatasource.get(1)
            ).thenReturn(
                Result.success(movEquipVisitTercRoomModel)
            )
            val result = repository.getObserv(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull(),
                "TESTE OBSERV"
            )
        }

    @Test
    fun `GetPlaca - Check return failure if have error in MovEquipVisitTercRoomDatasource Get`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.get(1)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.getPlaca(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipVisitTercRepository.getPlaca -> Unknown Error"
            )
        }

    @Test
    fun `GetPlaca - Check return true if MovEquipVisitTercRoomDatasource Get execute successfully`() =
        runTest {
            val movEquipVisitTercRoomModel = MovEquipVisitTercRoomModel(
                idMovEquipVisitTerc = 1,
                nroMatricVigiaMovEquipVisitTerc = 19759,
                idLocalMovEquipVisitTerc = 1,
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                dthrMovEquipVisitTerc = 1723213270250,
                veiculoMovEquipVisitTerc = "TESTE VEICULO",
                placaMovEquipVisitTerc = "TESTE PLACA",
                destinoMovEquipVisitTerc = "TESTE DESTINO",
                observMovEquipVisitTerc = "TESTE OBSERV",
                statusMovEquipVisitTerc = StatusData.OPEN,
                statusSendMovEquipVisitTerc = StatusSend.SEND,
                statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
            )
            whenever(
                movEquipVisitTercRoomDatasource.get(1)
            ).thenReturn(
                Result.success(movEquipVisitTercRoomModel)
            )
            val result = repository.getPlaca(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                "TESTE PLACA"
            )
        }

    @Test
    fun `GetTypeVisitTerc - ADD - Check return failure if have error in MovEquipVisitTercSharedPreferencesDatasource Get`() =
        runTest {
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.getTypeVisitTerc(
                FlowApp.ADD,
                1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipVisitTercRepository.getTypeVisitTerc -> Unknown Error"
            )
        }

    @Test
    fun `GetTypeVisitTerc - ADD - Check return type if MovEquipVisitTercSharedPreferencesDatasource Get execute successfully`() =
        runTest {
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(
                    MovEquipVisitTercSharedPreferencesModel(
                        tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO
                    )
                )
            )
            val result = repository.getTypeVisitTerc(
                FlowApp.ADD,
                1
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                TypeVisitTerc.TERCEIRO
            )
        }

    @Test
    fun `GetTypeVisitTerc - CHANGE - Check return failure if have error in MovEquipVisitTercSharedPreferencesDatasource Get`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.get(1)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.getTypeVisitTerc(
                FlowApp.CHANGE,
                1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipVisitTercRepository.getTypeVisitTerc -> Unknown Error"
            )
        }

    @Test
    fun `GetTypeVisitTerc - CHANGE - Check return true if MovEquipVisitTercSharedPreferencesDatasource Get execute successfully`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.get(1)
            ).thenReturn(
                Result.success(
                    MovEquipVisitTercRoomModel(
                        idMovEquipVisitTerc = 1,
                        nroMatricVigiaMovEquipVisitTerc = 19759,
                        idLocalMovEquipVisitTerc = 1,
                        tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                        idVisitTercMovEquipVisitTerc = 1000,
                        tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.VISITANTE,
                        dthrMovEquipVisitTerc = 1723213270250,
                        veiculoMovEquipVisitTerc = "TESTE",
                        placaMovEquipVisitTerc = "TESTE",
                        destinoMovEquipVisitTerc = "TESTE",
                        observMovEquipVisitTerc = "TESTE",
                        statusMovEquipVisitTerc = StatusData.OPEN,
                        statusSendMovEquipVisitTerc = StatusSend.SEND,
                        statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
                    )
                )
            )
            val result = repository.getTypeVisitTerc(
                FlowApp.CHANGE,
                1
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                TypeVisitTerc.VISITANTE
            )
        }

    @Test
    fun `GetVeiculo - Check return failure if have error in MovEquipVisitTercRoomDatasource Get`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.get(1)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.getVeiculo(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipVisitTercRepository.getVeiculo -> Unknown Error"
            )
        }

    @Test
    fun `GetVeiculo - Check return true if MovEquipVisitTercRoomDatasource Get execute successfully`() =
        runTest {
            val movEquipVisitTercRoomModel = MovEquipVisitTercRoomModel(
                idMovEquipVisitTerc = 1,
                nroMatricVigiaMovEquipVisitTerc = 19759,
                idLocalMovEquipVisitTerc = 1,
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                dthrMovEquipVisitTerc = 1723213270250,
                veiculoMovEquipVisitTerc = "TESTE VEICULO",
                placaMovEquipVisitTerc = "TESTE PLACA",
                destinoMovEquipVisitTerc = "TESTE DESTINO",
                observMovEquipVisitTerc = "TESTE OBSERV",
                statusMovEquipVisitTerc = StatusData.OPEN,
                statusSendMovEquipVisitTerc = StatusSend.SEND,
                statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
            )
            whenever(
                movEquipVisitTercRoomDatasource.get(1)
            ).thenReturn(
                Result.success(movEquipVisitTercRoomModel)
            )
            val result = repository.getVeiculo(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                "TESTE VEICULO"
            )
        }

    @Test
    fun `Check failure failure if have error in MovEquipVisitTercRoomDatasource listInputOpen`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.listInside()
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
                "IMovEquipVisitTercRepository.listInside -> Unknown Error"
            )
        }

    @Test
    fun `Check return list empty if listOpenInput execute successfully and list is empty`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.listInside()
            ).thenReturn(
                Result.success(listOf())
            )
            val result = repository.listInside()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!.isEmpty(),
                true
            )
        }

    @Test
    fun `Check return list if listOpenInput execute successfully`() =
        runTest {
            val movEquipVisitTercRoomModel = MovEquipVisitTercRoomModel(
                idMovEquipVisitTerc = 1,
                nroMatricVigiaMovEquipVisitTerc = 19759,
                idLocalMovEquipVisitTerc = 1,
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                dthrMovEquipVisitTerc = 1723213270250,
                veiculoMovEquipVisitTerc = "VEICULO TESTE",
                placaMovEquipVisitTerc = "PLACA TESTE",
                destinoMovEquipVisitTerc = "DESTINO TESTE",
                observMovEquipVisitTerc = "OBSERV TESTE",
                statusMovEquipVisitTerc = StatusData.OPEN,
                statusSendMovEquipVisitTerc = StatusSend.SEND,
                statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
            )
            whenever(
                movEquipVisitTercRoomDatasource.listInside()
            ).thenReturn(
                Result.success(
                    listOf(movEquipVisitTercRoomModel)
                )
            )
            val result = repository.listInside()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!.isNotEmpty(),
                true
            )
            assertEquals(
                result.getOrNull()!!.size,
                1
            )
            val model = result.getOrNull()!![0]
            assertEquals(
                model.idMovEquipVisitTerc,
                1
            )
            assertEquals(
                model.veiculoMovEquipVisitTerc,
                "VEICULO TESTE"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercSharedPreferencesDatasource save`() =
        runTest {
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.save(
                matricVigia = 19759,
                idLocal = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipVisitTercRepository.save -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRoomDatasource Save`() =
        runTest {
            val modelSharedPreferences = MovEquipVisitTercSharedPreferencesModel(
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                dthrMovEquipVisitTerc = Date(1723213270250),
                veiculoMovEquipVisitTerc = "VEICULO TESTE",
                placaMovEquipVisitTerc = "PLACA TESTE",
                destinoMovEquipVisitTerc = "DESTINO TESTE",
                observMovEquipVisitTerc = "OBSERV TESTE",
            )
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(modelSharedPreferences)
            )
            val modelRoom =
                modelSharedPreferences.entityToSharedPreferencesModel()
                    .entityToRoomModel(
                        matricVigia = 19759,
                        idLocal = 1
                    )
            whenever(
                movEquipVisitTercRoomDatasource.save(modelRoom)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.save(
                matricVigia = 19759,
                idLocal = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipVisitTercRepository.save -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercSharedPreferencesDatasource Clear`() =
        runTest {
            val modelSharedPreferences = MovEquipVisitTercSharedPreferencesModel(
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                dthrMovEquipVisitTerc = Date(1723213270250),
                veiculoMovEquipVisitTerc = "VEICULO TESTE",
                placaMovEquipVisitTerc = "PLACA TESTE",
                destinoMovEquipVisitTerc = "DESTINO TESTE",
                observMovEquipVisitTerc = "OBSERV TESTE",
            )
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(modelSharedPreferences)
            )
            val modelRoom =
                modelSharedPreferences.entityToSharedPreferencesModel()
                    .entityToRoomModel(
                        matricVigia = 19759,
                        idLocal = 1
                    )
            whenever(
                movEquipVisitTercRoomDatasource.save(modelRoom)
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.clear()
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.save(
                matricVigia = 19759,
                idLocal = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipVisitTercRepository.save -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in id is 0`() =
        runTest {
            val modelSharedPreferences = MovEquipVisitTercSharedPreferencesModel(
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                dthrMovEquipVisitTerc = Date(1723213270250),
                veiculoMovEquipVisitTerc = "VEICULO TESTE",
                placaMovEquipVisitTerc = "PLACA TESTE",
                destinoMovEquipVisitTerc = "DESTINO TESTE",
                observMovEquipVisitTerc = "OBSERV TESTE",
            )
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(modelSharedPreferences)
            )
            val modelRoom =
                modelSharedPreferences.entityToSharedPreferencesModel()
                    .entityToRoomModel(
                        matricVigia = 19759,
                        idLocal = 1
                    )
            whenever(
                movEquipVisitTercRoomDatasource.save(modelRoom)
            ).thenReturn(
                Result.success(0)
            )
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.clear()
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.save(
                matricVigia = 19759,
                idLocal = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipVisitTercRepository.save"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception: Id is 0"
            )
        }

    @Test
    fun `Check return true if Save execute successfully`() =
        runTest {
            val modelSharedPreferences = MovEquipVisitTercSharedPreferencesModel(
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                dthrMovEquipVisitTerc = Date(1723213270250),
                veiculoMovEquipVisitTerc = "VEICULO TESTE",
                placaMovEquipVisitTerc = "PLACA TESTE",
                destinoMovEquipVisitTerc = "DESTINO TESTE",
                observMovEquipVisitTerc = "OBSERV TESTE",
            )
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(modelSharedPreferences)
            )
            val modelRoom =
                modelSharedPreferences.entityToSharedPreferencesModel()
                    .entityToRoomModel(
                        matricVigia = 19759,
                        idLocal = 1
                    )
            whenever(
                movEquipVisitTercRoomDatasource.save(modelRoom)
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.clear()
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.save(
                matricVigia = 19759,
                idLocal = 1
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
    fun `Check return failure if have error in MovEquipVisitTercSharedPreferencesDatasource setDestino - FlowApp ADD`() =
        runTest {
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.setDestino("DESTINO TESTE")
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.setDestino(
                destino = "DESTINO TESTE",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipVisitTercRepository.setDestino -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRoomDatasource setDestino - FlowApp CHANGE`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.setDestino(
                    destino = "DESTINO TESTE",
                    id = 1
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.setDestino(
                destino = "DESTINO TESTE",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipVisitTercRepository.setDestino -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if SetDestino execute successfully - FlowApp ADD`() =
        runTest {
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.setDestino("DESTINO TESTE")
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setDestino(
                destino = "DESTINO TESTE",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return true if SetDestino execute successfully - FlowApp CHANCE`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.setDestino(
                    destino = "DESTINO TESTE",
                    id = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setDestino(
                destino = "DESTINO TESTE",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercSharedPreferencesDatasource setIdVisitTerc - FlowApp ADD`() =
        runTest {
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.setIdVisitTerc(1)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.setIdVisitTerc(
                idVisitTerc = 1,
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipVisitTercRepository.setIdVisitTerc -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRoomDatasource setIdVisitTerc - FlowApp CHANGE`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.setIdVisitTerc(
                    idVisitTerc = 1,
                    id = 1
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.setIdVisitTerc(
                idVisitTerc = 1,
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipVisitTercRepository.setIdVisitTerc -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if SetIdVisitTerc execute successfully - FlowApp ADD`() =
        runTest {
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.setIdVisitTerc(1)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setIdVisitTerc(
                idVisitTerc = 1,
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
    fun `Check return true if SetIdVisitTerc execute successfully - FlowApp CHANGE`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.setIdVisitTerc(
                    idVisitTerc = 1,
                    id = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setIdVisitTerc(
                idVisitTerc = 1,
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
    fun `Check return failure if have error in MovEquipVisitTercSharedPreferencesDatasource setObserv - FlowApp ADD`() =
        runTest {
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.setObserv("Teste Observ")
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.setObserv(
                observ = "Teste Observ",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipVisitTercRepository.setObserv -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRoomDatasource setObserv - FlowApp CHANGE`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.setObserv(
                    observ = "Teste Observ",
                    id = 1
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.setObserv(
                observ = "Teste Observ",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipVisitTercRepository.setObserv -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if SetObserv execute successfully - FlowApp ADD`() =
        runTest {
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.setObserv("Teste Observ")
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setObserv(
                observ = "Teste Observ",
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
    fun `Check return true if SetObserv execute successfully - FlowApp CHANGE`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.setObserv(
                    observ = "Teste Observ",
                    id = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setObserv(
                observ = "Teste Observ",
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
    fun `Check return failure if have error in MovEquipVisitTercSharedPreferencesDatasource setPlaca - FlowApp ADD`() =
        runTest {
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.setPlaca("Teste Placa")
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.setPlaca(
                placa = "Teste Placa",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipVisitTercRepository.setPlaca -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRoomDatasource setPlaca - FlowApp CHANGE`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.setPlaca(
                    placa = "Teste Placa",
                    id = 1
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.setPlaca(
                placa = "Teste Placa",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipVisitTercRepository.setPlaca -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if SetPlaca execute successfully - FlowApp ADD`() =
        runTest {
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.setPlaca("Teste Placa")
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setPlaca(
                placa = "Teste Placa",
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
    fun `Check return true if SetPlaca execute successfully - FlowApp CHANGE`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.setPlaca(
                    placa = "Teste Placa",
                    id = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setPlaca(
                placa = "Teste Placa",
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
    fun `Check return failure if have error in MovEquipVisitTercSharedPreferencesDatasource SetTipo`() =
        runTest {
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.setTipoVisitTerc(
                    typeVisitTerc = TypeVisitTerc.TERCEIRO
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.setTipoVisitTerc(TypeVisitTerc.TERCEIRO)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipVisitTercRepository.setTipoVisitTerc -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if SetTipo execute successfully`() =
        runTest {
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.setTipoVisitTerc(
                    typeVisitTerc = TypeVisitTerc.TERCEIRO
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setTipoVisitTerc(TypeVisitTerc.TERCEIRO)
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
    fun `Check return failure if have error in MovEquipVisitTercSharedPreferencesDatasource setVeiculo - FlowApp ADD`() =
        runTest {
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.setVeiculo("Teste Veiculo")
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.setVeiculo(
                veiculo = "Teste Veiculo",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipVisitTercRepository.setVeiculo -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRoomDatasource setVeiculo - FlowApp CHANGE`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.setVeiculo(
                    veiculo = "Teste Veiculo",
                    id = 1
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.setVeiculo(
                veiculo = "Teste Veiculo",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipVisitTercRepository.setVeiculo -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if SetVeiculo execute successfully - FlowApp ADD`() =
        runTest {
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.setVeiculo("Teste Veiculo")
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setVeiculo(
                veiculo = "Teste Veiculo",
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
    fun `Check return true if SetVeiculo execute successfully - FlowApp CHANGE`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.setVeiculo(
                    veiculo = "Teste Veiculo",
                    id = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setVeiculo(
                veiculo = "Teste Veiculo",
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
    fun `Check return failure if have error in MovEquipVisitTercSharedPreferencesDatasource start`() =
        runTest {
            val movEquipVisitTerc = MovEquipVisitTerc(
                idMovEquipVisitTerc = 1,
                nroMatricVigiaMovEquipVisitTerc = 19759,
                idLocalMovEquipVisitTerc = 1,
                tipoMovEquipVisitTerc = TypeMovEquip.OUTPUT,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                dthrMovEquipVisitTerc = Date(1723213270250),
                veiculoMovEquipVisitTerc = "TESTE",
                placaMovEquipVisitTerc = "TESTE",
                destinoMovEquipVisitTerc = "TESTE",
                observMovEquipVisitTerc = "TESTE",
                statusMovEquipVisitTerc = StatusData.OPEN,
                statusSendMovEquipVisitTerc = StatusSend.SEND,
                statusMovEquipForeignerVisitTerc = StatusForeigner.INSIDE,
            )
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.start(
                    movEquipVisitTerc.entityToSharedPreferencesModel()
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.start(movEquipVisitTerc)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipVisitTercRepository.start(OUTSIDE) -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if Start execute successfully`() =
        runTest {
            val movEquipVisitTerc = MovEquipVisitTerc(
                idMovEquipVisitTerc = 1,
                nroMatricVigiaMovEquipVisitTerc = 19759,
                idLocalMovEquipVisitTerc = 1,
                tipoMovEquipVisitTerc = TypeMovEquip.OUTPUT,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                dthrMovEquipVisitTerc = Date(1723213270250),
                veiculoMovEquipVisitTerc = "TESTE",
                placaMovEquipVisitTerc = "TESTE",
                destinoMovEquipVisitTerc = "TESTE",
                observMovEquipVisitTerc = "TESTE",
                statusMovEquipVisitTerc = StatusData.OPEN,
                statusSendMovEquipVisitTerc = StatusSend.SEND,
                statusMovEquipForeignerVisitTerc = StatusForeigner.INSIDE,
            )
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.start(
                    movEquipVisitTerc.entityToSharedPreferencesModel()
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.start(movEquipVisitTerc)
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
    fun `Check return failure Datasource in MovEquipVisitTercRoomDatasource setOutside`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.setOutside(1)
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
                "IMovEquipVisitTercRepository.setOutside -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if have success in MovEquipVisitTercRoomDatasource setOutside`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.setOutside(1)
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
    fun `Check return failure if have errors in MovEquipVisitTercRoomDatasource checkSend`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.checkSend()
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
                "IMovEquipVisitTercRepository.checkSend -> Unknown Error"
            )
        }

    @Test
    fun `CheckSend - Check return false if not have MovEquipVisitTerc to send`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.checkSend()
            ).thenReturn(
                Result.success(false)
            )
            val result = repository.checkSend()
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
    fun `CheckSend - Check return true if have MovEquipVisitTerc to send`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.checkSend()
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.checkSend()
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
    fun `Check return failure if have errors in MovEquipVisitTercRoomDatasource listSend`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.listSend()
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
                "IMovEquipVisitTercRepository.listSend -> Unknown Error"
            )
        }

    @Test
    fun `Check return list if MovEquipVisitTercRepository listSend execute successfully`() =
        runTest {
            val roomModel = MovEquipVisitTercRoomModel(
                idMovEquipVisitTerc = 1,
                nroMatricVigiaMovEquipVisitTerc = 19759,
                idLocalMovEquipVisitTerc = 1,
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                dthrMovEquipVisitTerc = 1723213270250,
                veiculoMovEquipVisitTerc = "VEICULO TESTE",
                placaMovEquipVisitTerc = "PLACA TESTE",
                destinoMovEquipVisitTerc = "DESTINO TESTE",
                observMovEquipVisitTerc = "OBSERV TESTE",
                statusMovEquipVisitTerc = StatusData.OPEN,
                statusSendMovEquipVisitTerc = StatusSend.SEND,
                statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
            )
            whenever(
                movEquipVisitTercRoomDatasource.listSend()
            ).thenReturn(
                Result.success(
                    listOf(
                        roomModel
                    )
                )
            )
            val result = repository.listSend()
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                1
            )
            val entity = list[0]
            assertEquals(
                entity.idMovEquipVisitTerc,
                1
            )
            assertEquals(
                entity.placaMovEquipVisitTerc,
                "PLACA TESTE"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipProprioRetrofitDatasource send`() =
        runTest {
            val retrofitModel = MovEquipVisitTercRetrofitModelOutput(
                idMovEquipVisitTerc = 1,
                nroMatricVigiaMovEquipVisitTerc = 19759,
                idLocalMovEquipVisitTerc = 1,
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT.ordinal + 1,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO.ordinal + 1,
                dthrMovEquipVisitTerc = SimpleDateFormat(
                    "dd/MM/yyyy HH:mm",
                    Locale("pt", "BR")
                ).format(
                    Date(1723213270250)
                ),
                veiculoMovEquipVisitTerc = "VEICULO TESTE",
                placaMovEquipVisitTerc = "PLACA TESTE",
                destinoMovEquipVisitTerc = "DESTINO TESTE",
                observMovEquipVisitTerc = "OBSERV TESTE",
                nroAparelhoMovEquipVisitTerc = 16997417840L,
                movEquipVisitTercPassagList = emptyList(),
            )
            val entity = MovEquipVisitTerc(
                idMovEquipVisitTerc = 1,
                nroMatricVigiaMovEquipVisitTerc = 19759,
                idLocalMovEquipVisitTerc = 1,
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                dthrMovEquipVisitTerc = Date(1723213270250),
                veiculoMovEquipVisitTerc = "VEICULO TESTE",
                placaMovEquipVisitTerc = "PLACA TESTE",
                destinoMovEquipVisitTerc = "DESTINO TESTE",
                observMovEquipVisitTerc = "OBSERV TESTE",
                statusMovEquipVisitTerc = StatusData.OPEN,
                statusSendMovEquipVisitTerc = StatusSend.SEND,
                statusMovEquipForeignerVisitTerc = StatusForeigner.INSIDE,
            )
            val retrofitModelList = listOf(retrofitModel)
            val list = listOf(entity)
            val token = "123456"
            val number = 16997417840L
            whenever(
                movEquipVisitTercRetrofitDatasource.send(
                    token = token,
                    list = retrofitModelList
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.send(
                list = list,
                number = number,
                token = token
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipVisitTercRepository.send -> Unknown Error"
            )
        }

    @Test
    fun `Check return list if Send execute successfully`() =
        runTest {
            val retrofitModelOutput = MovEquipVisitTercRetrofitModelOutput(
                idMovEquipVisitTerc = 1,
                nroMatricVigiaMovEquipVisitTerc = 19759,
                idLocalMovEquipVisitTerc = 1,
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT.ordinal + 1,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO.ordinal + 1,
                dthrMovEquipVisitTerc = SimpleDateFormat(
                    "dd/MM/yyyy HH:mm",
                    Locale("pt", "BR")
                ).format(
                    Date(1723213270250)
                ),
                veiculoMovEquipVisitTerc = "VEICULO TESTE",
                placaMovEquipVisitTerc = "PLACA TESTE",
                destinoMovEquipVisitTerc = "DESTINO TESTE",
                observMovEquipVisitTerc = "OBSERV TESTE",
                nroAparelhoMovEquipVisitTerc = 16997417840L,
                movEquipVisitTercPassagList = emptyList(),
            )
            val entity = MovEquipVisitTerc(
                idMovEquipVisitTerc = 1,
                nroMatricVigiaMovEquipVisitTerc = 19759,
                idLocalMovEquipVisitTerc = 1,
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                dthrMovEquipVisitTerc = Date(1723213270250),
                veiculoMovEquipVisitTerc = "VEICULO TESTE",
                placaMovEquipVisitTerc = "PLACA TESTE",
                destinoMovEquipVisitTerc = "DESTINO TESTE",
                observMovEquipVisitTerc = "OBSERV TESTE",
                statusMovEquipVisitTerc = StatusData.OPEN,
                statusSendMovEquipVisitTerc = StatusSend.SEND,
                statusMovEquipForeignerVisitTerc = StatusForeigner.INSIDE,
            )
            val retrofitModelInput = MovEquipVisitTercRetrofitModelInput(
                idMovEquipVisitTerc = 1,
            )
            val retrofitModelList = listOf(retrofitModelOutput)
            val entityList = listOf(entity)
            val token = "123456"
            val number = 16997417840L
            whenever(
                movEquipVisitTercRetrofitDatasource.send(
                    token = token,
                    list = retrofitModelList
                )
            ).thenReturn(
                Result.success(
                    listOf(retrofitModelInput)
                )
            )
            val result = repository.send(
                list = entityList,
                number = number,
                token = token
            )
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                1
            )
            val entityRet = list[0]
            assertEquals(
                entityRet.idMovEquipVisitTerc,
                1
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRoomDatasource setSent`() =
        runTest {
            val movEquipVisitTerc =
                MovEquipVisitTerc(
                    idMovEquipVisitTerc = 1,
                )
            whenever(
                movEquipVisitTercRoomDatasource.setSent(1)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.setSent(
                listOf(
                    movEquipVisitTerc
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipVisitTercRepository.setSent -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure with 2 elements if have error in MovEquipVisitTercRoomDatasource setSent`() =
        runTest {
            val movEquipVisitTerc1 =
                MovEquipVisitTerc(
                    idMovEquipVisitTerc = 1,
                )
            val movEquipVisitTerc2 =
                MovEquipVisitTerc(
                    idMovEquipVisitTerc = 2,
                )
            whenever(
                movEquipVisitTercRoomDatasource.setSent(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipVisitTercRoomDatasource.setSent(2)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.setSent(
                listOf(
                    movEquipVisitTerc1,
                    movEquipVisitTerc2
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipVisitTercRepository.setSent -> Unknown Error"
            )
        }

    @Test
    fun `Check return true with 2 elements if MovEquipVisitTercRoomDatasource setSent execute successfully`() =
        runTest {
            val movEquipVisitTerc1 =
                MovEquipVisitTerc(
                    idMovEquipVisitTerc = 1,
                )
            val movEquipVisitTerc2 =
                MovEquipVisitTerc(
                    idMovEquipVisitTerc = 2,
                )
            whenever(
                movEquipVisitTercRoomDatasource.setSent(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipVisitTercRoomDatasource.setSent(2)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setSent(
                listOf(
                    movEquipVisitTerc1,
                    movEquipVisitTerc2
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
    fun `Check return failure if have error in movEquipVisitTercRoomDatasource get`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.get(1)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.delete(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipVisitTercRepository.delete -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in movEquipProprioRoomDatasource delete`() =
        runTest {
            val model = MovEquipVisitTercRoomModel(
                idMovEquipVisitTerc = 1,
                nroMatricVigiaMovEquipVisitTerc = 19759,
                idLocalMovEquipVisitTerc = 1,
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                dthrMovEquipVisitTerc = 1723213270250,
                veiculoMovEquipVisitTerc = "TESTE",
                placaMovEquipVisitTerc = "TESTE",
                destinoMovEquipVisitTerc = "TESTE",
                observMovEquipVisitTerc = "TESTE",
                statusMovEquipVisitTerc = StatusData.OPEN,
                statusSendMovEquipVisitTerc = StatusSend.SEND,
                statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
            )
            whenever(
                movEquipVisitTercRoomDatasource.get(1)
            ).thenReturn(
                Result.success(model)
            )
            whenever(
                movEquipVisitTercRoomDatasource.delete(model)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.delete(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipVisitTercRepository.delete -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if MovEquipVisitTercRepository delete execute successfully`() =
        runTest {
            val model = MovEquipVisitTercRoomModel(
                idMovEquipVisitTerc = 1,
                nroMatricVigiaMovEquipVisitTerc = 19759,
                idLocalMovEquipVisitTerc = 1,
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                dthrMovEquipVisitTerc = 1723213270250,
                veiculoMovEquipVisitTerc = "TESTE",
                placaMovEquipVisitTerc = "TESTE",
                destinoMovEquipVisitTerc = "TESTE",
                observMovEquipVisitTerc = "TESTE",
                statusMovEquipVisitTerc = StatusData.OPEN,
                statusSendMovEquipVisitTerc = StatusSend.SEND,
                statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
            )
            whenever(
                movEquipVisitTercRoomDatasource.get(1)
            ).thenReturn(
                Result.success(model)
            )
            whenever(
                movEquipVisitTercRoomDatasource.delete(model)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.delete(1)
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
    fun `Check return failure if have errors in MovEquipVisitTercRoomDatasource listSent`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.listSent()
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.listSent()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipVisitTercRepository.listSent -> Unknown Error"
            )
        }

    @Test
    fun `Check return list if MovEquipVisitTercRepository listSent execute successfully`() =
        runTest {
            val roomModel = MovEquipVisitTercRoomModel(
                idMovEquipVisitTerc = 1,
                nroMatricVigiaMovEquipVisitTerc = 19759,
                idLocalMovEquipVisitTerc = 1,
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                dthrMovEquipVisitTerc = 1723213270250,
                veiculoMovEquipVisitTerc = "TESTE",
                placaMovEquipVisitTerc = "TESTE",
                destinoMovEquipVisitTerc = "TESTE",
                observMovEquipVisitTerc = "TESTE",
                statusMovEquipVisitTerc = StatusData.OPEN,
                statusSendMovEquipVisitTerc = StatusSend.SENT,
                statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
            )
            whenever(
                movEquipVisitTercRoomDatasource.listSent()
            ).thenReturn(
                Result.success(
                    listOf(
                        roomModel
                    )
                )
            )
            val result = repository.listSent()
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                1
            )
            val model = list[0]
            assertEquals(
                model.idMovEquipVisitTerc,
                1
            )
            assertEquals(
                model.idVisitTercMovEquipVisitTerc,
                1000
            )
            assertEquals(
                model.statusSendMovEquipVisitTerc,
                StatusSend.SENT
            )
        }

    @Test
    fun `Check return failure if have errors in MovEquipVisitTercRoomDatasource checkOpen`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.checkOpen()
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
                "IMovEquipVisitTercRepository.checkOpen -> Unknown Error"
            )
        }

    @Test
    fun `CheckOpen - Check return false if not have MovEquipVisitTerc to open`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.checkOpen()
            ).thenReturn(
                Result.success(false)
            )
            val result = repository.checkOpen()
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
    fun `CheckSend - Check return true if have MovEquipVisitTerc to open`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.checkOpen()
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.checkOpen()
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