package br.com.usinasantafe.pcp.domain.usecases.config

import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class SetIdLocalConfigImplTest {

    @Test
    fun `Chech return failure Datasource if have error in ConfigRepository SetIdLocal`() = runTest {
        val configRepository = mock<ConfigRepository>()
        whenever(
            configRepository.setIdLocal(1)
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val usecase = ISetIdLocalConfig(configRepository)
        val result = usecase(1)
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Repository -> ConfigRepository.setIdLocal")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), Exception().toString())
    }

    @Test
    fun `Chech return true if usecase is success`() = runTest {
        val configRepository = mock<ConfigRepository>()
        whenever(configRepository.setIdLocal(1)).thenReturn(
            Result.success(true)
        )
        val usecase = ISetIdLocalConfig(configRepository)
        val result = usecase(1)
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
    }
}