package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class CheckSendMovVisitTercImplTest {

    private val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
    private val usecase = ICheckSendMovVisitTerc(movEquipVisitTercRepository)

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository checkSend`() =
        runTest {
            whenever(
                movEquipVisitTercRepository.checkSend()
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
                "ICheckSendMovResidencia -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if have mov to send`() =
        runTest {
            whenever(
                movEquipVisitTercRepository.checkSend()
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull(),
                true
            )
        }

    @Test
    fun `Check return false if not have mov to send`() =
        runTest {
            whenever(
                movEquipVisitTercRepository.checkSend()
            ).thenReturn(
                Result.success(false)
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull(),
                false
            )
        }
}