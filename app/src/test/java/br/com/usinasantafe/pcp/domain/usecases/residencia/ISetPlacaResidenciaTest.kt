package br.com.usinasantafe.pcp.domain.usecases.residencia

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcp.utils.FlowApp
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ISetPlacaResidenciaTest {

    private val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
    private val startProcessSendData = mock<StartProcessSendData>()
    private val usecase = ISetPlacaResidencia(
        movEquipResidenciaRepository,
        startProcessSendData
    )

    @Test
    fun `Check return failure if have error in MovEquipResidenciaRepository SetPlaca`() =
        runTest {
            whenever(
                movEquipResidenciaRepository.setPlaca(
                    placa = "Placa",
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(
                placa = "Placa",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetPlacaResidencia -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if SetPlacaResidenciaImpl execute successfully`() =
        runTest {
            whenever(
                movEquipResidenciaRepository.setPlaca(
                    placa = "Placa",
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(
                placa = "Placa",
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