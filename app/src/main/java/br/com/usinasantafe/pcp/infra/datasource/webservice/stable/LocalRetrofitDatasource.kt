package br.com.usinasantafe.pcp.infra.datasource.webservice.stable

import br.com.usinasantafe.pcp.infra.models.room.stable.LocalRoomModel
import kotlinx.coroutines.flow.Flow

interface LocalRetrofitDatasource {

    suspend fun getAllLocal(token: String): Flow<Result<List<LocalRoomModel>>>

}