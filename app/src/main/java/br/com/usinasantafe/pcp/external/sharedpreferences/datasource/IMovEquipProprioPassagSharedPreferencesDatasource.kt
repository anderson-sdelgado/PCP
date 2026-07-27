package br.com.usinasantafe.pcp.external.sharedpreferences.datasource

import android.content.SharedPreferences
import androidx.core.content.edit
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.MovEquipProprioPassagSharedPreferencesDatasource
import br.com.usinasantafe.pcp.lib.BASE_SHARED_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO_EQUIP_SEG
import br.com.usinasantafe.pcp.lib.BASE_SHARED_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO_PASSAG
import br.com.usinasantafe.pcp.utils.EmptyResult
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class IMovEquipProprioPassagSharedPreferencesDatasource @Inject constructor(
    private val sharedPreferences: SharedPreferences
): MovEquipProprioPassagSharedPreferencesDatasource {

    private val typeToken = object : TypeToken<List<Int>>() {}.type

    suspend fun updateModel(block: List<Int>.() -> List<Int>) {
        val model = list().getOrThrow()
        val newModel = model.block()
        save(newModel).getOrThrow()
    }

    override suspend fun add(matricColab: Int): EmptyResult =
        result(getClassAndMethod()) {
            updateModel {
                this + matricColab
            }
        }

    override suspend fun clean(): EmptyResult =
        result(getClassAndMethod()) {
            sharedPreferences.edit {
                putString(
                    BASE_SHARED_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO_PASSAG,
                    null
                )
            }
        }

    override suspend fun delete(matricColab: Int): EmptyResult =
        result(getClassAndMethod()) {
            updateModel {
                this - matricColab
            }
        }
    
    override suspend fun list(): Result<List<Int>> =
        result(getClassAndMethod()) {
            val data = sharedPreferences.getString(
                BASE_SHARED_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO_PASSAG,
                null
            )
            if(data.isNullOrEmpty()) return@result emptyList()
            Gson().fromJson(data, typeToken)
        }

    suspend fun save(model: List<Int>): EmptyResult =
        result(getClassAndMethod()) {
            sharedPreferences.edit {
                putString(
                    BASE_SHARED_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO_PASSAG,
                    Gson().toJson(model)
                )
            }
        }

}