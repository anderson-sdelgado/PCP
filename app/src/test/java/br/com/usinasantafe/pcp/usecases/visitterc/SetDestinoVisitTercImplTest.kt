package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.errors.RepositoryException
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcp.utils.FlowApp
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class SetDestinoVisitTercImplTest {

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository setDestino`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(
                movEquipVisitTercRepository.setDestino(
                    destino = "Destino",
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipVisitTercRepository.setDestino",
                        cause = Exception()
                    )
                )
            )
            val usecase = ISetDestinoVisitTerc(
                movEquipVisitTercRepository,
                startProcessSendData
            )
            val result = usecase(
                destino = "Destino",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipVisitTercRepository.setDestino"
            )
        }

    @Test
    fun `Check return true if SetDestinoVisitTercImpl execute successfully`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(
                movEquipVisitTercRepository.setDestino(
                    destino = "Destino",
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(true)
            )
            val usecase = ISetDestinoVisitTerc(
                movEquipVisitTercRepository,
                startProcessSendData
            )
            val result = usecase(
                destino = "Destino",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }
}