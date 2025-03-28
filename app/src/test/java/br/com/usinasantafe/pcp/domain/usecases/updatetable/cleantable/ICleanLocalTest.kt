package br.com.usinasantafe.pcp.domain.usecases.updatetable.cleantable

import br.com.usinasantafe.pcp.domain.repositories.stable.LocalRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ICleanLocalTest {

    @Test
    fun `Check execution correct`() = runTest {
        val localRepository = mock<LocalRepository>()
        whenever(localRepository.deleteAll()).thenReturn(Result.success(true))
        val usecase = ICleanLocal(localRepository)
        val result = usecase()
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull(), true)
    }

    @Test
    fun `Check execution incorrect`() = runTest {
        val localRepository = mock<LocalRepository>()
        whenever(
            localRepository.deleteAll()
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val usecase = ICleanLocal(localRepository)
        val result = usecase()
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> LocalRepository.deleteAll")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), "java.lang.Exception")
    }
}