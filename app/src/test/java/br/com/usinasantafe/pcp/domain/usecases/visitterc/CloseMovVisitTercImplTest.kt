package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipVisitTerc
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class CloseMovVisitTercImplTest {

    @Test
    fun `Check return failure if have failure in MovEquipVisitTercRepository get`() = runTest {
        val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
        whenever(
            movEquipVisitTercRepository.get(1)
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val usecase = ICloseMovVisitTerc(
            movEquipVisitTercRepository,
        )
        val result = usecase(1)
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Repository -> MovEquipVisitTercRepository.get"
        )
    }

    @Test
    fun `Check return failure if have failure in MovEquipVisitTercRepository setClose`() = runTest {
        val movEquipVisitTerc = MovEquipVisitTerc(
            idMovEquipVisitTerc = 1
        )
        val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
        whenever(
            movEquipVisitTercRepository.get(1)
        ).thenReturn(
            Result.success(movEquipVisitTerc)
        )
        whenever(
            movEquipVisitTercRepository.setClose(
                1
            )
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val usecase = ICloseMovVisitTerc(
            movEquipVisitTercRepository
        )
        val result = usecase(1)
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Repository -> MovEquipVisitTercRepository.setClose"
        )
    }

    @Test
    fun `Check return true if CloseMovVisitTercImpl execute successfully`() = runTest {
        val movEquipVisitTerc = MovEquipVisitTerc(
            idMovEquipVisitTerc = 1
        )
        val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
        whenever(
            movEquipVisitTercRepository.get(1)
        ).thenReturn(
            Result.success(movEquipVisitTerc)
        )
        whenever(
            movEquipVisitTercRepository.setClose(
                1
            )
        ).thenReturn(
            Result.success(true)
        )
        val usecase = ICloseMovVisitTerc(
            movEquipVisitTercRepository
        )
        val result = usecase(1)
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
    }
}