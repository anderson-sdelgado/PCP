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

class IStartOutputMovEquipResidenciaTest {

    private val movEquipResidenciaRepository =
        mock<MovEquipResidenciaRepository>()
    private val usecase = IStartOutputMovEquipResidencia(
        movEquipResidenciaRepository
    )

    @Test
    fun `Check return failure if have error in MovEquipResidenciaRepository get`() =
        runTest {
            whenever(
                movEquipResidenciaRepository.get(1)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IStartOutputMovEquipResidencia -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipResidenciaRepository start`() =
        runTest {
            val entity = MovEquipResidencia(
                idMovEquipResidencia = 1,
                matricVigiaMovEquipResidencia = 1000,
                idLocalMovEquipResidencia = 1000,
                tipoMovEquipResidencia = TypeMovEquip.INPUT,
                dthrMovEquipResidencia = Date(1723213270250),
                motoristaMovEquipResidencia = "MOTORISTA TESTE",
                veiculoMovEquipResidencia = "VEICULO TESTE",
                placaMovEquipResidencia = "PLACA TESTE",
                statusMovEquipResidencia = StatusData.OPEN,
                statusSendMovEquipResidencia = StatusSend.SEND,
                statusMovEquipForeignerResidencia = StatusForeigner.INSIDE,
            )
            whenever(
                movEquipResidenciaRepository.get(1)
            ).thenReturn(
                Result.success(entity)
            )
            entity.tipoMovEquipResidencia = TypeMovEquip.OUTPUT
            entity.dthrMovEquipResidencia = Date()
            entity.statusMovEquipForeignerResidencia = StatusForeigner.OUTSIDE
            entity.observMovEquipResidencia = null
            whenever(
                movEquipResidenciaRepository.start(entity)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IStartOutputMovEquipResidencia -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if StartOutputMovEquipResidencia execute successfully`() =
        runTest {
            val entity = MovEquipResidencia(
                idMovEquipResidencia = 1,
                matricVigiaMovEquipResidencia = 1000,
                idLocalMovEquipResidencia = 1000,
                tipoMovEquipResidencia = TypeMovEquip.INPUT,
                dthrMovEquipResidencia = Date(1723213270250),
                motoristaMovEquipResidencia = "MOTORISTA TESTE",
                veiculoMovEquipResidencia = "VEICULO TESTE",
                placaMovEquipResidencia = "PLACA TESTE",
                statusMovEquipResidencia = StatusData.OPEN,
                statusSendMovEquipResidencia = StatusSend.SEND,
                statusMovEquipForeignerResidencia = StatusForeigner.INSIDE,
            )
            whenever(
                movEquipResidenciaRepository.get(1)
            ).thenReturn(
                Result.success(entity)
            )
            entity.tipoMovEquipResidencia = TypeMovEquip.OUTPUT
            entity.dthrMovEquipResidencia = Date()
            entity.statusMovEquipForeignerResidencia = StatusForeigner.OUTSIDE
            entity.observMovEquipResidencia = null
            whenever(
                movEquipResidenciaRepository.start(entity)
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(1)
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