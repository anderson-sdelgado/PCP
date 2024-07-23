package br.com.usinasantafe.pcp.external.sharedpreferences

import android.content.SharedPreferences
import br.com.usinasantafe.pcp.utils.BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_VISIT_TERC
import br.com.usinasantafe.pcp.utils.TypeVisitTerc
import br.com.usinasantafe.pcp.infra.datasource.sharedpreferences.MovEquipVisitTercSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.MovEquipVisitTercSharedPreferencesModel
import com.google.gson.Gson
import javax.inject.Inject

class MovEquipVisitTercSharedPreferencesDatasourceImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : MovEquipVisitTercSharedPreferencesDatasource {

    override suspend fun clearMovEquipVisitTerc(): Boolean {
        try {
            val editor = sharedPreferences.edit()
            editor.putString(BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_VISIT_TERC, null)
            editor.commit()
        } catch(exception: Exception) {
            return false
        }
        return true
    }

    override suspend fun getMovEquipVisitTerc(): MovEquipVisitTercSharedPreferencesModel {
        val movEquipVisitTerc = sharedPreferences.getString(
            BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_VISIT_TERC, null)!!
        return Gson().fromJson(movEquipVisitTerc, MovEquipVisitTercSharedPreferencesModel::class.java)
    }

    override suspend fun setDestinoMovEquipVisitTerc(destino: String): Boolean {
        try {
            val movEquipVisitTerc = getMovEquipVisitTerc()
            movEquipVisitTerc.destinoMovEquipVisitTerc = destino
            saveMovEquipVisitTerc(movEquipVisitTerc)
        } catch(exception: Exception) {
            return false
        }
        return true
    }

    override suspend fun setIdVisitTercMovEquipVisitTerc(idVisitTerc: Long): Boolean {
        try {
            val movEquipVisitTerc = getMovEquipVisitTerc()
            movEquipVisitTerc.idVisitTercMovEquipVisitTerc = idVisitTerc
            saveMovEquipVisitTerc(movEquipVisitTerc)
        } catch(exception: Exception) {
            return false
        }
        return true
    }

    override suspend fun setObservMovEquipVisitTerc(observ: String?): Boolean {
        try {
            val movEquipVisitTerc = getMovEquipVisitTerc()
            movEquipVisitTerc.observMovEquipVisitTerc = observ
            saveMovEquipVisitTerc(movEquipVisitTerc)
        } catch(exception: Exception) {
            return false
        }
        return true
    }

    override suspend fun setPlacaMovEquipVisitTerc(placa: String): Boolean {
        try {
            val movEquipVisitTerc = getMovEquipVisitTerc()
            movEquipVisitTerc.placaMovEquipVisitTerc = placa
            saveMovEquipVisitTerc(movEquipVisitTerc)
        } catch(exception: Exception) {
            return false
        }
        return true
    }

    override suspend fun setTipoVisitTercMovEquipVisitTerc(typeVisitTerc: TypeVisitTerc): Boolean {
        try {
            val movEquipVisitTerc = getMovEquipVisitTerc()
            movEquipVisitTerc.tipoVisitTercMovEquipVisitTerc = typeVisitTerc
            saveMovEquipVisitTerc(movEquipVisitTerc)
        } catch(exception: Exception) {
            return false
        }
        return true
    }

    override suspend fun setVeiculoMovEquipVisitTerc(veiculo: String): Boolean {
        try {
            val movEquipVisitTerc = getMovEquipVisitTerc()
            movEquipVisitTerc.veiculoMovEquipVisitTerc = veiculo
            saveMovEquipVisitTerc(movEquipVisitTerc)
        } catch(exception: Exception) {
            return false
        }
        return true
    }

    override suspend fun startMovEquipVisitTerc(): Boolean {
        try {
            saveMovEquipVisitTerc(MovEquipVisitTercSharedPreferencesModel())
        } catch(exception: Exception) {
            return false
        }
        return true
    }

    private fun saveMovEquipVisitTerc(movEquipVisitTercSharedPreferencesModel: MovEquipVisitTercSharedPreferencesModel) {
        val editor = sharedPreferences.edit()
        editor.putString(BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_VISIT_TERC, Gson().toJson(movEquipVisitTercSharedPreferencesModel))
        editor.commit()
    }
}