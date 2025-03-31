package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class CheckSendMovProprioImplTest {

    private val movEquipProprioRepository = mock<MovEquipProprioRepository>()
    private val usecase = ICheckSendMovProprio(
        movEquipProprioRepository = movEquipProprioRepository
    )

    @Test
    fun `Check return failure if have error in MovEquipProprioRepository checkSend`() =
        runTest {
            whenever(
                movEquipProprioRepository.checkSend()
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
                "ICheckSendMovProprio -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if have mov to send`() =
        runTest {
            whenever(
                movEquipProprioRepository.checkSend()
            ).thenReturn(
                Result.success(true)
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

    @Test
    fun `Check return false if not have mov to send`() =
        runTest {
            whenever(
                movEquipProprioRepository.checkSend()
            ).thenReturn(
                Result.success(false)
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                false
            )
        }

}