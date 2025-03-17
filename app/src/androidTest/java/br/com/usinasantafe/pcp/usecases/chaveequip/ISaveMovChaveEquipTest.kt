package br.com.usinasantafe.pcp.domain.usecases.chaveequip

import br.com.usinasantafe.pcp.domain.entities.variable.Config
import br.com.usinasantafe.pcp.external.room.dao.variable.MovChaveEquipDao
import br.com.usinasantafe.pcp.generateTestAppComponent
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.MovChaveEquipSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.MovChaveEquipSharedPreferencesModel
import br.com.usinasantafe.pcp.utils.StatusForeigner
import br.com.usinasantafe.pcp.utils.TypeMovKey
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject
import java.util.Date
import kotlin.test.assertEquals

class ISaveMovChaveEquipTest : KoinTest {

    private val usecase: SaveMovChaveEquip by inject()
    private val configSharedPreferencesDatasource:
            ConfigSharedPreferencesDatasource by inject()
    private val movChaveEquipDao: MovChaveEquipDao by inject()
    private val movChaveEquipSharedPreferencesDatasource:
            MovChaveEquipSharedPreferencesDatasource by inject()

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
    fun check_return_failure_if_not_have_data_in_config_receipt() =
        runTest {
            val result = usecase(
                typeMov = TypeMovKey.RECEIPT,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Usecase -> ISaveMovChaveEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause!!.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_mov_chave_shared_preferences_receipt() =
        runTest {
            configSharedPreferencesDatasource.save(
                Config(
                    matricVigia = 19035,
                    idLocal = 1
                )
            )
            val result = usecase(
                typeMov = TypeMovKey.RECEIPT,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> IMovChaveEquipSharedPreferencesDatasource.get"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause!!.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_true_and_data_returned_receipt() =
        runTest {
            configSharedPreferencesDatasource.save(
                Config(
                    matricVigia = 19035,
                    idLocal = 1
                )
            )
            movChaveEquipSharedPreferencesDatasource.start(
                MovChaveEquipSharedPreferencesModel(
                    dthrMovChaveEquip = Date(),
                    tipoMovChaveEquip = TypeMovKey.RECEIPT,
                    idEquipMovChaveEquip = 1,
                    matricColabMovChaveEquip = 19759,
                    observMovChaveEquip = "TESTE"
                )
            )
            val listBefore = movChaveEquipDao.listStatusForeigner(StatusForeigner.INSIDE)
            assertEquals(
                listBefore.size,
                0
            )
            val result = usecase(
                typeMov = TypeMovKey.RECEIPT,
                id = 0
            )
            assertEquals(
                result.isSuccess,
                true
            )
            val listAfter = movChaveEquipDao.listStatusForeigner(StatusForeigner.INSIDE)
            assertEquals(
                listAfter.size,
                1
            )
            val movChaveEquip = listAfter[0]
            assertEquals(
                movChaveEquip.idEquipMovChaveEquip,
                1
            )
            assertEquals(
                movChaveEquip.matricColabMovChaveEquip,
                19759
            )
        }
}