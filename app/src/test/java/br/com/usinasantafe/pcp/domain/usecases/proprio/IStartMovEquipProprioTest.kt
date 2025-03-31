package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioPassagRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioEquipSegRepository
import br.com.usinasantafe.pcp.utils.TypeMovEquip
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class IStartMovEquipProprioTest {

    private val movEquipProprioRepository = mock<MovEquipProprioRepository>()
    private val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
    private val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
    private val usecase = IStartMovEquipProprio(
        movEquipProprioRepository,
        movEquipProprioEquipSegRepository,
        movEquipProprioPassagRepository
    )

    @Test
    fun `Check return failure if have failure in MovEquipProprioRepository Start`() = runTest {
        whenever(
            movEquipProprioRepository.start(TypeMovEquip.INPUT)
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val result = usecase(TypeMovEquip.INPUT)
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "IStartMovEquipProprio -> Unknown Error"
        )
    }

    @Test
    fun `Check return failure if have failure in MovEquipProprioSegRepository clear`() = runTest {
        whenever(
            movEquipProprioRepository.start(TypeMovEquip.INPUT)
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            movEquipProprioEquipSegRepository.clean()
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val result = usecase(TypeMovEquip.INPUT)
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "IStartMovEquipProprio -> Unknown Error"
        )
    }

    @Test
    fun `Check return failure if have failure in MovEquipProprioPassagRepository clear`() = runTest {
        whenever(
            movEquipProprioRepository.start(TypeMovEquip.INPUT)
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            movEquipProprioEquipSegRepository.clean()
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            movEquipProprioPassagRepository.clean()
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val result = usecase(TypeMovEquip.INPUT)
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "IStartMovEquipProprio -> Unknown Error"
        )
    }

    @Test
    fun `Check return true if execute correctly`() = runTest {
        whenever(
            movEquipProprioRepository.start(TypeMovEquip.INPUT)
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            movEquipProprioEquipSegRepository.clean()
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            movEquipProprioPassagRepository.clean()
        ).thenReturn(
            Result.success(true)
        )
        val result = usecase(TypeMovEquip.INPUT)
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