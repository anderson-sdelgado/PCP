package br.com.usinasantafe.pcp.domain.usecases.residencia

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipResidencia
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcp.utils.StatusData
import br.com.usinasantafe.pcp.utils.StatusForeigner
import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.utils.TypeMovEquip
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.Date

class ICloseAllMovResidenciaTest {

    @Test
    fun `Check return failure if have error in MovEquipResidenciaRepository ListOpen`() =
        runTest {
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            whenever(
                movEquipResidenciaRepository.listOpen()
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val usecase = ICloseAllMovResidencia(
                movEquipResidenciaRepository
            )
            val result = usecase()
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipResidenciaRepository.listOpen"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipResidenciaRepository SetClose`() =
        runTest {
            val movList = listOf(
                MovEquipResidencia(
                    idMovEquipResidencia = 1,
                    matricVigiaMovEquipResidencia = 1000,
                    idLocalMovEquipResidencia = 1000,
                    tipoMovEquipResidencia = TypeMovEquip.INPUT,
                    dthrMovEquipResidencia = Date(1723213270250),
                    motoristaMovEquipResidencia = "TESTE",
                    veiculoMovEquipResidencia = "TESTE",
                    placaMovEquipResidencia = "TESTE",
                    observMovEquipResidencia = "TESTE",
                    statusMovEquipResidencia = StatusData.OPEN,
                    statusSendMovEquipResidencia = StatusSend.SEND,
                    statusMovEquipForeignerResidencia = StatusForeigner.INSIDE,
                )
            )
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            whenever(
                movEquipResidenciaRepository.listOpen()
            ).thenReturn(
                Result.success(movList)
            )
            whenever(
                movEquipResidenciaRepository.setClose(1)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val usecase = ICloseAllMovResidencia(
                movEquipResidenciaRepository
            )
            val result = usecase()
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipResidenciaRepository.setClose"
            )
        }

    @Test
    fun `Check return true if CloseAllMovResidenciaImpl execute successfully`() =
        runTest {
            val movList = listOf(
                MovEquipResidencia(
                    idMovEquipResidencia = 1,
                    matricVigiaMovEquipResidencia = 1000,
                    idLocalMovEquipResidencia = 1000,
                    tipoMovEquipResidencia = TypeMovEquip.INPUT,
                    dthrMovEquipResidencia = Date(1723213270250),
                    motoristaMovEquipResidencia = "TESTE",
                    veiculoMovEquipResidencia = "TESTE",
                    placaMovEquipResidencia = "TESTE",
                    observMovEquipResidencia = "TESTE",
                    statusMovEquipResidencia = StatusData.OPEN,
                    statusSendMovEquipResidencia = StatusSend.SEND,
                    statusMovEquipForeignerResidencia = StatusForeigner.INSIDE,
                )
            )
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            whenever(
                movEquipResidenciaRepository.listOpen()
            ).thenReturn(
                Result.success(movList)
            )
            whenever(
                movEquipResidenciaRepository.setClose(1)
            ).thenReturn(
                Result.success(true)
            )
            val usecase = ICloseAllMovResidencia(
                movEquipResidenciaRepository
            )
            val result = usecase()
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }
}