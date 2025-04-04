package br.com.usinasantafe.pcp.domain.usecases.updatetable.getserver

import br.com.usinasantafe.pcp.domain.entities.stable.Terceiro
import br.com.usinasantafe.pcp.domain.repositories.stable.TerceiroRepository
import br.com.usinasantafe.pcp.domain.usecases.common.GetToken
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class IGetServerTerceiroTest {

    private val getToken = mock<GetToken>()
    private val terceiroRepository = mock<TerceiroRepository>()
    private val usecase = IGetServerTerceiro(
        getToken,
        terceiroRepository
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
            "IGetServerTerceiro -> Unknown Error"
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
            "IGetServerTerceiro"
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
            terceiroRepository.recoverAll("token")
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
            "IGetServerTerceiro -> Unknown Error"
        )
    }

    @Test
    fun `Check return list if have success getConfig and success recoverAll`() = runTest {
        val terceiroList = listOf(
            Terceiro(
                idTerceiro = 1,
                idBDTerceiro = 1,
                cpfTerceiro = "123.456.789-00",
                nomeTerceiro = "Terceiro",
                empresaTerceiro = "Empresa Terceiro"
            )
        )
        whenever(
            getToken()
        ).thenReturn(
            Result.success("token")
        )
        whenever(
            terceiroRepository.recoverAll("token")
        ).thenReturn(
            Result.success(terceiroList)
        )
        val result = usecase()
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result,
            Result.success(terceiroList)
        )
    }

}