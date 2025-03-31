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

    private val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
    private val terceiroRepository = mock<TerceiroRepository>()
    private val visitanteRepository = mock<VisitanteRepository>()
    private val usecase = IGetCpfVisitTerc(
        movEquipVisitTercRepository = movEquipVisitTercRepository,
        terceiroRepository = terceiroRepository,
        visitanteRepository = visitanteRepository
    )

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository GetType`() = runTest {
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
        val result = usecase(id = 1)
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "IGetCpfVisitTerc -> Unknown Error"
        )
    }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository GetIdVisitTerc`() = runTest {
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
        val result = usecase(id = 1)
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "IGetCpfVisitTerc -> Unknown Error"
        )
    }

    @Test
    fun `Check return failure if have error in TerceiroRepository GetCpf`() = runTest {
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
        val result = usecase(id = 1)
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "IGetCpfVisitTerc -> Unknown Error"
        )
    }

    @Test
    fun `Check return failure if have error in VisitanteRepository GetCpf`() = runTest {
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
        val result = usecase(id = 1)
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "IGetCpfVisitTerc -> Unknown Error"
        )
    }

    @Test
    fun `Check return cpf if GetCpf execute successfully of Terceiro`() = runTest {
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
        val result = usecase(id = 1)
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
    fun `Check return cpf if GetCpf execute successfully of Visitante`() = runTest {
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
        val result = usecase(id = 1)
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!,
            "123.456.789-01"
        )
    }

}