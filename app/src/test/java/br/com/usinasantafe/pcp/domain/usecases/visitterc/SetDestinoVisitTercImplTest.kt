package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcp.utils.FlowApp
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class SetDestinoVisitTercImplTest {

    private val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
    private val startProcessSendData = mock<StartProcessSendData>()
    private val usecase = ISetDestinoVisitTerc(
        movEquipVisitTercRepository,
        startProcessSendData
    )

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository setDestino`() =
        runTest {
            whenever(
                movEquipVisitTercRepository.setDestino(
                    destino = "Destino",
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(
                destino = "Destino",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetDestinoVisitTerc -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if SetDestinoVisitTercImpl execute successfully`() =
        runTest {
            whenever(
                movEquipVisitTercRepository.setDestino(
                    destino = "Destino",
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(
                destino = "Destino",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }
}