package br.com.usinasantafe.pcp.infra.repositories.variable

import br.com.usinasantafe.pcp.infra.datasource.room.variable.MovEquipVisitTercPassagRoomDatasource
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.MovEquipVisitTercPassagSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipVisitTercPassagRoomModel
import br.com.usinasantafe.pcp.utils.FlowApp
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IMovEquipVisitTercPassagRepositoryTest {

    private val movEquipVisitTercPassagSharedPreferencesDatasource =
        mock<MovEquipVisitTercPassagSharedPreferencesDatasource>()
    private val movEquipVisitTercPassagRoomDatasource =
        mock<MovEquipVisitTercPassagRoomDatasource>()

    private val repository = IMovEquipVisitTercPassagRepository(
        movEquipVisitTercPassagSharedPreferencesDatasource,
        movEquipVisitTercPassagRoomDatasource
    )

    @Test
    fun `Check return failure if have error in MovEquipVisitTercPassagSharedPreferencesDatasource add - FlowApp ADD`() =
        runTest {
            whenever(
                movEquipVisitTercPassagSharedPreferencesDatasource.add(10)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.add(
                idVisitTerc = 10,
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipVisitTercPassagRepository.add -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercPassagRepository add - FlowApp CHANGE`() =
        runTest {
            whenever(
                movEquipVisitTercPassagRoomDatasource.add(10, 1)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.add(
                idVisitTerc = 10,
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipVisitTercPassagRepository.add -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if Add execute successfully - FlowApp ADD`() =
        runTest {
            whenever(
                movEquipVisitTercPassagSharedPreferencesDatasource.add(10)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.add(
                idVisitTerc = 10,
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
    fun `Check return true if Add execute successfully - FlowApp CHANGE`() =
        runTest {
            whenever(
                movEquipVisitTercPassagRoomDatasource.add(10, 1)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.add(
                idVisitTerc = 10,
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
    fun `Check return failure if have error in MovEquipVisitTercPassagRepository clean`() =
        runTest {
            whenever(
                movEquipVisitTercPassagSharedPreferencesDatasource.clean()
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
                "IMovEquipVisitTercPassagRepository.clean -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if MovEquipVisitTercPassagRepositoryImpl Clear execute successfully`() =
        runTest {
            whenever(
                movEquipVisitTercPassagSharedPreferencesDatasource.clean()
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
    fun `Check return failure if have error in MovEquipVisitTercPassagSharedPreferencesDatasource delete - FlowApp ADD`() =
        runTest {
            whenever(
                movEquipVisitTercPassagSharedPreferencesDatasource.delete(10)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.delete(
                idVisitTerc = 10,
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipVisitTercPassagRepository.delete -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercPassagRepository delete - FlowApp CHANGE`() =
        runTest {
            whenever(
                movEquipVisitTercPassagRoomDatasource.delete(10, 1)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.delete(
                idVisitTerc = 10,
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMovEquipVisitTercPassagRepository.delete -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if Delete execute successfully - FlowApp ADD`() =
        runTest {
            whenever(
                movEquipVisitTercPassagSharedPreferencesDatasource.delete(10)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.delete(
                idVisitTerc = 10,
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
    fun `Check return true if Delete execute successfully - FlowApp CHANGE`() =
        runTest {
            whenever(
                movEquipVisitTercPassagRoomDatasource.delete(10, 1)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.delete(
                idVisitTerc = 10,
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
    fun `Check return failure if have error in MovEquipVisitTercPassagSharedPreferencesDatasource list - FlowApp ADD`() =
        runTest {
            whenever(
                movEquipVisitTercPassagSharedPreferencesDatasource.list()
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
                "IMovEquipVisitTercPassagRepository.list -> Unknown Error"
            )
        }

    @Test
    fun `Check failure Datasource in MovEquipVisitTercPassagRoomDatasource list - FlowApp CHANGE`() =
        runTest {
            whenever(
                movEquipVisitTercPassagRoomDatasource.list(1)
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
                "IMovEquipVisitTercPassagRepository.list -> Unknown Error"
            )
        }

    @Test
    fun `Check return list if have success in MovEquipVisitTercPassagRepository list - FlowApp ADD`() =
        runTest {
            whenever(
                movEquipVisitTercPassagSharedPreferencesDatasource.list()
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
                result.getOrNull()!![0].idVisitTerc,
                10
            )
        }

    @Test
    fun `Check return list if have success in MovEquipVisitTercPassagRepository list - FlowApp CHANGE`() =
        runTest {
            whenever(
                movEquipVisitTercPassagRoomDatasource.list(1)
            ).thenReturn(
                Result.success(
                    listOf(
                        MovEquipVisitTercPassagRoomModel(
                            idMovEquipVisitTercPassag = 1,
                            idMovEquipVisitTerc = 1,
                            idVisitTerc = 10
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
                result.getOrNull()!![0].idVisitTerc,
                10
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercPassagRepository list`() = runTest {
        whenever(
            movEquipVisitTercPassagSharedPreferencesDatasource.list()
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
            "IMovEquipVisitTercPassagRepository.save -> Unknown Error"
        )
    }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercPassagRoomDatasource AddAll`() =
        runTest {
            val list = listOf(10, 20, 30)
            val modelList = list.map {
                MovEquipVisitTercPassagRoomModel(
                    idMovEquipVisitTerc = 1,
                    idVisitTerc = it
                )
            }
            whenever(
                movEquipVisitTercPassagSharedPreferencesDatasource.list()
            ).thenReturn(
                Result.success(list)
            )
            whenever(
                movEquipVisitTercPassagRoomDatasource.addAll(modelList)
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
                "IMovEquipVisitTercPassagRepository.save -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if MovEquipVisitTercPassagRepositoryImpl Save execute successfully`() =
        runTest {
            val list = listOf(10, 20, 30)
            val modelList = list.map {
                MovEquipVisitTercPassagRoomModel(
                    idMovEquipVisitTerc = 1,
                    idVisitTerc = it
                )
            }
            whenever(
                movEquipVisitTercPassagSharedPreferencesDatasource.list()
            ).thenReturn(
                Result.success(list)
            )
            whenever(
                movEquipVisitTercPassagRoomDatasource.addAll(modelList)
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
    fun `Check return failure if have error in movEquipVisitTercPassagRoomDatasource delete`() =
        runTest {
            whenever(
                movEquipVisitTercPassagRoomDatasource.delete(1)
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
                "IMovEquipVisitTercPassagRepository.delete -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if MovEquipVisitTercPassagRepository delete execute successfully`() =
        runTest {
            whenever(
                movEquipVisitTercPassagRoomDatasource.delete(1)
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