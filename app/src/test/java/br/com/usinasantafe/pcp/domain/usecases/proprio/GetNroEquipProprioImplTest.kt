package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class GetNroEquipProprioImplTest {

    @Test
    fun `Check return failure if have error in MovEquipProprioRepository get`() = runTest {
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        val equipRepository = mock<EquipRepository>()
        whenever(
            movEquipProprioRepository.getIdEquip(id = 1)
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val usecase = IGetNroEquipProprio(
            movEquipProprioRepository,
            equipRepository
        )
        val result = usecase(id = 1)
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Repository -> MovEquipProprioRepository.get")
    }

    @Test
    fun `Check return failure if have error in EquipRepository getNro`() = runTest {
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        val equipRepository = mock<EquipRepository>()
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
        val usecase = IGetNroEquipProprio(
            movEquipProprioRepository,
            equipRepository
        )
        val result = usecase(id = 1)
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Repository -> EquipRepository.getNro")
    }

    @Test
    fun `Check return nroEquip if GetNroEquipImpl execute success`() = runTest {
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        val equipRepository = mock<EquipRepository>()
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
        val usecase = IGetNroEquipProprio(
            movEquipProprioRepository,
            equipRepository
        )
        val result = usecase(id = 1)
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!, "100")
    }
}