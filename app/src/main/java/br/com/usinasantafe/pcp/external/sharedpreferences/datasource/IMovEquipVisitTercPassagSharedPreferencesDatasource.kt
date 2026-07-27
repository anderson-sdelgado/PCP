package br.com.usinasantafe.pcp.external.sharedpreferences.datasource

import android.content.SharedPreferences
import androidx.core.content.edit
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.MovEquipVisitTercPassagSharedPreferencesDatasource
import br.com.usinasantafe.pcp.lib.BASE_SHARED_PREFERENCES_TABLE_MOV_EQUIP_VISIT_TERC_PASSAG
import br.com.usinasantafe.pcp.utils.EmptyResult
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class IMovEquipVisitTercPassagSharedPreferencesDatasource @Inject constructor(
    private val sharedPreferences: SharedPreferences
): MovEquipVisitTercPassagSharedPreferencesDatasource {

    private val typeToken = object : TypeToken<List<Int>>() {}.type

    suspend fun updateModel(block: List<Int>.() -> List<Int>) {
        val model = list().getOrThrow()
        val newModel = model.block()
        save(newModel).getOrThrow()
    }

    override suspend fun add(idVisitTerc: Int): EmptyResult =
        result(getClassAndMethod()) {
            updateModel {
                this + idVisitTerc
            }
        }

    override suspend fun clean(): EmptyResult =
        result(getClassAndMethod()) {
            sharedPreferences.edit {
                putString(
                    BASE_SHARED_PREFERENCES_TABLE_MOV_EQUIP_VISIT_TERC_PASSAG,
                    null
                )
            }
        }

    override suspend fun delete(idVisitTerc: Int): EmptyResult =
        result(getClassAndMethod()) {
            updateModel {
                this - idVisitTerc
            }
        }

    override suspend fun list(): Result<List<Int>> =
        result(getClassAndMethod()) {
            val data = sharedPreferences.getString(
                BASE_SHARED_PREFERENCES_TABLE_MOV_EQUIP_VISIT_TERC_PASSAG,
                null
            )
            if(data.isNullOrEmpty()) return@result emptyList()
            Gson().fromJson(data, typeToken)
        }

    suspend fun save(model: List<Int>): EmptyResult =
        result(getClassAndMethod()) {
            sharedPreferences.edit {
                putString(
                    BASE_SHARED_PREFERENCES_TABLE_MOV_EQUIP_VISIT_TERC_PASSAG,
                    Gson().toJson(model)
                )
            }
        }

}