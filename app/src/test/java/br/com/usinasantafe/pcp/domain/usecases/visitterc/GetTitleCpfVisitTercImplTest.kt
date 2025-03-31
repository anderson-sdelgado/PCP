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

    private val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
    private val usecase = IGetTitleCpfVisitTerc(
        movEquipVisitTercRepository
    )

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository getTypeVisitTerc`() =
        runTest {
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
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetTitleCpfVisitTerc -> Unknown Error"
            )
        }

    @Test
    fun `Check return title if GetTitleCpfVisitTercImpl execute success`() = runTest {
        whenever(
            movEquipVisitTercRepository.getTypeVisitTerc(
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.success(TypeVisitTerc.VISITANTE)
        )
        val result = usecase(
            flowApp = FlowApp.ADD,
            id = 0
        )
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!,
            "VISITANTE"
        )
    }
}