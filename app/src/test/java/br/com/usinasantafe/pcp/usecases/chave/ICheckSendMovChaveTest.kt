package br.com.usinasantafe.pcp.domain.usecases.chave

import br.com.usinasantafe.pcp.domain.errors.RepositoryException
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class ICheckSendMovChaveTest {

    private val movChaveRepository = mock<MovChaveRepository>()
    private val usecase = ICheckSendMovChave(movChaveRepository)

    @Test
    fun `Check return failure if have error in MovChaveRepository checkSend`() =
        runTest {
            whenever(
                movChaveRepository.checkSend()
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "IMovChaveRepository.checkSend",
                        cause = Exception()
                    )
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> IMovChaveRepository.checkSend"
            )
        }

    @Test
    fun `Check return correct if function execute successfully`() =
        runTest {
            whenever(
                movChaveRepository.checkSend()
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