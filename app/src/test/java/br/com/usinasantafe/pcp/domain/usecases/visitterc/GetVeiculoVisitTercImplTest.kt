package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class GetVeiculoVisitTercImplTest {

    private val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
    private val usecase = IGetVeiculoVisitTerc(
        movEquipVisitTercRepository
    )

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository getVeiculo`() =
        runTest {
            whenever(
                movEquipVisitTercRepository.getVeiculo(
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
                "IGetVeiculoVisitTerc -> Unknown Error"
            )
        }

    @Test
    fun `Check return veiculo if GetVeiculoVisitTercImpl execute successfully`() = runTest {
        whenever(
            movEquipVisitTercRepository.getVeiculo(
                id = 1
            )
        ).thenReturn(
            Result.success(
                "Veiculo"
            )
        )
        val usecase = IGetVeiculoVisitTerc(
            movEquipVisitTercRepository
        )
        val result = usecase(id = 1)
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!,
            "Veiculo"
        )
    }
}