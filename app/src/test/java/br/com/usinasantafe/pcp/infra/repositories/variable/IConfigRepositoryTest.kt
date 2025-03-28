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

    private fun getRepository() = IConfigRepository(
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
        val repository = getRepository()
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
        val repository = getRepository()
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
        val repository = getRepository()
        val result = repository.hasConfig()
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> ConfigSharedPreferencesDatasource.hasConfig"
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
        val repository = getRepository()
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
        val repository = getRepository()
        val result = repository.getPassword()
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> ConfigSharedPreferencesDatasource.getConfig"
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
        val repository = getRepository()
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
        val repository = getRepository()
        val result = repository.getFlagUpdate()
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> ConfigSharedPreferencesDatasource.getConfig"
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
        val repository = getRepository()
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
        val repository = getRepository()
        val result = repository.send(
            Config()
        )
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Repository -> ConfigRepositoryImpl.send"
        )
        assertEquals(result.exceptionOrNull()!!.cause.toString(), NullPointerException().toString())
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
        val repository = getRepository()
        val result = repository.send(config)
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> ConfigRetrofitDatasource.recoverToken"
        )
        assertEquals(result.exceptionOrNull()!!.cause.toString(), NullPointerException().toString())
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
        val repository = getRepository()
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
                configSharedPreferencesDatasource.clear()
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val repository = getRepository()
            val result = repository.cleanConfig()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> ConfigSharedPreferencesDatasource.clear"
            )
        }

    @Test
    fun `Check return true if CleanConfig execute successfully`() =
        runTest {
            whenever(
                configSharedPreferencesDatasource.clear()
            ).thenReturn(
                Result.success(true)
            )
            val repository = getRepository()
            val result = repository.cleanConfig()
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