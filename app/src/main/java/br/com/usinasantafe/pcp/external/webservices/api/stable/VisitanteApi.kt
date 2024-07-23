package br.com.usinasantafe.pcp.external.webservices.api.stable

import br.com.usinasantafe.pcp.utils.WEB_ALL_VISITANTE
import br.com.usinasantafe.pcp.infra.models.room.stable.VisitanteRoomModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface VisitanteApi {

    @GET(WEB_ALL_VISITANTE)
    suspend fun all(@Header("Authorization") auth: String): Response<List<VisitanteRoomModel>>

}