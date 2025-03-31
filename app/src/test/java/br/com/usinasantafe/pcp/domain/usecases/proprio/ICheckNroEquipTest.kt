package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcp.domain.usecases.common.ICheckNroEquip
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class ICheckNroEquipTest {

    private val equipRepository = mock<EquipRepository>()
    private val usecase = ICheckNroEquip(
        equipRepository = equipRepository
    )

    @Test
    fun `Check return failure if matric is incorrect`() = runTest {
        val result = usecase("100a")
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "ICheckNroEquip"
        )
    }

    @Test
    fun `Check return failure if have error in Repository`() = runTest {
        whenever(
            equipRepository.checkNro(100)
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val result = usecase("100")
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "ICheckNroEquip -> Unknown Error"
        )
    }

    @Test
    fun `Check return true in executed correctly`() = runTest {
        whenever(
            equipRepository.checkNro(19759)
        ).thenReturn(
            Result.success(true)
        )
        val result = usecase("19759")
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!,
            true
        )
    }

    @Test
    fun `Check return false in executed correctly`() = runTest {
        whenever(
            equipRepository.checkNro(19759)
        ).thenReturn(
            Result.success(false)
        )
        val result = usecase("19759")
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!,
            false
        )
    }
}