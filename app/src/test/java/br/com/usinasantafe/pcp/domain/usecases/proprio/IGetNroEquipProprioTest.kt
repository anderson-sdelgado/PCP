package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IGetNroEquipProprioTest {

    private val movEquipProprioRepository = mock<MovEquipProprioRepository>()
    private val equipRepository = mock<EquipRepository>()
    private val usecase = IGetNroEquipProprio(
        movEquipProprioRepository,
        equipRepository
    )

    @Test
    fun `Check return failure if have error in MovEquipProprioRepository get`() = runTest {
        whenever(
            movEquipProprioRepository.getIdEquip(id = 1)
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val result = usecase(id = 1)
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "IGetNroEquipProprio -> Unknown Error"
        )
    }

    @Test
    fun `Check return failure if have error in EquipRepository getNro`() = runTest {
        whenever(
            movEquipProprioRepository.getIdEquip(
                id = 1
            )
        ).thenReturn(
            Result.success(10)
        )
        whenever(
            equipRepository.getNro(
                idEquip = 10
            )
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val result = usecase(id = 1)
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "IGetNroEquipProprio -> Unknown Error"
        )
    }

    @Test
    fun `Check return nroEquip if GetNroEquipImpl execute success`() = runTest {
        whenever(
            movEquipProprioRepository.getIdEquip(
                id = 1
            )
        ).thenReturn(
            Result.success(10)
        )
        whenever(equipRepository.getNro(idEquip = 10)).thenReturn(
            Result.success(100)
        )
        val result = usecase(id = 1)
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!,
            "100"
        )
    }
}