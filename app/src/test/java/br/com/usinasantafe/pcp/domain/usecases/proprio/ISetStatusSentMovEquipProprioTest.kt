package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipProprio
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ISetStatusSentMovEquipProprioTest {

    private val movEquipProprioRepository = mock<MovEquipProprioRepository>()
    private val usecase = ISetStatusSentMovProprio(movEquipProprioRepository)

    @Test
    fun `Check return failure if have error in MovEquipProprioRepository setStatusSent`() =
        runTest {
            val list = listOf(
                MovEquipProprio(
                    idMovEquipProprio = 1
                )
            )
            whenever(
                movEquipProprioRepository.setSent(list)
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
                "ISetStatusSentMovProprio -> Unknown Error"
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
            whenever(
                movEquipProprioRepository.setSent(list)
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