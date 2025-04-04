package br.com.usinasantafe.pcp.domain.usecases.chave

import br.com.usinasantafe.pcp.external.room.dao.stable.ChaveDao
import br.com.usinasantafe.pcp.external.room.dao.stable.ColabDao
import br.com.usinasantafe.pcp.external.room.dao.stable.LocalTrabDao
import br.com.usinasantafe.pcp.external.room.dao.variable.MovChaveDao
import br.com.usinasantafe.pcp.generateTestAppComponent
import br.com.usinasantafe.pcp.infra.models.room.stable.ChaveRoomModel
import br.com.usinasantafe.pcp.infra.models.room.stable.ColabRoomModel
import br.com.usinasantafe.pcp.infra.models.room.stable.LocalTrabRoomModel
import br.com.usinasantafe.pcp.infra.models.room.variable.MovChaveRoomModel
import br.com.usinasantafe.pcp.utils.StatusData
import br.com.usinasantafe.pcp.utils.StatusForeigner
import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.utils.TypeMovKey
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject
import java.util.Date

class IGetMovChaveInsideListTest : KoinTest {

    private val usecase: GetMovChaveInsideList by inject()
    private val movChaveDao: MovChaveDao by inject()
    private val colabDao: ColabDao by inject()
    private val chaveDao: ChaveDao by inject()
    private val localTrabDao: LocalTrabDao by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(
            generateTestAppComponent(
                server.url("").toString()
            )
        )
    }

    @Test
    fun check_return_list_empty_if_not_have_data() =
        runTest {
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!.size,
                0
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_colab_table() =
        runTest {
            movChaveDao.insert(
                MovChaveRoomModel(
                    dthrMovChave = Date().time,
                    tipoMovChave = TypeMovKey.REMOVE,
                    matricVigiaMovChave = 19759,
                    idLocalMovChave = 1,
                    idChaveMovChave = 1,
                    matricColabMovChave = 19035,
                    observMovChave = "OBSERV",
                    statusMovChave = StatusData.OPEN,
                    statusSendMovChave = StatusSend.SEND,
                    statusForeignerMovChave = StatusForeigner.INSIDE,
                    uuidMainMovChave = "UUID"
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Usecase -> IGetMovChaveInsideList"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_chave_table() =
        runTest {
            movChaveDao.insert(
                MovChaveRoomModel(
                    dthrMovChave = Date().time,
                    tipoMovChave = TypeMovKey.REMOVE,
                    matricVigiaMovChave = 19759,
                    idLocalMovChave = 1,
                    idChaveMovChave = 1,
                    matricColabMovChave = 19035,
                    observMovChave = "OBSERV",
                    statusMovChave = StatusData.OPEN,
                    statusSendMovChave = StatusSend.SEND,
                    statusForeignerMovChave = StatusForeigner.INSIDE,
                    uuidMainMovChave = "UUID"
                )
            )
            colabDao.insertAll(
                listOf(
                    ColabRoomModel(
                        matricColab = 19035,
                        nomeColab = "NOME TESTE"
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
                "Failure Repository -> IChaveRepository.get"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Parameter specified as non-null is null: method br.com.usinasantafe.pcp.infra.models.room.stable.ChaveRoomModelKt.roomModelToEntity, parameter <this>"
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_local_trab_table() =
        runTest {
            movChaveDao.insert(
                MovChaveRoomModel(
                    dthrMovChave = Date().time,
                    tipoMovChave = TypeMovKey.REMOVE,
                    matricVigiaMovChave = 19759,
                    idLocalMovChave = 1,
                    idChaveMovChave = 1,
                    matricColabMovChave = 19035,
                    observMovChave = "OBSERV",
                    statusMovChave = StatusData.OPEN,
                    statusSendMovChave = StatusSend.SEND,
                    statusForeignerMovChave = StatusForeigner.INSIDE,
                    uuidMainMovChave = "UUID"
                )
            )
            colabDao.insertAll(
                listOf(
                    ColabRoomModel(
                        matricColab = 19035,
                        nomeColab = "NOME TESTE"
                    )
                )
            )
            chaveDao.insertAll(
                listOf(
                    ChaveRoomModel(
                        idChave = 1,
                        descrChave = "01 - SALA TI",
                        idLocalTrab = 1
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
                "Failure Datasource -> ILocalTrabRoomDatasource.getDescr"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Attempt to invoke virtual method 'java.lang.String br.com.usinasantafe.pcp.infra.models.room.stable.LocalTrabRoomModel.getDescrLocalTrab()' on a null object reference"
            )
        }

    @Test
    fun check_return_true_and_data_returned() =
        runTest {
            movChaveDao.insert(
                MovChaveRoomModel(
                    dthrMovChave = Date().time,
                    tipoMovChave = TypeMovKey.REMOVE,
                    matricVigiaMovChave = 19759,
                    idLocalMovChave = 1,
                    idChaveMovChave = 1,
                    matricColabMovChave = 19035,
                    observMovChave = "OBSERV",
                    statusMovChave = StatusData.OPEN,
                    statusSendMovChave = StatusSend.SEND,
                    statusForeignerMovChave = StatusForeigner.INSIDE,
                    uuidMainMovChave = "UUID"
                )
            )
            colabDao.insertAll(
                listOf(
                    ColabRoomModel(
                        matricColab = 19035,
                        nomeColab = "NOME TESTE"
                    )
                )
            )
            chaveDao.insertAll(
                listOf(
                    ChaveRoomModel(
                        idChave = 1,
                        descrChave = "01 - SALA TI",
                        idLocalTrab = 1
                    )
                )
            )
            localTrabDao.insertAll(
                listOf(
                    LocalTrabRoomModel(
                        idLocalTrab = 1,
                        descrLocalTrab = "TI"
                    )
                )
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
                entity.chave,
                "01 - SALA TI - TI"
            )
            assertEquals(
                entity.colab,
                "19035 - NOME TESTE"
            )
        }

}