package br.com.usinasantafe.pcp.infra.repositories.stable

import br.com.usinasantafe.pcp.domain.entities.stable.Colab
import br.com.usinasantafe.pcp.infra.datasource.room.stable.ColabRoomDatasource
import br.com.usinasantafe.pcp.infra.datasource.retrofit.stable.ColabRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.stable.ColabRetrofitModel
import br.com.usinasantafe.pcp.infra.models.room.stable.ColabRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IColabRepositoryTest {

    private val colabRoomDatasource = mock<ColabRoomDatasource>()
    private val colabRetrofitDatasource = mock<ColabRetrofitDatasource>()
    private fun getRepository() = IColabRepository(
        colabRoomDatasource,
        colabRetrofitDatasource
    )

    @Test
    fun `Check execution correct deleteAll`() = runTest {
        whenever(
            colabRoomDatasource.deleteAll()
        ).thenReturn(
            Result.success(true)
        )
        val repository = getRepository()
        val result = repository.deleteAll()
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull(), true)
    }

    @Test
    fun `Check execution incorrect deleteAll`() = runTest {
        whenever(
            colabRoomDatasource.deleteAll()
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val repository = getRepository()
        val result = repository.deleteAll()
        assertEquals(result.isFailure, true)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> ColabRoomDatasource.deleteAll"
        )
    }

    val token = "Bearer E49AD0C7AAA85FA6AB01FFD4AF7205C7"

    @Test
    fun `Check failure Datasource in recover data`() = runTest {
        whenever(
            colabRetrofitDatasource.recoverAll(token)
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val repository = getRepository()
        val result = repository.recoverAll(token)
        assertEquals(result.isFailure, true)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> ColabRetrofitDatasource.recoverAll"
        )
    }

    @Test
    fun `Check data is correct in recover data`() = runTest {
        val entityList = listOf(
            Colab(
                matricColab = 19759,
                nomeColab = "Anderson"
            )
        )
        val retrofitModelList = listOf(
            ColabRetrofitModel(
                matricColab = 19759,
                nomeColab = "Anderson"
            )
        )
        whenever(
            colabRetrofitDatasource.recoverAll(token)
        ).thenReturn(
            Result.success(retrofitModelList)
        )
        val repository = getRepository()
        val result = repository.recoverAll(token)
        assertEquals(result.isSuccess, true)
        assertEquals(result, Result.success(entityList))
    }

    @Test
    fun `Check execution correct addAll`() = runTest {
        val colabList = listOf(
            Colab(
                matricColab = 19759,
                nomeColab = "Anderson"
            )
        )
        val colabRoomModelList = listOf(
            ColabRoomModel(
                matricColab = 19759,
                nomeColab = "Anderson"
            )
        )
        whenever(
            colabRoomDatasource.addAll(colabRoomModelList)
        ).thenReturn(
            Result.success(
                true
            )
        )
        val repository = getRepository()
        val result = repository.addAll(colabList)
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull(), true)
    }

    @Test
    fun `Check execution incorrect addAll`() = runTest {
        val colabList = listOf(
            Colab(
                matricColab = 19759,
                nomeColab = "Anderson"
            )
        )
        val colabRoomModelList = listOf(
            ColabRoomModel(
                matricColab = 19759,
                nomeColab = "Anderson"
            )
        )
        whenever(
            colabRoomDatasource.addAll(colabRoomModelList)
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val repository = getRepository()
        val result = repository.addAll(colabList)
        assertEquals(result.isFailure, true)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> ColabRoomDatasource.addAll"
        )
    }

    @Test
    fun `Check return false if not exist Colab`() = runTest {
        whenever(
            colabRoomDatasource.checkMatric(19759)
        ).thenReturn(
            Result.success(false)
        )
        val repository = getRepository()
        val result = repository.checkMatric(19759)
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull()!!, false)
    }

    @Test
    fun `Check return true if exist Colab`() = runTest {
        whenever(
            colabRoomDatasource.checkMatric(19759)
        ).thenReturn(
            Result.success(true)
        )
        val repository = getRepository()
        val result = repository.checkMatric(19759)
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull()!!, true)
    }

    @Test
    fun `Check return failure if have error in checkMatric Datasource`() = runTest {
        whenever(
            colabRoomDatasource.checkMatric(19759)
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val repository = getRepository()
        val result = repository.checkMatric(19759)
        assertEquals(result.isFailure, true)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> ColabRoomDatasource.checkMatric"
        )
    }

    @Test
    fun `Check return NomeVigia if have success in getNome`() = runTest {
        whenever(
            colabRoomDatasource.getNome(19759)
        ).thenReturn(
            Result.success("ANDERSON DA SILVA DELGADO")
        )
        val repository = getRepository()
        val result = repository.getNome(19759)
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!, "ANDERSON DA SILVA DELGADO")
    }

    @Test
    fun `Check return failure if have error in getNome Datasource`() = runTest {
        whenever(
            colabRoomDatasource.getNome(19759)
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val repository = getRepository()
        val result = repository.getNome(19759)
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> ColabRoomDatasource.getNome"
        )
    }

}