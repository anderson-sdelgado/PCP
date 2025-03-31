package br.com.usinasantafe.pcp.domain.usecases.residencia

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IGetObservResidenciaTest {

    private val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
    private val usecase = IGetObservResidencia(
        movEquipResidenciaRepository
    )

    @Test
    fun `Check return failure if have error in MovEquipResidenciaRepository GetObserv`() =
        runTest {
            whenever(
                movEquipResidenciaRepository.getObserv(
                    id = 1
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(id = 1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetObservResidencia -> Unknown Error"
            )
        }

    @Test
    fun `Check return observ if GetObservResidenciaImpl execute successfully`() =
        runTest {
            whenever(
                movEquipResidenciaRepository.getObserv(
                    id = 1
                )
            ).thenReturn(
                Result.success("Observacao")
            )
            val result = usecase(id = 1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull(),
                "Observacao"
            )
        }


    @Test
    fun `Check return observ if GetObservResidenciaImpl execute successfully and return null`() =
        runTest {
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
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull(),
                null
            )
        }
}