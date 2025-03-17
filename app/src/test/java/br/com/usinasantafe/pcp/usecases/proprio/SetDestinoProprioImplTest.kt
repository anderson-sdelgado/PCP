package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.errors.RepositoryException
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcp.utils.FlowApp
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class SetDestinoProprioImplTest {

    @Test
    fun `Chech return failure Datasource if have error in MovEquipProprioRepository setDestino`() =
        runTest {
            val movEquipProprioRepository = mock<MovEquipProprioRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(
                movEquipProprioRepository.setDestino(
                    destino = "Teste",
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipProprioRepository.setDestino",
                        cause = Exception()
                    )
                )
            )
            val usecase = ISetDestinoProprio(
                movEquipProprioRepository,
                startProcessSendData
            )
            val result = usecase(
                destino = "Teste",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(result.isFailure, true)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipProprioRepository.setDestino"
            )
        }

    @Test
    fun `Chech return true if MovEquipProprioRepository setDestino execute success`() =
        runTest {
            val movEquipProprioRepository = mock<MovEquipProprioRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(
                movEquipProprioRepository.setDestino(
                    destino = "Teste",
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(true)
            )
            val usecase = ISetDestinoProprio(
                movEquipProprioRepository,
                startProcessSendData
            )
            val result = usecase(
                destino = "Teste",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isSuccess)
            assertTrue(
                result.getOrNull()!!
            )
        }
}