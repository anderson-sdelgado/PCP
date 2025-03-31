package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioEquipSegRepository
import br.com.usinasantafe.pcp.utils.FlowApp
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IDeleteEquipSegTest {

    private val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
    private val usecase = IDeleteEquipSeg(
        movEquipProprioEquipSegRepository = movEquipProprioEquipSegRepository
    )

    @Test
    fun `Chech return failure Usecase if have error in MovEquipProprioEquipSegRepository`() =
        runTest {
            whenever(
                movEquipProprioEquipSegRepository.delete(
                    idEquip = 10,
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(
                idEquip = 10,
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IDeleteEquipSeg -> Unknown Error"
            )
        }

    @Test
    fun `Chech return true if MovEquipProprioEquipSegRepository delete execute success`() =
        runTest {
            whenever(
                movEquipProprioEquipSegRepository.delete(
                    idEquip = 10,
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(
                idEquip = 10,
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