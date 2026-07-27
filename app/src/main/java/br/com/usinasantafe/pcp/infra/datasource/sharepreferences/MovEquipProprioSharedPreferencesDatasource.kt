package br.com.usinasantafe.pcp.infra.datasource.sharepreferences

import br.com.usinasantafe.pcp.infra.models.sharedpreferences.MovEquipProprioSharedPreferencesModel
import br.com.usinasantafe.pcp.lib.TypeMovEquip
import br.com.usinasantafe.pcp.utils.EmptyResult

interface MovEquipProprioSharedPreferencesDatasource {
    suspend fun clean(): EmptyResult
    suspend fun get(): Result<MovEquipProprioSharedPreferencesModel>
    suspend fun setDestino(destino: String): EmptyResult
    suspend fun setIdEquip(idEquip: Int): EmptyResult
    suspend fun setNotaFiscal(notaFiscal: Int?): EmptyResult
    suspend fun setMatricColab(matric: Int): EmptyResult
    suspend fun setObserv(observ: String?): EmptyResult
    suspend fun start(typeMov: TypeMovEquip): EmptyResult
}