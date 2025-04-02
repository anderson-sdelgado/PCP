package br.com.usinasantafe.pcp.external.sharedpreferences.datasource

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pcp.utils.BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_VISIT_TERC_PASSAG
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class IMovEquipVisitTercPassagSharedPreferencesDatasourceTest {

    private lateinit var context : Context
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var IMovEquipVisitTercPassagSharedPreferencesDatasource : IMovEquipVisitTercPassagSharedPreferencesDatasource

    @Before
    fun before() {
        context = ApplicationProvider.getApplicationContext()
        sharedPreferences = context.getSharedPreferences("teste", Context.MODE_PRIVATE)
        IMovEquipVisitTercPassagSharedPreferencesDatasource = IMovEquipVisitTercPassagSharedPreferencesDatasource(sharedPreferences)
    }

    @Test
    fun `Check return list empty if not have data`() = runTest {
        val result = IMovEquipVisitTercPassagSharedPreferencesDatasource.list()
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
        val result = IMovEquipVisitTercPassagSharedPreferencesDatasource.add(1)
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
        IMovEquipVisitTercPassagSharedPreferencesDatasource.add(1)
        val result = sharedPreferences.getString(
            BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_VISIT_TERC_PASSAG,
            null
        )
        assertEquals(
            result,
            "[1]"
        )
        val resultList = IMovEquipVisitTercPassagSharedPreferencesDatasource.list()
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
        IMovEquipVisitTercPassagSharedPreferencesDatasource.add(1)
        val result = sharedPreferences.getString(
            BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_VISIT_TERC_PASSAG,
            null
        )
        assertEquals(
            result,
            "[1]"
        )
        val resultList = IMovEquipVisitTercPassagSharedPreferencesDatasource.list()
        val list = resultList.getOrNull()!!
        assertEquals(
            resultList.isSuccess,
            true
        )
        assertEquals(
            list.size,
            1
        )
        val resultClean = IMovEquipVisitTercPassagSharedPreferencesDatasource.clean()
        assertEquals(
            resultClean.isSuccess,
            true
        )
        assertEquals(
            resultClean.getOrNull()!!,
            true
        )
        val resultListClean = IMovEquipVisitTercPassagSharedPreferencesDatasource.list()
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
        IMovEquipVisitTercPassagSharedPreferencesDatasource.add(10)
        IMovEquipVisitTercPassagSharedPreferencesDatasource.add(20)
        val result = sharedPreferences.getString(
            BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_VISIT_TERC_PASSAG,
            null
        )
        assertEquals(
            result,
            "[10,20]"
        )
        val resultList = IMovEquipVisitTercPassagSharedPreferencesDatasource.list()
        assertEquals(
            resultList.isSuccess,
            true
        )
        val list = resultList.getOrNull()!!
        assertEquals(
            list.size,
            2
        )
        val resultClean = IMovEquipVisitTercPassagSharedPreferencesDatasource.delete(10)
        assertEquals(
            resultClean.isSuccess,
            true
        )
        assertEquals(
            resultClean.getOrNull()!!,
            true
        )
        val resultListClean = IMovEquipVisitTercPassagSharedPreferencesDatasource.list()
        assertEquals(
            resultListClean.isSuccess,
            true
        )
        val listClear = resultListClean.getOrNull()!!
        assertEquals(
            listClear.size,
            1
        )
        assertEquals(
            listClear[0],
            20
        )
    }

}