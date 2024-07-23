package br.com.usinasantafe.pcp.infra.datasource.webservice.stable

import br.com.usinasantafe.pcp.infra.models.room.stable.EquipRoomModel
import kotlinx.coroutines.flow.Flow

interface EquipRetrofitDatasource {

    suspend fun getAllEquip(token: String): Flow<Result<List<EquipRoomModel>>>

}