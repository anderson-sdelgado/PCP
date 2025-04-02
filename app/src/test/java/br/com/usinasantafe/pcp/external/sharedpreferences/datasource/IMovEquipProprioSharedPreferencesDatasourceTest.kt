package br.com.usinasantafe.pcp.external.sharedpreferences.datasource

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pcp.utils.TypeMovEquip
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class IMovEquipProprioSharedPreferencesDatasourceTest {

    private lateinit var context : Context
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var iMovEquipProprioSharedPreferencesDatasource : IMovEquipProprioSharedPreferencesDatasource

    @Before
    fun before() {
        context = ApplicationProvider.getApplicationContext()
        sharedPreferences = context.getSharedPreferences("teste", Context.MODE_PRIVATE)
        iMovEquipProprioSharedPreferencesDatasource = IMovEquipProprioSharedPreferencesDatasource(sharedPreferences)
    }

    @Test
    fun `Check return data correct if MovEquipProprioSharedPreferences Start execute correctly`() = runTest {
        iMovEquipProprioSharedPreferencesDatasource.start(TypeMovEquip.INPUT)
        val result = iMovEquipProprioSharedPreferencesDatasource.get()
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!.tipoMovEquipProprio,
            TypeMovEquip.INPUT
        )
    }

    @Test
    fun `Check altered in table if MovEquipProprioSharedPreferences setMatricColab execute correctly`() = runTest {
        iMovEquipProprioSharedPreferencesDatasource.start(TypeMovEquip.INPUT)
        iMovEquipProprioSharedPreferencesDatasource.setMatricColab(19759)
        val result = iMovEquipProprioSharedPreferencesDatasource.get()
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!.tipoMovEquipProprio,
            TypeMovEquip.INPUT
        )
        assertEquals(
            result.getOrNull()!!.matricColabMovEquipProprio,
            19759
        )
    }

    @Test
    fun `Check return idEquip correct if MovEquipProprioSharedPreferences setIdEquip execute correctly`() = runTest {
        iMovEquipProprioSharedPreferencesDatasource.start(TypeMovEquip.INPUT)
        iMovEquipProprioSharedPreferencesDatasource.setIdEquip(10)
        val result = iMovEquipProprioSharedPreferencesDatasource.get()
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!.tipoMovEquipProprio,
            TypeMovEquip.INPUT
        )
        assertEquals(
            result.getOrNull()!!.idEquipMovEquipProprio,
            10
        )
    }

    @Test
    fun `Check return destino correct if MovEquipProprioSharedPreferences setDestino execute correctly`() = runTest {
        iMovEquipProprioSharedPreferencesDatasource.start(TypeMovEquip.INPUT)
        iMovEquipProprioSharedPreferencesDatasource.setDestino("Teste")
        val result = iMovEquipProprioSharedPreferencesDatasource.get()
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!.tipoMovEquipProprio,
            TypeMovEquip.INPUT
        )
        assertEquals(
            result.getOrNull()!!.destinoMovEquipProprio,
            "Teste"
        )
    }

    @Test
    fun `Check return destino correct if MovEquipProprioSharedPreferences setNotaFiscal execute correctly`() = runTest {
        iMovEquipProprioSharedPreferencesDatasource.start(TypeMovEquip.INPUT)
        iMovEquipProprioSharedPreferencesDatasource.setNotaFiscal(123456)
        val result = iMovEquipProprioSharedPreferencesDatasource.get()
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!.tipoMovEquipProprio,
            TypeMovEquip.INPUT
        )
        assertEquals(
            result.getOrNull()!!.notaFiscalMovEquipProprio,
            123456
        )
    }

    @Test
    fun `Check return destino correct if MovEquipProprioSharedPreferences setNotaFiscal execute correctly and value is null`() = runTest {
        iMovEquipProprioSharedPreferencesDatasource.start(TypeMovEquip.INPUT)
        iMovEquipProprioSharedPreferencesDatasource.setNotaFiscal(null)
        val result = iMovEquipProprioSharedPreferencesDatasource.get()
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!.tipoMovEquipProprio,
            TypeMovEquip.INPUT
        )
        assertEquals(
            result.getOrNull()!!.notaFiscalMovEquipProprio,
            null
        )
    }

    @Test
    fun `Check return observ correct if MovEquipProprioSharedPreferences setObserv execute correctly`() = runTest {
        iMovEquipProprioSharedPreferencesDatasource.start(TypeMovEquip.INPUT)
        iMovEquipProprioSharedPreferencesDatasource.setObserv("Teste")
        val result = iMovEquipProprioSharedPreferencesDatasource.get()
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!.tipoMovEquipProprio,
            TypeMovEquip.INPUT
        )
        assertEquals(
            result.getOrNull()!!.observMovEquipProprio,
            "Teste"
        )
    }
}