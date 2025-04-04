package br.com.usinasantafe.pcp.infra.repositories.variable

import br.com.usinasantafe.pcp.infra.datasource.room.variable.MovEquipProprioEquipSegRoomDatasource
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.MovEquipProprioEquipSegSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipProprioEquipSegRoomModel
import br.com.usinasantafe.pcp.utils.FlowApp
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IMovEquipProprioEquipSegRepositoryTest {

    private val movEquipProprioEquipSegSharedPreferencesDatasource =
        mock<MovEquipProprioEquipSegSharedPreferencesDatasource>()
    private val movEquipProprioEquipSegRoomDatasource =
        mock<MovEquipProprioEquipSegRoomDatasource>()
    private val repository = IMovEquipProprioEquipSegRepository(
        movEquipProprioEquipSegSharedPreferencesDatasource,
        movEquipProprioEquipSegRoomDatasource
    )

    @Test
    fun `Check failure Datasource in MovEquipProprioSegRepository clear`() = runTest {
        whenever(
            movEquipProprioEquipSegSharedPreferencesDatasource.clean()
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val result = repository.clean()
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "IMovEquipProprioEquipSegRepository.clean -> Unknown Error"
        )
    }

    @Test
    fun `Check true if have success in MovEquipProprioSegRepository clean`() = runTest {
        whenever(
            movEquipProprioEquipSegSharedPreferencesDatasource.clean()
        ).thenReturn(
            Result.success(true)
        )
        val result = repository.clean()
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
    fun `Check failure Datasource in MovEquipProprioEquipSegRepository list FLowApp ADD`() = runTest {
        whenever(
            movEquipProprioEquipSegSharedPreferencesDatasource.list()
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val result = repository.list(
            FlowApp.ADD,
            0
        )
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "IMovEquipProprioEquipSegRepository.list -> Unknown Error"
        )
    }

    @Test
    fun `Check failure Datasource in MovEquipProprioEquipSegRepository list FLowApp CHANGE`() = runTest {
        whenever(
            movEquipProprioEquipSegRoomDatasource.list(1)
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val result = repository.list(
            FlowApp.CHANGE,
            1
        )
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "IMovEquipProprioEquipSegRepository.list -> Unknown Error"
        )
    }

    @Test
    fun `Check return list if have success in MovEquipProprioEquipSegRepository list FLowApp ADD`() = runTest {
        whenever(
            movEquipProprioEquipSegSharedPreferencesDatasource.list()
        ).thenReturn(
            Result.success(
                listOf(10)
            )
        )
        val result = repository.list(
            FlowApp.ADD,
            0
        )
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!.size,
            1
        )
        assertEquals(
            result.getOrNull()!![0].idEquip,
            10
        )
    }

    @Test
    fun `Check return list if have success in MovEquipProprioEquipSegRepository list FLowApp CHANCE`() = runTest {
        whenever(
            movEquipProprioEquipSegRoomDatasource.list(1)
        ).thenReturn(
            Result.success(
                listOf(
                    MovEquipProprioEquipSegRoomModel(
                        idMovEquipProprioEquipSeg = 1,
                        idMovEquipProprio = 1,
                        idEquip = 10
                    )
                )
            )
        )
        val result = repository.list(
            FlowApp.CHANGE,
            1
        )
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!.size,
            1
        )
        assertEquals(
            result.getOrNull()!![0].idEquip,
            10
        )
    }

    @Test
    fun `Check return failure Datasource if have error in MovEquipProprioEquipSegRepository delete FLowApp ADD`() =
        runTest {
            whenever(
                movEquipProprioEquipSegSharedPreferencesDatasource.delete(10)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.delete(
                idEquip = 10,
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipProprioEquipSegRepository.delete -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure Datasource if have error in MovEquipProprioEquipSegRepository delete FLowApp CHANGE`() =
        runTest {
            whenever(
                movEquipProprioEquipSegRoomDatasource.delete(10, 1)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.delete(
                idEquip = 10,
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipProprioEquipSegRepository.delete -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if MovEquipProprioEquipSegRepository delete execute success FLowApp ADD`() =
        runTest {
            whenever(
                movEquipProprioEquipSegSharedPreferencesDatasource.delete(10)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.delete(
                idEquip = 10,
                flowApp = FlowApp.ADD,
                id = 0
            )
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
    fun `Check return true if MovEquipProprioEquipSegRepository delete execute success FLowApp CHANGE`() =
        runTest {
            whenever(
                movEquipProprioEquipSegRoomDatasource.delete(10, 1)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.delete(
                idEquip = 10,
                flowApp = FlowApp.CHANGE,
                id = 1
            )
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
    fun `Check return failure if have error in MovEquipProprioEquipSegRepository add FlowApp ADD`() = runTest {
        whenever(
            movEquipProprioEquipSegSharedPreferencesDatasource.add(10)
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val result = repository.add(
            idEquip = 10,
            flowApp = FlowApp.ADD,
            id = 0
        )
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "IMovEquipProprioEquipSegRepository.add -> Unknown Error"
        )
    }

    @Test
    fun `Check return true if MovEquipProprioEquipSegRepository add execute success FlowApp ADD`() = runTest {
        whenever(
            movEquipProprioEquipSegSharedPreferencesDatasource.add(10)
        ).thenReturn(
            Result.success(true)
        )
        val result = repository.add(
            idEquip = 10,
            flowApp = FlowApp.ADD,
            id = 0
        )
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
    fun `Check return failure if have error in MovEquipProprioEquipSegRepository add FlowApp CHANGE`() = runTest {
        whenever(
            movEquipProprioEquipSegRoomDatasource.add(
                idEquip = 10,
                id = 1
            )
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val result = repository.add(
            idEquip = 10,
            flowApp = FlowApp.CHANGE,
            id = 1
        )
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "IMovEquipProprioEquipSegRepository.add -> Unknown Error"
        )
    }

    @Test
    fun `Check return true if MovEquipProprioEquipSegRepository add execute success FlowApp CHANGE`() = runTest {
        whenever(
            movEquipProprioEquipSegRoomDatasource.add(10, 1)
        ).thenReturn(
            Result.success(true)
        )
        val result = repository.add(
            idEquip = 10,
            flowApp = FlowApp.CHANGE,
            id = 1
        )
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
    fun `Check return failure Save if have error in MovEquipProprioEquipSegSharedPreferencesDatasource list`() =
        runTest {
            whenever(
                movEquipProprioEquipSegSharedPreferencesDatasource.list()
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.save(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipProprioEquipSegRepository.save -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure Save if have error in MovEquipProprioEquipSegRoomDatasource addAll`() =
        runTest {
            val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            val movEquipProprioEquipSegRoomModelList = list.map {
                MovEquipProprioEquipSegRoomModel(
                    idMovEquipProprio = 1,
                    idEquip = it
                )
            }
            whenever(
                movEquipProprioEquipSegSharedPreferencesDatasource.list()
            ).thenReturn(
                Result.success(list)
            )
            whenever(
                movEquipProprioEquipSegRoomDatasource.addAll(
                    movEquipProprioEquipSegRoomModelList
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.save(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipProprioEquipSegRepository.save -> Unknown Error"
            )
        }

    @Test
    fun `Check return true Save if MovEquipProprioEquipSegRoomDatasource addAll execute successfully`() =
        runTest {
            val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            val movEquipProprioEquipSegRoomModelList = list.map {
                MovEquipProprioEquipSegRoomModel(
                    idMovEquipProprio = 1,
                    idEquip = it
                )
            }
            whenever(
                movEquipProprioEquipSegSharedPreferencesDatasource.list()
            ).thenReturn(
                Result.success(list)
            )
            whenever(
                movEquipProprioEquipSegRoomDatasource.addAll(
                    movEquipProprioEquipSegRoomModelList
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.save(1)
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
    fun `Check return failure if have error in movEquipProprioEquipSegRoomDatasource delete`() =
        runTest {
            whenever(
                movEquipProprioEquipSegRoomDatasource.delete(1)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.delete(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipProprioEquipSegRepository.delete -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if MovEquipProprioEquipSegRepository delete execute successfully`() =
        runTest {
            whenever(
                movEquipProprioEquipSegRoomDatasource.delete(1)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.delete(1)
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