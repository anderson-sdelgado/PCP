package br.com.usinasantafe.pcp.external.sharedpreferences.datasource

import android.content.SharedPreferences
import androidx.core.content.edit
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.MovChaveEquipSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.MovChaveEquipSharedPreferencesModel
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.sharedPreferencesModelToEntity
import br.com.usinasantafe.pcp.lib.BASE_SHARED_PREFERENCES_TABLE_MOV_CHAVE_EQUIP
import br.com.usinasantafe.pcp.utils.EmptyResult
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.result
import com.google.gson.Gson
import javax.inject.Inject
import kotlin.getOrThrow

class IMovChaveEquipSharedPreferencesDatasource @Inject constructor(
    private val sharedPreferences: SharedPreferences
): MovChaveEquipSharedPreferencesDatasource {

    suspend fun updateModel(block: MovChaveEquipSharedPreferencesModel.() -> Unit) {
        val model = get().getOrThrow()
        model.block()
        save(model).getOrThrow()
    }

    override suspend fun clean(): EmptyResult =
        result(getClassAndMethod()) {
            sharedPreferences.edit {
                putString(
                    BASE_SHARED_PREFERENCES_TABLE_MOV_CHAVE_EQUIP,
                    null
                )
            }
        }

    override suspend fun get(): Result<MovChaveEquipSharedPreferencesModel> =
        result(getClassAndMethod()) {
            val data = sharedPreferences.getString(
                BASE_SHARED_PREFERENCES_TABLE_MOV_CHAVE_EQUIP,
                null
            )
            if (data.isNullOrEmpty()) return@result MovChaveEquipSharedPreferencesModel()
            val model = Gson().fromJson(
                data,
                MovChaveEquipSharedPreferencesModel::class.java
            )
            model.sharedPreferencesModelToEntity()
            model
        }

    override suspend fun setIdEquip(idEquip: Int): EmptyResult =
        result(getClassAndMethod()) {
            updateModel {
                this.idEquipMovChaveEquip = idEquip
            }
        }

    override suspend fun setMatricColab(matricColab: Int): EmptyResult =
        result(getClassAndMethod()) {
            updateModel {
                this.matricColabMovChaveEquip = matricColab
            }
        }

    override suspend fun setObserv(observ: String?): EmptyResult =
        result(getClassAndMethod()) {
            updateModel {
                this.observMovChaveEquip = observ
            }
        }

    override suspend fun save(model: MovChaveEquipSharedPreferencesModel): EmptyResult =
        result(getClassAndMethod()) {
            sharedPreferences.edit {
                putString(
                    BASE_SHARED_PREFERENCES_TABLE_MOV_CHAVE_EQUIP,
                    Gson().toJson(model)
                )
            }
        }

}