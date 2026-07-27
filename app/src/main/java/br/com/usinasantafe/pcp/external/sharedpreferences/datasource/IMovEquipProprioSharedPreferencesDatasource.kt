package br.com.usinasantafe.pcp.external.sharedpreferences.datasource

import android.content.SharedPreferences
import androidx.core.content.edit
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.MovEquipProprioSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.MovEquipProprioSharedPreferencesModel
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.sharedPreferencesModelToEntity
import br.com.usinasantafe.pcp.lib.BASE_SHARED_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO
import br.com.usinasantafe.pcp.lib.TypeMovEquip
import br.com.usinasantafe.pcp.utils.EmptyResult
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.result
import com.google.gson.Gson
import javax.inject.Inject
import kotlin.getOrThrow

class IMovEquipProprioSharedPreferencesDatasource @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : MovEquipProprioSharedPreferencesDatasource {

    suspend fun updateModel(block: MovEquipProprioSharedPreferencesModel.() -> Unit) {
        val model = get().getOrThrow()
        model.block()
        save(model).getOrThrow()
    }

    override suspend fun clean(): EmptyResult =
        result(getClassAndMethod()) {
            sharedPreferences.edit {
                putString(
                    BASE_SHARED_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO,
                    null
                )
            }
        }

    override suspend fun get(): Result<MovEquipProprioSharedPreferencesModel> =
        result(getClassAndMethod()) {
            val data = sharedPreferences.getString(
                BASE_SHARED_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO, null
            )
            if (data.isNullOrEmpty()) return@result MovEquipProprioSharedPreferencesModel()
            val model = Gson().fromJson(
                data,
                MovEquipProprioSharedPreferencesModel::class.java
            )
            model.sharedPreferencesModelToEntity()
            model
        }

    override suspend fun setDestino(destino: String): EmptyResult =
        result(getClassAndMethod()) {
            updateModel {
                this.destinoMovEquipProprio = destino
            }
        }

    override suspend fun setIdEquip(idEquip: Int): EmptyResult =
        result(getClassAndMethod()) {
            updateModel {
                this.idEquipMovEquipProprio = idEquip
            }
        }

    override suspend fun setNotaFiscal(notaFiscal: Int?): EmptyResult =
        result(getClassAndMethod()) {
            updateModel {
                this.notaFiscalMovEquipProprio = notaFiscal
            }
        }

    override suspend fun setMatricColab(matric: Int): EmptyResult =
        result(getClassAndMethod()) {
            updateModel {
                this.matricColabMovEquipProprio = matric
            }
        }

    override suspend fun setObserv(observ: String?): EmptyResult =
        result(getClassAndMethod()) {
            updateModel {
                this.observMovEquipProprio = observ
            }
        }

    override suspend fun start(typeMov: TypeMovEquip): EmptyResult =
        result(getClassAndMethod()) {
            updateModel {
                this.tipoMovEquipProprio = typeMov
            }
        }

    suspend fun save(model: MovEquipProprioSharedPreferencesModel): EmptyResult =
        result(getClassAndMethod()) {
            sharedPreferences.edit {
                putString(
                    BASE_SHARED_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO,
                    Gson().toJson(model)
                )
            }
        }

}