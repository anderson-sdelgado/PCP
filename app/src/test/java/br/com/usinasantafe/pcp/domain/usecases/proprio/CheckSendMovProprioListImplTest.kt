package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class CheckSendMovProprioImplTest {

    @Test
    fun `Check return failure if have error in MovEquipProprioRepository checkSend`() =
        runTest {
            val movEquipProprioRepository = mock<MovEquipProprioRepository>()
            whenever(
                movEquipProprioRepository.checkSend()
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val usecase = ICheckSendMovProprio(movEquipProprioRepository)
            val result = usecase()
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipProprioRepository.checkSend"
            )
        }

    @Test
    fun `Check return true if have mov to send`() =
        runTest {
            val movEquipProprioRepository = mock<MovEquipProprioRepository>()
            whenever(
                movEquipProprioRepository.checkSend()
            ).thenReturn(
                Result.success(true)
            )
            val usecase = ICheckSendMovProprio(movEquipProprioRepository)
            val result = usecase()
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return false if not have mov to send`() =
        runTest {
            val movEquipProprioRepository = mock<MovEquipProprioRepository>()
            whenever(
                movEquipProprioRepository.checkSend()
            ).thenReturn(
                Result.success(false)
            )
            val usecase = ICheckSendMovProprio(movEquipProprioRepository)
            val result = usecase()
            assertTrue(result.isSuccess)
            assertFalse(result.getOrNull()!!)
        }

}