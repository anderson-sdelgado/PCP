package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioEquipSegRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class ICleanEquipSegTest {

    private val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
    private val usecase = ICleanEquipSeg(
        movEquipProprioEquipSegRepository = movEquipProprioEquipSegRepository
    )

    @Test
    fun `Chech return failure Datasource if have error in cleanEquipSeg`() = runTest {
        whenever(
            movEquipProprioEquipSegRepository.clear()
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
            "ICleanEquipSeg -> Unknown Error"
        )
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            Exception().toString()
        )
    }

    @Test
    fun `Chech return true Datasource if cleanEquipSeg execute success`() = runTest {
        whenever(
            movEquipProprioEquipSegRepository.clear()
        ).thenReturn(
            Result.success(true)
        )
        val result = usecase()
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!,
            true
        )
    }

}