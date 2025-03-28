package br.com.usinasantafe.pcp.domain.usecases.initial

import br.com.usinasantafe.pcp.domain.entities.variable.Config
import br.com.usinasantafe.pcp.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetNomeVigiaImplTest {

    @Test
    fun `Check return failure if have failure in getConfig`() = runTest {
        val configRepository = mock<ConfigRepository>()
        val colarepository = mock<ColabRepository>()
        whenever(
            configRepository.getConfig()
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val usecase = IGetNomeVigia(configRepository, colarepository)
        val result = usecase()
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> ConfigRepository.getConfig")
    }

    @Test
    fun `Check return failure if have failure in getNome`() = runTest {
        val config = Config(
            matricVigia = 19759
        )
        val configRepository = mock<ConfigRepository>()
        val colabRepository = mock<ColabRepository>()
        whenever(
            configRepository.getConfig()
        ).thenReturn(
            Result.success(config)
        )
        whenever(
            colabRepository.getNome(19759)
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val usecase = IGetNomeVigia(configRepository, colabRepository)
        val result = usecase()
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> ColabRepository.getNome")
    }

    @Test
    fun `Check return NomeVigia if the process execute success`() = runTest {
        val config = Config(
            matricVigia = 19759
        )
        val configRepository = mock<ConfigRepository>()
        val colarepository = mock<ColabRepository>()
        whenever(
            configRepository.getConfig()
        ).thenReturn(
            Result.success(config)
        )
        whenever(colarepository.getNome(19759)).thenReturn(
            Result.success("ANDERSON DA SILVA DELGADO")
        )
        val usecase = IGetNomeVigia(configRepository, colarepository)
        val result = usecase()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!, "ANDERSON DA SILVA DELGADO")
    }
}