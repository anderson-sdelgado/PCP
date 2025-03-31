package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.entities.stable.Equip
import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipProprioEquipSeg
import br.com.usinasantafe.pcp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioEquipSegRepository
import br.com.usinasantafe.pcp.utils.FlowApp
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class IGetEquipSegListTest {

    private val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
    private val equipRepository = mock<EquipRepository>()
    private val usecase = IGetEquipSegList(
        movEquipProprioEquipSegRepository,
        equipRepository
    )

    @Test
    fun `Chech return failure Usecase if have failure in MovEquipProprioEquipSegRepository`() =
        runTest {

            whenever(
                movEquipProprioEquipSegRepository.list(
                    FlowApp.ADD,
                    0
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(
                FlowApp.ADD,
                0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetEquipSegList -> Unknown Error"
            )
        }

    @Test
    fun `Chech return failure Usecase if not have data in table equip`() = runTest {
        whenever(
            movEquipProprioEquipSegRepository.list(
                FlowApp.ADD,
                0
            )
        ).thenReturn(
            Result.success(
                listOf(
                    MovEquipProprioEquipSeg(
                        idEquip = 10
                    )
                )
            )
        )
        val result = usecase(
            FlowApp.ADD,
            0
        )
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "IGetEquipSegList"
        )
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            "java.lang.NullPointerException"
        )
    }

    @Test
    fun `Chech return failure if colabRepository getNome return failure`() = runTest {
        whenever(
            movEquipProprioEquipSegRepository.list(
                FlowApp.ADD,
                0
            )
        ).thenReturn(
            Result.success(
                listOf(
                    MovEquipProprioEquipSeg(
                        idEquip = 10
                    )
                )
            )
        )
        whenever(
            equipRepository.get(10)
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val result = usecase(
            FlowApp.ADD,
            0
        )
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "IGetEquipSegList -> Unknown Error"
        )
    }

    @Test
    fun `Chech return list Equip if process execute successfully`() = runTest {
        whenever(
            movEquipProprioEquipSegRepository.list(
                FlowApp.ADD,
                0
            )
        ).thenReturn(
            Result.success(
                listOf(
                    MovEquipProprioEquipSeg(
                        idEquip = 10
                    )
                )
            )
        )
        whenever(
            equipRepository.get(10)
        ).thenReturn(
            Result.success(
                Equip(
                    idEquip = 10,
                    nroEquip = 100,
                    descrEquip = "Teste"
                )
            )
        )
        val result = usecase(
            FlowApp.ADD,
            0
        )
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!.size,
            1
        )
        assertEquals(
            result.getOrNull()!![0].nroEquip,
            100
        )
        assertEquals(
            result.getOrNull()!![0].idEquip,
            10
        )
    }
}