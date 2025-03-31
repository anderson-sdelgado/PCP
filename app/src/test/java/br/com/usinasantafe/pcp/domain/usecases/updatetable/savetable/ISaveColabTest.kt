package br.com.usinasantafe.pcp.domain.usecases.updatetable.savetable

import br.com.usinasantafe.pcp.domain.entities.stable.Colab
import br.com.usinasantafe.pcp.domain.repositories.stable.ColabRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.whenever

class ISaveColabTest {

    private val colabRepository = Mockito.mock<ColabRepository>()
    private val usecase = ISaveColab(colabRepository)

    @Test
    fun `Check execution correct`() = runTest {
        val colabList = listOf(
            Colab(
                matricColab = 19759,
                nomeColab = "ANDERSON DA SILVA DELGADO"
            )
        )
        whenever(
            colabRepository.addAll(colabList)
        ).thenReturn(
            Result.success(true)
        )
        val result = usecase(colabList)
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
    fun `Check execution incorrect`() = runTest {
        val colabList = listOf(
            Colab(
                matricColab = 19759,
                nomeColab = "ANDERSON DA SILVA DELGADO"
            )
        )
        whenever(
            colabRepository.addAll(colabList)
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val result = usecase(colabList)
        assertEquals(
            result.isFailure,
            true)

        assertEquals(
            result.exceptionOrNull()!!.message,
            "ISaveColab -> Unknown Error"
        )
    }
}