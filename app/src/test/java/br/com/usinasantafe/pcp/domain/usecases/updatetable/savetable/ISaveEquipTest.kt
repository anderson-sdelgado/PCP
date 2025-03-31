package br.com.usinasantafe.pcp.domain.usecases.updatetable.savetable

import br.com.usinasantafe.pcp.domain.entities.stable.Equip
import br.com.usinasantafe.pcp.domain.repositories.stable.EquipRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.whenever

class ISaveEquipTest {

    private val equipRepository = Mockito.mock<EquipRepository>()
    private val usecase = ISaveEquip(equipRepository)

    @Test
    fun `Check execution correct`() = runTest {
        val equipList = listOf(
            Equip(
                idEquip = 10,
                nroEquip = 100,
                descrEquip = "teste"
            )
        )
        whenever(
            equipRepository.addAll(equipList)
        ).thenReturn(
            Result.success(true)
        )
        val result = usecase(equipList)
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull(),
            true
        )
    }

    @Test
    fun `Check execution incorrect`() = runTest {
        val equipList = listOf(
            Equip(
                idEquip = 10,
                nroEquip = 100,
                descrEquip = "teste"
            )
        )
        whenever(
            equipRepository.addAll(equipList)
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val result = usecase(equipList)
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "ISaveEquip -> Unknown Error"
        )
    }
}