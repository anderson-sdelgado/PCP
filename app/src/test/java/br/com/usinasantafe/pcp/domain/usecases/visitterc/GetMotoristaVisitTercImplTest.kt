package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.entities.stable.Terceiro
import br.com.usinasantafe.pcp.domain.entities.stable.Visitante
import br.com.usinasantafe.pcp.domain.repositories.stable.TerceiroRepository
import br.com.usinasantafe.pcp.domain.repositories.stable.VisitanteRepository
import br.com.usinasantafe.pcp.utils.TypeVisitTerc
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class GetMotoristaVisitTercImplTest {

    private val terceiroRepository = mock<TerceiroRepository>()
    private val visitanteRepository = mock<VisitanteRepository>()
    private val usecase = IGetMotoristaVisitTerc(
        terceiroRepository = terceiroRepository,
        visitanteRepository = visitanteRepository
    )

    @Test
    fun `Check return failure if have error in VisitanteRepository `() = runTest{
        whenever(
            visitanteRepository.get(1)
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val result = usecase(
            typeVisitTerc = TypeVisitTerc.VISITANTE,
            idVisitTerc = 1
        )
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "IGetMotoristaVisitTerc -> Unknown Error"
        )
    }

    @Test
    fun `Check return failure if have error in TerceiroRepository `() = runTest{
        whenever(
            terceiroRepository.get(1)
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val result = usecase(
            typeVisitTerc = TypeVisitTerc.TERCEIRO,
            idVisitTerc = 1
        )
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "IGetMotoristaVisitTerc -> Unknown Error"
        )
    }

    @Test
    fun `Check return motorista if VisitanteRepository execute successfully`() = runTest{
        whenever(
            visitanteRepository.get(1)
        ).thenReturn(
            Result.success(
                Visitante(
                    idVisitante = 1,
                    nomeVisitante = "Anderson",
                    cpfVisitante = "123.456.789-00",
                    empresaVisitante = "Empresa Teste"
                )
            )
        )
        val result = usecase(
            typeVisitTerc = TypeVisitTerc.VISITANTE,
            idVisitTerc = 1
        )
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!,
            "123.456.789-00 - Anderson"
        )
    }

    @Test
    fun `Check return motorista if TerceiroRepository execute successfully`() = runTest{
        val terceiroRepository = mock<TerceiroRepository>()
        val visitanteRepository = mock<VisitanteRepository>()
        whenever(
            terceiroRepository.get(1)
        ).thenReturn(
            Result.success(
                Terceiro(
                    idTerceiro = 1,
                    idBDTerceiro = 1,
                    nomeTerceiro = "Anderson",
                    cpfTerceiro = "123.456.789-00",
                    empresaTerceiro = "Empresa Teste"
                )
            )
        )
        val usecase = IGetMotoristaVisitTerc(
            terceiroRepository = terceiroRepository,
            visitanteRepository = visitanteRepository
        )
        val result = usecase(
            typeVisitTerc = TypeVisitTerc.TERCEIRO,
            idVisitTerc = 1
        )
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!,
            "123.456.789-00 - Anderson"
        )
    }
}