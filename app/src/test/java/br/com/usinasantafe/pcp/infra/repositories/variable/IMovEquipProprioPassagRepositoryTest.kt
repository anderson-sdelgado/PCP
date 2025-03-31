package br.com.usinasantafe.pcp.infra.repositories.variable

import br.com.usinasantafe.pcp.infra.datasource.room.variable.MovEquipProprioPassagRoomDatasource
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.MovEquipProprioPassagSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipProprioPassagRoomModel
import br.com.usinasantafe.pcp.utils.FlowApp
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IMovEquipProprioPassagRepositoryTest {

    private val movEquipProprioPassagSharedPreferencesDatasource =
        mock<MovEquipProprioPassagSharedPreferencesDatasource>()
    private val movEquipProprioPassagRoomDatasource =
        mock<MovEquipProprioPassagRoomDatasource>()
    private val repository = IMovEquipProprioPassagRepository(
        movEquipProprioPassagSharedPreferencesDatasource,
        movEquipProprioPassagRoomDatasource
    )

    @Test
    fun `Check failure Datasource in MovEquipProprioPassagRepository clean`() = runTest {
        whenever(
            movEquipProprioPassagSharedPreferencesDatasource.clean()
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
            "IMovEquipProprioPassagRepository.clean -> Unknown Error"
        )
    }

    @Test
    fun `Check true if have success in MovEquipProprioPassagRepository clear`() = runTest {
        whenever(
            movEquipProprioPassagSharedPreferencesDatasource.clean()
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
    fun `Check failure Datasource in MovEquipProprioPassagRepository list - FlowApp ADD`() =
        runTest {
            whenever(
                movEquipProprioPassagSharedPreferencesDatasource.list()
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
                "IMovEquipProprioPassagRepository.list -> Unknown Error"
            )
        }

    @Test
    fun `Check failure Datasource in MovEquipProprioPassagRepository list - FlowApp CHANGE`() =
        runTest {
            whenever(
                movEquipProprioPassagRoomDatasource.list(1)
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
                "IMovEquipProprioPassagRepository.list -> Unknown Error"
            )
        }

    @Test
    fun `Check return list if have success in MovEquipProprioPassagRepository list - FlowApp ADD`() =
        runTest {
            whenever(
                movEquipProprioPassagSharedPreferencesDatasource.list()
            ).thenReturn(
                Result.success(
                    listOf(19759)
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
                result.getOrNull()!![0].matricColab,
                19759
            )
        }

    @Test
    fun `Check return list if have success in MovEquipProprioPassagRepository list - FlowApp CHANGE`() =
        runTest {
            whenever(
                movEquipProprioPassagRoomDatasource.list(1)
            ).thenReturn(
                Result.success(
                    listOf(
                        MovEquipProprioPassagRoomModel(
                            idMovEquipProprioPassag = 1,
                            idMovEquipProprio = 1,
                            matricColab = 19759
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
                result.getOrNull()!![0].matricColab,
                19759
            )
        }

    @Test
    fun `Check return failure Datasource if have error in MovEquipProprioPassagRepository delete - FlowApp ADD`() =
        runTest {
            whenever(
                movEquipProprioPassagSharedPreferencesDatasource.delete(19759)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.delete(
                matricColab = 19759,
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipProprioPassagRepository.delete -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure Datasource if have error in MovEquipProprioPassagRepository delete - FlowApp CHANGE`() =
        runTest {
            whenever(
                movEquipProprioPassagRoomDatasource.delete(19759, 1)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.delete(
                matricColab = 19759,
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipProprioPassagRepository.delete -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if MovEquipProprioPassagRepository delete execute success - FlowApp ADD`() =
        runTest {
            whenever(
                movEquipProprioPassagSharedPreferencesDatasource.delete(19759)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.delete(
                matricColab = 19759,
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
    fun `Check return true if MovEquipProprioPassagRepository delete execute success - FlowApp CHANGE`() =
        runTest {
            whenever(
                movEquipProprioPassagRoomDatasource.delete(19759, 1)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.delete(
                matricColab = 19759,
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
    fun `Check return failure if have error in MovEquipProprioPassagRepository add - FlowApp ADD`() =
        runTest {
            whenever(
                movEquipProprioPassagSharedPreferencesDatasource.add(19759)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.add(
                matricColab = 19759,
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipProprioPassagRepository.add -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipProprioPassagRepository add - FlowApp CHANGE`() =
        runTest {
            whenever(
                movEquipProprioPassagRoomDatasource.add(19759, 1)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.add(
                matricColab = 19759,
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipProprioPassagRepository.add -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if MovEquipProprioPassagRepository add execute success - FlowApp ADD`() =
        runTest {
            whenever(movEquipProprioPassagSharedPreferencesDatasource.add(19759)).thenReturn(
                Result.success(true)
            )
            val result = repository.add(
                matricColab = 19759,
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
    fun `Check return true if MovEquipProprioPassagRepository add execute success - FlowApp CHANGE`() =
        runTest {
            whenever(
                movEquipProprioPassagRoomDatasource.add(19759, 1)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.add(
                matricColab = 19759,
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
    fun `Check return failure if have error in MovEquipProprioPassagRepository list`() = runTest {
        whenever(
            movEquipProprioPassagSharedPreferencesDatasource.list()
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
            "IMovEquipProprioPassagRepository.save -> Unknown Error"
        )
    }

    @Test
    fun `Check return failure if have error in MovEquipProprioPassagRoomDatasource addAll`() =
        runTest {
            val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            val movEquipProprioPassagRoomModelList = list.map {
                MovEquipProprioPassagRoomModel(
                    idMovEquipProprio = 1,
                    matricColab = it
                )
            }
            whenever(
                movEquipProprioPassagSharedPreferencesDatasource.list()
            ).thenReturn(
                Result.success(list)
            )
            whenever(
                movEquipProprioPassagRoomDatasource.addAll(
                    movEquipProprioPassagRoomModelList
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
                "IMovEquipProprioPassagRepository.save -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if MovEquipProprioPassagRepository save execute successfully`() =
        runTest {
            val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            val movEquipProprioPassagRoomModelList = list.map {
                MovEquipProprioPassagRoomModel(
                    idMovEquipProprio = 1,
                    matricColab = it
                )
            }
            whenever(
                movEquipProprioPassagSharedPreferencesDatasource.list()
            ).thenReturn(
                Result.success(list)
            )
            whenever(
                movEquipProprioPassagRoomDatasource.addAll(
                    movEquipProprioPassagRoomModelList
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
    fun `Check return failure if have error in movEquipProprioPassagRoomDatasource delete`() =
        runTest {
            whenever(
                movEquipProprioPassagRoomDatasource.delete(1)
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
                "IMovEquipProprioPassagRepository.delete -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if MovEquipProprioPassagRepository delete execute successfully`() =
        runTest {
            whenever(
                movEquipProprioPassagRoomDatasource.delete(1)
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