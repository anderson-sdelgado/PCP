package br.com.usinasantafe.pcp.domain.usecases.residencia

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipResidencia
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.StatusData
import br.com.usinasantafe.pcp.utils.StatusForeigner
import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.utils.TypeMovEquip
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.Date

class ISetObservResidenciaTest {

    private val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
    private val startProcessSendData = mock<StartProcessSendData>()

    private fun getUsecase() = ISetObservResidencia(
        movEquipResidenciaRepository,
        startProcessSendData
    )

    @Test
    fun `Check return true if SetObservResidenciaImpl - TypeMov OUTPUT and FlowApp ADD - execute successfully`() =
        runTest {
            val model = MovEquipResidencia(
                idMovEquipResidencia = 1,
                matricVigiaMovEquipResidencia = 1000,
                idLocalMovEquipResidencia = 1000,
                tipoMovEquipResidencia = TypeMovEquip.INPUT,
                dthrMovEquipResidencia = Date(1723213270250),
                motoristaMovEquipResidencia = "MOTORISTA TESTE",
                veiculoMovEquipResidencia = "VEICULO TESTE",
                placaMovEquipResidencia = "PLACA TESTE",
                observMovEquipResidencia = "OBSERVACAO TESTE",
                statusMovEquipResidencia = StatusData.OPEN,
                statusSendMovEquipResidencia = StatusSend.SEND,
                statusMovEquipForeignerResidencia = StatusForeigner.INSIDE,
            )
            whenever(
                movEquipResidenciaRepository.get(
                    id = 1
                )
            ).thenReturn(
                Result.success(model)
            )
            model.observMovEquipResidencia = "observ"
            model.tipoMovEquipResidencia = TypeMovEquip.OUTPUT
            model.dthrMovEquipResidencia = Date()
            model.statusMovEquipForeignerResidencia = StatusForeigner.OUTSIDE

            val usecase = getUsecase()
            val result = usecase(
                observ = "observ",
                flowApp = FlowApp.ADD,
                id = 1
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository setObserv`() =
        runTest {
            whenever(
                movEquipResidenciaRepository.setObserv(
                    observ = "observ",
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val usecase = getUsecase()
            val result = usecase(
                observ = "observ",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipResidenciaRepository.setObserv"
            )
        }

    @Test
    fun `Check return true if SetObservVisitTercImpl - NOT TypeMov OUTPUT and FlowApp ADD - execute successfully`() =
        runTest {
            whenever(
                movEquipResidenciaRepository.setObserv(
                    observ = "observ",
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(true)
            )
            val usecase = getUsecase()
            val result = usecase(
                observ = "observ",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }
}