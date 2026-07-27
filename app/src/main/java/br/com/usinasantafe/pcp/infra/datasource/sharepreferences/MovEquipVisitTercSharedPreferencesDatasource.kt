package br.com.usinasantafe.pcp.infra.datasource.sharepreferences

import br.com.usinasantafe.pcp.infra.models.sharedpreferences.MovEquipVisitTercSharedPreferencesModel
import br.com.usinasantafe.pcp.lib.TypeVisitTerc
import br.com.usinasantafe.pcp.utils.EmptyResult

interface MovEquipVisitTercSharedPreferencesDatasource {
    suspend fun clear(): EmptyResult
    suspend fun get(): Result<MovEquipVisitTercSharedPreferencesModel>
    suspend fun setDestino(destino: String): EmptyResult
    suspend fun setIdVisitTerc(idVisitTerc: Int): EmptyResult
    suspend fun setObserv(observ: String?):EmptyResult
    suspend fun setPlaca(placa: String): EmptyResult
    suspend fun setTipoVisitTerc(typeVisitTerc: TypeVisitTerc): EmptyResult
    suspend fun setVeiculo(veiculo: String): EmptyResult
    suspend fun save(model: MovEquipVisitTercSharedPreferencesModel): EmptyResult
}