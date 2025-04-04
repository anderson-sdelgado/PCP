package br.com.usinasantafe.pcp.domain.usecases.updatetable.getserver

import br.com.usinasantafe.pcp.domain.entities.stable.Equip
import br.com.usinasantafe.pcp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcp.domain.usecases.common.GetToken
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class IGetServerEquipTest {

    private val getToken = mock<GetToken>()
    private val equipRepository = mock<EquipRepository>()
    private val usecase = IGetServerEquip(
        getToken,
        equipRepository
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
            "IGetServerEquip -> Unknown Error"
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
            "IGetServerEquip"
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
            equipRepository.recoverAll("token")
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
            "IGetServerEquip -> Unknown Error"
        )
    }

    @Test
    fun `Check return list if have success getConfig and success recoverAll`() = runTest {
        val equipList = listOf(
            Equip(
                idEquip = 10,
                nroEquip = 200,
                descrEquip = "teste"
            )
        )
        whenever(
            getToken()
        ).thenReturn(
            Result.success("token")
        )
        whenever(
            equipRepository.recoverAll("token")
        ).thenReturn(
            Result.success(equipList))
        val result = usecase()
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result,
            Result.success(equipList)
        )
    }

}