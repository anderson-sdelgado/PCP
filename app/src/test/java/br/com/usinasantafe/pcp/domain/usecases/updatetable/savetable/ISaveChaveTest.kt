package br.com.usinasantafe.pcp.domain.usecases.updatetable.savetable

import br.com.usinasantafe.pcp.domain.entities.stable.Chave
import br.com.usinasantafe.pcp.domain.repositories.stable.ChaveRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.whenever

class ISaveChaveTest {

    private val chaveRepository = Mockito.mock<ChaveRepository>()
    private val usecase = ISaveChave(chaveRepository)

    @Test
    fun `Check execution correct`() = runTest {
        val chaveList = listOf(
            Chave(
                idChave = 1,
                descrChave = "02 - Sala TI",
                idLocalTrab = 1
            )
        )
        whenever(
            chaveRepository.addAll(chaveList)
        ).thenReturn(
            Result.success(true)
        )
        val result = usecase(chaveList)
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
        val chaveList = listOf(
            Chave(
                idChave = 1,
                descrChave = "02 - Sala TI",
                idLocalTrab = 1
            )
        )
        whenever(
            chaveRepository.addAll(chaveList)
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val result = usecase(chaveList)
        assertEquals(
            result.isFailure,
            true)

        assertEquals(
            result.exceptionOrNull()!!.message,
            "ISaveChave -> Unknown Error"
        )
    }
}