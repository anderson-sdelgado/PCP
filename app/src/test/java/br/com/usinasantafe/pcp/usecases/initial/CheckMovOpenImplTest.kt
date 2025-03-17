package br.com.usinasantafe.pcp.domain.usecases.initial

import br.com.usinasantafe.pcp.domain.errors.RepositoryException
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveEquipRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class CheckMovOpenImplTest {

    private val movEquipProprioRepository = mock<MovEquipProprioRepository>()
    private val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
    private val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
    private val movChaveRepository = mock<MovChaveRepository>()
    private val movChaveEquipRepository = mock<MovChaveEquipRepository>()

    private val usecase = ICheckMovOpen(
        movEquipProprioRepository,
        movEquipVisitTercRepository,
        movEquipResidenciaRepository,
        movChaveRepository,
        movChaveEquipRepository
    )

    @Test
    fun `Check return failure if not have data`() =
        runTest {
            val result = usecase()
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Usecase -> CheckMovOpenImpl"
            )
        }

    @Test
    fun `Check return failure if have error in movEquipProprioRepository checkOpen`() =
        runTest {
            whenever(
                movEquipProprioRepository.checkOpen()
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipProprioRepository.checkOpen",
                    )
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipProprioRepository.checkOpen"
            )
        }

    @Test
    fun `Check return true if movEquipProprioRepository checkOpen execute successfully and returned true`() =
        runTest {
            whenever(
                movEquipProprioRepository.checkOpen()
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase()
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
    fun `Check return failure if have error in movEquipVisitTercRepository checkOpen`() =
        runTest {
            whenever(
                movEquipProprioRepository.checkOpen()
            ).thenReturn(
                Result.success(false)
            )
            whenever(
                movEquipVisitTercRepository.checkOpen()
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipVisitTercRepository.checkOpen",
                    )
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipVisitTercRepository.checkOpen"
            )
        }

    @Test
    fun `Check return true if movEquipVisitTercRepository checkOpen execute successfully and returned true`() =
        runTest {
            whenever(
                movEquipProprioRepository.checkOpen()
            ).thenReturn(
                Result.success(false)
            )
            whenever(
                movEquipVisitTercRepository.checkOpen()
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase()
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
    fun `Check return failure if have error in movEquipResidenciaRepository checkOpen`() =
        runTest {
            whenever(
                movEquipProprioRepository.checkOpen()
            ).thenReturn(
                Result.success(false)
            )
            whenever(
                movEquipVisitTercRepository.checkOpen()
            ).thenReturn(
                Result.success(false)
            )
            whenever(
                movEquipResidenciaRepository.checkOpen()
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipResidenciaRepository.checkOpen",
                    )
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipResidenciaRepository.checkOpen"
            )
        }

    @Test
    fun `Check return true if movEquipResidenciaRepository checkOpen execute successfully and returned true`() =
        runTest {
            whenever(
                movEquipProprioRepository.checkOpen()
            ).thenReturn(
                Result.success(false)
            )
            whenever(
                movEquipVisitTercRepository.checkOpen()
            ).thenReturn(
                Result.success(false)
            )
            whenever(
                movEquipResidenciaRepository.checkOpen()
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase()
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
    fun `Check return failure if have error in movChaveRepository checkOpen`() =
        runTest {
            whenever(
                movEquipProprioRepository.checkOpen()
            ).thenReturn(
                Result.success(false)
            )
            whenever(
                movEquipVisitTercRepository.checkOpen()
            ).thenReturn(
                Result.success(false)
            )
            whenever(
                movEquipResidenciaRepository.checkOpen()
            ).thenReturn(
                Result.success(false)
            )
            whenever(
                movChaveRepository.checkOpen()
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovChaveRepository.checkOpen",
                        cause = Exception()
                    )
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovChaveRepository.checkOpen"
            )
        }

    @Test
    fun `Check return true if movChaveRepository checkOpen execute successfully and returned true`() =
        runTest {
            whenever(
                movEquipProprioRepository.checkOpen()
            ).thenReturn(
                Result.success(false)
            )
            whenever(
                movEquipVisitTercRepository.checkOpen()
            ).thenReturn(
                Result.success(false)
            )
            whenever(
                movEquipResidenciaRepository.checkOpen()
            ).thenReturn(
                Result.success(false)
            )
            whenever(
                movChaveRepository.checkOpen()
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase()
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
    fun `Check return failure if have error in MovChaveEquipRepository checkOpen`() =
        runTest {
            whenever(
                movEquipProprioRepository.checkOpen()
            ).thenReturn(
                Result.success(false)
            )
            whenever(
                movEquipVisitTercRepository.checkOpen()
            ).thenReturn(
                Result.success(false)
            )
            whenever(
                movEquipResidenciaRepository.checkOpen()
            ).thenReturn(
                Result.success(false)
            )
            whenever(
                movChaveRepository.checkOpen()
            ).thenReturn(
                Result.success(false)
            )
            whenever(
                movChaveEquipRepository.checkOpen()
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovChaveEquipRepository.checkOpen",
                        cause = Exception()
                    )
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovChaveEquipRepository.checkOpen"
            )
        }

    @Test
    fun `Check return true if MovChaveEquipRepository checkOpen execute successfully and returned true`() =
        runTest {
            whenever(
                movEquipProprioRepository.checkOpen()
            ).thenReturn(
                Result.success(false)
            )
            whenever(
                movEquipVisitTercRepository.checkOpen()
            ).thenReturn(
                Result.success(false)
            )
            whenever(
                movEquipResidenciaRepository.checkOpen()
            ).thenReturn(
                Result.success(false)
            )
            whenever(
                movChaveRepository.checkOpen()
            ).thenReturn(
                Result.success(false)
            )
            whenever(
                movChaveEquipRepository.checkOpen()
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase()
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
    fun `Check return false if CheckMovOpenImpl execute successfully and returned false in all repositories`() =
        runTest {
            whenever(
                movEquipProprioRepository.checkOpen()
            ).thenReturn(
                Result.success(false)
            )
            whenever(
                movEquipVisitTercRepository.checkOpen()
            ).thenReturn(
                Result.success(false)
            )
            whenever(
                movEquipResidenciaRepository.checkOpen()
            ).thenReturn(
                Result.success(false)
            )
            whenever(
                movChaveRepository.checkOpen()
            ).thenReturn(
                Result.success(false)
            )
            whenever(
                movChaveEquipRepository.checkOpen()
            ).thenReturn(
                Result.success(false)
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                false
            )
        }
}