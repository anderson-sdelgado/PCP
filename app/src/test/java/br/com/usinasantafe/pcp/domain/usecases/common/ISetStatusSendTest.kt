package br.com.usinasantafe.pcp.domain.usecases.common

import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.utils.StatusSend
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class ISetStatusSendTest {

    private val configRepository = mock<ConfigRepository>()
    private val usecase = ISetStatusSend(
        configRepository = configRepository
    )

    @Test
    fun `Check return failure if have error in ConfigRepository setSend`() =
        runTest {
            whenever(
                configRepository.setStatusSend(
                    StatusSend.SEND
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(StatusSend.SEND)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> ConfigRepository.setStatusSend"
            )
        }

    @Test
    fun `Check return correct if function execute successfully`() =
        runTest {
            whenever(
                configRepository.setStatusSend(
                    StatusSend.SEND
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(StatusSend.SEND)
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