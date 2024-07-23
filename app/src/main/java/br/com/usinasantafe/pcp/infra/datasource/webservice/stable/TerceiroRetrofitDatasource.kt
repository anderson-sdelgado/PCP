package br.com.usinasantafe.pcp.infra.datasource.webservice.stable

import br.com.usinasantafe.pcp.infra.models.room.stable.TerceiroRoomModel
import kotlinx.coroutines.flow.Flow

interface TerceiroRetrofitDatasource {

    suspend fun getAllTerceiro(token: String): Flow<Result<List<TerceiroRoomModel>>>

}