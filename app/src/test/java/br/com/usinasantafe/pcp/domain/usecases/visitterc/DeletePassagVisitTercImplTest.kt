package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import br.com.usinasantafe.pcp.utils.FlowApp
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class DeletePassagVisitTercImplTest {

    private val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
    private val usecase = IDeletePassagVisitTerc(movEquipVisitTercPassagRepository)

    @Test
    fun `Chech return failure Usecase if have error in MovEquipVisitTercPassagRepository delete`() =
        runTest {
            whenever(
                movEquipVisitTercPassagRepository.delete(
                    idVisitTerc = 1,
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(
                idVisitTerc = 1,
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IDeletePassagVisitTerc -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if MovEquipVisitTercPassagRepository delete execute success`() =
        runTest {
            whenever(
                movEquipVisitTercPassagRepository.delete(
                    idVisitTerc = 1,
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(
                idVisitTerc = 1,
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