package br.com.usinasantafe.pcp.external.sharedpreferences.datasource

import android.content.SharedPreferences
import androidx.core.content.edit
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.MovEquipVisitTercSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.MovEquipResidenciaSharedPreferencesModel
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.MovEquipVisitTercSharedPreferencesModel
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.sharedPreferencesModelToEntity
import br.com.usinasantafe.pcp.lib.BASE_SHARED_PREFERENCES_TABLE_MOV_EQUIP_VISIT_TERC
import br.com.usinasantafe.pcp.lib.TypeVisitTerc
import br.com.usinasantafe.pcp.utils.EmptyResult
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.result
import com.google.gson.Gson
import javax.inject.Inject

class IMovEquipVisitTercSharedPreferencesDatasource @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : MovEquipVisitTercSharedPreferencesDatasource {

    suspend fun updateModel(block: MovEquipVisitTercSharedPreferencesModel.() -> Unit) {
        val model = get().getOrThrow()
        model.block()
        save(model).getOrThrow()
    }

    override suspend fun clear(): EmptyResult =
        result(getClassAndMethod()) {
            sharedPreferences.edit {
                putString(
                    BASE_SHARED_PREFERENCES_TABLE_MOV_EQUIP_VISIT_TERC,
                    null
                )
            }
        }

    override suspend fun get(): Result<MovEquipVisitTercSharedPreferencesModel> =
        result(getClassAndMethod()) {
            val data = sharedPreferences.getString(
                BASE_SHARED_PREFERENCES_TABLE_MOV_EQUIP_VISIT_TERC,
                null
            )
            if (data.isNullOrEmpty()) return@result MovEquipVisitTercSharedPreferencesModel()
            val model = Gson().fromJson(
                data,
                MovEquipVisitTercSharedPreferencesModel::class.java
            )
            model.sharedPreferencesModelToEntity()
            model
        }

    override suspend fun setDestino(destino: String): EmptyResult =
        result(getClassAndMethod()) {
            updateModel {
                this.destinoMovEquipVisitTerc = destino
            }
        }

    override suspend fun setIdVisitTerc(idVisitTerc: Int): EmptyResult =
        result(getClassAndMethod()) {
            updateModel {
                this.idVisitTercMovEquipVisitTerc = idVisitTerc
            }
        }

    override suspend fun setObserv(observ: String?): EmptyResult =
        result(getClassAndMethod()) {
            updateModel {
                this.observMovEquipVisitTerc = observ
            }
        }

    override suspend fun setPlaca(placa: String): EmptyResult =
        result(getClassAndMethod()) {
            updateModel {
                this.placaMovEquipVisitTerc = placa
            }
        }

    override suspend fun setTipoVisitTerc(typeVisitTerc: TypeVisitTerc): EmptyResult =
        result(getClassAndMethod()) {
            updateModel {
                this.tipoVisitTercMovEquipVisitTerc = typeVisitTerc
            }
        }

    override suspend fun setVeiculo(veiculo: String): EmptyResult =
        result(getClassAndMethod()) {
            updateModel {
                this.veiculoMovEquipVisitTerc = veiculo
            }
        }

    override suspend fun save(model: MovEquipVisitTercSharedPreferencesModel): EmptyResult =
        result(getClassAndMethod()) {
            sharedPreferences.edit {
                putString(
                    BASE_SHARED_PREFERENCES_TABLE_MOV_EQUIP_VISIT_TERC,
                    Gson().toJson(model)
                )
            }
        }

}