package br.com.usinasantafe.pcp.external.sharedpreferences

import android.content.SharedPreferences
import br.com.usinasantafe.pcp.utils.BASE_SHARE_PREFERENCES_TABLE_CONFIG
import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.domain.entities.variable.Config
import br.com.usinasantafe.pcp.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import com.google.gson.Gson
import javax.inject.Inject

class ConfigSharedPreferencesDatasourceImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : ConfigSharedPreferencesDatasource {

    override suspend fun hasConfig(): Boolean {
        val result = sharedPreferences.getString(BASE_SHARE_PREFERENCES_TABLE_CONFIG, null)
        return result != null
    }

    override suspend fun getConfig(): Config {
        val config = sharedPreferences.getString(BASE_SHARE_PREFERENCES_TABLE_CONFIG, null)
        if(config.isNullOrEmpty())
            return Config()
        return Gson().fromJson(config, Config::class.java)
    }

    override suspend fun saveConfig(config: Config) {
        val editor = sharedPreferences.edit()
        editor.putString(BASE_SHARE_PREFERENCES_TABLE_CONFIG, Gson().toJson(config))
        editor.commit()
    }

    override suspend fun setStatusSend(statusSend: StatusSend) {
        val config = getConfig()
        config.statusEnvio = statusSend
        saveConfig(config)
    }

    override suspend fun clearAllDataConfig() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.commit()
    }

}