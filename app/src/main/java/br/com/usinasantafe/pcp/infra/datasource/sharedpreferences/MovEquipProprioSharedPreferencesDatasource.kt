package br.com.usinasantafe.pcp.infra.datasource.sharedpreferences

import br.com.usinasantafe.pcp.utils.TypeMov
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.MovEquipProprioSharedPreferencesModel

interface MovEquipProprioSharedPreferencesDatasource {

    suspend fun clearMovEquipProprio(): Boolean

    suspend fun getMovEquipProprio(): MovEquipProprioSharedPreferencesModel

    suspend fun setDestinoMovEquipProprio(destino: String): Boolean

    suspend fun setMotoristaMovEquipProprio(nroMatric: Long): Boolean

    suspend fun setNotaFiscalMovEquipProprio(notaFiscal: Long): Boolean

    suspend fun setObservMovEquipProprio(observ: String?): Boolean

    suspend fun setVeiculoMovEquipProprio(idEquip: Long): Boolean

    suspend fun startMovEquipProprio(typeMov: TypeMov): Boolean

}