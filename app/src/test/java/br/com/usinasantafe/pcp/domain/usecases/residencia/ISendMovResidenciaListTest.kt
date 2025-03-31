package br.com.usinasantafe.pcp.domain.usecases.residencia

import br.com.usinasantafe.pcp.domain.entities.variable.Config
import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipResidencia
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcp.utils.StatusData
import br.com.usinasantafe.pcp.utils.StatusForeigner
import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.utils.TypeMovEquip
import br.com.usinasantafe.pcp.utils.token
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.Date

class ISendMovResidenciaListTest {

    private val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
    private val configRepository = mock<ConfigRepository>()
    private val usecase = ISendMovResidenciaList(
        movEquipResidenciaRepository,
        configRepository
    )

    @Test
    fun `Check return failure if have error in MovEquipResidenciaRepository listSend`() =
        runTest {
            whenever(
                movEquipResidenciaRepository.listSend()
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
                "ISendMovResidenciaList -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in ConfigRepository GetConfig`() =
        runTest {
            whenever(
                movEquipResidenciaRepository.listSend()
            ).thenReturn(
                Result.success(
                    listOf(
                        MovEquipResidencia(
                            idMovEquipResidencia = 1,
                            matricVigiaMovEquipResidencia = 19759,
                            idLocalMovEquipResidencia = 1,
                            tipoMovEquipResidencia = TypeMovEquip.INPUT,
                            dthrMovEquipResidencia = Date(1723213270250),
                            motoristaMovEquipResidencia = "MOTORISTA TESTE",
                            veiculoMovEquipResidencia = "VEICULO TESTE",
                            placaMovEquipResidencia = "PLACA TESTE",
                            observMovEquipResidencia = "OBSERV TESTE",
                            statusMovEquipResidencia = StatusData.OPEN,
                            statusSendMovEquipResidencia = StatusSend.SEND,
                            statusMovEquipForeignerResidencia = StatusForeigner.INSIDE,
                        )
                    )
                )
            )
            whenever(
                configRepository.getConfig()
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
                "ISendMovResidenciaList -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipResidenciaRepository send`() =
        runTest {
            val entityList = listOf(
                MovEquipResidencia(
                    idMovEquipResidencia = 1,
                    matricVigiaMovEquipResidencia = 19759,
                    idLocalMovEquipResidencia = 1,
                    tipoMovEquipResidencia = TypeMovEquip.INPUT,
                    dthrMovEquipResidencia = Date(1723213270250),
                    motoristaMovEquipResidencia = "MOTORISTA TESTE",
                    veiculoMovEquipResidencia = "VEICULO TESTE",
                    placaMovEquipResidencia = "PLACA TESTE",
                    observMovEquipResidencia = "OBSERV TESTE",
                    statusMovEquipResidencia = StatusData.OPEN,
                    statusSendMovEquipResidencia = StatusSend.SEND,
                    statusMovEquipForeignerResidencia = StatusForeigner.INSIDE,
                )
            )
            val entityConfig = Config(
                idBD = 1,
                number = 16997417840,
                version = "1.00"
            )
            val token = token(
                number = entityConfig.number!!,
                version = entityConfig.version!!,
                idBD = entityConfig.idBD!!
            )
            whenever(
                movEquipResidenciaRepository.listSend()
            ).thenReturn(
                Result.success(entityList)
            )
            whenever(
                configRepository.getConfig()
            ).thenReturn(
                Result.success(entityConfig)
            )
            whenever(
                movEquipResidenciaRepository.send(
                    list = entityList,
                    number = 16997417840,
                    token = token
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
                "ISendMovResidenciaList -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if MovEquipResidenciaRepository send execute successfully`() =
        runTest {
            val entityList = listOf(
                MovEquipResidencia(
                    idMovEquipResidencia = 1,
                    matricVigiaMovEquipResidencia = 19759,
                    idLocalMovEquipResidencia = 1,
                    tipoMovEquipResidencia = TypeMovEquip.INPUT,
                    dthrMovEquipResidencia = Date(1723213270250),
                    motoristaMovEquipResidencia = "MOTORISTA TESTE",
                    veiculoMovEquipResidencia = "VEICULO TESTE",
                    placaMovEquipResidencia = "PLACA TESTE",
                    observMovEquipResidencia = "OBSERV TESTE",
                    statusMovEquipResidencia = StatusData.OPEN,
                    statusSendMovEquipResidencia = StatusSend.SEND,
                    statusMovEquipForeignerResidencia = StatusForeigner.INSIDE,
                )
            )
            val entityConfig = Config(
                idBD = 1,
                number = 16997417840,
                version = "1.00"
            )
            val token = token(
                number = entityConfig.number!!,
                version = entityConfig.version!!,
                idBD = entityConfig.idBD!!
            )
            whenever(
                movEquipResidenciaRepository.listSend()
            ).thenReturn(
                Result.success(entityList)
            )
            whenever(
                configRepository.getConfig()
            ).thenReturn(
                Result.success(entityConfig)
            )
            whenever(
                movEquipResidenciaRepository.send(
                    list = entityList,
                    number = 16997417840,
                    token = token
                )
            ).thenReturn(
                Result.success(entityList)
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                1
            )
            val entity = list[0]
            assertEquals(
                entity.idMovEquipResidencia,
                1
            )
            assertEquals(
                entity.placaMovEquipResidencia,
                "PLACA TESTE"
            )
        }
}