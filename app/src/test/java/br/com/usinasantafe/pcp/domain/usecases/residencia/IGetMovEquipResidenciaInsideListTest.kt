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

class IGetMovEquipResidenciaInsideListTest {

    private val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
    private val usecase = IGetMovEquipResidenciaInsideList(
        movEquipResidenciaRepository
    )

    @Test
    fun `Check return failure if have error in MovEquipResidenciaRepository ListOpenInput`() =
        runTest {
            whenever(
                movEquipResidenciaRepository.listInside()
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
                "IGetMovEquipResidenciaInsideList -> Unknown Error"
            )
        }

    @Test
    fun `Check return model list if GetMovEquipResidenciaInputOpenListImpl execute successfully`() =
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
            val list = listOf(model)
            whenever(
                movEquipResidenciaRepository.listInside()
            ).thenReturn(
                Result.success(list)
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            val modelList = result.getOrNull()!!
            assertEquals(
                modelList.size,
                1
            )
            assertEquals(
                modelList[0].id,
                1
            )
            assertEquals(
                modelList[0].dthr,
                "09/08/2024 11:21"
            )
            assertEquals(
                modelList[0].veiculo,
                "VEICULO TESTE"
            )
            assertEquals(
                modelList[0].placa,
                "PLACA TESTE"
            )
            assertEquals(
                modelList[0].motorista,
                "MOTORISTA TESTE"
            )
        }

}