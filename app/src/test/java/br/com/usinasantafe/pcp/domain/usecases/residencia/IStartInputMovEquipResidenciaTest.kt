package br.com.usinasantafe.pcp.domain.usecases.residencia

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IStartInputMovEquipResidenciaTest {

    private val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
    private val usecase = IStartInputMovEquipResidencia(
        movEquipResidenciaRepository
    )

    @Test
    fun `Check return failure if have error in MovEquipResidenciaRepository start`() =
        runTest {
            whenever(
                movEquipResidenciaRepository.start()
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IStartInputMovEquipResidencia -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if StartMovEquipResidenciaImplTest execute successfully`() =
        runTest {
            whenever(
                movEquipResidenciaRepository.start()
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase()
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