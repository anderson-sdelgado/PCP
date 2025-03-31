package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class GetPlacaVisitTercImplTest {

    private val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
    private val usecase = IGetPlacaVisitTerc(
        movEquipVisitTercRepository
    )

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository getPlaca`() = runTest {
        whenever(
            movEquipVisitTercRepository.getPlaca(
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
            "IGetPlacaVisitTerc -> Unknown Error"
        )
    }

    @Test
    fun `Check return placa if GetPlaca execute success`() = runTest {
        whenever(
            movEquipVisitTercRepository.getPlaca(
                id = 1
            )
        ).thenReturn(
            Result.success(
                "Placa"
            )
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