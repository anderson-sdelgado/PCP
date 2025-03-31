package br.com.usinasantafe.pcp.domain.usecases.residencia

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipResidencia
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ISetStatusSentMovResidenciaTest {

    private val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
    private val usecase = ISetStatusSentMovResidencia(movEquipResidenciaRepository)

    @Test
    fun `Check return failure if have error in MovEquipResidenciaRepository setStatusSent`() =
        runTest {
            val list = listOf(
                MovEquipResidencia(
                    idMovEquipResidencia = 1
                )
            )
            whenever(
                movEquipResidenciaRepository.setSent(list)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(list)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetStatusSentMovResidencia -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if MovEquipResidenciaRepository setStatusSent execute successfully`() =
        runTest {
            val list = listOf(
                MovEquipResidencia(
                    idMovEquipResidencia = 1
                )
            )
            whenever(
                movEquipResidenciaRepository.setSent(list)
            ).thenReturn(
                Result.success(true)
            )
            val usecase = ISetStatusSentMovResidencia(movEquipResidenciaRepository)
            val result = usecase(list)
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