package br.com.usinasantafe.pcp.domain.usecases.config

import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class ISetMatricVigiaConfigTest {

    @Test
    fun `Chech return failure Datasource if have error in ConfigRepository SetMatricVigia`() =
        runTest {
            val configRepository = mock<ConfigRepository>()
            whenever(
                configRepository.setMatricVigia(19759)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val usecase = ISetMatricVigiaConfig(configRepository)
            val result = usecase("19759")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetMatricVigiaConfig -> Unknown Error"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                Exception().toString()
            )
        }

    @Test
    fun `Chech return true if usecase is success`() = runTest {
            val configRepository = mock<ConfigRepository>()
            whenever(
                configRepository.setMatricVigia(19759)
            ).thenReturn(
                Result.success(true)
            )
            val usecase = ISetMatricVigiaConfig(configRepository)
            val result = usecase("19759")
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