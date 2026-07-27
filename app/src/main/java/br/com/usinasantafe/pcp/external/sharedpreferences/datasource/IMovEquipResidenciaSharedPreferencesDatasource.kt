package br.com.usinasantafe.pcp.external.sharedpreferences.datasource

import android.content.SharedPreferences
import androidx.core.content.edit
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.MovEquipResidenciaSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.MovEquipProprioSharedPreferencesModel
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.MovEquipResidenciaSharedPreferencesModel
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.sharedPreferencesModelToEntity
import br.com.usinasantafe.pcp.lib.BASE_SHARED_PREFERENCES_TABLE_MOV_EQUIP_RESIDENCIA
import br.com.usinasantafe.pcp.utils.EmptyResult
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.result
import com.google.gson.Gson
import javax.inject.Inject

class IMovEquipResidenciaSharedPreferencesDatasource @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : MovEquipResidenciaSharedPreferencesDatasource {

    suspend fun updateModel(block: MovEquipResidenciaSharedPreferencesModel.() -> Unit) {
        val model = get().getOrThrow()
        model.block()
        save(model).getOrThrow()
    }

    override suspend fun clean(): EmptyResult =
        result(getClassAndMethod()) {
            sharedPreferences.edit {
                putString(
                    BASE_SHARED_PREFERENCES_TABLE_MOV_EQUIP_RESIDENCIA,
                    null
                )
            }
        }

    override suspend fun get(): Result<MovEquipResidenciaSharedPreferencesModel> =
        result(getClassAndMethod()) {
            val data = sharedPreferences.getString(
                BASE_SHARED_PREFERENCES_TABLE_MOV_EQUIP_RESIDENCIA,
                null
            )
            if (data.isNullOrEmpty()) return@result MovEquipResidenciaSharedPreferencesModel()
            val model = Gson().fromJson(
                data,
                MovEquipResidenciaSharedPreferencesModel::class.java
            )
            model.sharedPreferencesModelToEntity()
            model
        }

    override suspend fun setMotorista(motorista: String): EmptyResult =
        result(getClassAndMethod()) {
            updateModel {
                this.motoristaMovEquipResidencia = motorista
            }
        }

    override suspend fun setObserv(observ: String?): EmptyResult =
        result(getClassAndMethod()) {
            updateModel {
                this.observMovEquipResidencia = observ
            }
        }

    override suspend fun setPlaca(placa: String): EmptyResult =
        result(getClassAndMethod()) {
            updateModel {
                this.placaMovEquipResidencia = placa
            }
        }

    override suspend fun setVeiculo(veiculo: String): EmptyResult =
        result(getClassAndMethod()) {
            updateModel {
                this.veiculoMovEquipResidencia = veiculo
            }
        }

    override suspend fun save(model: MovEquipResidenciaSharedPreferencesModel): EmptyResult =
        result(getClassAndMethod()) {
            sharedPreferences.edit {
                putString(
                    BASE_SHARED_PREFERENCES_TABLE_MOV_EQUIP_RESIDENCIA,
                    Gson().toJson(model)
                )
            }
        }

}