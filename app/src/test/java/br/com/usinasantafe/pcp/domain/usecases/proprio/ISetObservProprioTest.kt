package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcp.utils.FlowApp
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ISetObservProprioTest {

    private val movEquipProprioRepository = mock<MovEquipProprioRepository>()
    private val startProcessSendData = mock<StartProcessSendData>()
    private val usecase = ISetObservProprio(
        movEquipProprioRepository,
        startProcessSendData
    )

    @Test
    fun `Chech return failure if have error in MovEquipProprioRepository setObserv`() =
        runTest {
            whenever(
                movEquipProprioRepository.setObserv(
                    observ = "Teste",
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(
                observ = "Teste",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetObservProprio -> Unknown Error"
            )
        }

    @Test
    fun `Chech return true if MovEquipProprioRepository setObserv execute success`() =
        runTest {
            whenever(
                movEquipProprioRepository.setObserv(
                    observ = "Teste",
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(
                observ = "Teste",
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


    @Test
    fun `Chech return true if MovEquipProprioRepository setObserv execute success and value is null`() =
        runTest {
            whenever(
                movEquipProprioRepository.setObserv(
                    observ = null,
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(
                observ = null,
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