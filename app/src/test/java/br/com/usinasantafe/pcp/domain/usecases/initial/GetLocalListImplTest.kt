package br.com.usinasantafe.pcp.domain.usecases.initial

import br.com.usinasantafe.pcp.domain.entities.stable.Local
import br.com.usinasantafe.pcp.domain.repositories.stable.LocalRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetLocalListImplTest {

    @Test
    fun `Check return failure if have failure in getAll`() = runTest {
        val localRepository = mock<LocalRepository>()
        whenever(
            localRepository.list()
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val usecase = IGetLocalList(localRepository)
        val result = usecase()
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> LocalRepository.getAll")
    }

    @Test
    fun `Check return success`() = runTest {
        val localRepository = mock<LocalRepository>()
        whenever(localRepository.list()).thenReturn(
            Result.success(
                listOf(
                    Local(
                        idLocal = 1,
                        descrLocal = "USINA"
                    )
                )
            )
        )
        val usecase = IGetLocalList(localRepository)
        val result = usecase()
        assertTrue(result.isSuccess)
        val locals = result.getOrNull()!!
        assertEquals(locals[0].descrLocal, "USINA")
    }
}