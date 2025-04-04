package br.com.usinasantafe.pcp.domain.usecases.updatetable.getserver

import br.com.usinasantafe.pcp.domain.entities.stable.Local
import br.com.usinasantafe.pcp.domain.repositories.stable.LocalRepository
import br.com.usinasantafe.pcp.domain.usecases.common.GetToken
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class IGetServerLocalTest {

    private val getToken = mock<GetToken>()
    private val localRepository = mock<LocalRepository>()
    private val usecase = IGetServerLocal(
        getToken,
        localRepository
    )

    @Test
    fun `Check return Failure Datasouce if have failure in Config Repository`() = runTest {
        whenever(
            getToken()
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
            "IGetServerLocal -> Unknown Error"
        )
    }

    @Test
    fun `Check return Failure Usecase if have empty fields in object Config return `() = runTest {
        whenever(
            getToken()
        ).thenReturn(
            Result.success("token")
        )
        val result = usecase()
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "IGetServerLocal"
        )
    }

    @Test
    fun `Check return Failure Datasource if have success getConfig and failure Datasource in recoverAll`() = runTest {
        whenever(
            getToken()
        ).thenReturn(
            Result.success("token")
        )
        whenever(
            localRepository.recoverAll("token")
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
            "IGetServerLocal -> Unknown Error"
        )
    }

    @Test
    fun `Check return list if have success getConfig and success recoverAll`() = runTest {
        val localList = listOf(
            Local(
                idLocal = 1,
                descrLocal = "Usina",
            )
        )
        whenever(
            getToken()
        ).thenReturn(
            Result.success("token")
        )
        whenever(
            localRepository.recoverAll("token")
        ).thenReturn(
            Result.success(localList)
        )
        val result = usecase()
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result,
            Result.success(localList)
        )
    }

}