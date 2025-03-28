package br.com.usinasantafe.pcp.domain.usecases.updatetable.cleantable

import br.com.usinasantafe.pcp.domain.repositories.stable.ColabRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ICleanColabTest {

    @Test
    fun `Check execution correct`() = runTest {
        val colabRepository = mock<ColabRepository>()
        whenever(
            colabRepository.deleteAll()
        ).thenReturn(
            Result.success(true)
        )
        val usecase =
            ICleanColab(
                colabRepository
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
    fun `Check execution incorrect`() = runTest {
        val colabRepository = mock<ColabRepository>()
        whenever(
            colabRepository.deleteAll()
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val usecase =
            ICleanColab(
                colabRepository
            )
        val result = usecase()
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> ColabRepository.deleteAll"
        )
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            "java.lang.Exception"
        )
    }
}