package br.com.usinasantafe.pcp.external.sharedpreferences.datasource

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pcp.utils.BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO_PASSAG
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class IMovEquipProprioPassagSharedPreferencesDatasourceTest {

    private lateinit var context : Context
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var IMovEquipProprioPassagSharedPreferencesDatasource : IMovEquipProprioPassagSharedPreferencesDatasource

    @Before
    fun before() {
        context = ApplicationProvider.getApplicationContext()
        sharedPreferences = context.getSharedPreferences("teste", Context.MODE_PRIVATE)
        IMovEquipProprioPassagSharedPreferencesDatasource = IMovEquipProprioPassagSharedPreferencesDatasource(sharedPreferences)
    }

    @Test
    fun `Check return list empty if not have data`() = runTest {
        val result = IMovEquipProprioPassagSharedPreferencesDatasource.list()
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
        val result = IMovEquipProprioPassagSharedPreferencesDatasource.add(1)
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
        IMovEquipProprioPassagSharedPreferencesDatasource.add(1)
        val result = sharedPreferences.getString(
            BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO_PASSAG,
            null
        )
        assertEquals(
            result,
            "[1]"
        )
        val resultList = IMovEquipProprioPassagSharedPreferencesDatasource.list()
        assertEquals(
            resultList.isSuccess,
            true
        )
        val list = resultList.getOrNull()!!
        assertEquals(
            list.size,
            1
        )
    }

    @Test
    fun `Check return true if clear execute successfully`() = runTest {
        IMovEquipProprioPassagSharedPreferencesDatasource.add(1)
        val result = sharedPreferences.getString(
            BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO_PASSAG,
            null
        )
        assertEquals(
            result,
            "[1]"
        )
        val resultList = IMovEquipProprioPassagSharedPreferencesDatasource.list()
        assertEquals(
            resultList.isSuccess,
            true
        )
        val list = resultList.getOrNull()!!
        assertEquals(
            list.size,
            1
        )
        val resultClean = IMovEquipProprioPassagSharedPreferencesDatasource.clean()
        assertEquals(
            resultClean.isSuccess,
            true
        )
        assertEquals(
            resultClean.getOrNull()!!,
            true
        )
        val resultListClean = IMovEquipProprioPassagSharedPreferencesDatasource.list()
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
    fun `Check return true if delete execute successfully`() = runTest {
        IMovEquipProprioPassagSharedPreferencesDatasource.add(19759)
        IMovEquipProprioPassagSharedPreferencesDatasource.add(19035)
        val result = sharedPreferences.getString(
            BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO_PASSAG,
            null
        )
        assertEquals(
            result,
            "[19759,19035]"
        )
        val resultList = IMovEquipProprioPassagSharedPreferencesDatasource.list()
        assertEquals(
            resultList.isSuccess,
            true
        )
        val list = resultList.getOrNull()!!
        assertEquals(
            list.size,
            2
        )
        val resultClean = IMovEquipProprioPassagSharedPreferencesDatasource.delete(19759)
        assertEquals(
            resultClean.isSuccess,
            true
        )
        assertEquals(
            resultClean.getOrNull()!!,
            true
        )
        val resultListClean = IMovEquipProprioPassagSharedPreferencesDatasource.list()
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
            19035
        )
    }

}