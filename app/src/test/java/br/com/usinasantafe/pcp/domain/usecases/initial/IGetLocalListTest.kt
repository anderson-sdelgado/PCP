package br.com.usinasantafe.pcp.domain.usecases.initial

import br.com.usinasantafe.pcp.domain.entities.stable.Local
import br.com.usinasantafe.pcp.domain.repositories.stable.LocalRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class IGetLocalListTest {

    private val localRepository = mock<LocalRepository>()
    private val usecase = IGetLocalList(
        localRepository = localRepository
    )

    @Test
    fun `Check return failure if have failure in getAll`() = runTest {

        whenever(
            localRepository.list()
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
            "IGetLocalList -> Unknown Error"
        )
    }

    @Test
    fun `Check return success`() = runTest {
        whenever(
            localRepository.list()
        ).thenReturn(
            Result.success(
                listOf(
                    Local(
                        idLocal = 1,
                        descrLocal = "USINA"
                    )
                )
            )
        )
        val result = usecase()
        assertEquals(
            result.isSuccess,
            true
        )
        val locals = result.getOrNull()!!
        assertEquals(
            locals[0].descrLocal,
            "USINA"
        )
    }
}