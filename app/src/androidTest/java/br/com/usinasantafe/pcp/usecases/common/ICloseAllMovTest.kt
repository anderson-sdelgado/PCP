package br.com.usinasantafe.pcp.domain.usecases.common

import br.com.usinasantafe.pcp.external.room.dao.variable.MovChaveDao
import br.com.usinasantafe.pcp.external.room.dao.variable.MovChaveEquipDao
import br.com.usinasantafe.pcp.external.room.dao.variable.MovEquipProprioDao
import br.com.usinasantafe.pcp.external.room.dao.variable.MovEquipResidenciaDao
import br.com.usinasantafe.pcp.external.room.dao.variable.MovEquipVisitTercDao
import br.com.usinasantafe.pcp.generateTestAppComponent
import br.com.usinasantafe.pcp.infra.models.room.variable.MovChaveEquipRoomModel
import br.com.usinasantafe.pcp.infra.models.room.variable.MovChaveRoomModel
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipProprioRoomModel
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipResidenciaRoomModel
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipVisitTercRoomModel
import br.com.usinasantafe.pcp.utils.StatusData
import br.com.usinasantafe.pcp.utils.StatusForeigner
import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.utils.TypeMovEquip
import br.com.usinasantafe.pcp.utils.TypeMovKey
import br.com.usinasantafe.pcp.utils.TypeVisitTerc
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before

