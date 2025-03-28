package br.com.usinasantafe.pcp.domain.usecases.residencia

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IGetObservResidenciaTest {

    @Test
    fun `Check return failure if have error in MovEquipResidenciaRepository GetObserv`() =
        runTest {
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            whenever(
                movEquipResidenciaRepository.getObserv(
                    id = 1
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val usecase = IGetObservResidencia(
                movEquipResidenciaRepository
            )
            val result = usecase(id = 1)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipResidenciaRepository.GetObserv"
            )
        }

    @Test
    fun `Check return observ if GetObservResidenciaImpl execute successfully`() =
        runTest {
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            whenever(
                movEquipResidenciaRepository.getObserv(
                    id = 1
                )
            ).thenReturn(
                Result.success("Observacao")
            )
            val usecase = IGetObservResidencia(
                movEquipResidenciaRepository
            )
            val result = usecase(id = 1)
            assertTrue(result.isSuccess)
            assertEquals(result.getOrNull()!!, "Observacao")
        }


    @Test
    fun `Check return observ if GetObservResidenciaImpl execute successfully and return null`() =
        runTest {
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            whenever(
                movEquipResidenciaRepository.getObserv(
                    id = 1
                )
            ).thenReturn(
                Result.success(null)
            )
            val usecase = IGetObservResidencia(
                movEquipResidenciaRepository
            )
            val result = usecase(id = 1)
            assertTrue(result.isSuccess)
            assertNull(result.getOrNull())
        }
}