package br.com.usinasantafe.pcp.infra.repositories.variable

import br.com.usinasantafe.pcp.domain.entities.variable.Config
import br.com.usinasantafe.pcp.infra.datasource.retrofit.variable.ConfigRetrofitDatasource
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.ConfigRetrofitModelInput
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.entityToRetrofitModel
import br.com.usinasantafe.pcp.utils.FlagUpdate
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class IConfigRepositoryTest {

    private val configSharedPreferencesDatasource =
        mock<ConfigSharedPreferencesDatasource>()
    private val configRetrofitDatasource =
        mock<ConfigRetrofitDatasource>()

    private val repository = IConfigRepository(
        configSharedPreferencesDatasource,
        configRetrofitDatasource
    )

    @Test
    fun `Check return true if have data in table Config internal`() = runTest {
        whenever(
            configSharedPreferencesDatasource.has()
        ).thenReturn(
            Result.success(true)
        )
        val result = repository.hasConfig()
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
    fun `Check return false if don't have data in table Config internal`() = runTest {
        whenever(
            configSharedPreferencesDatasource.has()
        ).thenReturn(
            Result.success(false)
        )
        val result = repository.hasConfig()
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
    fun `Check return failure Datasource if have failure in hasConfig`() = runTest {
        whenever(
            configSharedPreferencesDatasource.has()
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val result = repository.hasConfig()
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "IConfigRepository.hasConfig -> Unknown Error"
        )
    }

    @Test
    fun `Check return password input table Config internal`() = runTest {
        whenever(
            configSharedPreferencesDatasource.get()
        ).thenReturn(
            Result.success(
                Config(password = "12345")
            )
        )
        val result = repository.getPassword()
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!,
            "12345"
        )
    }

    @Test
    fun `Check return failure if have failure in execution getConfig getPassword`() = runTest {
        whenever(
            configSharedPreferencesDatasource.get()
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val result = repository.getPassword()
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "IConfigRepository.getPassword -> Unknown Error"
        )
    }

    @Test
    fun `Check return flagUpdate input table Config internal`() = runTest {
        whenever(
            configSharedPreferencesDatasource.get()
        ).thenReturn(
            Result.success(
                Config(flagUpdate = FlagUpdate.UPDATED)
            )
        )
        val result = repository.getFlagUpdate()
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!,
            FlagUpdate.UPDATED
        )
    }

    @Test
    fun `Check return failure if have failure in execution getConfig getFlagUpdate`() = runTest {
        whenever(
            configSharedPreferencesDatasource.get()
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val result = repository.getFlagUpdate()
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "IConfigRepository.getFlagUpdate -> Unknown Error"
        )
    }

    @Test
    fun `Check return object Config if have data in table Config internal`() = runTest {
        val config = Config(
            password = "12345",
            number = 16997417840
        )
        whenever(
            configSharedPreferencesDatasource.get()
        ).thenReturn(
            Result.success(config)
        )
        val result = repository.getConfig()
        assertEquals(
            result.getOrNull()!!.number,
            16997417840
        )
        assertEquals(
            result.getOrNull()!!.password,
            "12345"
        )
    }

    @Test
    fun `Check return failure de Repository if Config is null`() = runTest {
        val result = repository.send(
            Config()
        )
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "IConfigRepository.send"
        )
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            NullPointerException().toString()
        )
    }

    @Test
    fun `Check return failure de Datasource if have error in Config Datasource`() = runTest {
        val config = Config(
            version = "6.00",
            number = 16997417840,
        )
        whenever(
            configRetrofitDatasource.recoverToken(
                config.entityToRetrofitModel()
            )
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val result = repository.send(config)
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "IConfigRepository.send -> Unknown Error"
        )
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            "java.lang.Exception"
        )
    }

    @Test
    fun `Check return ID if send is correct`() = runTest {
        val config = Config(
            version = "6.00",
            number = 16997417840,
        )
        val configRetrofitModelInput = ConfigRetrofitModelInput(
            idBD = 1
        )
        whenever(
            configRetrofitDatasource.recoverToken(
                config.entityToRetrofitModel()
            )
        ).thenReturn(
            Result.success(configRetrofitModelInput)
        )
        val result = repository.send(config)
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result,
            Result.success(1)
        )
    }

    @Test
    fun `Check return failure if have error in configSharedPreferencesDatasource clear`() =
        runTest {
            whenever(
                configSharedPreferencesDatasource.clean()
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.clean()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IConfigRepository.clean -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if CleanConfig execute successfully`() =
        runTest {
            whenever(
                configSharedPreferencesDatasource.clean()
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.clean()
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