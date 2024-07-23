package br.com.usinasantafe.pcp.external.webservices.api.stable

import br.com.usinasantafe.pcp.utils.WEB_ALL_COLAB
import br.com.usinasantafe.pcp.infra.models.room.stable.ColabRoomModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ColabApi {

    @GET(WEB_ALL_COLAB)
    suspend fun all(@Header("Authorization") auth: String): Response<List<ColabRoomModel>>

}