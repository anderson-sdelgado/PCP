package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipProprio
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class ICloseMovProprioTest {

    private val movEquipProprioRepository = mock<MovEquipProprioRepository>()
    private val usecase = ICloseMovProprio(
        movEquipProprioRepository,
    )

    @Test
    fun `Check return failure if have failure in MovEquipProprioRepository setClose`() = runTest {
        whenever(
            movEquipProprioRepository.setClose(1)
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val result = usecase(1)
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "ICloseMovProprio -> Unknown Error"
        )
    }

    @Test
    fun `Check return true if CloseMovProprioImpl execute successfully`() = runTest {
        val movEquipProprio = MovEquipProprio(
            idMovEquipProprio = 1
        )
        whenever(
            movEquipProprioRepository.get(1)
        ).thenReturn(
            Result.success(movEquipProprio)
        )
        whenever(
            movEquipProprioRepository.setClose(
                1
            )
        ).thenReturn(
            Result.success(true)
        )
        val result = usecase(1)
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