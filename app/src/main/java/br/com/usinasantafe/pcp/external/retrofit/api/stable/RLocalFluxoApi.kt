package br.com.usinasantafe.pcp.external.retrofit.api.stable

import br.com.usinasantafe.pcp.infra.models.retrofit.stable.RLocalFluxoRetrofitModel
import br.com.usinasantafe.pcp.utils.WEB_ALL_R_LOCAL_FLUXO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface RLocalFluxoApi {

    @GET(WEB_ALL_R_LOCAL_FLUXO)
    suspend fun all(@Header("Authorization") auth: String): Response<List<RLocalFluxoRetrofitModel>>

}