package br.com.usinasantafe.pcp.infra.datasource.webservice.stable

import br.com.usinasantafe.pcp.infra.models.room.stable.VisitanteRoomModel
import kotlinx.coroutines.flow.Flow

interface VisitanteRetrofitDatasource {

    suspend fun getAllVisitante(token: String): Flow<Result<List<VisitanteRoomModel>>>

}