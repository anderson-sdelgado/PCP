package br.com.usinasantafe.pcp.external.sharedpreferences.datasource

import android.content.SharedPreferences
import androidx.core.content.edit
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.MovChaveSharedPreferencesModel
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.sharedPreferencesModelToEntity
import br.com.usinasantafe.pcp.lib.BASE_SHARED_PREFERENCES_TABLE_CONFIG
import br.com.usinasantafe.pcp.lib.FlagUpdate
import br.com.usinasantafe.pcp.lib.StatusSend
import br.com.usinasantafe.pcp.utils.EmptyResult
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.result
import com.google.gson.Gson
import javax.inject.Inject

class IConfigSharedPreferencesDatasource @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : ConfigSharedPreferencesDatasource {

    suspend fun updateModel(block: ConfigSharedPreferencesModel.() -> Unit) {
        val model = get().getOrThrow()
        model.block()
        save(model).getOrThrow()
    }

    override suspend fun clean(): EmptyResult =
        result(getClassAndMethod()) {
            sharedPreferences.edit {
                putString(
                    BASE_SHARED_PREFERENCES_TABLE_CONFIG,
                    null
                )
            }
        }

    override suspend fun has(): Result<Boolean> =
        result(getClassAndMethod()) {
            val data = sharedPreferences.getString(
                BASE_SHARED_PREFERENCES_TABLE_CONFIG,
                null
            )
            !data.isNullOrEmpty()
        }

    override suspend fun get(): Result<ConfigSharedPreferencesModel> =
        result(getClassAndMethod()) {
            val config = sharedPreferences.getString(
                BASE_SHARED_PREFERENCES_TABLE_CONFIG,
                null
            )
            if(config.isNullOrEmpty()) return@result ConfigSharedPreferencesModel()
            val model = Gson().fromJson(
                config,
                ConfigSharedPreferencesModel::class.java
            )
            model.sharedPreferencesModelToEntity()
            model
        }


    override suspend fun save(model: ConfigSharedPreferencesModel): EmptyResult =
        result(getClassAndMethod()) {
            sharedPreferences.edit {
                putString(
                    BASE_SHARED_PREFERENCES_TABLE_CONFIG,
                    Gson().toJson(model)
                )
            }
        }

    override suspend fun setFlagUpdate(flagUpdate: FlagUpdate): EmptyResult =
        result(getClassAndMethod()) {
            updateModel {
                this.flagUpdate = flagUpdate
            }
        }

    override suspend fun setIdLocal(idLocal: Int): EmptyResult =
        result(getClassAndMethod()) {
            updateModel {
                this.idLocal = idLocal
            }
        }

    override suspend fun setMatricVigia(matric: Int): EmptyResult =
        result(getClassAndMethod()) {
            updateModel {
                this.matricVigia = matric
            }
        }

    override suspend fun setStatusSend(statusSend: StatusSend): EmptyResult =
        result(getClassAndMethod()) {
            updateModel {
                this.statusSend = statusSend
            }
        }

}