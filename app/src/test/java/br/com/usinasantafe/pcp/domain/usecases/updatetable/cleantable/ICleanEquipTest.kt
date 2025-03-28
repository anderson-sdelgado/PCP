package br.com.usinasantafe.pcp.domain.usecases.updatetable.cleantable

import br.com.usinasantafe.pcp.domain.repositories.stable.EquipRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ICleanEquipTest {

    @Test
    fun `Check execution correct`() = runTest {
        val equipRepository = mock<EquipRepository>()
        whenever(
            equipRepository.deleteAll()
        ).thenReturn(
            Result.success(true)
        )
        val usecase = ICleanEquip(equipRepository)
        val result = usecase()
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull(), true)
    }

    @Test
    fun `Check execution incorrect`() = runTest {
        val equipRepository = mock<EquipRepository>()
        whenever(
            equipRepository.deleteAll()
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val usecase = ICleanEquip(equipRepository)
        val result = usecase()
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> EquipRepository.deleteAll")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), "java.lang.Exception")
    }
}