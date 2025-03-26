package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipProprio
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class ICloseAllMovProprioTest {

    @Test
    fun `Check return failure if have failure in MovEquipProprioRepository listOpen`() = runTest {
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        whenever(
            movEquipProprioRepository.listOpen()
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val usecase = ICloseAllMovProprio(
            movEquipProprioRepository,
        )
        val result = usecase()
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Repository -> MovEquipProprioRepository.listOpen"
        )
    }

    @Test
    fun `Check return failure if have failure in MovEquipProprioRepository setClose`() = runTest {
        val movEquipProprio = MovEquipProprio(
            idMovEquipProprio = 1
        )
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        whenever(
            movEquipProprioRepository.listOpen()
        ).thenReturn(
            Result.success(
                listOf(
                    movEquipProprio
                )
            )
        )
        whenever(
            movEquipProprioRepository.setClose(
                1
            )
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val usecase = ICloseAllMovProprio(
            movEquipProprioRepository
        )
        val result = usecase()
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Repository -> MovEquipProprioRepository.setClose"
        )
    }

    @Test
    fun `Check return true if CloseAllMovProprioImpl execute successfully`() = runTest {
        val movEquipProprio = MovEquipProprio(
            idMovEquipProprio = 1
        )
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        whenever(
            movEquipProprioRepository.listOpen()
        ).thenReturn(
            Result.success(
                listOf(
                    movEquipProprio
                )
            )
        )
        whenever(
            movEquipProprioRepository.setClose(
                1
            )
        ).thenReturn(
            Result.success(true)
        )
        val usecase = ICloseAllMovProprio(
            movEquipProprioRepository
        )
        val result = usecase()
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
    }
}