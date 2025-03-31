package br.com.usinasantafe.pcp.domain.usecases.config

import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class ICheckPasswordTest {

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
        val usecase = ICheckPassword(configRepository)
        val result = usecase("")
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "ICheckPassword -> Unknown Error"
        )
    }

    @Test
    fun `Check access if don't have db config save and password is null`() = runTest {
        val configRepository = mock<ConfigRepository>()
        whenever(
            configRepository.hasConfig()
        ).thenReturn(
            Result.success(false)
        )
        val usecase = ICheckPassword(configRepository)
        val result = usecase("")
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!,
            true
        )
    }

    @Test
    fun `Check return failure if have failure in getPassword`() = runTest {
        val configRepository = mock<ConfigRepository>()
        whenever(
            configRepository.hasConfig()
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            configRepository.getPassword()
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val usecase = ICheckPassword(configRepository)
        val result = usecase("")
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "ICheckPassword -> Unknown Error"
        )
    }

    @Test
    fun `Check don't access if have db config save and password is null`() = runTest {
        val configRepository = mock<ConfigRepository>()
        whenever(
            configRepository.hasConfig()
        ).thenReturn(
            Result.success(true)
        )
        val usecase = ICheckPassword(configRepository)
        val result = usecase("")
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!,
            false
        )
    }

    @Test
    fun `Check access if have db config save and input password equal password db`() = runTest {
        val configRepository = mock<ConfigRepository>()
        whenever(
            configRepository.hasConfig()
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            configRepository.getPassword()
        ).thenReturn(
            Result.success("12345")
        )
        val usecase = ICheckPassword(configRepository)
        val result = usecase("12345")
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!,
            true
        )
    }

    @Test
    fun `Check don't access if have db config save and input password different password db`() = runTest {
        val configRepository = mock<ConfigRepository>()
        whenever(
            configRepository.hasConfig()
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            configRepository.getPassword()
        ).thenReturn(
            Result.success("123456")
        )
        val usecase = ICheckPassword(configRepository)
        val result = usecase("12345")
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