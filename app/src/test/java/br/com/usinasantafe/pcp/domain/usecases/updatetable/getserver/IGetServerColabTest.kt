package br.com.usinasantafe.pcp.domain.usecases.updatetable.getserver

import br.com.usinasantafe.pcp.domain.entities.stable.Colab
import br.com.usinasantafe.pcp.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pcp.domain.usecases.common.GetToken
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class IGetServerColabTest {

    private val getToken = mock<GetToken>()
    private val colabRepository = mock<ColabRepository>()
    private val usecase = IGetServerColab(
        getToken,
        colabRepository
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
            "IGetServerColab -> Unknown Error"
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
            "IGetServerColab"
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
                colabRepository.recoverAll("token")
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
                "IGetServerColab -> Unknown Error"
            )
        }

    @Test
    fun `Check return list if have success getConfig and success recoverAll`() = runTest {
        val colabList = listOf(
            Colab(
                matricColab = 19759,
                nomeColab = "Anderson"
            )
        )
        whenever(
            getToken()
        ).thenReturn(
            Result.success("token")
        )
        whenever(
            colabRepository.recoverAll("token")
        ).thenReturn(
            Result.success(colabList)
        )
        val result = usecase()
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result,
            Result.success(colabList)
        )
    }

}