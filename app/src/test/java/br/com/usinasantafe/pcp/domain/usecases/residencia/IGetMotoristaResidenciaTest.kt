package br.com.usinasantafe.pcp.domain.usecases.residencia

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IGetMotoristaResidenciaTest {

    @Test
    fun `Check return failure if have error in MovEquipResidenciaRepository GetMotorista`() =
        runTest {
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            whenever(
                movEquipResidenciaRepository.getMotorista(
                    id = 1
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val usecase = IGetMotoristaResidencia(
                movEquipResidenciaRepository
            )
            val result = usecase(id = 1)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipResidenciaRepository.GetMotorista"
            )
        }

    @Test
    fun `Check return motorista if GetMotoristaResidenciaImpl execute successfully`() =
        runTest {
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            whenever(
                movEquipResidenciaRepository.getMotorista(
                    id = 1
                )
            ).thenReturn(
                Result.success("Motorista")
            )
            val usecase = IGetMotoristaResidencia(
                movEquipResidenciaRepository
            )
            val result = usecase(id = 1)
            assertTrue(result.isSuccess)
            assertEquals(result.getOrNull()!!, "Motorista")
        }
}