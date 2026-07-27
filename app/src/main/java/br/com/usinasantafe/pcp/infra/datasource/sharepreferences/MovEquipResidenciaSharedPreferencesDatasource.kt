package br.com.usinasantafe.pcp.infra.datasource.sharepreferences

import br.com.usinasantafe.pcp.infra.models.sharedpreferences.MovEquipResidenciaSharedPreferencesModel
import br.com.usinasantafe.pcp.utils.EmptyResult

interface MovEquipResidenciaSharedPreferencesDatasource {
    suspend fun clean(): EmptyResult
    suspend fun get(): Result<MovEquipResidenciaSharedPreferencesModel>
    suspend fun setMotorista(motorista: String): EmptyResult
    suspend fun setObserv(observ: String?): EmptyResult
    suspend fun setPlaca(placa: String): EmptyResult
    suspend fun setVeiculo(veiculo: String): EmptyResult
    suspend fun save(model: MovEquipResidenciaSharedPreferencesModel): EmptyResult
}