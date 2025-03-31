package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioPassagRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class ICleanPassagColabTest {

    private val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
    private val usecase = ICleanPassagColab(
        movEquipProprioPassagRepository = movEquipProprioPassagRepository
    )

    @Test
    fun `Chech return failure Datasource if have error in cleanPassag`() = runTest {
        whenever(
            movEquipProprioPassagRepository.clean()
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
            "ICleanPassagColab -> Unknown Error"
        )
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            Exception().toString()
        )
    }

    @Test
    fun `Chech return true Datasource if cleanPassag execute success`() = runTest {
        whenever(
            movEquipProprioPassagRepository.clean()
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