package br.com.usinasantafe.pcp.domain.usecases.residencia

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcp.utils.FlowApp
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ISetMotoristaResidenciaTest {

    @Test
    fun `Check return failure if have error in MovEquipResidenciaRepository SetMotorista`() =
        runTest {
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(
                movEquipResidenciaRepository.setMotorista(
                    motorista = "Motorista",
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val usecase = ISetMotoristaResidencia(
                movEquipResidenciaRepository,
                startProcessSendData
            )
            val result = usecase(
                motorista = "Motorista",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipResidenciaRepository.SetMotorista"
            )
        }

    @Test
    fun `Check return true if SetMotoristaResidenciaImpl execute successfully`() =
        runTest {
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(
                movEquipResidenciaRepository.setMotorista(
                    motorista = "Motorista",
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(true)
            )
            val usecase = ISetMotoristaResidencia(
                movEquipResidenciaRepository,
                startProcessSendData
            )
            val result = usecase(
                motorista = "Motorista",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }
}