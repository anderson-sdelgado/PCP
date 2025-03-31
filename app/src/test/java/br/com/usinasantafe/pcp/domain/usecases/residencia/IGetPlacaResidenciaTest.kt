package br.com.usinasantafe.pcp.domain.usecases.residencia

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IGetPlacaResidenciaTest {

    private val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
    private val usecase = IGetPlacaResidencia(
        movEquipResidenciaRepository
    )

    @Test
    fun `Check return failure if have error in MovEquipResidenciaRepository GetPlaca`() =
        runTest {
            whenever(
                movEquipResidenciaRepository.getPlaca(
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
                "IGetPlacaResidencia -> Unknown Error"
            )
        }

    @Test
    fun `Check return placa if GetPlacaResidenciaImpl execute successfully`() =
        runTest {
            whenever(
                movEquipResidenciaRepository.getPlaca(
                    id = 1
                )
            ).thenReturn(
                Result.success("Placa")
            )
            val result = usecase(id = 1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                "Placa"
            )
        }
}