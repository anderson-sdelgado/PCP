package br.com.usinasantafe.pcp.domain.usecases.common

import br.com.usinasantafe.pcp.domain.entities.variable.Config
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.utils.StatusSend
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class IGetStatusSendTest {

    private val configRepository = mock<ConfigRepository>()
    private val usecase = IGetStatusSend(
        configRepository = configRepository
    )

    @Test
    fun `Check return failure if have error in ConfigRepository getConfig`() =
        runTest {
            whenever(
                configRepository.getConfig()
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val resultFlow = usecase()
            val list = resultFlow.toList()
            assertEquals(list.count(), 1)
            val result = list[0]
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> ConfigRepository.getConfig"
            )
        }

    @Test
    fun `Check return correct if function execute successfully`() =
        runTest {
            whenever(
                configRepository.getConfig()
            ).thenReturn(
                Result.success(
                    Config(
                        statusSend = StatusSend.SEND
                    )
                )
            )
            val resultFlow = usecase()
            val list = resultFlow.toList()
            assertEquals(list.count(), 1)
            val result = list[0]
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                StatusSend.SEND
            )
        }

}