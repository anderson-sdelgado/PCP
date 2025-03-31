package br.com.usinasantafe.pcp.infra.datasource.sharepreferences

import br.com.usinasantafe.pcp.infra.models.sharedpreferences.MovEquipResidenciaSharedPreferencesModel

interface MovEquipResidenciaSharedPreferencesDatasource {
    suspend fun clean(): Result<Boolean>
    suspend fun get(): Result<MovEquipResidenciaSharedPreferencesModel>
    suspend fun setMotorista(motorista: String): Result<Boolean>
    suspend fun setObserv(observ: String?): Result<Boolean>
    suspend fun setPlaca(placa: String): Result<Boolean>
    suspend fun setVeiculo(veiculo: String): Result<Boolean>
    suspend fun start(
        movEquipResidenciaSharedPreferencesModel: MovEquipResidenciaSharedPreferencesModel =
            MovEquipResidenciaSharedPreferencesModel()
    ): Result<Boolean>
}