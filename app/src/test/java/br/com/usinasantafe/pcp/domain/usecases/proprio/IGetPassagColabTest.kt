package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipProprioPassag
import br.com.usinasantafe.pcp.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioPassagRepository
import br.com.usinasantafe.pcp.utils.FlowApp
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class IGetPassagColabTest {

    private val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
    private val colabRepository = mock<ColabRepository>()
    private val usecase = IGetPassagColabList(
        movEquipProprioPassagRepository,
        colabRepository
    )

    @Test
    fun `Check return failure Usecase if have failure in MovEquipProprioPassagRepository`() =
        runTest {
            whenever(
                movEquipProprioPassagRepository.list(
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
                "IGetPassagColabList -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure Usecase if not have data in table colab`() = runTest {
        whenever(
            movEquipProprioPassagRepository.list(
                FlowApp.ADD,
                0
            )
        ).thenReturn(
            Result.success(
                listOf(
                    MovEquipProprioPassag(
                        matricColab = 19759
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
            "IGetPassagColabList"
        )
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            "java.lang.NullPointerException"
        )
    }

    @Test
    fun `Check return failure if colabRepository getNome return failure`() = runTest {
        whenever(
            movEquipProprioPassagRepository.list(
                FlowApp.ADD,
                0
            )
        ).thenReturn(
            Result.success(
                listOf(
                    MovEquipProprioPassag(
                        matricColab = 19759
                    )
                )
            )
        )
        whenever(
            colabRepository.getNome(19759)
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
            "IGetPassagColabList -> Unknown Error"
        )
    }

    @Test
    fun `Check return list Colab if process execute successfully`() = runTest {
        whenever(
            movEquipProprioPassagRepository.list(
                FlowApp.ADD,
                0
            )
        ).thenReturn(
            Result.success(
                listOf(
                    MovEquipProprioPassag(
                        matricColab = 19759
                    )
                )
            )
        )
        whenever(colabRepository.getNome(19759)).thenReturn(
            Result.success(
                "ANDERSON DA SILVA DELGADO"
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
            result.getOrNull()!![0].matricColab,
            19759
        )
    }
}