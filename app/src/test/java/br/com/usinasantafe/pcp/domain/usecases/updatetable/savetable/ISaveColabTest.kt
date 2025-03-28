package br.com.usinasantafe.pcp.domain.usecases.updatetable.savetable

import br.com.usinasantafe.pcp.domain.entities.stable.Colab
import br.com.usinasantafe.pcp.domain.repositories.stable.ColabRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.whenever

class ISaveColabTest {

    @Test
    fun `Check execution correct`() = runTest {
        val colabList = listOf(
            Colab(
                matricColab = 19759,
                nomeColab = "ANDERSON DA SILVA DELGADO"
            )
        )
        val colabRepository = Mockito.mock<ColabRepository>()
        whenever(
            colabRepository.addAll(colabList)
        ).thenReturn(
            Result.success(true)
        )
        val usecase = ISaveColab(colabRepository)
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
        val colabRepository = Mockito.mock<ColabRepository>()
        whenever(
            colabRepository.addAll(colabList)
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val usecase = ISaveColab(colabRepository)
        val result = usecase(colabList)
        assertEquals(
            result.isFailure,
            true)

        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> ColabRepository.addAll"
        )
    }
}