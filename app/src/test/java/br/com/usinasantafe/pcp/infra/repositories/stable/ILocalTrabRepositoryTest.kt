package br.com.usinasantafe.pcp.infra.repositories.stable

import br.com.usinasantafe.pcp.domain.entities.stable.LocalTrab
import br.com.usinasantafe.pcp.infra.datasource.retrofit.stable.LocalTrabRetrofitDatasource
import br.com.usinasantafe.pcp.infra.datasource.room.stable.LocalTrabRoomDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.stable.LocalTrabRetrofitModel
import br.com.usinasantafe.pcp.infra.models.room.stable.LocalTrabRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ILocalTrabRepositoryTest {

    private val localTrabRoomDatasource = mock<LocalTrabRoomDatasource>()
    private val localTrabRetrofitDatasource = mock<LocalTrabRetrofitDatasource>()
    private val repository = ILocalTrabRepository(
        localTrabRoomDatasource = localTrabRoomDatasource,
        localTrabRetrofitDatasource = localTrabRetrofitDatasource
    )

    @Test
    fun `AddAll - Check return failure if have error`() =
        runTest {
            val roomModelList = listOf(
                LocalTrabRoomModel(
                    idLocalTrab = 1,
                    descrLocalTrab = "TI"
                )
            )
            val entityList = listOf(
                LocalTrab(
                    idLocalTrab = 1,
                    descrLocalTrab = "TI"
                )
            )
            whenever(
                localTrabRoomDatasource.addAll(roomModelList)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.addAll(entityList)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ILocalTrabRepository.addAll -> Unknown Error"
            )
        }

    @Test
    fun `AddAll - Check return true if function execute successfully`() =
        runTest {
            val roomModelList = listOf(
                LocalTrabRoomModel(
                    idLocalTrab = 1,
                    descrLocalTrab = "TI"
                )
            )
            val entityList = listOf(
                LocalTrab(
                    idLocalTrab = 1,
                    descrLocalTrab = "TI"
                )
            )
            whenever(
                localTrabRoomDatasource.addAll(roomModelList)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.addAll(entityList)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result,
                Result.success(true)
            )
        }

    @Test
    fun `DeleteAll - Check return failure if have error`() =
        runTest {
            whenever(
                localTrabRoomDatasource.deleteAll()
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
                "ILocalTrabRepository.deleteAll -> Unknown Error"
            )
        }

    @Test
    fun `DeleteAll - Check return true if function execute successfully`() =
        runTest {
            whenever(
                localTrabRoomDatasource.deleteAll()
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.deleteAll()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result,
                Result.success(true)
            )
        }

    @Test
    fun `RecoverAll - Check return failure if have error`() =
        runTest {
            whenever(
                localTrabRetrofitDatasource.recoverAll("token")
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.recoverAll("token")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ILocalTrabRepository.recoverAll -> Unknown Error"
            )
        }

    @Test
    fun `RecoverAll - Check return true if function execute successfully`() =
        runTest {
            val retrofitModelList = listOf(
                LocalTrabRetrofitModel(
                    idLocalTrab = 1,
                    descrLocalTrab = "TI"
                )
            )
            val entityList = listOf(
                LocalTrab(
                    idLocalTrab = 1,
                    descrLocalTrab = "TI"
                )
            )
            whenever(
                localTrabRetrofitDatasource.recoverAll("token")
            ).thenReturn(
                Result.success(
                    retrofitModelList
                )
            )
            val result = repository.recoverAll("token")
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
    fun `Check return failure if have error in ILocalTrabRepository getDescr`() =
        runTest {
            whenever(
                localTrabRoomDatasource.getDescr(1)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.getDescr(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ILocalTrabRepository.getDescr -> Unknown Error"
            )
        }

    @Test
    fun `Check return description if ILocalTrabRepository getDescr execute successfully`() =
        runTest {
            whenever(
                localTrabRoomDatasource.getDescr(1)
            ).thenReturn(
                Result.success("TI")
            )
            val result = repository.getDescr(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                "TI"
            )
        }

}