package br.com.usinasantafe.pcp.domain.usecases.chave

import br.com.usinasantafe.pcp.domain.entities.variable.MovChave
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class ISetStatusSentMovChaveTest {

    private val movChaveRepository = mock<MovChaveRepository>()
    private val usecase = ISetStatusSentMovChave(movChaveRepository)

    @Test
    fun `Check return failure if have error in MovChaveRepository setSent`() =
        runTest {
            val list = listOf(
                MovChave(
                    idMovChave = 1
                )
            )
            whenever(
                movChaveRepository.setSent(list)
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
                "ISetObservMovChave -> Unknown Error"
            )
        }

    @Test
    fun `Check return correct if function execute successfully`() =
        runTest {
            val list = listOf(
                MovChave(
                    idMovChave = 1
                )
            )
            whenever(
                movChaveRepository.setSent(list)
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