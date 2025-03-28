package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioPassagRepository
import br.com.usinasantafe.pcp.utils.FlowApp
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class DeletePassagColabImplTest {

    @Test
    fun `Chech return failure Usecase if have error in MovEquipProprioPassagRepository`() =
        runTest {
            val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
            whenever(
                movEquipProprioPassagRepository.delete(
                    matricColab = 19759,
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val usecase = IDeletePassagColab(
                movEquipProprioPassagRepository
            )
            val result = usecase(
                matricColab = 19759,
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipProprioPassagRepository.delete"
            )
        }

    @Test
    fun `Check return true if MovEquipProprioPassagRepository delete execute success`() =
        runTest {
            val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
            whenever(
                movEquipProprioPassagRepository.delete(
                    matricColab = 19759,
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(true)
            )
            val usecase = IDeletePassagColab(
                movEquipProprioPassagRepository
            )
            val result = usecase(
                matricColab = 19759,
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

}