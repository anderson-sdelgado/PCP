package br.com.usinasantafe.pcp.external.sharedpreferences.datasource

import android.content.SharedPreferences
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.MovEquipProprioSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.MovEquipProprioSharedPreferencesModel
import br.com.usinasantafe.pcp.utils.BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO
import br.com.usinasantafe.pcp.utils.TypeMovEquip
import com.google.gson.Gson

class IMovEquipProprioSharedPreferencesDatasource(
    private val sharedPreferences: SharedPreferences
) : MovEquipProprioSharedPreferencesDatasource {

    override suspend fun clear(): Result<Boolean> {
        try {
            val editor = sharedPreferences.edit()
            editor.putString(BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO, null)
            editor.apply()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipProprioSharedPreferencesDatasource.clear",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun get(): Result<MovEquipProprioSharedPreferencesModel> {
        try {
            val movEquipProprio = sharedPreferences.getString(
                BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO, null
            )!!
            return Result.success(
                Gson().fromJson(
                    movEquipProprio,
                    MovEquipProprioSharedPreferencesModel::class.java
                )
            )
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipProprioSharedPreferencesDatasource.get",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setDestino(destino: String): Result<Boolean> {
        try {
            val resultGet = get()
            if (resultGet.isFailure)
                return Result.failure(resultGet.exceptionOrNull()!!)
            val movEquipProprio = resultGet.getOrNull()!!
            movEquipProprio.destinoMovEquipProprio = destino
            save(movEquipProprio)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipProprioSharedPreferencesDatasource.setDestino",
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
            val movEquipProprio = resultGet.getOrNull()!!
            movEquipProprio.idEquipMovEquipProprio = idEquip
            save(movEquipProprio)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipProprioSharedPreferencesDatasource.setIdEquip",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setNotaFiscal(notaFiscal: Int?): Result<Boolean> {
        try {
            val resultGet = get()
            if (resultGet.isFailure)
                return Result.failure(resultGet.exceptionOrNull()!!)
            val movEquipProprio = resultGet.getOrNull()!!
            movEquipProprio.notaFiscalMovEquipProprio = notaFiscal
            save(movEquipProprio)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipProprioSharedPreferencesDatasource.setNotaFiscal",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setMatricColab(matric: Int): Result<Boolean> {
        try {
            val resultGet = get()
            if (resultGet.isFailure)
                return Result.failure(resultGet.exceptionOrNull()!!)
            val movEquipProprio = resultGet.getOrNull()!!
            movEquipProprio.matricColabMovEquipProprio = matric
            save(movEquipProprio)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipProprioSharedPreferencesDatasource.setMatricColab",
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
            val movEquipProprio = resultGet.getOrNull()!!
            movEquipProprio.observMovEquipProprio = observ
            save(movEquipProprio)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipProprioSharedPreferencesDatasource.setObserv",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun start(typeMov: TypeMovEquip): Result<Boolean> {
        try {
            save(MovEquipProprioSharedPreferencesModel(tipoMovEquipProprio = typeMov))
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipProprioSharedPreferencesDatasource.start",
                message = "-",
                cause = e
            )
        }
    }

    fun save(movEquipProprio: MovEquipProprioSharedPreferencesModel) {
        val editor = sharedPreferences.edit()
        editor.putString(
            BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO,
            Gson().toJson(movEquipProprio)
        )
        editor.apply()
    }

}