package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipProprio
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ISetStatusSentMovEquipProprioTest {

    @Test
    fun `Check return failure if have error in MovEquipProprioRepository setStatusSent`() =
        runTest {
            val list = listOf(
                MovEquipProprio(
                    idMovEquipProprio = 1
                )
            )
            val movEquipProprioRepository = mock<MovEquipProprioRepository>()
            whenever(
                movEquipProprioRepository.setSent(list)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val usecase = ISetStatusSentMovProprio(movEquipProprioRepository)
            val result = usecase(list)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> SetStatusSentMovProprioImpl"
            )
        }

    @Test
    fun `Check return true if MovEquipProprioRepository setStatusSent execute successfully`() =
        runTest {
            val list = listOf(
                MovEquipProprio(
                    idMovEquipProprio = 1
                )
            )
            val movEquipProprioRepository = mock<MovEquipProprioRepository>()
            whenever(
                movEquipProprioRepository.setSent(list)
            ).thenReturn(
                Result.success(true)
            )
            val usecase = ISetStatusSentMovProprio(movEquipProprioRepository)
            val result = usecase(list)
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }
}