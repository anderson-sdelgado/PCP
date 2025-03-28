package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.repositories.stable.TerceiroRepository
import br.com.usinasantafe.pcp.domain.repositories.stable.VisitanteRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeVisitTerc
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class GetCpfVisitTercImplTest {

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository GetType`() = runTest {
        val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
        val terceiroRepository = mock<TerceiroRepository>()
        val visitanteRepository = mock<VisitanteRepository>()
        whenever(
            movEquipVisitTercRepository.getTypeVisitTerc(
                flowApp = FlowApp.CHANGE,
                id = 1
            )
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val usecase = IGetCpfVisitTerc(
            movEquipVisitTercRepository = movEquipVisitTercRepository,
            terceiroRepository = terceiroRepository,
            visitanteRepository = visitanteRepository
        )
        val result = usecase(id = 1)
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Repository -> MovEquipVisitTercRepositoryImpl.getTypeVisitTerc"
        )
    }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository GetIdVisitTerc`() = runTest {
        val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
        val terceiroRepository = mock<TerceiroRepository>()
        val visitanteRepository = mock<VisitanteRepository>()
        whenever(
            movEquipVisitTercRepository.getTypeVisitTerc(
                flowApp = FlowApp.CHANGE,
                id = 1
            )
        ).thenReturn(
            Result.success(
                TypeVisitTerc.TERCEIRO
            )
        )
        whenever(
            movEquipVisitTercRepository.getIdVisitTerc(
                id = 1
            )
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val usecase = IGetCpfVisitTerc(
            movEquipVisitTercRepository = movEquipVisitTercRepository,
            terceiroRepository = terceiroRepository,
            visitanteRepository = visitanteRepository
        )
        val result = usecase(id = 1)
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Repository -> MovEquipVisitTercRepositoryImpl.getIdVisitTerc"
        )
    }

    @Test
    fun `Check return failure if have error in TerceiroRepository GetCpf`() = runTest {
        val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
        val terceiroRepository = mock<TerceiroRepository>()
        val visitanteRepository = mock<VisitanteRepository>()
        whenever(
            movEquipVisitTercRepository.getTypeVisitTerc(
                flowApp = FlowApp.CHANGE,
                id = 1
            )
        ).thenReturn(
            Result.success(
                TypeVisitTerc.TERCEIRO
            )
        )
        whenever(
            movEquipVisitTercRepository.getIdVisitTerc(
                id = 1
            )
        ).thenReturn(
            Result.success(10)
        )
        whenever(
            terceiroRepository.getCpf(
                id = 10
            )
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val usecase = IGetCpfVisitTerc(
            movEquipVisitTercRepository = movEquipVisitTercRepository,
            terceiroRepository = terceiroRepository,
            visitanteRepository = visitanteRepository
        )
        val result = usecase(id = 1)
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Repository -> TerceiroRepository.getCpf"
        )
    }

    @Test
    fun `Check return failure if have error in VisitanteRepository GetCpf`() = runTest {
        val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
        val terceiroRepository = mock<TerceiroRepository>()
        val visitanteRepository = mock<VisitanteRepository>()
        whenever(
            movEquipVisitTercRepository.getTypeVisitTerc(
                flowApp = FlowApp.CHANGE,
                id = 1
            )
        ).thenReturn(
            Result.success(
                TypeVisitTerc.VISITANTE
            )
        )
        whenever(
            movEquipVisitTercRepository.getIdVisitTerc(
                id = 1
            )
        ).thenReturn(
            Result.success(10)
        )
        whenever(
            visitanteRepository.getCpf(
                id = 10
            )
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val usecase = IGetCpfVisitTerc(
            movEquipVisitTercRepository = movEquipVisitTercRepository,
            terceiroRepository = terceiroRepository,
            visitanteRepository = visitanteRepository
        )
        val result = usecase(id = 1)
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Repository -> VisitanteRepository.getCpf"
        )
    }

    @Test
    fun `Check return cpf if GetCpf execute successfully of Terceiro`() = runTest {
        val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
        val terceiroRepository = mock<TerceiroRepository>()
        val visitanteRepository = mock<VisitanteRepository>()
        whenever(
            movEquipVisitTercRepository.getTypeVisitTerc(
                flowApp = FlowApp.CHANGE,
                id = 1
            )
        ).thenReturn(
            Result.success(
                TypeVisitTerc.TERCEIRO
            )
        )
        whenever(
            movEquipVisitTercRepository.getIdVisitTerc(
                id = 1
            )
        ).thenReturn(
            Result.success(10)
        )
        whenever(
            terceiroRepository.getCpf(
                id = 10
            )
        ).thenReturn(
            Result.success("123.456.789-00")
        )
        val usecase = IGetCpfVisitTerc(
            movEquipVisitTercRepository = movEquipVisitTercRepository,
            terceiroRepository = terceiroRepository,
            visitanteRepository = visitanteRepository
        )
        val result = usecase(id = 1)
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!, "123.456.789-00")
    }

    @Test
    fun `Check return cpf if GetCpf execute successfully of Visitante`() = runTest {
        val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
        val terceiroRepository = mock<TerceiroRepository>()
        val visitanteRepository = mock<VisitanteRepository>()
        whenever(
            movEquipVisitTercRepository.getTypeVisitTerc(
                flowApp = FlowApp.CHANGE,
                id = 1
            )
        ).thenReturn(
            Result.success(
                TypeVisitTerc.VISITANTE
            )
        )
        whenever(
            movEquipVisitTercRepository.getIdVisitTerc(
                id = 1
            )
        ).thenReturn(
            Result.success(10)
        )
        whenever(
            visitanteRepository.getCpf(
                id = 10
            )
        ).thenReturn(
            Result.success("123.456.789-01")
        )
        val usecase = IGetCpfVisitTerc(
            movEquipVisitTercRepository = movEquipVisitTercRepository,
            terceiroRepository = terceiroRepository,
            visitanteRepository = visitanteRepository
        )
        val result = usecase(id = 1)
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!, "123.456.789-01")
    }

}