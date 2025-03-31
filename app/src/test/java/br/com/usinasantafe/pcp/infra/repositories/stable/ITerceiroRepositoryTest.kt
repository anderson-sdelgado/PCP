package br.com.usinasantafe.pcp.infra.repositories.stable

import br.com.usinasantafe.pcp.domain.entities.stable.Terceiro
import br.com.usinasantafe.pcp.infra.datasource.room.stable.TerceiroRoomDatasource
import br.com.usinasantafe.pcp.infra.datasource.retrofit.stable.TerceiroRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.stable.TerceiroRetrofitModel
import br.com.usinasantafe.pcp.infra.models.room.stable.TerceiroRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ITerceiroRepositoryTest {

    private val terceiroRoomDatasource = mock<TerceiroRoomDatasource>()
    private val terceiroRetrofitDatasource = mock<TerceiroRetrofitDatasource>()
    private val repository = ITerceiroRepository(
        terceiroRoomDatasource,
        terceiroRetrofitDatasource
    )

    @Test
    fun `Check execution correct deleteAll`() = runTest {
        whenever(
            terceiroRoomDatasource.deleteAll()
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
            terceiroRoomDatasource.deleteAll()
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
            "ITerceiroRepository.deleteAll -> Unknown Error"
        )
    }

    val token = "Bearer E49AD0C7AAA85FA6AB01FFD4AF7205C7"

    @Test
    fun `Check failure Datasource in recover data`() = runTest {
        whenever(
            terceiroRetrofitDatasource.recoverAll(token)
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
            "ITerceiroRepository.recoverAll -> Unknown Error"
        )
    }

    @Test
    fun `Check data is correct in recover data`() = runTest {
        val entityList = listOf(
            Terceiro(
                idTerceiro = 1,
                idBDTerceiro = 1,
                nomeTerceiro = "Terceiro",
                cpfTerceiro = "123.456.789-00",
                empresaTerceiro = "Empresa Terceiro"
            )
        )
        val retrofitModelList = listOf(
            TerceiroRetrofitModel(
                idTerceiro = 1,
                idBDTerceiro = 1,
                nomeTerceiro = "Terceiro",
                cpfTerceiro = "123.456.789-00",
                empresaTerceiro = "Empresa Terceiro"
            )
        )
        whenever(
            terceiroRetrofitDatasource.recoverAll(token)
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
        val terceiroList = listOf(
            Terceiro(
                idTerceiro = 1,
                idBDTerceiro = 1,
                nomeTerceiro = "Terceiro",
                cpfTerceiro = "123.456.789-00",
                empresaTerceiro = "Empresa Terceiro"
            )
        )
        val terceiroRoomModelList = listOf(
            TerceiroRoomModel(
                idTerceiro = 1,
                idBDTerceiro = 1,
                nomeTerceiro = "Terceiro",
                cpfTerceiro = "123.456.789-00",
                empresaTerceiro = "Empresa Terceiro"
            )
        )
        whenever(
            terceiroRoomDatasource.addAll(terceiroRoomModelList)
        ).thenReturn(
            Result.success(
                true
            )
        )
        val result = repository.addAll(terceiroList)
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
        val terceiroList = listOf(
            Terceiro(
                idTerceiro = 1,
                idBDTerceiro = 1,
                nomeTerceiro = "Terceiro",
                cpfTerceiro = "123.456.789-00",
                empresaTerceiro = "Empresa Terceiro"
            )
        )
        val terceiroRoomModelList = listOf(
            TerceiroRoomModel(
                idTerceiro = 1,
                idBDTerceiro = 1,
                nomeTerceiro = "Terceiro",
                cpfTerceiro = "123.456.789-00",
                empresaTerceiro = "Empresa Terceiro"
            )
        )
        whenever(
            terceiroRoomDatasource.addAll(terceiroRoomModelList)
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val result = repository.addAll(terceiroList)
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "ITerceiroRepository.addAll -> Unknown Error"
        )
    }

    @Test
    fun `Check return failure if have error in TerceiroRoomDatasource checkCPF`() =
        runTest {
            whenever(
                terceiroRoomDatasource.checkCpf("123.456.789-00")
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
                "ITerceiroRepository.checkCPF -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if CheckCPF execute successfully`() =
        runTest {
            whenever(
                terceiroRoomDatasource.checkCpf("123.456.789-00")
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.checkCPF("123.456.789-00")
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
    fun `Get - Check return failure if have error in VisitanteRoomDatasource get`() =
        runTest {
            whenever(
                terceiroRoomDatasource.get(1)
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
                "ITerceiroRepository.get -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if TerceiroRepositoryImplTest Get execute successfully`() =
        runTest {
            val list = listOf(
                TerceiroRoomModel(
                    idTerceiro = 1,
                    idBDTerceiro = 1,
                    nomeTerceiro = "Terceiro",
                    cpfTerceiro = "123.456.789-00",
                    empresaTerceiro = "Empresa Terceiro"
                )
            )
            whenever(
                terceiroRoomDatasource.get(1)
            ).thenReturn(
                Result.success(list)
            )
            val result = repository.get(1)
            assertEquals(
                result.isSuccess,
                true
            )
            val entity = result.getOrNull()!!
            assertEquals(
                entity.nomeTerceiro,
                "Terceiro"
            )
        }

    @Test
    fun `GetCpf - Check return failure if have error in VisitanteRoomDatasource get`() =
        runTest {
            whenever(
                terceiroRoomDatasource.get(1)
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
                "ITerceiroRepository.get -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if TerceiroRepositoryImplTest GetCpf execute successfully`() =
        runTest {
            val list = listOf(
                TerceiroRoomModel(
                    idTerceiro = 1,
                    idBDTerceiro = 1,
                    nomeTerceiro = "Terceiro",
                    cpfTerceiro = "123.456.789-00",
                    empresaTerceiro = "Empresa Terceiro"
                )
            )
            whenever(
                terceiroRoomDatasource.get(1)
            ).thenReturn(
                Result.success(list)
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
                terceiroRoomDatasource.get("123.456.789-00")
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
                "ITerceiroRepository.getId -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if TerceiroRepositoryImplTest GetId execute successfully`() =
        runTest {
            val list = listOf(
                TerceiroRoomModel(
                    idTerceiro = 1,
                    idBDTerceiro = 1,
                    nomeTerceiro = "Terceiro",
                    cpfTerceiro = "123.456.789-00",
                    empresaTerceiro = "Empresa Terceiro"
                ),
                TerceiroRoomModel(
                    idTerceiro = 1,
                    idBDTerceiro = 1,
                    nomeTerceiro = "Terceiro",
                    cpfTerceiro = "123.456.789-00",
                    empresaTerceiro = "Empresa Terceiro 2"
                )
            )
            whenever(
                terceiroRoomDatasource.get("123.456.789-00")
            ).thenReturn(
                Result.success(list)
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
                terceiroRoomDatasource.get("123.456.789-00")
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
                "ITerceiroRepository.getNome -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if TerceiroRepositoryImplTest GetNome execute successfully`() =
        runTest {
            val list = listOf(
                TerceiroRoomModel(
                    idTerceiro = 1,
                    idBDTerceiro = 1,
                    nomeTerceiro = "Terceiro",
                    cpfTerceiro = "123.456.789-00",
                    empresaTerceiro = "Empresa Terceiro"
                ),
                TerceiroRoomModel(
                    idTerceiro = 1,
                    idBDTerceiro = 1,
                    nomeTerceiro = "Terceiro",
                    cpfTerceiro = "123.456.789-00",
                    empresaTerceiro = "Empresa Terceiro 2"
                )
            )
            whenever(
                terceiroRoomDatasource.get("123.456.789-00")
            ).thenReturn(
                Result.success(list)
            )
            val result = repository.getNome("123.456.789-00")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                "Terceiro"
            )
        }

    @Test
    fun `GetEmpresas - Check return failure if have error in VisitanteRoomDatasource get`() =
        runTest {
            whenever(
                terceiroRoomDatasource.get("123.456.789-00")
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
                "ITerceiroRepository.getEmpresas -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if TerceiroRepositoryImplTest GetEmpresas execute successfully`() =
        runTest {
            val list = listOf(
                TerceiroRoomModel(
                    idTerceiro = 1,
                    idBDTerceiro = 1,
                    nomeTerceiro = "Terceiro",
                    cpfTerceiro = "123.456.789-00",
                    empresaTerceiro = "Empresa Terceiro"
                ),
                TerceiroRoomModel(
                    idTerceiro = 1,
                    idBDTerceiro = 1,
                    nomeTerceiro = "Terceiro",
                    cpfTerceiro = "123.456.789-00",
                    empresaTerceiro = "Empresa Terceiro 2"
                )
            )
            whenever(
                terceiroRoomDatasource.get("123.456.789-00")
            ).thenReturn(
                Result.success(list)
            )
            val result = repository.getEmpresas("123.456.789-00")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                "Empresa Terceiro\nEmpresa Terceiro 2"
            )
        }
}