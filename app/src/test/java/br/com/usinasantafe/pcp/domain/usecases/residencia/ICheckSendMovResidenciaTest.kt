package br.com.usinasantafe.pcp.domain.usecases.residencia

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ICheckSendMovResidenciaTest {

    @Test
    fun `Check return failure if have error in MovEquipResidenciaRepository checkSend`() =
        runTest {
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            whenever(
                movEquipResidenciaRepository.checkSend()
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val usecase = ICheckSendMovResidencia(movEquipResidenciaRepository)
            val result = usecase()
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipResidenciaRepository.checkSend"
            )
        }

    @Test
    fun `Check return true if have mov to send`() =
        runTest {
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            whenever(
                movEquipResidenciaRepository.checkSend()
            ).thenReturn(
                Result.success(true)
            )
            val usecase = ICheckSendMovResidencia(movEquipResidenciaRepository)
            val result = usecase()
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return false if not have mov to send`() =
        runTest {
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            whenever(
                movEquipResidenciaRepository.checkSend()
            ).thenReturn(
                Result.success(false)
            )
            val usecase = ICheckSendMovResidencia(movEquipResidenciaRepository)
            val result = usecase()
            assertTrue(result.isSuccess)
            assertFalse(result.getOrNull()!!)
        }
}