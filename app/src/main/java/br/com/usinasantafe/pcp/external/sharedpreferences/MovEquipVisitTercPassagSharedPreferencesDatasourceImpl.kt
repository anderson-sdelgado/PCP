package br.com.usinasantafe.pcp.external.sharedpreferences

import android.content.SharedPreferences
import br.com.usinasantafe.pcp.utils.BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_VISIT_TERC_PASSAG
import br.com.usinasantafe.pcp.infra.datasource.sharedpreferences.MovEquipVisitTercPassagSharedPreferencesDatasource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class MovEquipVisitTercPassagSharedPreferencesDatasourceImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : MovEquipVisitTercPassagSharedPreferencesDatasource {

    override suspend fun addPassag(idVisitTerc: Long): Boolean {
        try {
            val data = listPassag() as MutableList<Long>
            data.add(idVisitTerc)
            val editor = sharedPreferences.edit()
            val typeToken = object : TypeToken<List<Long>>() {}.type
            editor.putString(BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_VISIT_TERC_PASSAG, Gson().toJson(data, typeToken))
            editor.commit()
            data.clear()
        } catch (e: Exception) {
            return false
        }
        return true
    }

    override suspend fun clearPassag(): Boolean {
        try {
            val editor = sharedPreferences.edit()
            editor.putString(
                BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_VISIT_TERC_PASSAG, null)
            editor.commit()
        } catch(exception: Exception) {
            return false
        }
        return true
    }

    override suspend fun deletePassag(pos: Int): Boolean {
        try {
            var data = listPassag() as MutableList<Long>
            data.removeAt(pos)
            val editor = sharedPreferences.edit()
            val typeToken = object : TypeToken<List<Long>>() {}.type
            editor.putString(BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_VISIT_TERC_PASSAG, Gson().toJson(data, typeToken))
            editor.commit()
            data.clear()
        } catch (e: Exception) {
            return false
        }
        return true
    }

    override suspend fun listPassag(): List<Long> {
        var data = mutableListOf<Long>()
        val typeToken = object : TypeToken<List<Long>>() {}.type
        val result = sharedPreferences.getString(
            BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_VISIT_TERC_PASSAG, null)
        if(!result.isNullOrEmpty()){
            data = Gson().fromJson(result, typeToken)
        }
        return data
    }

}