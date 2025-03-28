package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class GetObservProprioImplTest {

    @Test
    fun `Check return failure if have error in MovEquipProprioRepository getObserv`() = runTest {
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        whenever(
            movEquipProprioRepository.getObserv(
                id = 1
            )
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val usecase = IGetObservProprio(
            movEquipProprioRepository
        )
        val result = usecase(id = 1)
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Repository -> MovEquipProprioRepository.getObserv")
    }

    @Test
    fun `Check return observ if GetObservImpl execute success`() = runTest {
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        whenever(
            movEquipProprioRepository.getObserv(
                id = 1
            )
        ).thenReturn(
            Result.success("Observação")
        )
        val usecase = IGetObservProprio(
            movEquipProprioRepository
        )
        val result = usecase(id = 1)
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull(), "Observação")
    }


    @Test
    fun `Check return null if GetObservImpl execute success and field is null`() = runTest {
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        whenever(
            movEquipProprioRepository.getObserv(
                id = 1
            )
        ).thenReturn(
            Result.success(null)
        )
        val usecase = IGetObservProprio(
            movEquipProprioRepository
        )
        val result = usecase(id = 1)
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull(), null)
    }
}