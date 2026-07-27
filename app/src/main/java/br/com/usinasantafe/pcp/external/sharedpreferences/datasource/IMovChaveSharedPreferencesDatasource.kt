package br.com.usinasantafe.pcp.external.sharedpreferences.datasource

import android.content.SharedPreferences
import androidx.core.content.edit
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.MovChaveSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.MovChaveEquipSharedPreferencesModel
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.MovChaveSharedPreferencesModel
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.sharedPreferencesModelToEntity
import br.com.usinasantafe.pcp.lib.BASE_SHARED_PREFERENCES_TABLE_MOV_CHAVE
import br.com.usinasantafe.pcp.lib.BASE_SHARED_PREFERENCES_TABLE_MOV_CHAVE_EQUIP
import br.com.usinasantafe.pcp.utils.EmptyResult
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.result
import com.google.gson.Gson
import javax.inject.Inject
import kotlin.getOrThrow

class IMovChaveSharedPreferencesDatasource @Inject constructor(
    private val sharedPreferences: SharedPreferences
): MovChaveSharedPreferencesDatasource {

    suspend fun updateModel(block: MovChaveSharedPreferencesModel.() -> Unit) {
        val model = get().getOrThrow()
        model.block()
        save(model).getOrThrow()
    }

    override suspend fun clean(): EmptyResult =
        result(getClassAndMethod()) {
            sharedPreferences.edit {
                putString(
                    BASE_SHARED_PREFERENCES_TABLE_MOV_CHAVE,
                    null
                )
            }
        }

    override suspend fun get(): Result<MovChaveSharedPreferencesModel> =
        result(getClassAndMethod()) {
            val data = sharedPreferences.getString(
                BASE_SHARED_PREFERENCES_TABLE_MOV_CHAVE,
                null
            )
            if (data.isNullOrEmpty()) return@result MovChaveSharedPreferencesModel()
            val model = Gson().fromJson(
                data,
                MovChaveSharedPreferencesModel::class.java
            )
            model.sharedPreferencesModelToEntity()
            model
        }

    override suspend fun setIdChave(idChave: Int): EmptyResult =
        result(getClassAndMethod()) {
            updateModel {
                this.idChaveMovChave = idChave
            }
        }

    override suspend fun setMatricColab(matricColab: Int): EmptyResult =
        result(getClassAndMethod()) {
            updateModel {
                this.matricColabMovChave = matricColab
            }
        }

    override suspend fun setObserv(observ: String?): EmptyResult =
        result(getClassAndMethod()) {
            updateModel {
                this.observMovChave = observ
            }
        }

    override suspend fun save(model: MovChaveSharedPreferencesModel): EmptyResult =
        result(getClassAndMethod()) {
            sharedPreferences.edit {
                putString(
                    BASE_SHARED_PREFERENCES_TABLE_MOV_CHAVE,
                    Gson().toJson(model)
                )
            }
        }

}