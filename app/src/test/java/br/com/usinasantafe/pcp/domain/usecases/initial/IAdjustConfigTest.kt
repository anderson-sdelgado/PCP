package br.com.usinasantafe.pcp.domain.usecases.initial

import br.com.usinasantafe.pcp.domain.entities.variable.Config
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IAdjustConfigTest {

    private val configRepository = mock<ConfigRepository>()
    private val startProcessSendData = mock<StartProcessSendData>()
    private val usecase = IAdjustConfig(
        configRepository,
        startProcessSendData
    )

    @Test
    fun `Check return failure if have failure in hasConfig`() = runTest {
        whenever(
            configRepository.hasConfig()
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val result = usecase("1.00")
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "IAdjustConfig -> Unknown Error"
        )
    }

    @Test
    fun `Check return true if not have data in config`() = runTest {
        whenever(
            configRepository.hasConfig()
        ).thenReturn(
            Result.success(false)
        )
        val result = usecase("1.00")
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
    fun `Check return failure if have error in configRepository getConfig`() = runTest {
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
        val result = usecase("1.00")
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "IAdjustConfig -> Unknown Error"
        )
    }

    @Test
    fun `Check return failure if have error in configRepository cleanConfig`() = runTest {
        whenever(
            configRepository.hasConfig()
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            configRepository.getConfig()
        ).thenReturn(
            Result.success(
                Config(
                    version = "1.00"
                )
            )
        )
        whenever(
            configRepository.cleanConfig()
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val result = usecase("2.00")
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "IAdjustConfig -> Unknown Error"
        )
    }


    @Test
    fun `Check return true if have data in config and execute all success`() = runTest {
        whenever(
            configRepository.hasConfig()
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            configRepository.getConfig()
        ).thenReturn(
            Result.success(
                Config(
                    version = "1.00"
                )
            )
        )
        whenever(
            configRepository.cleanConfig()
        ).thenReturn(
            Result.success(true)
        )
        val result = usecase("1.00")
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