package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipVisitTerc
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class SetStatusSentMovVisitTercImplTest {

    private val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
    private val usecase = ISetStatusSentMovVisitTerc(movEquipVisitTercRepository)

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository setStatusSent`() =
        runTest {
            val list = listOf(
                MovEquipVisitTerc(
                    idMovEquipVisitTerc = 1
                )
            )
            whenever(
                movEquipVisitTercRepository.setSent(list)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(list)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetStatusSentMovVisitTerc -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if MovEquipVisitTercRepository setStatusSent execute successfully`() =
        runTest {
            val list = listOf(
                MovEquipVisitTerc(
                    idMovEquipVisitTerc = 1
                )
            )
            whenever(
                movEquipVisitTercRepository.setSent(list)
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(list)
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