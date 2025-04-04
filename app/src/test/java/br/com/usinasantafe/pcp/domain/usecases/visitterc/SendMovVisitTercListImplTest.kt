package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.entities.variable.Config
import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipVisitTerc
import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipVisitTercPassag
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.StatusData
import br.com.usinasantafe.pcp.utils.StatusForeigner
import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.utils.TypeMovEquip
import br.com.usinasantafe.pcp.utils.TypeVisitTerc
import br.com.usinasantafe.pcp.utils.token
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.Date

class SendMovVisitTercListImplTest {

    private val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
    private val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
    private val configRepository = mock<ConfigRepository>()
    private val usecase = ISendMovVisitTercList(
        movEquipVisitTercRepository,
        movEquipVisitTercPassagRepository,
        configRepository
    )

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository listSend`() =
        runTest {
            whenever(
                movEquipVisitTercRepository.listSend()
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
                "ISendMovVisitTercList -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercPassagRepository list`() =
        runTest {
            whenever(
                movEquipVisitTercRepository.listSend()
            ).thenReturn(
                Result.success(
                    listOf(
                        MovEquipVisitTerc(
                            idMovEquipVisitTerc = 1,
                            nroMatricVigiaMovEquipVisitTerc = 19759,
                            idLocalMovEquipVisitTerc = 1,
                            tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                            idVisitTercMovEquipVisitTerc = 1000,
                            tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                            dthrMovEquipVisitTerc = Date(1723213270250),
                            veiculoMovEquipVisitTerc = "VEICULO TESTE",
                            placaMovEquipVisitTerc = "PLACA TESTE",
                            destinoMovEquipVisitTerc = "DESTINO TESTE",
                            observMovEquipVisitTerc = "OBSERV TESTE",
                            statusMovEquipVisitTerc = StatusData.OPEN,
                            statusSendMovEquipVisitTerc = StatusSend.SEND,
                            statusMovEquipForeignerVisitTerc = StatusForeigner.INSIDE,
                        )
                    )
                )
            )
            whenever(
                movEquipVisitTercPassagRepository.list(
                    flowApp = FlowApp.CHANGE,
                    id = 1
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
                "ISendMovVisitTercList -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in ConfigRepository getConfig`() =
        runTest {
            whenever(
                movEquipVisitTercRepository.listSend()
            ).thenReturn(
                Result.success(
                    listOf(
                        MovEquipVisitTerc(
                            idMovEquipVisitTerc = 1,
                            nroMatricVigiaMovEquipVisitTerc = 19759,
                            idLocalMovEquipVisitTerc = 1,
                            tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                            idVisitTercMovEquipVisitTerc = 1000,
                            tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                            dthrMovEquipVisitTerc = Date(1723213270250),
                            veiculoMovEquipVisitTerc = "VEICULO TESTE",
                            placaMovEquipVisitTerc = "PLACA TESTE",
                            destinoMovEquipVisitTerc = "DESTINO TESTE",
                            observMovEquipVisitTerc = "OBSERV TESTE",
                            statusMovEquipVisitTerc = StatusData.OPEN,
                            statusSendMovEquipVisitTerc = StatusSend.SEND,
                            statusMovEquipForeignerVisitTerc = StatusForeigner.INSIDE,
                        )
                    )
                )
            )
            whenever(
                movEquipVisitTercPassagRepository.list(
                    flowApp = FlowApp.CHANGE,
                    id = 1
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        MovEquipVisitTercPassag(
                            idMovEquipVisitTercPassag = 1,
                            idMovEquipVisitTerc = 1,
                            idVisitTerc = 100
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
                "ISendMovVisitTercList -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository send`() =
        runTest {
            val listMov =
                listOf(
                    MovEquipVisitTerc(
                        idMovEquipVisitTerc = 1,
                        nroMatricVigiaMovEquipVisitTerc = 19759,
                        idLocalMovEquipVisitTerc = 1,
                        tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                        idVisitTercMovEquipVisitTerc = 1000,
                        tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                        dthrMovEquipVisitTerc = Date(1723213270250),
                        veiculoMovEquipVisitTerc = "VEICULO TESTE",
                        placaMovEquipVisitTerc = "PLACA TESTE",
                        destinoMovEquipVisitTerc = "DESTINO TESTE",
                        observMovEquipVisitTerc = "OBSERV TESTE",
                        statusMovEquipVisitTerc = StatusData.OPEN,
                        statusSendMovEquipVisitTerc = StatusSend.SEND,
                        statusMovEquipForeignerVisitTerc = StatusForeigner.INSIDE,
                    )
                )
            val listPassag =
                listOf(
                    MovEquipVisitTercPassag(
                        idMovEquipVisitTercPassag = 1,
                        idMovEquipVisitTerc = 1,
                        idVisitTerc = 100
                    )
                )
            val entityConfig = Config(
                idBD = 1,
                number = 16997417840,
                version = "1.00"
            )
            whenever(
                movEquipVisitTercRepository.listSend()
            ).thenReturn(
                Result.success(listMov)
            )
            whenever(
                movEquipVisitTercPassagRepository.list(
                    flowApp = FlowApp.CHANGE,
                    id = 1
                )
            ).thenReturn(
                Result.success(listPassag)
            )
            whenever(
                configRepository.getConfig()
            ).thenReturn(
                Result.success(entityConfig)
            )
            val listSendFull = listMov.map { entity ->
                entity.movEquipVisitTercPassagList = listPassag
                return@map entity
            }
            val token = token(
                number = entityConfig.number!!,
                version = entityConfig.version!!,
                idBD = entityConfig.idBD!!
            )
            whenever(
                movEquipVisitTercRepository.send(
                    list = listSendFull,
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
                "ISendMovVisitTercList -> Unknown Error"
            )
        }

    @Test
    fun `Check return list if SendMovVisitTercListImpl execute successfully`() =
        runTest {
            val listMov =
                listOf(
                    MovEquipVisitTerc(
                        idMovEquipVisitTerc = 1,
                        nroMatricVigiaMovEquipVisitTerc = 19759,
                        idLocalMovEquipVisitTerc = 1,
                        tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                        idVisitTercMovEquipVisitTerc = 1000,
                        tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                        dthrMovEquipVisitTerc = Date(1723213270250),
                        veiculoMovEquipVisitTerc = "VEICULO TESTE",
                        placaMovEquipVisitTerc = "PLACA TESTE",
                        destinoMovEquipVisitTerc = "DESTINO TESTE",
                        observMovEquipVisitTerc = "OBSERV TESTE",
                        statusMovEquipVisitTerc = StatusData.OPEN,
                        statusSendMovEquipVisitTerc = StatusSend.SEND,
                        statusMovEquipForeignerVisitTerc = StatusForeigner.INSIDE,
                    )
                )
            val listPassag =
                listOf(
                    MovEquipVisitTercPassag(
                        idMovEquipVisitTercPassag = 1,
                        idMovEquipVisitTerc = 1,
                        idVisitTerc = 100
                    )
                )
            val entityConfig = Config(
                idBD = 1,
                number = 16997417840,
                version = "1.00"
            )
            whenever(
                movEquipVisitTercRepository.listSend()
            ).thenReturn(
                Result.success(listMov)
            )
            whenever(
                movEquipVisitTercPassagRepository.list(
                    flowApp = FlowApp.CHANGE,
                    id = 1
                )
            ).thenReturn(
                Result.success(listPassag)
            )
            whenever(
                configRepository.getConfig()
            ).thenReturn(
                Result.success(entityConfig)
            )
            val listSendFull = listMov.map { entity ->
                entity.movEquipVisitTercPassagList = listPassag
                return@map entity
            }
            val token = token(
                number = entityConfig.number!!,
                version = entityConfig.version!!,
                idBD = entityConfig.idBD!!
            )
            whenever(
                movEquipVisitTercRepository.send(
                    list = listSendFull,
                    number = 16997417840,
                    token = token
                )
            ).thenReturn(
                Result.success(listSendFull)
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
                entity.idMovEquipVisitTerc,
                1
            )
            assertEquals(
                entity.nroMatricVigiaMovEquipVisitTerc,
                19759
            )
        }
}