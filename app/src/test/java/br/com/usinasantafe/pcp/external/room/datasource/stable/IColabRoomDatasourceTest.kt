package br.com.usinasantafe.pcp.external.room.datasource.stable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pcp.external.room.AppDatabaseRoom
import br.com.usinasantafe.pcp.external.room.dao.stable.ColabDao
import br.com.usinasantafe.pcp.infra.models.room.stable.ColabRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class IColabRoomDatasourceTest {

    private lateinit var colabDao: ColabDao
    private lateinit var db: AppDatabaseRoom

    @Before
    fun before() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabaseRoom::class.java).allowMainThreadQueries().build()
        colabDao = db.colabDao()
    }

    @After
    fun after() {
        db.close()
    }

    @Test
    fun `Check execution correct deleteAll`() = runTest {
        val datasource = IColabRoomDatasource(colabDao)
        val result = datasource.deleteAll()
        assertEquals(
            result.isSuccess,
            true
        )
    }

    @Test
    fun `Check failure addAll if have row repeated`() = runTest {
        val datasource = IColabRoomDatasource(colabDao)
        val result = datasource.addAll(
            listOf(
                ColabRoomModel(
                    matricColab = 19759,
                    nomeColab = "ANDERSON DA SILVA DELGADO"
                ),
                ColabRoomModel(
                    matricColab = 19759,
                    nomeColab = "ANDERSON DA SILVA DELGADO"
                )
            )
        )
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "IColabRoomDatasource.addAll"
        )
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            "android.database.sqlite.SQLiteConstraintException: Cannot execute for last inserted row ID"
        )
    }

    @Test
    fun `Check success addAll if have row is correct`() = runTest {
        val datasource = IColabRoomDatasource(colabDao)
        val result = datasource.addAll(
            listOf(
                ColabRoomModel(
                    matricColab = 19759,
                    nomeColab = "ANDERSON DA SILVA DELGADO"
                ),
                ColabRoomModel(
                    matricColab = 19035,
                    nomeColab = "JOSE DONIZETE ALVES DA SILVA"
                )
            )
        )
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!,
            true
        )
    }

    @Test
    fun `Check return false if not exist Colab`() = runTest {
        val datasource = IColabRoomDatasource(colabDao)
        val result = datasource.checkMatric(19759)
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!,
            false
        )
    }

    @Test
    fun `Check return true if exist Colab`() = runTest {
        val datasource = IColabRoomDatasource(colabDao)
        datasource.addAll(
            listOf(
                ColabRoomModel(
                    matricColab = 19759,
                    nomeColab = "ANDERSON DA SILVA DELGADO"
                ),
                ColabRoomModel(
                    matricColab = 19035,
                    nomeColab = "JOSE DONIZETE ALVES DA SILVA"
                )
            )
        )
        val result = datasource.checkMatric(19759)
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!,
            true
        )
    }

    @Test
    fun `Check return failure if not have data researched`() = runTest {
        val datasource = IColabRoomDatasource(colabDao)
        val exception = try {
            datasource.getNome(19759)
            null
        } catch (exception: Exception){
            exception
        }
        assertEquals(
            exception,
            null
        )
    }

    @Test
    fun `Check return getNro if have data researched`() = runTest {
        val datasource = IColabRoomDatasource(colabDao)
        datasource.addAll(
            listOf(
                ColabRoomModel(
                    matricColab = 19759,
                    nomeColab = "ANDERSON DA SILVA DELGADO"
                ),
                ColabRoomModel(
                    matricColab = 19035,
                    nomeColab = "JOSE DONIZETE ALVES DA SILVA"
                )
            )
        )
        val result = datasource.getNome(19759)
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!,
            "ANDERSON DA SILVA DELGADO"
        )
    }
}