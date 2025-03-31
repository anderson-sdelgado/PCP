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
    private val usecase = ISetObservResidencia(
        movEquipResidenciaRepository,
        startProcessSendData
    )

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
            val result = usecase(
                observ = "observ",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetObservResidencia -> Unknown Error"
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
            val result = usecase(
                observ = "observ",
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
}