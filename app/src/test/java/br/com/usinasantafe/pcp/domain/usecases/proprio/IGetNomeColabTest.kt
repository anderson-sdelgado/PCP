package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pcp.domain.usecases.common.IGetNomeColab
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class IGetNomeColabTest {

    private val colabRepository = mock<ColabRepository>()
    private val usecase = IGetNomeColab(
        colabRepository
    )

    @Test
    fun `Check return failure if have failure in getNome`() = runTest {
        whenever(
            colabRepository.getNome(19759)
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val result = usecase("19759")
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "IGetNomeColab -> Unknown Error"
        )
    }

    @Test
    fun `Check return NomeVigia if the process execute success`() = runTest {
        val colabRepository = mock<ColabRepository>()
        whenever(
            colabRepository.getNome(19759)
        ).thenReturn(
            Result.success("ANDERSON DA SILVA DELGADO")
        )
        val usecase = IGetNomeColab(
            colabRepository
        )
        val result = usecase("19759")
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