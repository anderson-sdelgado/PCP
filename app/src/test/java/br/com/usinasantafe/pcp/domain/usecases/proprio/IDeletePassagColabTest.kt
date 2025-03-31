package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioPassagRepository
import br.com.usinasantafe.pcp.utils.FlowApp
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IDeletePassagColabTest {

    private val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
    private val usecase = IDeletePassagColab(
        movEquipProprioPassagRepository = movEquipProprioPassagRepository
    )

    @Test
    fun `Chech return failure Usecase if have error in MovEquipProprioPassagRepository`() =
        runTest {
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
            val result = usecase(
                matricColab = 19759,
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IDeletePassagColab -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if MovEquipProprioPassagRepository delete execute success`() =
        runTest {
            whenever(
                movEquipProprioPassagRepository.delete(
                    matricColab = 19759,
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(
                matricColab = 19759,
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