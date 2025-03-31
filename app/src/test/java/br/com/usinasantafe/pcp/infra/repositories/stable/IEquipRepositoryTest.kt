package br.com.usinasantafe.pcp.infra.repositories.stable

import br.com.usinasantafe.pcp.domain.entities.stable.Equip
import br.com.usinasantafe.pcp.infra.datasource.room.stable.EquipRoomDatasource
import br.com.usinasantafe.pcp.infra.datasource.retrofit.stable.EquipRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.stable.EquipRetrofitModel
import br.com.usinasantafe.pcp.infra.models.room.stable.EquipRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IEquipRepositoryTest {

    private val equipRoomDatasource = mock<EquipRoomDatasource>()
    private val equipRetrofitDatasource = mock<EquipRetrofitDatasource>()
    private val repository = IEquipRepository(
        equipRoomDatasource,
        equipRetrofitDatasource
    )

    @Test
    fun `Check execution correct deleteAll`() = runTest {
        whenever(
            equipRoomDatasource.deleteAll()
        ).thenReturn(
            Result.success(true)
        )
        val result = repository.deleteAll()
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
    fun `Check execution incorrect deleteAll`() = runTest {
        whenever(
            equipRoomDatasource.deleteAll()
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val result = repository.deleteAll()
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "IEquipRepository.deleteAll -> Unknown Error"
        )
    }

    val token = "Bearer E49AD0C7AAA85FA6AB01FFD4AF7205C7"

    @Test
    fun `Check failure Datasource in recover data`() = runTest {
        whenever(
            equipRetrofitDatasource.recoverAll(token)
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val result = repository.recoverAll(token)
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "IEquipRepository.recoverAll -> Unknown Error"
        )
    }

    @Test
    fun `Check data is correct in recover data`() = runTest {
        val entityList = listOf(
            Equip(
                idEquip = 10,
                nroEquip = 19759,
                descrEquip = "teste"
            )
        )
        val retrofitModelList = listOf(
            EquipRetrofitModel(
                idEquip = 10,
                nroEquip = 19759,
                descrEquip = "teste"
            )
        )
        whenever(
            equipRetrofitDatasource.recoverAll(token)
        ).thenReturn(
            Result.success(retrofitModelList)
        )
        val result = repository.recoverAll(token)
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result,
            Result.success(entityList)
        )
    }

    @Test
    fun `Check execution correct addAll`() = runTest {
        val equipList = listOf(
            Equip(
                idEquip = 10,
                nroEquip = 19759,
                descrEquip = "teste"
            )
        )
        val equipRoomModelList = listOf(
            EquipRoomModel(
                idEquip = 10,
                nroEquip = 19759,
                descrEquip = "teste"
            )
        )
        whenever(
            equipRoomDatasource.addAll(equipRoomModelList)
        ).thenReturn(
            Result.success(
                true
            )
        )
        val result = repository.addAll(equipList)
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
    fun `Check execution incorrect addAll`() = runTest {
        val equipList = listOf(
            Equip(
                idEquip = 10,
                nroEquip = 19759,
                descrEquip = "teste"
            )
        )
        val equipRoomModelList = listOf(
            EquipRoomModel(
                idEquip = 10,
                nroEquip = 19759,
                descrEquip = "teste"
            )
        )
        whenever(
            equipRoomDatasource.addAll(equipRoomModelList)
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val result = repository.addAll(equipList)
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "IEquipRepository.addAll -> Unknown Error"
        )
    }

    @Test
    fun `Check return false if not exist Equip`() = runTest {
        whenever(
            equipRoomDatasource.checkNro(100)
        ).thenReturn(
            Result.success(false)
        )
        val result = repository.checkNro(100)
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!,
            false
        )
    }

    @Test
    fun `Check return true if exist Equip`() = runTest {
        whenever(
            equipRoomDatasource.checkNro(100)
        ).thenReturn(
            Result.success(true)
        )
        val result = repository.checkNro(100)
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
    fun `Check return failure if have error in checkNro Datasource`() = runTest {
        whenever(
            equipRoomDatasource.checkNro(100)
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val result = repository.checkNro(100)
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "IEquipRepository.checkNro -> Unknown Error"
        )
    }

    @Test
    fun `Check return failure if have failure in EquipRoomDatasource getNro`() = runTest {
        whenever(
            equipRoomDatasource.getNro(1)
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val result = repository.getNro(1)
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "IEquipRepository.getNro -> Unknown Error"
        )
    }

    @Test
    fun `Check return failure if have failure in EquipRoomDatasource getNro return 0`() = runTest {
        whenever(
            equipRoomDatasource.getNro(1)
        ).thenReturn(
            Result.success(0)
        )
        val result = repository.getNro(1)
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "IEquipRepository.getNro"
        )
        assertEquals(result.exceptionOrNull()!!.cause.toString(), "java.lang.Exception: Nro is 0")
    }

    @Test
    fun `Check return nroEquip if EquipRoomDatasource getNro execute successfully`() = runTest {
        whenever(
            equipRoomDatasource.getNro(1)
        ).thenReturn(
            Result.success(100)
        )
        val result = repository.getNro(1)
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!,
            100
        )
    }


    @Test
    fun `Check return failure if have failure in EquipRoomDatasource getId`() = runTest {
        whenever(
            equipRoomDatasource.getId(1)
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val result = repository.getId(1)
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "IEquipRepository.getId -> Unknown Error"
        )
    }

    @Test
    fun `Check return failure if have failure in EquipRoomDatasource getId return 0`() = runTest {
        whenever(
            equipRoomDatasource.getId(100)
        ).thenReturn(
            Result.success(0)
        )
        val result = repository.getId(100)
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "IEquipRepository.getId"
        )
        assertEquals(result.exceptionOrNull()!!.cause.toString(), "java.lang.Exception: Id is 0")
    }

    @Test
    fun `Check return idEquip if EquipRoomDatasource getId execute successfully`() = runTest {
        whenever(
            equipRoomDatasource.getId(100)
        ).thenReturn(
            Result.success(10)
        )
        val result = repository.getId(100)
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!,
            10
        )
    }

}