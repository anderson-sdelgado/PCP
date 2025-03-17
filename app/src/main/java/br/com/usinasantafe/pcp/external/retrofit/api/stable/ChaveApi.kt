package br.com.usinasantafe.pcp.external.retrofit.api.stable

import br.com.usinasantafe.pcp.infra.models.retrofit.stable.ChaveRetrofitModel
import br.com.usinasantafe.pcp.utils.WEB_ALL_CHAVE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ChaveApi {

    @GET(WEB_ALL_CHAVE)
    suspend fun all(@Header("Authorization") auth: String): Response<List<ChaveRetrofitModel>>

}