package br.com.usinasantafe.pcp.external.room.datasource.stable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pcp.external.room.AppDatabaseRoom
import br.com.usinasantafe.pcp.external.room.dao.stable.VisitanteDao
import br.com.usinasantafe.pcp.infra.models.room.stable.VisitanteRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class IVisitanteRoomDatasourceTest {

    private lateinit var visitanteDao: VisitanteDao
    private lateinit var db: AppDatabaseRoom

    @Before
    fun before() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabaseRoom::class.java).allowMainThreadQueries().build()
        visitanteDao = db.visitanteDao()
    }

    @After
    fun after() {
        db.close()
    }

    @Test
    fun `Check execution correct deleteAll`() = runTest {
        val datasource = IVisitanteRoomDatasource(visitanteDao)
        val result = datasource.deleteAll()
        assertEquals(
            result.isSuccess,
            true
        )
    }

    @Test
    fun `Check failure addAll if have row repeated`() = runTest {
        val datasource = IVisitanteRoomDatasource(visitanteDao)
        val result = datasource.addAll(
            listOf(
                VisitanteRoomModel(
                    idVisitante = 1,
                    cpfVisitante = "123.456.789-00",
                    nomeVisitante = "Visitante",
                    empresaVisitante = "Empresa Visitante"
                ),
                VisitanteRoomModel(
                    idVisitante = 1,
                    cpfVisitante = "123.456.789-00",
                    nomeVisitante = "Visitante",
                    empresaVisitante = "Empresa Visitante"
                )
            )
        )
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "IVisitanteRoomDatasource.addAll"
        )
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            "android.database.sqlite.SQLiteConstraintException: Cannot execute for last inserted row ID"
        )
    }

    @Test
    fun `Check success addAll if have row is correct`() = runTest {
        val datasource = IVisitanteRoomDatasource(visitanteDao)
        val result = datasource.addAll(
            listOf(
                VisitanteRoomModel(
                    idVisitante = 1,
                    cpfVisitante = "123.456.789-00",
                    nomeVisitante = "Visitante",
                    empresaVisitante = "Empresa Visitante"
                ),
                VisitanteRoomModel(
                    idVisitante = 2,
                    cpfVisitante = "123.456.789-99",
                    nomeVisitante = "Visitante 2",
                    empresaVisitante = "Empresa Visitante 2"
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
    fun `Check return false if not exist Cpf researched`() = runTest {
        val datasource = IVisitanteRoomDatasource(visitanteDao)
        val result = datasource.checkCpf("123.456.789-00")
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
    fun `Check return true if exist Cpf researched`() = runTest {
        val datasource = IVisitanteRoomDatasource(visitanteDao)
        datasource.addAll(
            listOf(
                VisitanteRoomModel(
                    idVisitante = 1,
                    cpfVisitante = "123.456.789-00",
                    nomeVisitante = "Visitante",
                    empresaVisitante = "Empresa Visitante"
                ),
                VisitanteRoomModel(
                    idVisitante = 2,
                    cpfVisitante = "123.456.789-99",
                    nomeVisitante = "Visitante 2",
                    empresaVisitante = "Empresa Visitante 2"
                )
            )
        )
        val result = datasource.checkCpf("123.456.789-00")
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
    fun `Check return roomModel if have data researched in get id`() = runTest {
        val datasource = IVisitanteRoomDatasource(visitanteDao)
        datasource.addAll(
            listOf(
                VisitanteRoomModel(
                    idVisitante = 1,
                    cpfVisitante = "123.456.789-00",
                    nomeVisitante = "Visitante",
                    empresaVisitante = "Empresa Visitante"
                ),
                VisitanteRoomModel(
                    idVisitante = 2,
                    cpfVisitante = "123.456.789-99",
                    nomeVisitante = "Visitante 2",
                    empresaVisitante = "Empresa Visitante 2"
                )
            )
        )
        val result = datasource.get(1)
        assertEquals(
            result.isSuccess,
            true
        )
        val roomModel = result.getOrNull()!!
        assertEquals(
            roomModel.idVisitante,
            1
        )
        assertEquals(
            roomModel.empresaVisitante,
            "Empresa Visitante"
        )
    }

    @Test
    fun `Check return roomModel if have data researched in get cpf`() = runTest {
        val datasource = IVisitanteRoomDatasource(visitanteDao)
        datasource.addAll(
            listOf(
                VisitanteRoomModel(
                    idVisitante = 1,
                    cpfVisitante = "123.456.789-00",
                    nomeVisitante = "Visitante",
                    empresaVisitante = "Empresa Visitante"
                ),
                VisitanteRoomModel(
                    idVisitante = 2,
                    cpfVisitante = "123.456.789-99",
                    nomeVisitante = "Visitante 2",
                    empresaVisitante = "Empresa Visitante 2"
                )
            )
        )
        val result = datasource.get("123.456.789-99")
        assertEquals(
            result.isSuccess,
            true
        )
        val roomModel = result.getOrNull()!!
        assertEquals(
            roomModel.idVisitante,
            2
        )
        assertEquals(
            roomModel.empresaVisitante,
            "Empresa Visitante 2"
        )
    }
}