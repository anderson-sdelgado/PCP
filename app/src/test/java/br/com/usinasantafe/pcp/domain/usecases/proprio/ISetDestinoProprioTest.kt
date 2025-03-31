package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcp.utils.FlowApp
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ISetDestinoProprioTest {

    private val movEquipProprioRepository = mock<MovEquipProprioRepository>()
    private val startProcessSendData = mock<StartProcessSendData>()
    private val usecase = ISetDestinoProprio(
        movEquipProprioRepository,
        startProcessSendData
    )

    @Test
    fun `Check return failure Datasource if have error in MovEquipProprioRepository setDestino`() =
        runTest {
            whenever(
                movEquipProprioRepository.setDestino(
                    destino = "Teste",
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(
                destino = "Teste",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetDestinoProprio -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if MovEquipProprioRepository setDestino execute success`() =
        runTest {
            whenever(
                movEquipProprioRepository.setDestino(
                    destino = "Teste",
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(
                destino = "Teste",
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