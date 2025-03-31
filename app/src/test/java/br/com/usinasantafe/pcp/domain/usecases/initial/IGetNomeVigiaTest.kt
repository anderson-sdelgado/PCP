package br.com.usinasantafe.pcp.domain.usecases.initial

import br.com.usinasantafe.pcp.domain.entities.variable.Config
import br.com.usinasantafe.pcp.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pcp.domain.repositories.stable.LocalRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class IGetNomeVigiaTest {

    private val configRepository = mock<ConfigRepository>()
    private val colabRepository = mock<ColabRepository>()
    private val usecase = IGetNomeVigia(
        configRepository = configRepository,
        colabRepository = colabRepository
    )

    @Test
    fun `Check return failure if have failure in getConfig`() = runTest {
        whenever(
            configRepository.getConfig()
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val result = usecase()
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "IGetNomeVigia -> Unknown Error"
        )
    }

    @Test
    fun `Check return failure if have failure in getNome`() = runTest {
        val config = Config(
            matricVigia = 19759
        )
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
        val result = usecase()
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "IGetNomeVigia -> Unknown Error"
        )
    }

    @Test
    fun `Check return NomeVigia if the process execute success`() = runTest {
        val config = Config(
            matricVigia = 19759
        )
        whenever(
            configRepository.getConfig()
        ).thenReturn(
            Result.success(config)
        )
        whenever(
            colabRepository.getNome(19759))
            .thenReturn(
            Result.success("ANDERSON DA SILVA DELGADO")
        )
        val result = usecase()
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!,
            "ANDERSON DA SILVA DELGADO"
        )
    }
}