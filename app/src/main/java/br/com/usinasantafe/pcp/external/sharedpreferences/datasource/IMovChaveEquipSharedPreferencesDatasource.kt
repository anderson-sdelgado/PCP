package br.com.usinasantafe.pcp.external.sharedpreferences.datasource

import android.content.SharedPreferences
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.MovChaveEquipSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.MovChaveEquipSharedPreferencesModel
import br.com.usinasantafe.pcp.utils.BASE_SHARE_PREFERENCES_TABLE_MOV_CHAVE_EQUIP
import com.google.gson.Gson

class IMovChaveEquipSharedPreferencesDatasource(
    private val sharedPreferences: SharedPreferences
): MovChaveEquipSharedPreferencesDatasource {

    override suspend fun clean(): Result<Boolean> {
        try {
            val editor = sharedPreferences.edit()
            editor.putString(
                BASE_SHARE_PREFERENCES_TABLE_MOV_CHAVE_EQUIP,
                null
            )
            editor.apply()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovChaveEquipSharedPreferencesDatasource.clear",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun get(): Result<MovChaveEquipSharedPreferencesModel> {
        try {
            val movChaveEquip = sharedPreferences.getString(
                BASE_SHARE_PREFERENCES_TABLE_MOV_CHAVE_EQUIP,
                null
            )!!
            return Result.success(
                Gson().fromJson(
                    movChaveEquip,
                    MovChaveEquipSharedPreferencesModel::class.java
                )
            )
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovChaveEquipSharedPreferencesDatasource.get",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setIdEquip(idEquip: Int): Result<Boolean> {
        try {
            val resultGet = get()
            if (resultGet.isFailure)
                return Result.failure(resultGet.exceptionOrNull()!!)
            val movChave = resultGet.getOrNull()!!
            movChave.idEquipMovChaveEquip = idEquip
            save(movChave)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovChaveEquipSharedPreferencesDatasource.setIdEquip",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setMatricColab(matricColab: Int): Result<Boolean> {
        try {
            val resultGet = get()
            if (resultGet.isFailure)
                return Result.failure(resultGet.exceptionOrNull()!!)
            val movChave = resultGet.getOrNull()!!
            movChave.matricColabMovChaveEquip = matricColab
            save(movChave)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovChaveEquipSharedPreferencesDatasource.setMatricColab",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setObserv(observ: String?): Result<Boolean> {
        try {
            val resultGet = get()
            if (resultGet.isFailure)
                return Result.failure(resultGet.exceptionOrNull()!!)
            val movChave = resultGet.getOrNull()!!
            movChave.observMovChaveEquip = observ
            save(movChave)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovChaveEquipSharedPreferencesDatasource.setObserv",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun start(movChaveSharedPreferencesModel: MovChaveEquipSharedPreferencesModel): Result<Boolean> {
        try {
            save(movChaveSharedPreferencesModel)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovChaveEquipSharedPreferencesDatasource.start",
                message = "-",
                cause = e
            )
        }
    }

    fun save(movChaveEquip: MovChaveEquipSharedPreferencesModel) {
        val editor = sharedPreferences.edit()
        editor.putString(
            BASE_SHARE_PREFERENCES_TABLE_MOV_CHAVE_EQUIP,
            Gson().toJson(movChaveEquip)
        )
        editor.apply()
    }

}