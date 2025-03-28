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

class GetPassagColabImplTest {

    @Test
    fun `Chech return failure Usecase if have failure in MovEquipProprioPassagRepository`() =
        runTest {
            val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
            val colabRepository = mock<ColabRepository>()
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
            val usecase = IGetPassagColabList(
                movEquipProprioPassagRepository,
                colabRepository
            )
            val result = usecase(
                FlowApp.ADD,
                0
            )
            assertEquals(result.isFailure, true)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipProprioPassagRepository.list"
            )
        }

    @Test
    fun `Chech return failure Usecase if not have data in table colab`() = runTest {
        val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
        val colabRepository = mock<ColabRepository>()
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
        val usecase = IGetPassagColabList(
            movEquipProprioPassagRepository,
            colabRepository
        )
        val result = usecase(
            FlowApp.ADD,
            0
        )
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Usecase -> RecoverPassagColab")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), "java.lang.NullPointerException")
    }

    @Test
    fun `Chech return failure if colabRepository getNome return failure`() = runTest {
        val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
        val colabRepository = mock<ColabRepository>()
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
        val usecase = IGetPassagColabList(
            movEquipProprioPassagRepository,
            colabRepository
        )
        val result = usecase(
            FlowApp.ADD,
            0
        )
        assertEquals(result.isFailure, true)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Repository -> ColabRepository.getNome"
        )
    }

    @Test
    fun `Chech return list Colab if process execute successfully`() = runTest {
        val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
        val colabRepository = mock<ColabRepository>()
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
        val usecase = IGetPassagColabList(
            movEquipProprioPassagRepository,
            colabRepository
        )
        val result = usecase(
            FlowApp.ADD,
            0
        )
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull()!!.size, 1)
        assertEquals(result.getOrNull()!![0].matricColab, 19759)
    }
}