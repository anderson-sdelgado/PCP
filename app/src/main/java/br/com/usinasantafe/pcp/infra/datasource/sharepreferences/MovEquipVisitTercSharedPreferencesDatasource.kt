package br.com.usinasantafe.pcp.infra.datasource.sharepreferences

import br.com.usinasantafe.pcp.infra.models.sharedpreferences.MovEquipVisitTercSharedPreferencesModel
import br.com.usinasantafe.pcp.utils.TypeVisitTerc

interface MovEquipVisitTercSharedPreferencesDatasource {
    suspend fun clear(): Result<Boolean>
    suspend fun get(): Result<MovEquipVisitTercSharedPreferencesModel>
    suspend fun setDestino(destino: String): Result<Boolean>
    suspend fun setIdVisitTerc(idVisitTerc: Int): Result<Boolean>
    suspend fun setObserv(observ: String?): Result<Boolean>
    suspend fun setPlaca(placa: String): Result<Boolean>
    suspend fun setTipoVisitTerc(typeVisitTerc: TypeVisitTerc): Result<Boolean>
    suspend fun setVeiculo(veiculo: String): Result<Boolean>
    suspend fun start(
        movEquipVisitTercSharedPreferencesModel: MovEquipVisitTercSharedPreferencesModel =
            MovEquipVisitTercSharedPreferencesModel()
    ): Result<Boolean>
}