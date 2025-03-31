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

class CheckCpfVisitTercImplTest {

    private val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
    private val terceiroRepository = mock<TerceiroRepository>()
    private val visitanteRepository = mock<VisitanteRepository>()
    private val usecase = ICheckCpfVisitTerc(
        movEquipVisitTercRepository,
        terceiroRepository,
        visitanteRepository
    )

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository GetTypeVisitTerc`() = runTest {
        whenever(
            movEquipVisitTercRepository.getTypeVisitTerc(
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val result = usecase(
            cpf = "326.949.728-88",
            flowApp = FlowApp.ADD,
            0
        )
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "ICheckCpfVisitTerc -> Unknown Error"
        )
    }

    @Test
    fun `Check return failure if have error in VisitanteRepository CheckCPF`() = runTest {
        whenever(
            movEquipVisitTercRepository.getTypeVisitTerc(
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.success(TypeVisitTerc.VISITANTE)
        )
        whenever(
            visitanteRepository.checkCPF(
                cpf = "326.949.728-88"
            )
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val result = usecase(
            cpf = "326.949.728-88",
            flowApp = FlowApp.ADD,
            0
        )
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "ICheckCpfVisitTerc -> Unknown Error"
        )
    }

    @Test
    fun `Check return failure if have error in TerceiroRepository CheckCPF`() = runTest {
        whenever(
            movEquipVisitTercRepository.getTypeVisitTerc(
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.success(TypeVisitTerc.TERCEIRO)
        )
        whenever(
            terceiroRepository.checkCPF(
                cpf = "326.949.728-88"
            )
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val result = usecase(
            cpf = "326.949.728-88",
            flowApp = FlowApp.ADD,
            0
        )
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message
            , "ICheckCpfVisitTerc -> Unknown Error"
        )
    }

    @Test
    fun `Check return false if CheckCPF is invalid`() = runTest {
        whenever(
            movEquipVisitTercRepository.getTypeVisitTerc(
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.success(TypeVisitTerc.TERCEIRO)
        )
        whenever(
            terceiroRepository.checkCPF(
                cpf = "326.949.728-88"
            )
        ).thenReturn(
            Result.success(false)
        )
        val result = usecase(
            cpf = "326.949.728-88",
            flowApp = FlowApp.ADD,
            0
        )
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull(),
            false
        )
    }

    @Test
    fun `Check return false if CheckCPF is valid`() = runTest {
        whenever(
            movEquipVisitTercRepository.getTypeVisitTerc(
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.success(TypeVisitTerc.TERCEIRO)
        )
        whenever(
            terceiroRepository.checkCPF(
                cpf = "326.949.728-88"
            )
        ).thenReturn(
            Result.success(true)
        )
        val result = usecase(
            cpf = "326.949.728-88",
            flowApp = FlowApp.ADD,
            0
        )
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull(),
            true
        )
    }
}