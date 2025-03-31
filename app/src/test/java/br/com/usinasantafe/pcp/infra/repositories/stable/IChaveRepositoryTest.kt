package br.com.usinasantafe.pcp.infra.repositories.stable

import br.com.usinasantafe.pcp.domain.entities.stable.Chave
import br.com.usinasantafe.pcp.infra.datasource.retrofit.stable.ChaveRetrofitDatasource
import br.com.usinasantafe.pcp.infra.datasource.room.stable.ChaveRoomDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.stable.ChaveRetrofitModel
import br.com.usinasantafe.pcp.infra.models.room.stable.ChaveRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IChaveRepositoryTest {

    private val chaveRoomDatasource = mock<ChaveRoomDatasource>()
    private val chaveRetrofitDatasource = mock<ChaveRetrofitDatasource>()
    private val repository = IChaveRepository(
        chaveRetrofitDatasource = chaveRetrofitDatasource,
        chaveRoomDatasource = chaveRoomDatasource
    )

    @Test
    fun `AddAll - Check return failure if have error`() =
        runTest {
            val roomModelList = listOf(
                ChaveRoomModel(
                    idChave = 1,
                    descrChave = "01 - TI",
                    idLocalTrab = 1
                )
            )
            val entityList = listOf(
                Chave(
                    idChave = 1,
                    descrChave = "01 - TI",
                    idLocalTrab = 1
                )
            )
            whenever(
                chaveRoomDatasource.addAll(roomModelList)
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
                "IChaveRepository.addAll -> Unknown Error"
            )
        }

    @Test
    fun `AddAll - Check return true if function execute successfully`() =
        runTest {
            val roomModelList = listOf(
                ChaveRoomModel(
                    idChave = 1,
                    descrChave = "01 - TI",
                    idLocalTrab = 1
                )
            )
            val entityList = listOf(
                Chave(
                    idChave = 1,
                    descrChave = "01 - TI",
                    idLocalTrab = 1
                )
            )
            whenever(
                chaveRoomDatasource.addAll(roomModelList)
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
                chaveRoomDatasource.deleteAll()
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
                "IChaveRepository.deleteAll -> Unknown Error"
            )
        }

    @Test
    fun `DeleteAll - Check return true if function execute successfully`() =
        runTest {
            whenever(
                chaveRoomDatasource.deleteAll()
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
                chaveRetrofitDatasource.recoverAll("token")
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
                "IChaveRepository.recoverAll -> Unknown Error"
            )
        }

    @Test
    fun `RecoverAll - Check return true if function execute successfully`() =
        runTest {
            val retrofitModelList = listOf(
                ChaveRetrofitModel(
                    idChave = 1,
                    descrChave = "01 - TI",
                    idLocalTrab = 1
                )
            )
            val entityList = listOf(
                Chave(
                    idChave = 1,
                    descrChave = "01 - TI",
                    idLocalTrab = 1
                )
            )
            whenever(
                chaveRetrofitDatasource.recoverAll("token")
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
    fun `Check return failure if have error in ChaveRoomDatasource getDescr`() =
        runTest {
            whenever(
                chaveRoomDatasource.get(1)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.get(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IChaveRepository.get -> Unknown Error"
            )
        }

    @Test
    fun `listAll - Check return failure if have error in ChaveRoomDatasource listAll`() =
        runTest {
            whenever(
                chaveRoomDatasource.listAll()
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.listAll()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IChaveRepository.listAll -> Unknown Error"
            )
        }

    @Test
    fun `listAll - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                chaveRoomDatasource.listAll()
            ).thenReturn(
                Result.success(
                    listOf(
                        ChaveRoomModel(
                            idChave = 1,
                            descrChave = "01 - TI",
                            idLocalTrab = 1
                        )
                    )
                )
            )
            val result = repository.listAll()
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                1
            )
            val entity = list[0]
            assertEquals(
                entity.idChave,
                1
            )
            assertEquals(
                entity.descrChave,
                "01 - TI"
            )
        }
}