package br.com.usinasantafe.pcp.infra.datasource.sharedpreferences

import br.com.usinasantafe.pcp.infra.models.sharedpreferences.MovEquipResidenciaSharedPreferencesModel

interface MovEquipResidenciaSharedPreferencesDatasource {

    suspend fun clearMovEquipResidencia(): Boolean

    suspend fun getMovEquipResidencia(): MovEquipResidenciaSharedPreferencesModel

    suspend fun setMotoristaMovEquipResidencia(motorista: String): Boolean

    suspend fun setObservMovEquipResidencia(observ: String?): Boolean

    suspend fun setPlacaMovEquipResidencia(placa: String): Boolean

    suspend fun setVeiculoMovEquipResidencia(veiculo: String): Boolean

    suspend fun startMovEquipResidencia(): Boolean

}