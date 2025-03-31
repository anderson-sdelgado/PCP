package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class GetObservVisitTercImplTest {

    private val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
    private val usecase = IGetObservVisitTerc(
        movEquipVisitTercRepository
    )

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository getObserv`() = runTest {
        whenever(
            movEquipVisitTercRepository.getObserv(
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
            "IGetObservVisitTerc -> Unknown Error"
        )
    }

    @Test
    fun `Check return observ if GetObservImpl execute success`() = runTest {
        whenever(
            movEquipVisitTercRepository.getObserv(
                id = 1
            )
        ).thenReturn(
            Result.success("Observação")
        )
        val usecase = IGetObservVisitTerc(
            movEquipVisitTercRepository
        )
        val result = usecase(id = 1)
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!,
            "Observação"
        )
    }

    @Test
    fun `Check return observ if GetObservImpl execute success and field is null`() = runTest {
        whenever(
            movEquipVisitTercRepository.getObserv(
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