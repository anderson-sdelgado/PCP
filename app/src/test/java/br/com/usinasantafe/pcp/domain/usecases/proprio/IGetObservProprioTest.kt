package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IGetObservProprioTest {

    private val movEquipProprioRepository = mock<MovEquipProprioRepository>()
    private val usecase = IGetObservProprio(
        movEquipProprioRepository
    )

    @Test
    fun `Check return failure if have error in MovEquipProprioRepository getObserv`() = runTest {
        whenever(
            movEquipProprioRepository.getObserv(
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
            "IGetObservProprio -> Unknown Error"
        )
    }

    @Test
    fun `Check return observ if GetObservImpl execute success`() = runTest {
        whenever(
            movEquipProprioRepository.getObserv(
                id = 1
            )
        ).thenReturn(
            Result.success("Observação")
        )
        val result = usecase(id = 1)
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull(),
            "Observação"
        )
    }


    @Test
    fun `Check return null if GetObservImpl execute success and field is null`() = runTest {
        whenever(
            movEquipProprioRepository.getObserv(
                id = 1
            )
        ).thenReturn(
            Result.success(null)
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