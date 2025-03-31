package br.com.usinasantafe.pcp.external.sharedpreferences.datasource

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.MovChaveEquipSharedPreferencesModel
import br.com.usinasantafe.pcp.utils.TypeMovKey
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
class IMovChaveEquipSharedPreferencesDatasourceTest {

    private lateinit var context : Context
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var iMovChaveEquipSharedPreferencesDatasource : IMovChaveEquipSharedPreferencesDatasource

    @Before
    fun before() {
        context = ApplicationProvider.getApplicationContext()
        sharedPreferences = context.getSharedPreferences("teste", Context.MODE_PRIVATE)
        iMovChaveEquipSharedPreferencesDatasource = IMovChaveEquipSharedPreferencesDatasource(sharedPreferences)
    }

    @Test
    fun `get - Check return failure if not have data`() =
        runTest {
            val result = iMovChaveEquipSharedPreferencesDatasource.get()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> IMovChaveEquipSharedPreferencesDatasource.get"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun `start and get - Check return correct if function execute successfully`() =
        runTest {
            val resultStart = iMovChaveEquipSharedPreferencesDatasource.start()
            assertEquals(
                resultStart.isSuccess,
                true
            )
            val resultGet = iMovChaveEquipSharedPreferencesDatasource.get()
            assertEquals(
                resultGet.isSuccess,
                true
            )
            val model = resultGet.getOrNull()!!
            assertEquals(
                model.tipoMovChaveEquip,
                TypeMovKey.RECEIPT
            )
        }

    @Test
    fun `setIdEquip - Check return failure if have error in not started`() =
        runTest {
            val resultStart = iMovChaveEquipSharedPreferencesDatasource.setIdEquip(1)
            assertEquals(
                resultStart.isFailure,
                true
            )
            assertEquals(
                resultStart.exceptionOrNull()!!.message,
                "Failure Datasource -> IMovChaveEquipSharedPreferencesDatasource.get"
            )
            assertEquals(
                resultStart.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun `setIdEquip - Check return correct if function execute successfully`() =
        runTest {
            val resultStart = iMovChaveEquipSharedPreferencesDatasource.start()
            assertEquals(
                resultStart.isSuccess,
                true
            )
            val resultSetIdChave = iMovChaveEquipSharedPreferencesDatasource.setIdEquip(1)
            assertEquals(
                resultSetIdChave.isSuccess,
                true
            )
            val resultGet = iMovChaveEquipSharedPreferencesDatasource.get()
            assertEquals(
                resultGet.isSuccess,
                true
            )
            val model = resultGet.getOrNull()!!
            assertEquals(
                model.idEquipMovChaveEquip,
                1
            )
        }

    @Test
    fun `setMatriColab - Check return failure if have error in not started`() =
        runTest {
            val resultStart = iMovChaveEquipSharedPreferencesDatasource.setMatricColab(19759)
            assertEquals(
                resultStart.isFailure,
                true
            )
            assertEquals(
                resultStart.exceptionOrNull()!!.message,
                "Failure Datasource -> IMovChaveEquipSharedPreferencesDatasource.get"
            )
            assertEquals(
                resultStart.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun `setMatriColab - Check return correct if function execute successfully`() =
        runTest {
            val resultStart = iMovChaveEquipSharedPreferencesDatasource.start()
            assertEquals(
                resultStart.isSuccess,
                true
            )
            val resultSetIdChave = iMovChaveEquipSharedPreferencesDatasource.setMatricColab(19759)
            assertEquals(
                resultSetIdChave.isSuccess,
                true
            )
            val resultGet = iMovChaveEquipSharedPreferencesDatasource.get()
            assertEquals(
                resultGet.isSuccess,
                true
            )
            val model = resultGet.getOrNull()!!
            assertEquals(
                model.matricColabMovChaveEquip,
                19759
            )
        }

    @Test
    fun `setObserv - Check return failure if have error in not started`() =
        runTest {
            val resultStart = iMovChaveEquipSharedPreferencesDatasource.setObserv(null)
            assertEquals(
                resultStart.isFailure,
                true
            )
            assertEquals(
                resultStart.exceptionOrNull()!!.message,
                "Failure Datasource -> IMovChaveEquipSharedPreferencesDatasource.get"
            )
            assertEquals(
                resultStart.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun `setObserv - Check return correct if function execute successfully`() =
        runTest {
            val resultStart = iMovChaveEquipSharedPreferencesDatasource.start()
            assertEquals(
                resultStart.isSuccess,
                true
            )
            val resultSetIdChave = iMovChaveEquipSharedPreferencesDatasource.setObserv("TESTE")
            assertEquals(
                resultSetIdChave.isSuccess,
                true
            )
            val resultGet = iMovChaveEquipSharedPreferencesDatasource.get()
            assertEquals(
                resultGet.isSuccess,
                true
            )
            val model = resultGet.getOrNull()!!
            assertEquals(
                model.observMovChaveEquip,
                "TESTE"
            )
        }

    @Test
    fun `clear - Check return correct if function execute successfully`() =
        runTest {
            val resultStart = iMovChaveEquipSharedPreferencesDatasource.start()
            assertEquals(
                resultStart.isSuccess,
                true
            )
            val resultSetIdChave = iMovChaveEquipSharedPreferencesDatasource.setObserv("TESTE")
            assertEquals(
                resultSetIdChave.isSuccess,
                true
            )
            val resultGet = iMovChaveEquipSharedPreferencesDatasource.get()
            assertEquals(
                resultGet.isSuccess,
                true
            )
            val model = resultGet.getOrNull()!!
            assertEquals(
                model.observMovChaveEquip,
                "TESTE"
            )
            val resultClear = iMovChaveEquipSharedPreferencesDatasource.clean()
            assertEquals(
                resultClear.isSuccess,
                true
            )
            val resultGetAfterClear = iMovChaveEquipSharedPreferencesDatasource.get()
            assertEquals(
                resultGetAfterClear.isFailure,
                true
            )
        }

    @Test
    fun `start and get - Check return correct if function execute successfully - REMOVE`() =
        runTest {
            val resultStart = iMovChaveEquipSharedPreferencesDatasource.start(
                MovChaveEquipSharedPreferencesModel(
                    tipoMovChaveEquip = TypeMovKey.REMOVE
                )
            )
            assertEquals(
                resultStart.isSuccess,
                true
            )
            val resultGet = iMovChaveEquipSharedPreferencesDatasource.get()
            assertEquals(
                resultGet.isSuccess,
                true
            )
            val model = resultGet.getOrNull()!!
            assertEquals(
                model.tipoMovChaveEquip,
                TypeMovKey.REMOVE
            )
        }

}