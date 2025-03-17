package br.com.usinasantafe.pcp.external.retrofit.api.stable

import br.com.usinasantafe.pcp.infra.models.retrofit.stable.FluxoRetrofitModel
import br.com.usinasantafe.pcp.utils.WEB_ALL_FLUXO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface FluxoApi {

    @GET(WEB_ALL_FLUXO)
    suspend fun all(@Header("Authorization") auth: String): Response<List<FluxoRetrofitModel>>

}