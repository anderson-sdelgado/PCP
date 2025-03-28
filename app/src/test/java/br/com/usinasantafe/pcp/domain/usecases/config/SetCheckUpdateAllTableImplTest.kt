package br.com.usinasantafe.pcp.domain.usecases.config

import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.utils.FlagUpdate
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class SetCheckUpdateAllTableImplTest {

    @Test
    fun `Chech return failure Datasource if have error in ConfigRepository setFlagUpdate`() = runTest {
        val configRepository = mock<ConfigRepository>()
        whenever(configRepository.setFlagUpdate(FlagUpdate.UPDATED)).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val usecase = ISetCheckUpdateAllTable(configRepository)
        val result = usecase(FlagUpdate.UPDATED)
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Repository -> ConfigRepository.setFlagUpdate"
        )
    }

    @Test
    fun `Chech return true if usecase is success`() = runTest {
        val configRepository = mock<ConfigRepository>()
        whenever(
            configRepository.setFlagUpdate(
                FlagUpdate.UPDATED
            )
        ).thenReturn(
            Result.success(true)
        )
        val usecase = ISetCheckUpdateAllTable(configRepository)
        val result = usecase(FlagUpdate.UPDATED)
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
    }
}