import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class ICloseAllMovTest: KoinTest {

    private val usecase: CloseAllMov by inject()
    private val movEquipProprioDao: MovEquipProprioDao by inject()
    private val movEquipVisitTercDao: MovEquipVisitTercDao by inject()
    private val movEquipResidenciaDao: MovEquipResidenciaDao by inject()
    private val movChaveDao: MovChaveDao by inject()
    private val movChaveEquipDao: MovChaveEquipDao by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_true_if_not_have_mov_open() = runTest {
        val result = usecase()
        assertTrue(result.isSuccess)
    }

    @Test
    fun check_return_true_and_data_returned() =
        runTest {

            movEquipProprioDao.insert(
                MovEquipProprioRoomModel(
                    idMovEquipProprio = 1,
                    matricVigiaMovEquipProprio = 19759,
                    idLocalMovEquipProprio = 1,
                    tipoMovEquipProprio = TypeMovEquip.INPUT,
                    dthrMovEquipProprio = 1723213270250,
                    idEquipMovEquipProprio = 1,
                    matricColabMovEquipProprio = 19759,
                    destinoMovEquipProprio = "TESTE DESTINO",
                    notaFiscalMovEquipProprio = 123456789,
                    observMovEquipProprio = "TESTE OBSERV",
                    statusMovEquipProprio = StatusData.OPEN,
                    statusSendMovEquipProprio = StatusSend.SEND
                )
            )
            movEquipVisitTercDao.insert(
                MovEquipVisitTercRoomModel(
                    idMovEquipVisitTerc = 1,
                    nroMatricVigiaMovEquipVisitTerc = 19759,
                    idLocalMovEquipVisitTerc = 1,
                    tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                    idVisitTercMovEquipVisitTerc = 1000,
                    tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                    dthrMovEquipVisitTerc = 1723213270250,
                    veiculoMovEquipVisitTerc = "VEICULO TESTE",
                    placaMovEquipVisitTerc = "PLACA TESTE",
                    destinoMovEquipVisitTerc = "DESTINO TESTE",
                    observMovEquipVisitTerc = "OBSERV TESTE",
                    statusMovEquipVisitTerc = StatusData.OPEN,
                    statusSendMovEquipVisitTerc = StatusSend.SEND,
                    statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
                )
            )
            movEquipResidenciaDao.insert(
                MovEquipResidenciaRoomModel(
                    idMovEquipResidencia = 1,
                    matricVigiaMovEquipResidencia = 19759,
                    idLocalMovEquipResidencia = 1,
                    tipoMovEquipResidencia = TypeMovEquip.INPUT,
                    dthrMovEquipResidencia = 1723213270250,
                    motoristaMovEquipResidencia = "MOTORISTA TESTE",
                    veiculoMovEquipResidencia = "VEICULO TESTE",
                    placaMovEquipResidencia = "PLACA TESTE",
                    observMovEquipResidencia = "OBSERV TESTE",
                    statusMovEquipResidencia = StatusData.OPEN,
                    statusSendMovEquipResidencia = StatusSend.SEND,
                    statusMovEquipForeignerResidencia = StatusForeigner.INSIDE,
                )
            )
            movChaveDao.insert(
                MovChaveRoomModel(
                    idMovChave = 1,
                    matricVigiaMovChave = 19759,
                    dthrMovChave =
                    1723213270250,
                    tipoMovChave = TypeMovKey.REMOVE,
                    idLocalMovChave = 1,
                    idChaveMovChave = 1,
                    matricColabMovChave = 19035,
                    statusMovChave = StatusData.OPEN,
                    statusSendMovChave = StatusSend.SEND,
                    statusForeignerMovChave = StatusForeigner.INSIDE,
                    observMovChave = "Teste",
                    uuidMainMovChave = "UUID"
                )
            )
            movChaveEquipDao.insert(
                MovChaveEquipRoomModel(
                    idMovChaveEquip = 1,
                    matricVigiaMovChaveEquip = 19759,
                    dthrMovChaveEquip =
                    1723213270250,
                    tipoMovChaveEquip = TypeMovKey.REMOVE,
                    idLocalMovChaveEquip = 1,
                    idEquipMovChaveEquip = 1,
                    matricColabMovChaveEquip = 19035,
                    statusMovChaveEquip = StatusData.OPEN,
                    statusSendMovChaveEquip = StatusSend.SEND,
                    statusForeignerMovChaveEquip = StatusForeigner.INSIDE,
                    observMovChaveEquip = "Teste",
                    uuidMainMovChaveEquip = "UUID"
                )
            )

            val movEquipProprioRoomModelBefore = movEquipProprioDao.get(1)
            assertEquals(
                movEquipProprioRoomModelBefore.statusMovEquipProprio,
                StatusData.OPEN
            )
            val movEquipVisitTercRoomModelBefore = movEquipVisitTercDao.get(1)
            assertEquals(
                movEquipVisitTercRoomModelBefore.statusMovEquipVisitTerc,
                StatusData.OPEN
            )
            val movEquipResidenciaRoomModelBefore = movEquipResidenciaDao.get(1)
            assertEquals(
                movEquipResidenciaRoomModelBefore.statusMovEquipResidencia,
                StatusData.OPEN
            )
            val movChaveRoomModelBefore = movChaveDao.get(1)
            assertEquals(
                movChaveRoomModelBefore.statusMovChave,
                StatusData.OPEN
            )
            val movChaveEquipRoomModelBefore = movChaveEquipDao.get(1)
            assertEquals(
                movChaveEquipRoomModelBefore.statusMovChaveEquip,
                StatusData.OPEN
            )

            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            );

            val movEquipProprioRoomModelAfter = movEquipProprioDao.get(1)
            assertEquals(
                movEquipProprioRoomModelAfter.statusMovEquipProprio,
                StatusData.CLOSE
            )
            val movEquipVisitTercRoomModelAfter = movEquipVisitTercDao.get(1)
            assertEquals(
                movEquipVisitTercRoomModelAfter.statusMovEquipVisitTerc,
                StatusData.CLOSE
            )
            val movEquipResidenciaRoomModelAfter = movEquipResidenciaDao.get(1)
            assertEquals(
                movEquipResidenciaRoomModelAfter.statusMovEquipResidencia,
                StatusData.CLOSE
            )
            val movChaveRoomModelAfter = movChaveDao.get(1)
            assertEquals(
                movChaveRoomModelAfter.statusMovChave,
                StatusData.CLOSE
            )
            val movChaveEquipRoomModelAfter = movChaveEquipDao.get(1)
            assertEquals(
                movChaveEquipRoomModelAfter.statusMovChaveEquip,
                StatusData.CLOSE
            )

    }
}