package br.com.usinasantafe.pcp.domain.usecases.common

import br.com.usinasantafe.pcp.domain.entities.variable.Config
import br.com.usinasantafe.pcp.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pcp.domain.repositories.stable.LocalRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class IGetHeaderTest {

    @Test
    fun `Chech return failure Datasource if have error in getConfig`() = runTest {
        val configRepository = mock<ConfigRepository>()
        val colabRepository = mock<ColabRepository>()
        val localRepository = mock<LocalRepository>()
        whenever(
            configRepository.getConfig()
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val usecase = IGetHeader(
            configRepository,
            colabRepository,
            localRepository
        )
        val result = usecase()
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "IGetHeader -> Unknown Error"
        )
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            Exception().toString()
        )
    }

    @Test
    fun `Check return failure if have failure in getNome`() = runTest {
        val config = Config(
            matricVigia = 19759
        )
        val configRepository = mock<ConfigRepository>()
        val colabRepository = mock<ColabRepository>()
        val localRepository = mock<LocalRepository>()
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
        val usecase = IGetHeader(
            configRepository,
            colabRepository,
            localRepository
        )
        val result = usecase()
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "IGetHeader -> Unknown Error"
        )
    }

    @Test
    fun `Check return failure if have failure in getDescr Local`() = runTest {
        val config = Config(
            matricVigia = 19759,
            idLocal = 1
        )
        val configRepository = mock<ConfigRepository>()
        val colabRepository = mock<ColabRepository>()
        val localRepository = mock<LocalRepository>()
        whenever(
            configRepository.getConfig()
        ).thenReturn(
            Result.success(config)
        )
        whenever(
            colabRepository.getNome(19759)
        ).thenReturn(
            Result.success("ANDERSON DA SILVA DELGADO")
        )
        whenever(
            localRepository.getDescr(1)
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val usecase = IGetHeader(
            configRepository,
            colabRepository,
            localRepository
        )
        val result = usecase()
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "IGetHeader -> Unknown Error"
        )
    }

    @Test
    fun `Check return Header if execute success`() = runTest {
        val config = Config(
            matricVigia = 19759,
            idLocal = 1
        )
        val configRepository = mock<ConfigRepository>()
        val colabRepository = mock<ColabRepository>()
        val localRepository = mock<LocalRepository>()
        whenever(
            configRepository.getConfig()
        ).thenReturn(
            Result.success(config)
        )
        whenever(
            colabRepository.getNome(19759)
        ).thenReturn(
            Result.success("ANDERSON DA SILVA DELGADO")
        )
        whenever(
            localRepository.getDescr(1)
        ).thenReturn(
            Result.success("1 - USINA")
        )
        val usecase = IGetHeader(
            configRepository,
            colabRepository,
            localRepository
        )
        val result = usecase()
        assertEquals(
            result.isSuccess,
            true
        )
        val header = result.getOrNull()!!
        assertEquals(
            header.descrVigia,
            "19759 - ANDERSON DA SILVA DELGADO"
        )
        assertEquals(
            header.descrLocal,
            "1 - USINA"
        )
    }
}