package br.com.usinasantafe.pcp.domain.usecases.chave

import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcp.utils.FlowApp
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class ISetMatricColabMovChaveTest {

    private val movChaveRepository = mock<MovChaveRepository>()
    private val startProcessSendData = mock<StartProcessSendData>()
    private val usecase = ISetMatricColabMovChave(
        movChaveRepository = movChaveRepository,
        startProcessSendData = startProcessSendData
    )

    @Test
    fun `Check return failure if have error in MovChaveRepository setMatricColab - ADD`() =
        runTest {
            whenever(
                movChaveRepository.setMatricColab(
                    matricColab = 19759,
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(
                matricColab = "19759",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetMatricColabMovChave -> Unknown Error"
            )
        }

    @Test
    fun `Check return correct if function execute successfully - ADD`() =
        runTest {
            whenever(
                movChaveRepository.setMatricColab(
                    matricColab = 19759,
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(
                matricColab = "19759",
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