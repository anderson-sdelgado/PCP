package br.com.usinasantafe.pcp.external.sharedpreferences.datasource

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pcp.utils.BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO_EQUIP_SEG
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class IMovEquipProprioEquipSegSharedPreferencesDatasourceTest {

    private lateinit var context : Context
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var IMovEquipProprioEquipSegSharedPreferencesDatasource : IMovEquipProprioEquipSegSharedPreferencesDatasource

    @Before
    fun before() {
        context = ApplicationProvider.getApplicationContext()
        sharedPreferences = context.getSharedPreferences("teste", Context.MODE_PRIVATE)
        IMovEquipProprioEquipSegSharedPreferencesDatasource = IMovEquipProprioEquipSegSharedPreferencesDatasource(sharedPreferences)
    }

    @Test
    fun `Check return list empty if not have data`() = runTest {
        val result = IMovEquipProprioEquipSegSharedPreferencesDatasource.list()
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!.isEmpty(),
            true
        )
    }

    @Test
    fun `Check return true if save data execute successfully`() = runTest {
        val result = IMovEquipProprioEquipSegSharedPreferencesDatasource.add(1)
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
    fun `Check return list if have data in table internal`() = runTest {
        IMovEquipProprioEquipSegSharedPreferencesDatasource.add(1)
        val result = sharedPreferences.getString(BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO_EQUIP_SEG, null)
        assertEquals(
            result,
            "[1]"
        )
        val resultList = IMovEquipProprioEquipSegSharedPreferencesDatasource.list()
        val list = resultList.getOrNull()!!
        assertEquals(
            resultList.isSuccess,
            true
        )
        assertEquals(
            list.size,
            1
        )
    }

    @Test
    fun `Check return true if MovEquipProprioSegSharedPreferencesDatasource clear execute successfully`() = runTest {
        IMovEquipProprioEquipSegSharedPreferencesDatasource.add(1)
        val result = sharedPreferences.getString(BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO_EQUIP_SEG, null)
        assertEquals(
            result,
            "[1]"
        )
        val resultList = IMovEquipProprioEquipSegSharedPreferencesDatasource.list()
        assertEquals(
            resultList.isSuccess,
            true
        )
        val list = resultList.getOrNull()!!
        assertEquals(
            list.size,
            1)
        val resultClean = IMovEquipProprioEquipSegSharedPreferencesDatasource.clean()
        assertEquals(
            resultClean.isSuccess,
            true
        )
        assertEquals(
            resultClean.getOrNull()!!,
            true
        )
        val resultListClean = IMovEquipProprioEquipSegSharedPreferencesDatasource.list()
        assertEquals(
            resultListClean.isSuccess,
            true
        )
        val listClean = resultListClean.getOrNull()!!
        assertEquals(
            listClean.size,
            0
        )
    }

    @Test
    fun `Check return true if MovEquipProprioPassagSharedPreferencesDatasource delete execute successfully`() = runTest {
        IMovEquipProprioEquipSegSharedPreferencesDatasource.add(10)
        IMovEquipProprioEquipSegSharedPreferencesDatasource.add(20)
        val result = sharedPreferences.getString(
            BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO_EQUIP_SEG, null)
        assertEquals(
            result,
            "[10,20]"
        )
        val resultList = IMovEquipProprioEquipSegSharedPreferencesDatasource.list()
        assertEquals(
            resultList.isSuccess,
            true
        )
        val list = resultList.getOrNull()!!
        assertEquals(
            list.size,
            2
        )
        val resultClean = IMovEquipProprioEquipSegSharedPreferencesDatasource.delete(10)
        assertEquals(
            resultClean.isSuccess,
            true
        )
        assertEquals(
            resultClean.getOrNull()!!,
            true
        )
        val resultListClean = IMovEquipProprioEquipSegSharedPreferencesDatasource.list()
        assertEquals(
            resultListClean.isSuccess,
            true
        )
        val listClean = resultListClean.getOrNull()!!
        assertEquals(
            listClean.size,
            1
        )
        assertEquals(
            listClean[0],
            20
        )
    }

}