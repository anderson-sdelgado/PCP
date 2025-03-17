package br.com.usinasantafe.pcp.domain.usecases.updatetable.savetable

import br.com.usinasantafe.pcp.domain.entities.stable.Terceiro
import br.com.usinasantafe.pcp.domain.errors.DatasourceException
import br.com.usinasantafe.pcp.domain.repositories.stable.TerceiroRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.whenever

class ISaveTerceiroTest {

    @Test
    fun `Check execution correct`() = runTest {
        val terceiroList = listOf(
            Terceiro(
                idTerceiro = 1,
                idBDTerceiro = 1,
                cpfTerceiro = "123.456.789-00",
                nomeTerceiro = "Terceiro",
                empresaTerceiro = "Empresa Terceiro",
            )
        )
        val terceiroRepository = Mockito.mock<TerceiroRepository>()
        whenever(terceiroRepository.addAll(terceiroList)).thenReturn(Result.success(true))
        val usecase = ISaveTerceiro(terceiroRepository)
        val result = usecase(terceiroList)
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull(), true)
    }

    @Test
    fun `Check execution incorrect`() = runTest {
        val terceiroList = listOf(
            Terceiro(
                idTerceiro = 1,
                idBDTerceiro = 1,
                cpfTerceiro = "123.456.789-00",
                nomeTerceiro = "Terceiro",
                empresaTerceiro = "Empresa Terceiro",
            )
        )
        val terceiroRepository = Mockito.mock<TerceiroRepository>()
        whenever(terceiroRepository.addAll(terceiroList)).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "TerceiroRepository.addAll",
                    cause = Exception()
                )
            )
        )
        val usecase = ISaveTerceiro(terceiroRepository)
        val result = usecase(terceiroList)
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> TerceiroRepository.addAll")
    }
}