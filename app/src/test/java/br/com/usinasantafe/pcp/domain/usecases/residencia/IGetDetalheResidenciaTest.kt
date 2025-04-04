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

class IGetDetalheResidenciaTest {

    private val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
    private val usecase = IGetDetalheResidencia(
        movEquipResidenciaRepository
    )

    @Test
    fun `Check return failure if have error in MovEquipResidenciaRepository Get`() =
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
                "IGetDetalheResidencia -> Unknown Error"
            )
        }

    @Test
    fun `Check return model if GetDetalheResidenciaImpl execute successfully Observ null`() =
        runTest {
            val model = MovEquipResidencia(
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
                Result.success(model)
            )
            val result = usecase(1)
            assertEquals(
                result.isSuccess,
                true
            )
            val detalhe = result.getOrNull()!!
            assertEquals(
                detalhe.dthr,
                "09/08/2024 11:21"
            )
            assertEquals(
                detalhe.tipoMov,
                "ENTRADA"
            )
            assertEquals(
                detalhe.veiculo,
                "VEICULO TESTE"
            )
            assertEquals(
                detalhe.placa,
                "PLACA TESTE"
            )
            assertEquals(
                detalhe.motorista,
                "MOTORISTA TESTE"
            )
            assertEquals(
                detalhe.observ,
                null
            )
        }

    @Test
    fun `Check return model if GetDetalheResidenciaImpl execute successfully`() =
        runTest {
            val model = MovEquipResidencia(
                idMovEquipResidencia = 1,
                matricVigiaMovEquipResidencia = 1000,
                idLocalMovEquipResidencia = 1000,
                tipoMovEquipResidencia = TypeMovEquip.INPUT,
                dthrMovEquipResidencia = Date(1723213270250),
                motoristaMovEquipResidencia = "MOTORISTA TESTE",
                veiculoMovEquipResidencia = "VEICULO TESTE",
                placaMovEquipResidencia = "PLACA TESTE",
                observMovEquipResidencia = "OBSERVACAO TESTE",
                statusMovEquipResidencia = StatusData.OPEN,
                statusSendMovEquipResidencia = StatusSend.SEND,
                statusMovEquipForeignerResidencia = StatusForeigner.INSIDE,
            )
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            whenever(
                movEquipResidenciaRepository.get(1)
            ).thenReturn(
                Result.success(model)
            )
            val usecase = IGetDetalheResidencia(
                movEquipResidenciaRepository
            )
            val result = usecase(1)
            assertEquals(
                result.isSuccess,
                true
            )
            val detalhe = result.getOrNull()!!
            assertEquals(
                detalhe.dthr,
                "09/08/2024 11:21"
            )
            assertEquals(
                detalhe.tipoMov,
                "ENTRADA"
            )
            assertEquals(
                detalhe.veiculo,
                "VEICULO TESTE"
            )
            assertEquals(
                detalhe.placa,
                "PLACA TESTE"
            )
            assertEquals(
                detalhe.motorista,
                "MOTORISTA TESTE"
            )
            assertEquals(
                detalhe.observ,
                "OBSERVACAO TESTE"
            )
        }
}