package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcp.utils.FlowApp
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class SetPlacaVisitTercImplTest {

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository setPlaca`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(
                movEquipVisitTercRepository.setPlaca(
                    placa = "Placa",
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val usecase = ISetPlacaVisitTerc(
                movEquipVisitTercRepository,
                startProcessSendData
            )
            val result = usecase(
                placa = "Placa",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipVisitTercRepository.setPlaca"
            )
        }

    @Test
    fun `Check return true if SetPlacaVisitTercImpl execute successfully`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(
                movEquipVisitTercRepository.setPlaca(
                    placa = "Placa",
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(true)
            )
            val usecase = ISetPlacaVisitTerc(
                movEquipVisitTercRepository,
                startProcessSendData
            )
            val result = usecase(
                placa = "Placa",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }
}