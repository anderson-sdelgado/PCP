package br.com.usinasantafe.pcp.domain.usecases.common

import br.com.usinasantafe.pcp.domain.entities.variable.MovChave
import br.com.usinasantafe.pcp.domain.entities.variable.MovChaveEquip
import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipProprio
import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipResidencia
import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipVisitTerc
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveEquipRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class ICloseAllMovTest {

    private val movEquipProprioRepository = mock<MovEquipProprioRepository>()
    private val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
    private val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
    private val movChaveRepository = mock<MovChaveRepository>()
    private val movChaveEquipRepository = mock<MovChaveEquipRepository>()
    private val usecase = ICloseAllMov(
        movEquipProprioRepository,
        movEquipVisitTercRepository,
        movEquipResidenciaRepository,
        movChaveRepository,
        movChaveEquipRepository
    )

    @Test
    fun `Check return failure if have failure in MovEquipProprioRepository listOpen`() = runTest {
        whenever(
            movEquipProprioRepository.listOpen()
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val result = usecase()
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Repository -> MovEquipProprioRepository.listOpen"
        )
    }

    @Test
    fun `Check return failure if have failure in MovEquipProprioRepository setClose`() = runTest {
        val movEquipProprio = MovEquipProprio(
            idMovEquipProprio = 1
        )
        whenever(
            movEquipProprioRepository.listOpen()
        ).thenReturn(
            Result.success(
                listOf(
                    movEquipProprio
                )
            )
        )
        whenever(
            movEquipProprioRepository.setClose(
                1
            )
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val result = usecase()
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Repository -> MovEquipProprioRepository.setClose"
        )
    }

    @Test
    fun `Check return failure if have failure in MovEquipVisitTercRepository listOpen`() = runTest {
        val movEquipProprio = MovEquipProprio(
            idMovEquipProprio = 1
        )
        whenever(movEquipProprioRepository.listOpen()).thenReturn(
            Result.success(
                listOf(
                    movEquipProprio
                )
            )
        )
        whenever(
            movEquipProprioRepository.setClose(
                1
            )
        ).thenReturn(
            Result.success(true)
        )
        whenever(movEquipVisitTercRepository.listOpen()).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val result = usecase()
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Repository -> MovEquipProprioVisitTerc.listOpen"
        )
    }

    @Test
    fun `Check return failure if have failure in MovEquipVisitTercRepository setClose`() = runTest {
        val movEquipProprio = MovEquipProprio(
            idMovEquipProprio = 1
        )
        val movEquipVisitTerc = MovEquipVisitTerc(
            idMovEquipVisitTerc = 1
        )
        whenever(movEquipProprioRepository.listOpen()).thenReturn(
            Result.success(
                listOf(
                    movEquipProprio
                )
            )
        )
        whenever(
            movEquipProprioRepository.setClose(
                1
            )
        ).thenReturn(
            Result.success(true)
        )
        whenever(movEquipVisitTercRepository.listOpen()).thenReturn(
            Result.success(
                listOf(
                    movEquipVisitTerc
                )
            )
        )
        whenever(
            movEquipVisitTercRepository.setClose(1)
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val result = usecase()
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Repository -> MovEquipProprioVisitTerc.setClose"
        )
    }

    @Test
    fun `Check return failure if have failure in MovEquipResidenciaRepository listOpen`() =
        runTest {
            val movEquipProprio = MovEquipProprio(
                idMovEquipProprio = 1
            )
            val movEquipVisitTerc = MovEquipVisitTerc(
                idMovEquipVisitTerc = 1
            )
            whenever(movEquipProprioRepository.listOpen()).thenReturn(
                Result.success(
                    listOf(
                        movEquipProprio
                    )
                )
            )
            whenever(
                movEquipProprioRepository.setClose(
                    1
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(movEquipVisitTercRepository.listOpen()).thenReturn(
                Result.success(
                    listOf(
                        movEquipVisitTerc
                    )
                )
            )
            whenever(
                movEquipVisitTercRepository.setClose(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(movEquipResidenciaRepository.listOpen()).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipResidenciaRepository.listOpen"
            )
        }

    @Test
    fun `Check return failure if have failure in MovEquipResidenciaRepository setClose`() =
        runTest {
            val movEquipProprio = MovEquipProprio(
                idMovEquipProprio = 1
            )
            val movEquipVisitTerc = MovEquipVisitTerc(
                idMovEquipVisitTerc = 1
            )
            val movEquipResidencia = MovEquipResidencia(
                idMovEquipResidencia = 1
            )
            whenever(movEquipProprioRepository.listOpen()).thenReturn(
                Result.success(
                    listOf(
                        movEquipProprio
                    )
                )
            )
            whenever(
                movEquipProprioRepository.setClose(
                    1
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(movEquipVisitTercRepository.listOpen()).thenReturn(
                Result.success(
                    listOf(
                        movEquipVisitTerc
                    )
                )
            )
            whenever(
                movEquipVisitTercRepository.setClose(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(movEquipResidenciaRepository.listOpen()).thenReturn(
                Result.success(
                    listOf(
                        movEquipResidencia
                    )
                )
            )
            whenever(
                movEquipResidenciaRepository.setClose(1)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipResidenciaRepository.setClose"
            )
        }

    @Test
    fun `Check return failure if have failure in MovChaveRepository listOpen`() =
        runTest {
            val movEquipProprio = MovEquipProprio(
                idMovEquipProprio = 1
            )
            val movEquipVisitTerc = MovEquipVisitTerc(
                idMovEquipVisitTerc = 1
            )
            val movEquipResidencia = MovEquipResidencia(
                idMovEquipResidencia = 1
            )
            whenever(movEquipProprioRepository.listOpen()).thenReturn(
                Result.success(
                    listOf(
                        movEquipProprio
                    )
                )
            )
            whenever(
                movEquipProprioRepository.setClose(
                    1
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(movEquipVisitTercRepository.listOpen()).thenReturn(
                Result.success(
                    listOf(
                        movEquipVisitTerc
                    )
                )
            )
            whenever(
                movEquipVisitTercRepository.setClose(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipResidenciaRepository.listOpen()
            ).thenReturn(
                Result.success(
                    listOf(
                        movEquipResidencia
                    )
                )
            )
            whenever(
                movEquipResidenciaRepository.setClose(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movChaveRepository.listOpen()
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovChaveRepository.listOpen"
            )
        }

    @Test
    fun `Check return failure if have failure in MovChaveRepository setClose`() =
        runTest {
            val movEquipProprio = MovEquipProprio(
                idMovEquipProprio = 1
            )
            val movEquipVisitTerc = MovEquipVisitTerc(
                idMovEquipVisitTerc = 1
            )
            val movEquipResidencia = MovEquipResidencia(
                idMovEquipResidencia = 1
            )
            val movChave = MovChave(
                idMovChave = 1
            )
            whenever(movEquipProprioRepository.listOpen()).thenReturn(
                Result.success(
                    listOf(
                        movEquipProprio
                    )
                )
            )
            whenever(
                movEquipProprioRepository.setClose(
                    1
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(movEquipVisitTercRepository.listOpen()).thenReturn(
                Result.success(
                    listOf(
                        movEquipVisitTerc
                    )
                )
            )
            whenever(
                movEquipVisitTercRepository.setClose(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipResidenciaRepository.listOpen()
            ).thenReturn(
                Result.success(
                    listOf(
                        movEquipResidencia
                    )
                )
            )
            whenever(
                movEquipResidenciaRepository.setClose(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movChaveRepository.listOpen()
            ).thenReturn(
                Result.success(
                    listOf(
                        movChave
                    )
                )
            )
            whenever(
                movChaveRepository.setClose(1)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovChaveRepository.setClose"
            )
        }

    @Test
    fun `Check return failure if have failure in MovChaveEquipRepository listOpen`() =
        runTest {
            val movEquipProprio = MovEquipProprio(
                idMovEquipProprio = 1
            )
            val movEquipVisitTerc = MovEquipVisitTerc(
                idMovEquipVisitTerc = 1
            )
            val movEquipResidencia = MovEquipResidencia(
                idMovEquipResidencia = 1
            )
            val movChave = MovChave(
                idMovChave = 1
            )
            whenever(movEquipProprioRepository.listOpen()).thenReturn(
                Result.success(
                    listOf(
                        movEquipProprio
                    )
                )
            )
            whenever(
                movEquipProprioRepository.setClose(
                    1
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(movEquipVisitTercRepository.listOpen()).thenReturn(
                Result.success(
                    listOf(
                        movEquipVisitTerc
                    )
                )
            )
            whenever(
                movEquipVisitTercRepository.setClose(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipResidenciaRepository.listOpen()
            ).thenReturn(
                Result.success(
                    listOf(
                        movEquipResidencia
                    )
                )
            )
            whenever(
                movEquipResidenciaRepository.setClose(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movChaveRepository.listOpen()
            ).thenReturn(
                Result.success(
                    listOf(
                        movChave
                    )
                )
            )
            whenever(
                movChaveRepository.setClose(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movChaveEquipRepository.listOpen()
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovChaveEquipRepository.listOpen"
            )
        }

    @Test
    fun `Check return failure if have failure in MovChaveEquipRepository setClose`() =
        runTest {
            val movEquipProprio = MovEquipProprio(
                idMovEquipProprio = 1
            )
            val movEquipVisitTerc = MovEquipVisitTerc(
                idMovEquipVisitTerc = 1
            )
            val movEquipResidencia = MovEquipResidencia(
                idMovEquipResidencia = 1
            )
            val movChave = MovChave(
                idMovChave = 1
            )
            val movChaveEquip = MovChaveEquip(
                idMovChaveEquip = 1
            )
            whenever(movEquipProprioRepository.listOpen()).thenReturn(
                Result.success(
                    listOf(
                        movEquipProprio
                    )
                )
            )
            whenever(
                movEquipProprioRepository.setClose(
                    1
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(movEquipVisitTercRepository.listOpen()).thenReturn(
                Result.success(
                    listOf(
                        movEquipVisitTerc
                    )
                )
            )
            whenever(
                movEquipVisitTercRepository.setClose(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipResidenciaRepository.listOpen()
            ).thenReturn(
                Result.success(
                    listOf(
                        movEquipResidencia
                    )
                )
            )
            whenever(
                movEquipResidenciaRepository.setClose(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movChaveRepository.listOpen()
            ).thenReturn(
                Result.success(
                    listOf(
                        movChave
                    )
                )
            )
            whenever(
                movChaveRepository.setClose(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movChaveEquipRepository.listOpen()
            ).thenReturn(
                Result.success(
                    listOf(
                        movChaveEquip
                    )
                )
            )
            whenever(
                movChaveEquipRepository.setClose(1)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovChaveEquipRepository.setClose"
            )
        }

    @Test
    fun `Check return success if have executed correctly`() =
        runTest {
            val movEquipProprio = MovEquipProprio(
                idMovEquipProprio = 1
            )
            val movEquipVisitTerc = MovEquipVisitTerc(
                idMovEquipVisitTerc = 1
            )
            val movEquipResidencia = MovEquipResidencia(
                idMovEquipResidencia = 1
            )
            val movChave = MovChave(
                idMovChave = 1
            )
            val movChaveEquip = MovChaveEquip(
                idMovChaveEquip = 1
            )
            whenever(movEquipProprioRepository.listOpen()).thenReturn(
                Result.success(
                    listOf(
                        movEquipProprio
                    )
                )
            )
            whenever(
                movEquipProprioRepository.setClose(
                    1
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(movEquipVisitTercRepository.listOpen()).thenReturn(
                Result.success(
                    listOf(
                        movEquipVisitTerc
                    )
                )
            )
            whenever(
                movEquipVisitTercRepository.setClose(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipResidenciaRepository.listOpen()
            ).thenReturn(
                Result.success(
                    listOf(
                        movEquipResidencia
                    )
                )
            )
            whenever(
                movEquipResidenciaRepository.setClose(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movChaveRepository.listOpen()
            ).thenReturn(
                Result.success(
                    listOf(
                        movChave
                    )
                )
            )
            whenever(
                movChaveRepository.setClose(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movChaveEquipRepository.listOpen()
            ).thenReturn(
                Result.success(
                    listOf(
                        movChaveEquip
                    )
                )
            )
            whenever(
                movChaveEquipRepository.setClose(1)
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
}