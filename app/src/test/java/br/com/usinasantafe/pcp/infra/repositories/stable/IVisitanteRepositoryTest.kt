package br.com.usinasantafe.pcp.infra.repositories.stable

import br.com.usinasantafe.pcp.domain.entities.stable.Visitante
import br.com.usinasantafe.pcp.infra.datasource.room.stable.VisitanteRoomDatasource
import br.com.usinasantafe.pcp.infra.datasource.retrofit.stable.VisitanteRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.stable.VisitanteRetrofitModel
import br.com.usinasantafe.pcp.infra.models.room.stable.VisitanteRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IVisitanteRepositoryTest {

    private val visitanteRoomDatasource = mock<VisitanteRoomDatasource>()
    private val visitanteRetrofitDatasource = mock<VisitanteRetrofitDatasource>()
    private val repository = IVisitanteRepository(
        visitanteRoomDatasource,
        visitanteRetrofitDatasource
    )

    @Test
    fun `Check execution correct deleteAll`() = runTest {
        whenever(
            visitanteRoomDatasource.deleteAll()
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
            visitanteRoomDatasource.deleteAll()
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
            "IVisitanteRepository.deleteAll -> Unknown Error"
        )
    }

    val token = "Bearer E49AD0C7AAA85FA6AB01FFD4AF7205C7"

    @Test
    fun `Check failure Datasource in recover data`() = runTest {
        whenever(
            visitanteRetrofitDatasource.recoverAll(token)
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
            "IVisitanteRepository.recoverAll -> Unknown Error"
        )
    }

    @Test
    fun `Check data is correct in recover data`() = runTest {
        val retrofitModelList = listOf(
            VisitanteRetrofitModel(
                idVisitante = 1,
                nomeVisitante = "Visitante",
                cpfVisitante = "123.456.789-00",
                empresaVisitante = "Empresa Visitante"
            )
        )
        whenever(
            visitanteRetrofitDatasource.recoverAll(token)
        ).thenReturn(
            Result.success(retrofitModelList)
        )
        val result = repository.recoverAll(token)
        assertEquals(
            result.isSuccess,
            true
        )
        val resultList = result.getOrNull()!!
        assertEquals(
            resultList.isNotEmpty(),
            true
        )
        val entity = resultList[0]
        assertEquals(
            entity.nomeVisitante,
            "Visitante"
        )
    }

    @Test
    fun `Check execution correct addAll`() = runTest {
        val visitanteList = listOf(
            Visitante(
                idVisitante = 1,
                nomeVisitante = "Visitante",
                cpfVisitante = "123.456.789-00",
                empresaVisitante = "Empresa Visitante"
            )
        )
        val visitanteRoomModelList = listOf(
            VisitanteRoomModel(
                idVisitante = 1,
                nomeVisitante = "Visitante",
                cpfVisitante = "123.456.789-00",
                empresaVisitante = "Empresa Visitante"
            )
        )
        whenever(
            visitanteRoomDatasource.addAll(visitanteRoomModelList)
        ).thenReturn(
            Result.success(
                true
            )
        )
        val result = repository.addAll(visitanteList)
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
        val visitanteList = listOf(
            Visitante(
                idVisitante = 1,
                nomeVisitante = "Visitante",
                cpfVisitante = "123.456.789-00",
                empresaVisitante = "Empresa Visitante"
            )
        )
        val visitanteRoomModelList = listOf(
            VisitanteRoomModel(
                idVisitante = 1,
                nomeVisitante = "Visitante",
                cpfVisitante = "123.456.789-00",
                empresaVisitante = "Empresa Visitante"
            )
        )
        whenever(
            visitanteRoomDatasource.addAll(visitanteRoomModelList)
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val result = repository.addAll(visitanteList)
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "IVisitanteRepository.addAll -> Unknown Error"
        )
    }

    @Test
    fun `Check return failure if have error in VisitanteRoomDatasource checkCPF`() =
        runTest {
            whenever(
                visitanteRoomDatasource.checkCpf("123.456.789-00")
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.checkCPF("123.456.789-00")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IVisitanteRepository.checkCPF -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if CheckCPF execute successfully`() =
        runTest {
            whenever(
                visitanteRoomDatasource.checkCpf("123.456.789-00")
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.checkCPF("123.456.789-00")
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return false if CheckCPF execute successfully`() =
        runTest {
            whenever(
                visitanteRoomDatasource.checkCpf("123.456.789-00")
            ).thenReturn(
                Result.success(false)
            )
            val result = repository.checkCPF("123.456.789-00")
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
    fun `Get - Check return failure if have error in VisitanteRoomDatasource get`() =
        runTest {
            whenever(
                visitanteRoomDatasource.get(1)
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
                "IVisitanteRepository.get -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if VisitanteRepositoryImplTest Get execute successfully`() =
        runTest {
            val roomModel = VisitanteRoomModel(
                idVisitante = 1,
                nomeVisitante = "Visitante",
                cpfVisitante = "123.456.789-00",
                empresaVisitante = "Empresa Visitante"
            )
            whenever(
                visitanteRoomDatasource.get(1)
            ).thenReturn(
                Result.success(roomModel)
            )
            val result = repository.get(1)
            assertEquals(
                result.isSuccess,
                true
            )
            val resultRoomModel = result.getOrNull()!!
            assertEquals(
                resultRoomModel.nomeVisitante,
                "Visitante"
            )
        }

    @Test
    fun `GetCPF - Check return failure if have error in VisitanteRoomDatasource getCPF`() =
        runTest {
            whenever(
                visitanteRoomDatasource.get(1)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.getCpf(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IVisitanteRepository.getCpf -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if VisitanteRepositoryImplTest GetCPF execute successfully`() =
        runTest {
            val roomModel = VisitanteRoomModel(
                idVisitante = 1,
                nomeVisitante = "Visitante",
                cpfVisitante = "123.456.789-00",
                empresaVisitante = "Empresa Visitante"
            )
            whenever(
                visitanteRoomDatasource.get(1)
            ).thenReturn(
                Result.success(roomModel)
            )
            val result = repository.getCpf(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                "123.456.789-00"
            )
        }

    @Test
    fun `GetId - Check return failure if have error in VisitanteRoomDatasource get`() =
        runTest {
            whenever(
                visitanteRoomDatasource.get("123.456.789-00")
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.getId("123.456.789-00")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IVisitanteRepository.getId -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if VisitanteRepositoryImplTest GetId execute successfully`() =
        runTest {
            val roomModel = VisitanteRoomModel(
                idVisitante = 1,
                nomeVisitante = "Visitante",
                cpfVisitante = "123.456.789-00",
                empresaVisitante = "Empresa Visitante"
            )
            whenever(
                visitanteRoomDatasource.get("123.456.789-00")
            ).thenReturn(
                Result.success(roomModel)
            )
            val result = repository.getId("123.456.789-00")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                1
            )
        }

    @Test
    fun `GetNome - Check return failure if have error in VisitanteRoomDatasource get`() =
        runTest {
            whenever(
                visitanteRoomDatasource.get("123.456.789-00")
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.getNome("123.456.789-00")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IVisitanteRepository.getNome -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if VisitanteRepositoryImplTest GetNome execute successfully`() =
        runTest {
            val roomModel = VisitanteRoomModel(
                idVisitante = 1,
                nomeVisitante = "Visitante",
                cpfVisitante = "123.456.789-00",
                empresaVisitante = "Empresa Visitante"
            )
            whenever(
                visitanteRoomDatasource.get("123.456.789-00")
            ).thenReturn(
                Result.success(roomModel)
            )
            val result = repository.getNome("123.456.789-00")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                "Visitante"
            )
        }

    @Test
    fun `GetEmpresas - Check return failure if have error in VisitanteRoomDatasource get`() =
        runTest {
            whenever(
                visitanteRoomDatasource.get("123.456.789-00")
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.getEmpresas("123.456.789-00")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IVisitanteRepository.getEmpresas -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if VisitanteRepositoryImplTest GetEmpresas execute successfully`() =
        runTest {
            val roomModel = VisitanteRoomModel(
                idVisitante = 1,
                nomeVisitante = "Visitante",
                cpfVisitante = "123.456.789-00",
                empresaVisitante = "Empresa Visitante"
            )
            whenever(
                visitanteRoomDatasource.get("123.456.789-00")
            ).thenReturn(
                Result.success(roomModel)
            )
            val result = repository.getEmpresas("123.456.789-00")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                "Empresa Visitante"
            )
        }
}