package br.com.usinasantafe.pcp.external.retrofit.api.stable

import br.com.usinasantafe.pcp.infra.models.retrofit.stable.LocalTrabRetrofitModel
import br.com.usinasantafe.pcp.utils.WEB_ALL_LOCAL_TRAB
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface LocalTrabApi {

    @GET(WEB_ALL_LOCAL_TRAB)
    suspend fun all(@Header("Authorization") auth: String): Response<List<LocalTrabRetrofitModel>>

}