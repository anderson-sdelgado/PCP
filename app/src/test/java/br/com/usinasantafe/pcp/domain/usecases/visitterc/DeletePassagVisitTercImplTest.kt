package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import br.com.usinasantafe.pcp.utils.FlowApp
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class DeletePassagVisitTercImplTest {

    @Test
    fun `Chech return failure Usecase if have error in MovEquipVisitTercPassagRepository delete`() =
        runTest {
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
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
            val usecase = IDeletePassagVisitTerc(
                movEquipVisitTercPassagRepository
            )
            val result = usecase(
                idVisitTerc = 1,
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipVisitTercPassagRepository.delete"
            )
        }

    @Test
    fun `Check return true if MovEquipVisitTercPassagRepository delete execute success`() =
        runTest {
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            whenever(
                movEquipVisitTercPassagRepository.delete(
                    idVisitTerc = 1,
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(true)
            )
            val usecase = IDeletePassagVisitTerc(
                movEquipVisitTercPassagRepository
            )
            val result = usecase(
                idVisitTerc = 1,
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }
}