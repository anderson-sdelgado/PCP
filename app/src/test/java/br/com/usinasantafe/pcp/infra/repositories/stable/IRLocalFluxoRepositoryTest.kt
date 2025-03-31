package br.com.usinasantafe.pcp.infra.repositories.stable

import br.com.usinasantafe.pcp.domain.entities.stable.RLocalFluxo
import br.com.usinasantafe.pcp.infra.datasource.retrofit.stable.RLocalFluxoRetrofitDatasource
import br.com.usinasantafe.pcp.infra.datasource.room.stable.RLocalFluxoRoomDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.stable.RLocalFluxoRetrofitModel
import br.com.usinasantafe.pcp.infra.models.room.stable.RLocalFluxoRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IRLocalFluxoRepositoryTest {
    
    private val rLocalFluxoRoomDatasource = mock<RLocalFluxoRoomDatasource>()
    private val rLocalFluxoRetrofitDatasource = mock<RLocalFluxoRetrofitDatasource>()
    private val repository = IRLocalFluxoRepository(
        rLocalFluxoRoomDatasource,
        rLocalFluxoRetrofitDatasource
    )

    @Test
    fun `AddAll - Check return failure if have error`() =
        runTest {
            val roomModelList = listOf(
                RLocalFluxoRoomModel(
                    idRLocalFluxo = 1,
                    idLocal = 1,
                    idFluxo = 1
                )
            )
            val entityList = listOf(
                RLocalFluxo(
                    idRLocalFluxo = 1,
                    idLocal = 1,
                    idFluxo = 1
                )
            )
            whenever(
                rLocalFluxoRoomDatasource.addAll(roomModelList)
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
                "IRLocalFluxoRepository.addAll -> Unknown Error"
            )
        }

    @Test
    fun `AddAll - Check return true if RLocalFluxoRepositoryImpl addAll execute successfully`() =
        runTest {
            val roomModelList = listOf(
                RLocalFluxoRoomModel(
                    idRLocalFluxo = 1,
                    idLocal = 1,
                    idFluxo = 1
                )
            )
            val entityList = listOf(
                RLocalFluxo(
                    idRLocalFluxo = 1,
                    idLocal = 1,
                    idFluxo = 1
                )
            )
            whenever(
                rLocalFluxoRoomDatasource.addAll(roomModelList)
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
                rLocalFluxoRoomDatasource.deleteAll()
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
                "IRLocalFluxoRepository.deleteAll -> Unknown Error"
            )
        }

    @Test
    fun `DeleteAll - Check return true if RLocalFluxoRepositoryImpl deleteAll execute successfully`() =
        runTest {
            whenever(
                rLocalFluxoRoomDatasource.deleteAll()
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
    fun `RecoverAll - Check return failure if have error in RLocalFluxoRetrofitDatasource recoverAll`() =
        runTest {
            whenever(
                rLocalFluxoRetrofitDatasource.recoverAll("token")
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
                "IRLocalFluxoRepository.recoverAll -> Unknown Error"
            )
        }

    @Test
    fun `RecoverAll - Check return true if RLocalFluxoRepositoryImpl RecoverAll execute successfully`() =
        runTest {
            val retrofitModelList = listOf(
                RLocalFluxoRetrofitModel(
                    idRLocalFluxo = 1,
                    idLocal = 1,
                    idFluxo = 1
                )
            )
            val entityList = listOf(
                RLocalFluxo(
                    idRLocalFluxo = 1,
                    idLocal = 1,
                    idFluxo = 1
                )
            )
            whenever(
                rLocalFluxoRetrofitDatasource.recoverAll("token")
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
    fun `List - Check return failure if have error in RLocalFluxoRoomDatasource list`() =
        runTest {
            whenever(
                rLocalFluxoRoomDatasource.list(1)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.list(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IRLocalFluxoRepository.list -> Unknown Error"
            )
        }

    @Test
    fun `List - Check return true if RLocalFluxoRepositoryImpl List execute successfully`() =
        runTest {
            val roomModelList = listOf(
                RLocalFluxoRoomModel(
                    idRLocalFluxo = 1,
                    idLocal = 1,
                    idFluxo = 1
                )
            )
            val entityList = listOf(
                RLocalFluxo(
                    idRLocalFluxo = 1,
                    idLocal = 1,
                    idFluxo = 1
                )
            )
            whenever(
                rLocalFluxoRoomDatasource.list(1)
            ).thenReturn(
                Result.success(
                    roomModelList
                )
            )
            val result = repository.list(1)
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list,
                entityList
            )
        }

}