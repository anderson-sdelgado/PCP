package br.com.usinasantafe.pcp.external.sharedpreferences

import android.content.SharedPreferences
import br.com.usinasantafe.pcp.utils.BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO_SEG
import br.com.usinasantafe.pcp.infra.datasource.sharedpreferences.MovEquipProprioSegSharedPreferencesDatasource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class MovEquipProprioSegSharedPreferencesDatasourceImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : MovEquipProprioSegSharedPreferencesDatasource {

    override suspend fun addEquipSeg(idEquip: Long): Boolean {
        try {
            var data = listEquipSeg() as MutableList<Long>
            data.add(idEquip)
            val editor = sharedPreferences.edit()
            val typeToken = object : TypeToken<List<Long>>() {}.type
            editor.putString(BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO_SEG, Gson().toJson(data, typeToken))
            editor.commit()
            data.clear()
        } catch (e: Exception) {
            return false
        }
        return true
    }

    override suspend fun clearEquipSeg(): Boolean {
        try {
            val result = sharedPreferences.getString(
                BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO_SEG, null)
            if(!result.isNullOrEmpty()){
                val editor = sharedPreferences.edit()
                editor.putString(BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO_SEG, null)
                editor.commit()
            }
        } catch (e: Exception) {
            return false
        }
        return true
    }

    override suspend fun deleteEquipSeg(pos: Int): Boolean {
        try {
            var data = listEquipSeg() as MutableList<Long>
            data.removeAt(pos)
            val editor = sharedPreferences.edit()
            val typeToken = object : TypeToken<List<Long>>() {}.type
            editor.putString(BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO_SEG, Gson().toJson(data, typeToken))
            editor.commit()
            data.clear()
        } catch (e: Exception) {
            return false
        }
        return true
    }

    override suspend fun listEquipSeg(): List<Long> {
        var data = mutableListOf<Long>()
        val typeToken = object : TypeToken<List<Long>>() {}.type
        val result = sharedPreferences.getString(BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO_SEG, null)
        if(!result.isNullOrEmpty()){
            data = Gson().fromJson(result, typeToken)
        }
        return data
    }

}