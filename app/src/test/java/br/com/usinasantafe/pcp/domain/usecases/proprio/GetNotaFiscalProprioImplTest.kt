package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class GetNotaFiscalProprioImplTest {

    @Test
    fun `Check return failure if have error in MovEquipProprioRepository getNotaFiscal`() = runTest {
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        whenever(
            movEquipProprioRepository.getNotaFiscal(id = 1)
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val usecase = IGetNotaFiscalProprio(
            movEquipProprioRepository
        )
        val result = usecase(id = 1)
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Repository -> MovEquipProprioRepository.getNotaFiscal")
    }

    @Test
    fun `Check return nota fiscal if GetNotaFiscalImpl execute success`() = runTest {
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        whenever(
            movEquipProprioRepository.getNotaFiscal(id = 1)
        ).thenReturn(
            Result.success(123456)
        )
        val usecase = IGetNotaFiscalProprio(
            movEquipProprioRepository
        )
        val result = usecase(id = 1)
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!, "123456")
    }

    @Test
    fun `Check return null if GetNotaFiscalImpl execute success and field is null`() = runTest {
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        whenever(
            movEquipProprioRepository.getNotaFiscal(id = 1)
        ).thenReturn(
            Result.success(null)
        )
        val usecase = IGetNotaFiscalProprio(
            movEquipProprioRepository
        )
        val result = usecase(id = 1)
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull(), null)
    }

}