package br.com.usinasantafe.pcp.external.sharedpreferences.datasource

import android.content.SharedPreferences
import br.com.usinasantafe.pcp.domain.entities.variable.Config
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pcp.utils.BASE_SHARE_PREFERENCES_TABLE_CONFIG
import com.google.gson.Gson

class IConfigSharedPreferencesDatasource(
    private val sharedPreferences: SharedPreferences
) : ConfigSharedPreferencesDatasource {

    override suspend fun clean(): Result<Boolean> {
        try {
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
            return Result.success(true)
        } catch (e: Exception){
            return resultFailure(
                context = "IConfigSharedPreferencesDatasource.clear",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun has(): Result<Boolean> {
        try {
            val result = sharedPreferences.getString(
                BASE_SHARE_PREFERENCES_TABLE_CONFIG,
                null
            )
            return Result.success(result != null)
        } catch (e: Exception){
            return resultFailure(
                context = "IConfigSharedPreferencesDatasource.has",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun get(): Result<Config> {
        try {
            val config = sharedPreferences.getString(
                BASE_SHARE_PREFERENCES_TABLE_CONFIG,
                null
            )
            if(config.isNullOrEmpty())
                return Result.success(Config())
            return Result.success(
                Gson().fromJson(
                    config,
                    Config::class.java
                )
            )
        } catch (e: Exception){
            return resultFailure(
                context = "IConfigSharedPreferencesDatasource.get",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun save(config: Config): Result<Boolean> {
        try {
            val editor = sharedPreferences.edit()
            editor.putString(
                BASE_SHARE_PREFERENCES_TABLE_CONFIG,
                Gson().toJson(config)
            )
            editor.apply()
            return Result.success(true)
        } catch (e: Exception){
            return resultFailure(
                context = "IConfigSharedPreferencesDatasource.save",
                message = "-",
                cause = e
            )
        }
    }
}