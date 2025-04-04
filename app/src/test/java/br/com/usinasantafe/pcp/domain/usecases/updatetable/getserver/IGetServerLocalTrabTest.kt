package br.com.usinasantafe.pcp.domain.usecases.updatetable.getserver

import br.com.usinasantafe.pcp.domain.entities.stable.LocalTrab
import br.com.usinasantafe.pcp.domain.repositories.stable.LocalTrabRepository
import br.com.usinasantafe.pcp.domain.usecases.common.GetToken
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IGetServerLocalTrabTest {

    private val getToken = mock<GetToken>()
    private val localTrabRepository = mock<LocalTrabRepository>()
    private val usecase = IGetServerLocalTrab(
        getToken,
        localTrabRepository
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
            "IGetServerLocalTrab -> Unknown Error"
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
            "IGetServerLocalTrab"
        )
    }

    @Test
    fun `Check return Failure Datasource if have success getConfig and failure Datasource in recoverAll`() =
        runTest {
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                localTrabRepository.recoverAll("token")
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
                "IGetServerLocalTrab -> Unknown Error"
            )
        }

    @Test
    fun `Check return list if have success getConfig and success recoverAll`() = runTest {
        val localTrabList = listOf(
            LocalTrab(
                idLocalTrab = 1,
                descrLocalTrab = "TI",
            )
        )
        whenever(
            getToken()
        ).thenReturn(
            Result.success("token")
        )
        whenever(
            localTrabRepository.recoverAll("token")
        ).thenReturn(
            Result.success(localTrabList)
        )
        val result = usecase()
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result,
            Result.success(localTrabList)
        )
    }

}