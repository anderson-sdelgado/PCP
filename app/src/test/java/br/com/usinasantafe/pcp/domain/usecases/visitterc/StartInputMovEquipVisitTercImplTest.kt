package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class StartInputMovEquipVisitTercImplTest {

    private val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
    private val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
    private val usecase = IStartInputMovEquipVisitTerc(
        movEquipVisitTercRepository,
        movEquipVisitTercPassagRepository
    )

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository start`() =
        runTest {
            whenever(
                movEquipVisitTercRepository.start()
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
                "IStartInputMovEquipVisitTerc -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercPassagRepository clear`() =
        runTest {
            whenever(
                movEquipVisitTercRepository.start()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipVisitTercPassagRepository.clear()
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
                "IStartInputMovEquipVisitTerc -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if StartMovEquipVisitTercImpl execute successfully`() =
        runTest {
            whenever(
                movEquipVisitTercRepository.start()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipVisitTercPassagRepository.clear()
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

}