package br.com.usinasantafe.pcp.infra.repositories.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipProprio
import br.com.usinasantafe.pcp.infra.datasource.room.variable.MovEquipProprioRoomDatasource
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.MovEquipProprioSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.datasource.retrofit.variable.MovEquipProprioRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.MovEquipProprioRetrofitModelInput
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.MovEquipProprioRetrofitModelOutput
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipProprioRoomModel
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.MovEquipProprioSharedPreferencesModel
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.StatusData
import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.utils.TypeMovEquip
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class IMovEquipProprioRepositoryTest {

    private val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
    private val movEquipProprioSharedPreferencesDatasource =
        mock<MovEquipProprioSharedPreferencesDatasource>()
    private val movEquipProprioRetrofitDatasource = mock<MovEquipProprioRetrofitDatasource>()
    private val repository = IMovEquipProprioRepository(
        movEquipProprioSharedPreferencesDatasource,
        movEquipProprioRoomDatasource,
        movEquipProprioRetrofitDatasource
    )

    @Test
    fun `Check failure Datasource in MovEquipProprioRoomDatasource listOpen`() = runTest {
        whenever(
            movEquipProprioRoomDatasource.listOpen()
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
            "IMovEquipProprioRepository.listOpen -> Unknown Error"
        )
    }

    @Test
    fun `Check failure success if have success in MovEquipProprioRoomDatasource listOpen`() =
        runTest {
            val movEquipProprioRoomModel = MovEquipProprioRoomModel(
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 1,
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = 1723213270250,
                idEquipMovEquipProprio = 1,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND
            )
            val movEquipProprio = MovEquipProprio(
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 1,
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = Date(1723213270250),
                idEquipMovEquipProprio = 1,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND
            )
            whenever(
                movEquipProprioRoomDatasource.listOpen()
            ).thenReturn(
                Result.success(
                    listOf(
                        movEquipProprioRoomModel
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
                movEquipProprio
            )
        }

    @Test
    fun `Check failure Datasource in MovEquipProprioRoomDatasource setClose`() = runTest {
        whenever(
            movEquipProprioRoomDatasource.setClose(1)
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
            "IMovEquipProprioRepository.setClose -> Unknown Error"
        )
    }

    @Test
    fun `Check success if have success in MovEquipProprioRoomDatasource setClose`() =
        runTest {
            whenever(
                movEquipProprioRoomDatasource.setClose(1)
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
    fun `Check failure if have failure in MovEquipProprioSharedPreferencesDatasource start`() =
        runTest {
            whenever(
                movEquipProprioSharedPreferencesDatasource.start(TypeMovEquip.INPUT)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.start(TypeMovEquip.INPUT)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipProprioRepository.start -> Unknown Error"
            )
        }

    @Test
    fun `Check success if MovEquipProprioSharedPreferencesDatasource start execute correctly`() =
        runTest {
            whenever(
                movEquipProprioSharedPreferencesDatasource.start(TypeMovEquip.INPUT)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.start(TypeMovEquip.INPUT)
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
    fun `Check failure if have failure in MovEquipProprioSharedPreferencesDatasource setMatricColab FlowApp ADD`() =
        runTest {
            whenever(
                movEquipProprioSharedPreferencesDatasource.setMatricColab(19759)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.setMatricColab(
                matricColab = 19759,
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(result.isFailure, true)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipProprioRepository.setMatricColab -> Unknown Error"
            )
        }

    @Test
    fun `Check failure if have failure in MovEquipProprioSharedPreferencesDatasource setMatricColab FlowApp CHANGE`() =
        runTest {
            whenever(
                movEquipProprioRoomDatasource.setMatricColab(
                    matricColab = 19759,
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
                "IMovEquipProprioRepository.setMatricColab -> Unknown Error"
            )
        }

    @Test
    fun `Check return success if have MovEquipProprioSharedPreferencesDatasource setMatricColab execute correctly FlowApp ADD`() =
        runTest {
            whenever(
                movEquipProprioSharedPreferencesDatasource.setMatricColab(19759)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setMatricColab(
                matricColab = 19759,
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
    fun `Check return success if have MovEquipProprioSharedPreferencesDatasource setMatricColab execute correctly FlowApp CHANGE`() =
        runTest {
            whenever(
                movEquipProprioRoomDatasource.setMatricColab(19759, 1)
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
    fun `Check failure if have failure in MovEquipProprioSharedPreferencesDatasource setIdEquip FlowApp ADD`() =
        runTest {
            whenever(
                movEquipProprioSharedPreferencesDatasource.setIdEquip(10)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.setIdEquip(
                idEquip = 10,
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipProprioRepository.setIdEquip -> Unknown Error"
            )
        }

    @Test
    fun `Check failure if have failure in MovEquipProprioSharedPreferencesDatasource setIdEquip FlowApp CHANGE`() =
        runTest {
            whenever(
                movEquipProprioRoomDatasource.setIdEquip(
                    idEquip = 10,
                    id = 1
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.setIdEquip(
                idEquip = 10,
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipProprioRepository.setIdEquip -> Unknown Error"
            )
        }

    @Test
    fun `Check return success if have MovEquipProprioSharedPreferencesDatasource setIdEquip execute correctly FlowApp ADD`() =
        runTest {
            whenever(movEquipProprioSharedPreferencesDatasource.setIdEquip(10)).thenReturn(
                Result.success(true)
            )
            val result = repository.setIdEquip(
                idEquip = 10,
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
    fun `Check return success if have MovEquipProprioSharedPreferencesDatasource setIdEquip execute correctly FlowApp CHANGE`() =
        runTest {
            whenever(movEquipProprioRoomDatasource.setIdEquip(10, 1)).thenReturn(
                Result.success(true)
            )
            val result = repository.setIdEquip(
                idEquip = 10,
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
    fun `Check failure if have failure in MovEquipProprioSharedPreferencesDatasource setDestino FlowApp ADD`() =
        runTest {
            whenever(
                movEquipProprioSharedPreferencesDatasource.setDestino("Teste")
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.setDestino(
                destino = "Teste",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipProprioRepository.setDestino -> Unknown Error"
            )
        }

    @Test
    fun `Check failure if have failure in MovEquipProprioSharedPreferencesDatasource setDestino FlowApp CHANGE`() =
        runTest {
            whenever(
                movEquipProprioRoomDatasource.setDestino(
                    destino = "Teste",
                    id = 1
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.setDestino(
                destino = "Teste",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipProprioRepository.setDestino -> Unknown Error"
            )
        }

    @Test
    fun `Check return success if have MovEquipProprioSharedPreferencesDatasource setDestino execute correctly FlowApp ADD`() =
        runTest {
            whenever(
                movEquipProprioSharedPreferencesDatasource.setDestino("Teste")
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setDestino(
                destino = "Teste",
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
    fun `Check return success if have MovEquipProprioSharedPreferencesDatasource setDestino execute correctly FlowApp CHANGE`() =
        runTest {
            whenever(
                movEquipProprioRoomDatasource.setDestino("Teste", 1)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setDestino(
                destino = "Teste",
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
    fun `Check failure if have failure in MovEquipProprioSharedPreferencesDatasource getTipoMov`() =
        runTest {
            whenever(
                movEquipProprioSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.getTipoMov()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipProprioRepository.getTipoMov -> Unknown Error"
            )
        }

    @Test
    fun `Check return tipoMov if have MovEquipProprioSharedPreferencesDatasource getTipoMov execute correctly`() =
        runTest {
            whenever(
                movEquipProprioSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(
                    MovEquipProprioSharedPreferencesModel(
                        tipoMovEquipProprio = TypeMovEquip.INPUT
                    )
                )
            )
            val result = repository.getTipoMov()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                TypeMovEquip.INPUT
            )
        }

    @Test
    fun `Check return failure if have errors in MovEquipProprioSharedPreferencesDatasource setNotaFiscal FlowApp ADD`() =
        runTest {
            whenever(
                movEquipProprioSharedPreferencesDatasource.setNotaFiscal(123456)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.setNotaFiscal(
                notaFiscal = 123456,
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipProprioRepository.setNotaFiscal -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have errors in MovEquipProprioSharedPreferencesDatasource setNotaFiscal FlowApp CHANGE`() =
        runTest {
            whenever(
                movEquipProprioRoomDatasource.setNotaFiscal(
                    notaFiscal =  123456,
                    id =1
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.setNotaFiscal(
                notaFiscal = 123456,
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipProprioRepository.setNotaFiscal -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if MovEquipProprioSharedPreferencesDatasource setNotaFiscal execute correctly FlowApp ADD`() =
        runTest {
            whenever(
                movEquipProprioSharedPreferencesDatasource.setNotaFiscal(123456)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setNotaFiscal(
                notaFiscal = 123456,
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
    fun `Check return true if MovEquipProprioSharedPreferencesDatasource setNotaFiscal execute correctly FlowApp CHANGE`() =
        runTest {
            whenever(
                movEquipProprioRoomDatasource.setNotaFiscal(123456, 1)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setNotaFiscal(
                notaFiscal = 123456,
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
    fun `Check return failure if have errors in MovEquipProprioSharedPreferencesDatasource setObserv FlowApp ADD`() =
        runTest {
            whenever(
                movEquipProprioSharedPreferencesDatasource.setObserv("TESTE OBSERV")
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.setObserv(
                observ = "TESTE OBSERV",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipProprioRepository.setObserv -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have errors in MovEquipProprioSharedPreferencesDatasource setObserv FlowApp CHANGE`() =
        runTest {
            whenever(
                movEquipProprioRoomDatasource.setObserv(
                    observ = "TESTE OBSERV",
                    id = 1
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.setObserv(
                observ = "TESTE OBSERV",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipProprioRepository.setObserv -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if MovEquipProprioSharedPreferencesDatasource setObserv execute correctly FlowApp ADD`() =
        runTest {
            whenever(
                movEquipProprioSharedPreferencesDatasource.setObserv("TESTE OBSERV")
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setObserv(
                observ = "TESTE OBSERV",
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
    fun `Check return true if MovEquipProprioSharedPreferencesDatasource setObserv execute correctly FlowApp ADD and value is null`() =
        runTest {
            whenever(
                movEquipProprioSharedPreferencesDatasource.setObserv(null)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setObserv(
                observ = null,
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
    fun `Check return true if MovEquipProprioSharedPreferencesDatasource setObserv execute correctly FlowApp CHANGE`() =
        runTest {
            whenever(
                movEquipProprioRoomDatasource.setObserv("TESTE OBSERV", 1)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setObserv(
                observ = "TESTE OBSERV",
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
    fun `Check return true if MovEquipProprioSharedPreferencesDatasource setObserv execute correctly FlowApp CHANGE and value is null`() =
        runTest {
            whenever(
                movEquipProprioRoomDatasource.setObserv(null, 1)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setObserv(
                observ = null,
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
    fun `Check return failure if have erros in MovEquipProprioSharedPreferencesDatasource get`() =
        runTest {
            whenever(
                movEquipProprioSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.save(19759, 1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipProprioRepository.save -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have erros in MovEquipProprioSharedPreferencesDatasource save`() =
        runTest {
            val movEquipProprioSharedPreferencesModel = MovEquipProprioSharedPreferencesModel(
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = Date(1723213270250),
                idEquipMovEquipProprio = 1,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
            )
            val movEquipProprioRoomModel = MovEquipProprioRoomModel(
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 1,
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = 1723213270250,
                idEquipMovEquipProprio = 1,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND
            )
            whenever(
                movEquipProprioSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(
                    movEquipProprioSharedPreferencesModel
                )
            )
            whenever(
                movEquipProprioRoomDatasource.save(movEquipProprioRoomModel)
            ).thenReturn(
                Result.success(1L)
            )
            whenever(
                movEquipProprioSharedPreferencesDatasource.clean()
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.save(19759, 1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipProprioRepository.save -> Unknown Error"
            )
        }

    @Test
    fun `Chech return failure if MovEquipProprioRepository save return zero`() =
        runTest {
            val movEquipProprioRoomModel = MovEquipProprioRoomModel(
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 1,
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = 1723213270250,
                idEquipMovEquipProprio = 1,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND
            )
            val movEquipProprioSharedPreferencesModel = MovEquipProprioSharedPreferencesModel(
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = Date(1723213270250),
                idEquipMovEquipProprio = 1,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
            )
            whenever(
                movEquipProprioSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(
                    movEquipProprioSharedPreferencesModel
                )
            )
            whenever(
                movEquipProprioRoomDatasource.save(movEquipProprioRoomModel)
            ).thenReturn(
                Result.success(0L)
            )
            val result = repository.save(19759, 1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipProprioRepository.save"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception: Id is 0"
            )
        }

    @Test
    fun `Check return id if MovEquipProprioRepositoryImpl save execute successfully`() =
        runTest {
            val movEquipProprioRoomModel = MovEquipProprioRoomModel(
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 1,
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = 1723213270250,
                idEquipMovEquipProprio = 1,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND
            )
            val movEquipProprioSharedPreferencesModel = MovEquipProprioSharedPreferencesModel(
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = Date(1723213270250),
                idEquipMovEquipProprio = 1,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
            )
            whenever(
                movEquipProprioSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(
                    movEquipProprioSharedPreferencesModel
                )
            )
            whenever(
                movEquipProprioRoomDatasource.save(movEquipProprioRoomModel)
            ).thenReturn(
                Result.success(1L)
            )
            whenever(
                movEquipProprioSharedPreferencesDatasource.clean()
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.save(19759, 1)
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
    fun `Check return failure if have errors in MovEquipProprioRoomDatasource checkSend`() =
        runTest {
            whenever(
                movEquipProprioRoomDatasource.checkSend()
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
                "IMovEquipProprioRepository.checkSend -> Unknown Error"
            )
        }

    @Test
    fun `CheckSend - Check return false if not have MovEquipProprio to send`() =
        runTest {
            whenever(
                movEquipProprioRoomDatasource.checkSend()
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
    fun `CheckSend - Check return true if have MovEquipProprio to send`() =
        runTest {
            whenever(
                movEquipProprioRoomDatasource.checkSend()
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
    fun `Check return failure if have errors in MovEquipProprioRoomDatasource listSend`() =
        runTest {
            whenever(
                movEquipProprioRoomDatasource.listSend()
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
                "IMovEquipProprioRepository.listSend -> Unknown Error"
            )
        }

    @Test
    fun `Check return list if MovEquipProprioRepository listSend execute successfully`() =
        runTest {
            val roomModel = MovEquipProprioRoomModel(
                idMovEquipProprio = 1,
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 1,
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = 1723213270250,
                idEquipMovEquipProprio = 1,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND
            )
            whenever(
                movEquipProprioRoomDatasource.listSend()
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
            assertEquals(
                result.getOrNull()!![0].idMovEquipProprio,
                1
            )
            assertEquals(
                result.getOrNull()!![0].matricColabMovEquipProprio,
                19759
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipProprioRetrofitDatasource send`() =
        runTest {
            val retrofitModel = MovEquipProprioRetrofitModelOutput(
                idMovEquipProprio = 1,
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 1,
                tipoMovEquipProprio = 1,
                dthrMovEquipProprio = SimpleDateFormat(
                    "dd/MM/yyyy HH:mm",
                    Locale("pt", "BR")
                ).format(Date(1723213270250)),
                idEquipMovEquipProprio = 1,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                nroAparelhoMovEquipProprio = 16997417840L,
                movEquipProprioEquipSegList = emptyList(),
                movEquipProprioPassagList = emptyList(),
            )

            val entity = MovEquipProprio(
                idMovEquipProprio = 1,
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 1,
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = Date(1723213270250),
                idEquipMovEquipProprio = 1,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND
            )
            val retrofitModelList = listOf(retrofitModel)
            val list = listOf(entity)
            val token = "123456"
            val number = 16997417840L
            whenever(
                movEquipProprioRetrofitDatasource.send(
                    retrofitModelList,
                    token
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
                "IMovEquipProprioRepository.send -> Unknown Error"
            )
        }

    @Test
    fun `Check return list if MovEquipProprioRepository send execute successfully`() =
        runTest {
            val movEquipProprioRetrofitModelOutput = MovEquipProprioRetrofitModelOutput(
                idMovEquipProprio = 1,
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 1,
                tipoMovEquipProprio = 1,
                dthrMovEquipProprio = SimpleDateFormat(
                    "dd/MM/yyyy HH:mm",
                    Locale("pt", "BR")
                ).format(Date(1723213270250)),
                idEquipMovEquipProprio = 1,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                nroAparelhoMovEquipProprio = 16997417840L,
                movEquipProprioEquipSegList = emptyList(),
                movEquipProprioPassagList = emptyList(),
            )
            val movEquipProprio = MovEquipProprio(
                idMovEquipProprio = 1,
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 1,
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = Date(1723213270250),
                idEquipMovEquipProprio = 1,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND
            )
            val movEquipProprioRetrofitModelInput =
                MovEquipProprioRetrofitModelInput(
                    idMovEquipProprio = 1
                )
            val listRetrofitModelOutput =
                listOf(movEquipProprioRetrofitModelOutput)
            val listRetrofitModelInput =
                listOf(movEquipProprioRetrofitModelInput)
            val list = listOf(movEquipProprio)
            val token = "123456"
            val number = 16997417840L
            whenever(
                movEquipProprioRetrofitDatasource.send(
                    listRetrofitModelOutput,
                    token
                )
            ).thenReturn(
                Result.success(listRetrofitModelInput)
            )
            val result = repository.send(list, number, token)
            assertEquals(
                result.isSuccess,
                true
            )
            val listResult = result.getOrNull()!!
            assertEquals(
                listResult[0].idMovEquipProprio,
                1
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipProprioRoomDatasource setSent`() =
        runTest {
            val movEquipProprio =
                MovEquipProprio(
                    idMovEquipProprio = 1,
                )
            whenever(
                movEquipProprioRoomDatasource.setSent(1)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.setSent(
                listOf(
                    movEquipProprio
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipProprioRepository.setSent -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure with 2 elements if have error in MovEquipProprioRoomDatasource setSent`() =
        runTest {
            val movEquipProprio1 =
                MovEquipProprio(
                    idMovEquipProprio = 1,
                )
            val movEquipProprio2 =
                MovEquipProprio(
                    idMovEquipProprio = 2,
                )
            whenever(
                movEquipProprioRoomDatasource.setSent(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipProprioRoomDatasource.setSent(2)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.setSent(
                listOf(
                    movEquipProprio1,
                    movEquipProprio2
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipProprioRepository.setSent -> Unknown Error"
            )
        }

    @Test
    fun `Check return true with 2 elements if MovEquipProprioRoomDatasource setSent execute successfully`() =
        runTest {
            val movEquipProprio1 =
                MovEquipProprio(
                    idMovEquipProprio = 1,
                )
            val movEquipProprio2 =
                MovEquipProprio(
                    idMovEquipProprio = 2,
                )
            whenever(
                movEquipProprioRoomDatasource.setSent(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipProprioRoomDatasource.setSent(2)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setSent(
                listOf(
                    movEquipProprio1,
                    movEquipProprio2
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
    fun `Check return failure if have error in MovEquipProprioRoomDatasource get`() =
        runTest {
            whenever(
                movEquipProprioRoomDatasource.get(1)
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
                "IMovEquipProprioRepository.get -> Unknown Error"
            )
        }

    @Test
    fun `Check return model if MovEquipProprioRepository get execute successfully`() =
        runTest {
            whenever(
                movEquipProprioRoomDatasource.get(1)
            ).thenReturn(
                Result.success(
                    MovEquipProprioRoomModel(
                        idMovEquipProprio = 1,
                        matricVigiaMovEquipProprio = 19759,
                        idLocalMovEquipProprio = 1,
                        tipoMovEquipProprio = TypeMovEquip.INPUT,
                        dthrMovEquipProprio = 1723213270250,
                        idEquipMovEquipProprio = 1,
                        matricColabMovEquipProprio = 19759,
                        destinoMovEquipProprio = "TESTE DESTINO",
                        notaFiscalMovEquipProprio = 123456789,
                        observMovEquipProprio = "TESTE OBSERV",
                        statusMovEquipProprio = StatusData.OPEN,
                        statusSendMovEquipProprio = StatusSend.SEND
                    )
                )
            )
            val result = repository.get(1)
            assertEquals(
                result.isSuccess,
                true
            )
            val model = result.getOrNull()!!
            assertEquals(
                model.idMovEquipProprio,
                1
            )
            assertEquals(
                model.destinoMovEquipProprio,
                "TESTE DESTINO"
            )
        }

    @Test
    fun `Check failure if have error in MovEquipProprioRoomDatasource GetDestino`() =
        runTest {
            whenever(
                movEquipProprioRoomDatasource.get(1)
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
                "IMovEquipProprioRepository.getDestino -> Unknown Error"
            )
        }

    @Test
    fun `Check return destino if MovEquipProprioRoomDatasource Get execute successfully`() =
        runTest {
            whenever(
                movEquipProprioRoomDatasource.get(1)
            ).thenReturn(
                Result.success(
                    MovEquipProprioRoomModel(
                        idMovEquipProprio = 1,
                        matricVigiaMovEquipProprio = 19759,
                        idLocalMovEquipProprio = 1,
                        tipoMovEquipProprio = TypeMovEquip.INPUT,
                        dthrMovEquipProprio = 1723213270250,
                        idEquipMovEquipProprio = 1,
                        matricColabMovEquipProprio = 19759,
                        destinoMovEquipProprio = "TESTE DESTINO",
                        notaFiscalMovEquipProprio = 123456789,
                        observMovEquipProprio = "TESTE OBSERV",
                        statusMovEquipProprio = StatusData.OPEN,
                        statusSendMovEquipProprio = StatusSend.SEND
                    )
                )
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
    fun `Check failure if have error in MovEquipProprioRoomDatasource GetNotaFiscal`() =
        runTest {
            whenever(
                movEquipProprioRoomDatasource.get(1)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.getNotaFiscal(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipProprioRepository.getNotaFiscal -> Unknown Error"
            )
        }

    @Test
    fun `Check return Nota Fiscal if MovEquipProprioRoomDatasource Get execute successfully`() =
        runTest {
            whenever(
                movEquipProprioRoomDatasource.get(1)
            ).thenReturn(
                Result.success(
                    MovEquipProprioRoomModel(
                        idMovEquipProprio = 1,
                        matricVigiaMovEquipProprio = 19759,
                        idLocalMovEquipProprio = 1,
                        tipoMovEquipProprio = TypeMovEquip.INPUT,
                        dthrMovEquipProprio = 1723213270250,
                        idEquipMovEquipProprio = 1,
                        matricColabMovEquipProprio = 19759,
                        destinoMovEquipProprio = "TESTE DESTINO",
                        notaFiscalMovEquipProprio = 123456789,
                        observMovEquipProprio = "TESTE OBSERV",
                        statusMovEquipProprio = StatusData.OPEN,
                        statusSendMovEquipProprio = StatusSend.SEND
                    )
                )
            )
            val result = repository.getNotaFiscal(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                123456789
            )
        }

    @Test
    fun `Check return Nota Fiscal if MovEquipProprioRoomDatasource Get execute successfully and field is null`() =
        runTest {
            whenever(
                movEquipProprioRoomDatasource.get(1)
            ).thenReturn(
                Result.success(
                    MovEquipProprioRoomModel(
                        idMovEquipProprio = 1,
                        matricVigiaMovEquipProprio = 19759,
                        idLocalMovEquipProprio = 1,
                        tipoMovEquipProprio = TypeMovEquip.INPUT,
                        dthrMovEquipProprio = 1723213270250,
                        idEquipMovEquipProprio = 1,
                        matricColabMovEquipProprio = 19759,
                        destinoMovEquipProprio = "TESTE DESTINO",
                        notaFiscalMovEquipProprio = null,
                        observMovEquipProprio = "TESTE OBSERV",
                        statusMovEquipProprio = StatusData.OPEN,
                        statusSendMovEquipProprio = StatusSend.SEND
                    )
                )
            )
            val result = repository.getNotaFiscal(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull(),
                null
            )
        }

    @Test
    fun `Check failure if have error in MovEquipProprioRoomDatasource GetObserv`() =
        runTest {
            whenever(
                movEquipProprioRoomDatasource.get(1)
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
                "IMovEquipProprioRepository.getObserv -> Unknown Error"
            )
        }

    @Test
    fun `Check return Observ if MovEquipProprioRoomDatasource Get execute successfully`() =
        runTest {
            whenever(
                movEquipProprioRoomDatasource.get(1)
            ).thenReturn(
                Result.success(
                    MovEquipProprioRoomModel(
                        idMovEquipProprio = 1,
                        matricVigiaMovEquipProprio = 19759,
                        idLocalMovEquipProprio = 1,
                        tipoMovEquipProprio = TypeMovEquip.INPUT,
                        dthrMovEquipProprio = 1723213270250,
                        idEquipMovEquipProprio = 1,
                        matricColabMovEquipProprio = 19759,
                        destinoMovEquipProprio = "TESTE DESTINO",
                        notaFiscalMovEquipProprio = 123456789,
                        observMovEquipProprio = "TESTE OBSERV",
                        statusMovEquipProprio = StatusData.OPEN,
                        statusSendMovEquipProprio = StatusSend.SEND
                    )
                )
            )
            val result = repository.getObserv(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                "TESTE OBSERV"
            )
        }

    @Test
    fun `Check return Observ if MovEquipProprioRoomDatasource Get execute successfully and field is null`() =
        runTest {
            whenever(
                movEquipProprioRoomDatasource.get(1)
            ).thenReturn(
                Result.success(
                    MovEquipProprioRoomModel(
                        idMovEquipProprio = 1,
                        matricVigiaMovEquipProprio = 19759,
                        idLocalMovEquipProprio = 1,
                        tipoMovEquipProprio = TypeMovEquip.INPUT,
                        dthrMovEquipProprio = 1723213270250,
                        idEquipMovEquipProprio = 1,
                        matricColabMovEquipProprio = 19759,
                        destinoMovEquipProprio = "TESTE DESTINO",
                        notaFiscalMovEquipProprio = null,
                        observMovEquipProprio = null,
                        statusMovEquipProprio = StatusData.OPEN,
                        statusSendMovEquipProprio = StatusSend.SEND
                    )
                )
            )
            val result = repository.getObserv(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull(),
                null
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipProprioSharedPreferencesDatasource setSend`() =
        runTest {
            whenever(
                movEquipProprioRoomDatasource.setSend(1)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.setSend(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipProprioRepository.setSend -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if MovEquipProprioSharedPreferencesDatasource setSend execute successfully`() =
        runTest {
            whenever(
                movEquipProprioRoomDatasource.setSend(1)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setSend(1)
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
    fun `Check return failure if have error in movEquipProprioRoomDatasource get`() =
        runTest {
            whenever(
                movEquipProprioRoomDatasource.get(1)
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
                "IMovEquipProprioRepository.delete -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in movEquipProprioRoomDatasource delete`() =
        runTest {
            val model = MovEquipProprioRoomModel(
                idMovEquipProprio = 1,
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 1,
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = 1723213270250,
                idEquipMovEquipProprio = 1,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = null,
                observMovEquipProprio = null,
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND
            )
            whenever(
                movEquipProprioRoomDatasource.get(1)
            ).thenReturn(
                Result.success(model)
            )
            whenever(
                movEquipProprioRoomDatasource.delete(model)
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
                "IMovEquipProprioRepository.delete -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if MovEquipProprioRepository delete execute successfully`() =
        runTest {
            val model = MovEquipProprioRoomModel(
                idMovEquipProprio = 1,
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 1,
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = 1723213270250,
                idEquipMovEquipProprio = 1,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = null,
                observMovEquipProprio = null,
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND
            )
            whenever(
                movEquipProprioRoomDatasource.get(1)
            ).thenReturn(
                Result.success(model)
            )
            whenever(
                movEquipProprioRoomDatasource.delete(model)
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
    fun `Check return failure if have errors in MovEquipProprioRoomDatasource listSent`() =
        runTest {
            whenever(
                movEquipProprioRoomDatasource.listSent()
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
                "IMovEquipProprioRepository.listSent -> Unknown Error"
            )
        }

    @Test
    fun `Check return list if MovEquipProprioRepository listSent execute successfully`() =
        runTest {
            val roomModel = MovEquipProprioRoomModel(
                idMovEquipProprio = 1,
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 1,
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = 1723213270250,
                idEquipMovEquipProprio = 1,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SENT
            )
            whenever(
                movEquipProprioRoomDatasource.listSent()
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
                model.idMovEquipProprio,
                1
            )
            assertEquals(
                model.matricColabMovEquipProprio,
                19759
            )
            assertEquals(
                model.statusSendMovEquipProprio,
                StatusSend.SENT
            )
        }

    @Test
    fun `Check return failure if have errors in MovEquipProprioRoomDatasource checkOpen`() =
        runTest {
            whenever(
                movEquipProprioRoomDatasource.checkOpen()
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
                "IMovEquipProprioRepository.checkOpen -> Unknown Error"
            )
        }

    @Test
    fun `CheckOpen - Check return false if not have MovEquipProprio to open`() =
        runTest {
            whenever(
                movEquipProprioRoomDatasource.checkOpen()
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
    fun `CheckSend - Check return true if have MovEquipProprio to open`() =
        runTest {
            whenever(
                movEquipProprioRoomDatasource.checkOpen()
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