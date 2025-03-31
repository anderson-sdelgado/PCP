package br.com.usinasantafe.pcp.domain.usecases.config

import br.com.usinasantafe.pcp.domain.entities.variable.Config
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.presenter.configuration.config.ConfigModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class IGetConfigInternalTest {

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
        val usecase = IGetConfigInternal(configRepository)
        val result = usecase()
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "IGetConfigInternal -> Unknown Error"
        )
    }

    @Test
    fun `Check null if haven't db config save`() = runTest {
        val configRepository = mock<ConfigRepository>()
        whenever(
            configRepository.hasConfig()
        ).thenReturn(
            Result.success(false)
        )
        val usecase = IGetConfigInternal(configRepository)
        val result = usecase()
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull(),
            null
        )
    }

    @Test
    fun `Check return failure if have failure in getConfig`() = runTest {
        val configRepository = mock<ConfigRepository>()
        whenever(
            configRepository.hasConfig()
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            configRepository.getConfig()
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val usecase = IGetConfigInternal(configRepository)
        val result = usecase()
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "IGetConfigInternal -> Unknown Error"
        )
    }

    @Test
    fun `Check return data if have db config save`() = runTest {
        val config = Config(
            number = 16997417840,
            password = "12345"
        )
        val configRepository = mock<ConfigRepository>()
        whenever(
            configRepository.hasConfig()
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            configRepository.getConfig()
        ).thenReturn(
            Result.success(config)
        )
        val usecase = IGetConfigInternal(configRepository)
        val result = usecase()
        assertEquals(
            result.getOrNull()!!,
            ConfigModel(
                number = "16997417840",
                password = "12345"
            )
        )
    }

}