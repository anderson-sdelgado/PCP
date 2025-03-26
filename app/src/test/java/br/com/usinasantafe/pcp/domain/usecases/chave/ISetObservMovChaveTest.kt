package br.com.usinasantafe.pcp.domain.usecases.chave

import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcp.utils.FlowApp
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class ISetObservMovChaveTest {

    private val movChaveRepository = mock<MovChaveRepository>()
    private val startProcessSendData = mock<StartProcessSendData>()
    private val usecase = ISetObservMovChave(
        movChaveRepository = movChaveRepository,
        startProcessSendData = startProcessSendData
    )

    @Test
    fun `Check return failure if have error in MovChaveRepository SetObserv`() =
        runTest {
            whenever(
                movChaveRepository.setObserv(
                    observ = "teste",
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(
                observ = "teste",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovChaveRepository.setObserv"
            )
        }

    @Test
    fun `Check return correct if function execute successfully`() =
        runTest {
            whenever(
                movChaveRepository.setObserv(
                    observ = "teste",
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(
                observ = "teste",
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