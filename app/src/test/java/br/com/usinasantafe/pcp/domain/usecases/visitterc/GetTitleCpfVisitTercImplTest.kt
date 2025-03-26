package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeVisitTerc
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class GetTitleCpfVisitTercImplTest {

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository getTypeVisitTerc`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
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
            val usecase = IGetTitleCpfVisitTerc(
                movEquipVisitTercRepository
            )
            val result = usecase(
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipVisitTercRepository.getTypeVisitTerc"
            )
        }

    @Test
    fun `Check return title if GetTitleCpfVisitTercImpl execute success`() = runTest {
        val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
        whenever(
            movEquipVisitTercRepository.getTypeVisitTerc(
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.success(TypeVisitTerc.VISITANTE)
        )
        val usecase = IGetTitleCpfVisitTerc(
            movEquipVisitTercRepository
        )
        val result = usecase(
            flowApp = FlowApp.ADD,
            id = 0
        )
        assertTrue(result.isSuccess)
        assertEquals(
            result.getOrNull()!!,
            "VISITANTE"
        )
    }
}