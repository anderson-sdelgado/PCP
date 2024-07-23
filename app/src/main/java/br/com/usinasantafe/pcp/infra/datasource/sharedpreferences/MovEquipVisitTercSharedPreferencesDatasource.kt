package br.com.usinasantafe.pcp.infra.datasource.sharedpreferences

import br.com.usinasantafe.pcp.utils.TypeVisitTerc
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.MovEquipVisitTercSharedPreferencesModel

interface MovEquipVisitTercSharedPreferencesDatasource {

    suspend fun clearMovEquipVisitTerc(): Boolean

    suspend fun getMovEquipVisitTerc(): MovEquipVisitTercSharedPreferencesModel

    suspend fun setDestinoMovEquipVisitTerc(destino: String): Boolean

    suspend fun setIdVisitTercMovEquipVisitTerc(idVisitTerc: Long): Boolean

    suspend fun setObservMovEquipVisitTerc(observ: String?): Boolean

    suspend fun setPlacaMovEquipVisitTerc(placa: String): Boolean

    suspend fun setTipoVisitTercMovEquipVisitTerc(typeVisitTerc: TypeVisitTerc): Boolean

    suspend fun setVeiculoMovEquipVisitTerc(veiculo: String): Boolean

    suspend fun startMovEquipVisitTerc(): Boolean

}