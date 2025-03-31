package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class GetDestinoVisitTercImplTest {

    private val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
    private val usecase = IGetDestinoVisitTerc(movEquipVisitTercRepository)

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository getDestino`() = runTest {
        whenever(
            movEquipVisitTercRepository.getDestino(
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
            "IGetDestinoVisitTerc -> Unknown Error"
        )
    }

    @Test
    fun `Check return destino if GetDestino execute success`() = runTest {
        val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
        whenever(
            movEquipVisitTercRepository.getDestino(
                id = 1
            )
        ).thenReturn(
            Result.success("Destino")
        )
        val usecase = IGetDestinoVisitTerc(
            movEquipVisitTercRepository,
        )
        val result = usecase(id = 1)
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!,
            "Destino"
        )
    }
}