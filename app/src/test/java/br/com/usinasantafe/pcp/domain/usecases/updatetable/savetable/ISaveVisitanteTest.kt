package br.com.usinasantafe.pcp.domain.usecases.updatetable.savetable

import br.com.usinasantafe.pcp.domain.entities.stable.Visitante
import br.com.usinasantafe.pcp.domain.repositories.stable.VisitanteRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.whenever

class ISaveVisitanteTest {

    @Test
    fun `Check execution correct`() = runTest {
        val visitanteList = listOf(
            Visitante(
                idVisitante = 1,
                cpfVisitante = "123.456.789-00",
                nomeVisitante = "Visitante",
                empresaVisitante = "Empresa Visitante",
            )
        )
        val visitanteRepository = Mockito.mock<VisitanteRepository>()
        whenever(visitanteRepository.addAll(visitanteList)).thenReturn(Result.success(true))
        val usecase = ISaveVisitante(visitanteRepository)
        val result = usecase(visitanteList)
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull(), true)
    }

    @Test
    fun `Check execution incorrect`() = runTest {
        val visitanteList = listOf(
            Visitante(
                idVisitante = 1,
                cpfVisitante = "123.456.789-00",
                nomeVisitante = "Visitante",
                empresaVisitante = "Empresa Visitante",
            )
        )
        val visitanteRepository = Mockito.mock<VisitanteRepository>()
        whenever(
            visitanteRepository.addAll(visitanteList)
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val usecase = ISaveVisitante(visitanteRepository)
        val result = usecase(visitanteList)
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> VisitanteRepository.addAll")
    }
}