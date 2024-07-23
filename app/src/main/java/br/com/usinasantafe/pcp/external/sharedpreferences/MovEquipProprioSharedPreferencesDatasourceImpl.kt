package br.com.usinasantafe.pcp.external.sharedpreferences

import android.content.SharedPreferences
import br.com.usinasantafe.pcp.utils.BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO
import br.com.usinasantafe.pcp.utils.TypeMov
import br.com.usinasantafe.pcp.infra.datasource.sharedpreferences.MovEquipProprioSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.MovEquipProprioSharedPreferencesModel
import com.google.gson.Gson
import javax.inject.Inject

class MovEquipProprioSharedPreferencesDatasourceImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) : MovEquipProprioSharedPreferencesDatasource {

    override suspend fun clearMovEquipProprio(): Boolean {
        try {
            val editor = sharedPreferences.edit()
            editor.putString(BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO, null)
            editor.commit()
        } catch(exception: Exception) {
            return false
        }
        return true
    }

    override suspend fun getMovEquipProprio(): MovEquipProprioSharedPreferencesModel {
        val movEquipProprio = sharedPreferences.getString(
            BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO, null)!!
        return Gson().fromJson(movEquipProprio, MovEquipProprioSharedPreferencesModel::class.java)
    }

    override suspend fun setDestinoMovEquipProprio(destino: String): Boolean {
        try {
            val movEquipProprio = getMovEquipProprio()
            movEquipProprio.destinoMovEquipProprio = destino
            saveMovEquipProprio(movEquipProprio)
        } catch(exception: Exception) {
            return false
        }
        return true
    }

    override suspend fun setMotoristaMovEquipProprio(nroMatric: Long): Boolean {
        try {
            val movEquipProprio = getMovEquipProprio()
            movEquipProprio.nroMatricColabMovEquipProprio = nroMatric
            saveMovEquipProprio(movEquipProprio)
        } catch(exception: Exception) {
            return false
        }
        return true
    }

    override suspend fun setNotaFiscalMovEquipProprio(notaFiscal: Long): Boolean {
        try {
            val movEquipProprio = getMovEquipProprio()
            movEquipProprio.nroNotaFiscalMovEquipProprio = notaFiscal
            saveMovEquipProprio(movEquipProprio)
        } catch(exception: Exception) {
            return false
        }
        return true
    }

    override suspend fun setObservMovEquipProprio(observ: String?): Boolean {
        try {
            val movEquipProprio = getMovEquipProprio()
            movEquipProprio.observMovEquipProprio = observ
            saveMovEquipProprio(movEquipProprio)
        } catch(exception: Exception) {
            return false
        }
        return true
    }

    override suspend fun setVeiculoMovEquipProprio(idEquip: Long): Boolean {
        try {
            val movEquipProprio = getMovEquipProprio()
            movEquipProprio.idEquipMovEquipProprio = idEquip
            saveMovEquipProprio(movEquipProprio)
        } catch(exception: Exception) {
            return false
        }
        return true
    }

    override suspend fun startMovEquipProprio(typeMov: TypeMov): Boolean {
        try {
            saveMovEquipProprio(MovEquipProprioSharedPreferencesModel(tipoMovEquipProprio = typeMov))
        } catch(exception: Exception) {
            return false
        }
        return true
    }

    private fun saveMovEquipProprio(movEquipProprio: MovEquipProprioSharedPreferencesModel) {
        val editor = sharedPreferences.edit()
        editor.putString(BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO, Gson().toJson(movEquipProprio))
        editor.commit()
    }
}