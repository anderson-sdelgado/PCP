package br.com.usinasantafe.pcp.domain.usecases.chave

import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class IStartRemoveMovChaveTest {

    private val movChaveRepository = mock<MovChaveRepository>()
    private val usecase = IStartRemoveMovChave(
        movChaveRepository = movChaveRepository
    )

    @Test
    fun `Check return failure if have error in MovChaveRepository start`() =
        runTest {
            whenever(
                movChaveRepository.start()
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
                "IStartRemoveMovChave -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if IStartRemoveChave execute successfully`() =
        runTest {
            whenever(
                movChaveRepository.start()
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