package br.com.usinasantafe.pcp.infra.repositories.stable

import br.com.usinasantafe.pcp.domain.entities.stable.Fluxo
import br.com.usinasantafe.pcp.infra.datasource.retrofit.stable.FluxoRetrofitDatasource
import br.com.usinasantafe.pcp.infra.datasource.room.stable.FluxoRoomDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.stable.FluxoRetrofitModel
import br.com.usinasantafe.pcp.infra.models.room.stable.FluxoRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IFluxoRepositoryTest {

    private val fluxoRoomDatasource = mock<FluxoRoomDatasource>()
    private val fluxoRetrofitDatasource = mock<FluxoRetrofitDatasource>()
    private val repository = IFluxoRepository(
        fluxoRoomDatasource,
        fluxoRetrofitDatasource
    )

    @Test
    fun `AddAll - Check return failure if have error in FluxoRoomDatasource addAll`() =
        runTest {
            val entityList = listOf(
                Fluxo(
                    idFluxo = 1,
                    descrFluxo = "teste"
                )
            )
            val roomModelList = listOf(
                FluxoRoomModel(
                    idFluxo = 1,
                    descrFluxo = "teste"
                )
            )
            whenever(
                fluxoRoomDatasource.addAll(
                    roomModelList
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.addAll(
                entityList
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IFluxoRepository.addAll -> Unknown Error"
            )
        }

    @Test
    fun `AddAll - Check return true if FluxoRepositoryImpl addAll execute successfully`() =
        runTest {
            val entityList = listOf(
                Fluxo(
                    idFluxo = 1,
                    descrFluxo = "teste"
                )
            )
            val roomModelList = listOf(
                FluxoRoomModel(
                    idFluxo = 1,
                    descrFluxo = "teste"
                )
            )
            whenever(
                fluxoRoomDatasource.addAll(
                    roomModelList
                )
            ).thenReturn(
                Result.success(
                    true
                )
            )
            val result = repository.addAll(
                entityList
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result,
                Result.success(
                    true
                )
            )
        }

    @Test
    fun `Delete All - Check return failure if have error`() =
        runTest {
            whenever(
                fluxoRoomDatasource.deleteAll()
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
                "IFluxoRepository.deleteAll -> Unknown Error"
            )
        }

    @Test
    fun `DeleteAll - Check return true if FluxoRepositoryImpl deleteAll execute successfully`() =
        runTest {
            whenever(
                fluxoRoomDatasource.deleteAll()
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
                fluxoRetrofitDatasource.recoverAll(
                    "token"
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.recoverAll(
                "token"
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IFluxoRepository.recoverAll -> Unknown Error"
            )
        }

    @Test
    fun `RecoverAll - Check return true if FluxoRepositoryImpl recoverAll execute successfully`() =
        runTest {
            val entityList = listOf(
                Fluxo(
                    idFluxo = 1,
                    descrFluxo = "teste"
                )
            )
            val retrofitModelList = listOf(
                FluxoRetrofitModel(
                    idFluxo = 1,
                    descrFluxo = "teste"
                )
            )
            whenever(
                fluxoRetrofitDatasource.recoverAll(
                    "token"
                )
            ).thenReturn(
                Result.success(
                    retrofitModelList
                )
            )
            val result = repository.recoverAll(
                "token"
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result,
                Result.success(
                    entityList
                )
            )
        }

    @Test
    fun `Get - Check return failure if have error in FluxoRoomDatasource get`() =
        runTest {
            whenever(
                fluxoRoomDatasource.get(1)
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
                "IFluxoRepository.get -> Unknown Error"
            )
        }

}