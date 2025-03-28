package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class GetPlacaVisitTercImplTest {

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository getPlaca`() = runTest {
        val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
        whenever(
            movEquipVisitTercRepository.getPlaca(
                id = 1
            )
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val usecase = IGetPlacaVisitTerc(
            movEquipVisitTercRepository
        )
        val result = usecase(id = 1)
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Repository -> MovEquipVisitTercRepository.getPlaca"
        )
    }

    @Test
    fun `Check return placa if GetPlaca execute success`() = runTest {
        val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
        whenever(
            movEquipVisitTercRepository.getPlaca(
                id = 1
            )
        ).thenReturn(
            Result.success(
                "Placa"
            )
        )
        val usecase = IGetPlacaVisitTerc(
            movEquipVisitTercRepository
        )
        val result = usecase(id = 1)
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!, "Placa")
    }
}