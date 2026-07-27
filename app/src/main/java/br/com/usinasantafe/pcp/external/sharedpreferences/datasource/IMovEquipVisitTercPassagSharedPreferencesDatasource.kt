package br.com.usinasantafe.pcp.external.sharedpreferences.datasource

import android.content.SharedPreferences
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.MovEquipVisitTercPassagSharedPreferencesDatasource
import br.com.usinasantafe.pcp.lib.BASE_SHARED_PREFERENCES_TABLE_MOV_EQUIP_VISIT_TERC_PASSAG
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class IMovEquipVisitTercPassagSharedPreferencesDatasource @Inject constructor(
    private val sharedPreferences: SharedPreferences
): MovEquipVisitTercPassagSharedPreferencesDatasource {

    private val typeToken = object : TypeToken<List<Int>>() {}.type

    override suspend fun add(idVisitTerc: Int): Result<Boolean> {
        try {
            val resultList = list()
            if(resultList.isFailure){
                val e = resultList.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipVisitTercPassagSharedPreferencesDatasource.add",
                    message = e.message,
                    cause = e.cause
                )
            }
            val list = resultList.getOrNull()!!
            var mutableList : MutableList<Int> = mutableListOf()
            if(list.isNotEmpty())
                mutableList = list.toMutableList()
            mutableList.add(idVisitTerc)
            val editor = sharedPreferences.edit()
            editor.putString(
                BASE_SHARED_PREFERENCES_TABLE_MOV_EQUIP_VISIT_TERC_PASSAG,
                Gson().toJson(mutableList, typeToken)
            )
            editor.apply()
            mutableList.clear()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipVisitTercPassagSharedPreferencesDatasource.add",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun clean(): Result<Boolean> {
        try {
            val editor = sharedPreferences.edit()
            editor.putString(BASE_SHARED_PREFERENCES_TABLE_MOV_EQUIP_VISIT_TERC_PASSAG, null)
            editor.apply()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipVisitTercPassagSharedPreferencesDatasource.clear",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun delete(idVisitTerc: Int): Result<Boolean> {
        try {
            val resultList = list()
            if(resultList.isFailure){
                val e = resultList.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipVisitTercPassagSharedPreferencesDatasource.delete",
                    message = e.message,
                    cause = e.cause
                )
            }
            val list = resultList.getOrNull()!!
            val listDelete = list.filter { it != idVisitTerc }
            val editor = sharedPreferences.edit()
            editor.putString(
                BASE_SHARED_PREFERENCES_TABLE_MOV_EQUIP_VISIT_TERC_PASSAG,
                Gson().toJson(listDelete, typeToken)
            )
            editor.apply()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipVisitTercPassagSharedPreferencesDatasource.delete",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun list(): Result<List<Int>> {
        try {
            val result = sharedPreferences.getString(
                BASE_SHARED_PREFERENCES_TABLE_MOV_EQUIP_VISIT_TERC_PASSAG,
                null
            )
            if(!result.isNullOrEmpty())
                return Result.success(
                    Gson().fromJson(result, typeToken)
                )
            return Result.success(emptyList())
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipVisitTercPassagSharedPreferencesDatasource.list",
                message = "-",
                cause = e
            )
        }
    }
}