package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcp.utils.FlowApp
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ISetNotaFiscalProprioTest {

    private val movEquipProprioRepository = mock<MovEquipProprioRepository>()
    private val startProcessSendData = mock<StartProcessSendData>()
    private val usecase = ISetNotaFiscalProprio(
        movEquipProprioRepository,
        startProcessSendData
    )

    @Test
    fun `Check return failure if have error in MovEquipProprioRepository setNotaFiscal`() =
        runTest {
            whenever(
                movEquipProprioRepository.setNotaFiscal(
                    notaFiscal = 123456,
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(
                notaFiscal = "123456",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNotaFiscalProprio -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if MovEquipProprioRepository setNotaFiscal execute success`() =
        runTest {
            whenever(
                movEquipProprioRepository.setNotaFiscal(
                    notaFiscal = 123456,
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(
                notaFiscal = "123456",
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
    fun `Check return true if MovEquipProprioRepository setNotaFiscal execute success and value is null`() =
        runTest {
            whenever(
                movEquipProprioRepository.setNotaFiscal(
                    notaFiscal = null,
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(
                notaFiscal = null,
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