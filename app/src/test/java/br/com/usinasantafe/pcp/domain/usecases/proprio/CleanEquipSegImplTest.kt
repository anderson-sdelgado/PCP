package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioEquipSegRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class CleanEquipSegImplTest {

    @Test
    fun `Chech return failure Datasource if have error in cleanEquipSeg`() = runTest {
        val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
        whenever(
            movEquipProprioEquipSegRepository.clear()
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val usecase = ICleanEquipSeg(movEquipProprioEquipSegRepository)
        val result = usecase()
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Repository -> MovEquipProprioEquipSegRepository.clear")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), Exception().toString())
    }

    @Test
    fun `Chech return true Datasource if cleanEquipSeg execute success`() = runTest {
        val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
        whenever(
            movEquipProprioEquipSegRepository.clear()
        ).thenReturn(
            Result.success(true)
        )
        val usecase = ICleanEquipSeg(movEquipProprioEquipSegRepository)
        val result = usecase()
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
    }

}