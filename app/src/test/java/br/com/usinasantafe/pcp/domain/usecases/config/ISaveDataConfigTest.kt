package br.com.usinasantafe.pcp.domain.usecases.config

import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class ISaveDataConfigTest {

    @Test
    fun `Chech return true if save is correct`() = runTest {
        val configRepository = mock<ConfigRepository>()
        whenever(
            configRepository.saveInitial(
                number = 16997417840,
                password = "12345",
                version = "6.00",
                idBD = 1
            )
        ).thenReturn(
            Result.success(true)
        )
        val usecase = ISaveDataConfig(configRepository)
        val result = usecase(
            number = "16997417840",
            password = "12345",
            version = "6.00",
            idBD = 1
        )
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
    fun `Chech return Failure Usecase if have error in Usecase`() = runTest {
        val configRepository = mock<ConfigRepository>()
        val usecase = ISaveDataConfig(configRepository)
        val result = usecase(
            number = "16997417840A",
            password = "12345",
            version = "6.00",
            idBD = 1
        )
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "ISaveDataConfig"
        )
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            "java.lang.NumberFormatException: For input string: \"16997417840A\""
        )
    }

    @Test
    fun `Chech return Failure Datasource if have error in Datasource`() = runTest {
        val configRepository = mock<ConfigRepository>()
        whenever(
            configRepository.saveInitial(
                number = 16997417840,
                password = "12345",
                version = "6.00",
                idBD = 1
            )
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val usecase = ISaveDataConfig(configRepository)
        val result = usecase(
            number = "16997417840",
            password = "12345",
            version = "6.00",
            idBD = 1
        )
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "ISaveDataConfig -> Unknown Error"
        )
    }
}