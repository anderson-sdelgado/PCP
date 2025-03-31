package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IGetNotaFiscalProprioTest {

    private val movEquipProprioRepository = mock<MovEquipProprioRepository>()
    private val usecase = IGetNotaFiscalProprio(
        movEquipProprioRepository
    )

    @Test
    fun `Check return failure if have error in MovEquipProprioRepository getNotaFiscal`() = runTest {
        whenever(
            movEquipProprioRepository.getNotaFiscal(id = 1)
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
            "IGetNotaFiscalProprio -> Unknown Error"
        )
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
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!,
            "123456"
        )
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