package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipVisitTerc
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class CloseAllMovVisitTercImplTest {

    private val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
    private val usecase = ICloseAllMovVisitTerc(movEquipVisitTercRepository)

    @Test
    fun `Check return failure if have failure in MovEquipVisitTercRepository listOpen`() = runTest {
        whenever(
            movEquipVisitTercRepository.listOpen()
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val result = usecase()
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "ICloseAllMovVisitTerc -> Unknown Error"
        )
    }

    @Test
    fun `Check return failure if have failure in MovEquipVisitTercRepository setClose`() = runTest {
        val movEquipVisitTerc = MovEquipVisitTerc(
            idMovEquipVisitTerc = 1
        )
        whenever(movEquipVisitTercRepository.listOpen()).thenReturn(
            Result.success(
                listOf(
                    movEquipVisitTerc
                )
            )
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
        val result = usecase()
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "ICloseAllMovVisitTerc -> Unknown Error"
        )
    }

    @Test
    fun `Check return true if CloseAllMovVisitTercImpl execute successfully`() = runTest {
        val movEquipVisitTerc = MovEquipVisitTerc(
            idMovEquipVisitTerc = 1
        )
        val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
        whenever(movEquipVisitTercRepository.listOpen()).thenReturn(
            Result.success(
                listOf(
                    movEquipVisitTerc
                )
            )
        )
        whenever(
            movEquipVisitTercRepository.setClose(
                1
            )
        ).thenReturn(
            Result.success(true)
        )
        val usecase = ICloseAllMovVisitTerc(
            movEquipVisitTercRepository
        )
        val result = usecase()
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!,
            true
        )
    }
}