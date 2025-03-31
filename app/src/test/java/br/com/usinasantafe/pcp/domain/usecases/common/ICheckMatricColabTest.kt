package br.com.usinasantafe.pcp.domain.usecases.common

import br.com.usinasantafe.pcp.domain.repositories.stable.ColabRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class ICheckMatricColabTest {

    @Test
    fun `Check return failure if matric is incorrect`() = runTest {
        val colabRepository = mock<ColabRepository>()
        val usecase = ICheckMatricColab(colabRepository)
        val result = usecase("19759a")
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "ICheckMatricColab"
        )
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            "java.lang.NumberFormatException: For input string: \"19759a\""
        )
    }

    @Test
    fun `Check return failure if have error in Repository`() = runTest {
        val colabRepository = mock<ColabRepository>()
        whenever(
            colabRepository.checkMatric(19759)
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val usecase = ICheckMatricColab(colabRepository)
        val result = usecase("19759")
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "ICheckMatricColab -> Unknown Error"
        )
    }

    @Test
    fun `Check return true in executed correctly`() = runTest {
        val colabRepository = mock<ColabRepository>()
        whenever(colabRepository.checkMatric(19759)).thenReturn(
            Result.success(true)
        )
        val usecase = ICheckMatricColab(colabRepository)
        val result = usecase("19759")
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!, true)
    }

    @Test
    fun `Check return false in executed correctly`() = runTest {
        val colabRepository = mock<ColabRepository>()
        whenever(
            colabRepository.checkMatric(19759)
        ).thenReturn(
            Result.success(false)
        )
        val usecase = ICheckMatricColab(colabRepository)
        val result = usecase("19759")
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