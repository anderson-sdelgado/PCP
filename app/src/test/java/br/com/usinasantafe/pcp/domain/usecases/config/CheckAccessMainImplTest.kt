package br.com.usinasantafe.pcp.domain.usecases.config

import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.utils.FlagUpdate
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class CheckAccessMainImplTest {

    @Test
    fun `Check return failure if have failure in hasConfig`() = runTest {
        val configRepository = mock<ConfigRepository>()
        whenever(
            configRepository.hasConfig()
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val usecase = ICheckAccessMain(configRepository)
        val result = usecase()
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> ConfigRepository.hasConfig")
    }

    @Test
    fun `Check not access if don't have db config save`() = runTest {
        val configRepository = mock<ConfigRepository>()
        whenever(
            configRepository.hasConfig()
        ).thenReturn(
            Result.success(false)
        )
        val usecase = ICheckAccessMain(configRepository)
        val result = usecase()
        assertTrue(result.isSuccess)
        assertFalse(result.getOrNull()!!)
    }

    @Test
    fun `Check return failure if have failure in getFlagUpdate`() = runTest {
        val configRepository = mock<ConfigRepository>()
        whenever(
            configRepository.hasConfig()
        ).thenReturn(
            Result.success(true)
        )
        whenever(configRepository.getFlagUpdate()).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val usecase = ICheckAccessMain(configRepository)
        val result = usecase()
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> ConfigRepository.getFlagUpdate")
    }

    @Test
    fun `Check return false if have flagUpdate is OUTDATED`() = runTest {
        val configRepository = mock<ConfigRepository>()
        whenever(
            configRepository.hasConfig()
        ).thenReturn(
            Result.success(true)
        )
        whenever(configRepository.getFlagUpdate()).thenReturn(
            Result.success(
                FlagUpdate.OUTDATED
            )
        )
        val usecase = ICheckAccessMain(configRepository)
        val result = usecase()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!, false)
    }

    @Test
    fun `Check return true if have flagUpdate is UPDATE`() = runTest {
        val configRepository = mock<ConfigRepository>()
        whenever(
            configRepository.hasConfig()
        ).thenReturn(
            Result.success(true)
        )
        whenever(configRepository.getFlagUpdate()).thenReturn(
            Result.success(
                FlagUpdate.UPDATED
            )
        )
        val usecase = ICheckAccessMain(configRepository)
        val result = usecase()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!, true)
    }
}