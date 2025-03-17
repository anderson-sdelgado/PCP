package br.com.usinasantafe.pcp.external.retrofit.api.stable

import br.com.usinasantafe.pcp.domain.entities.stable.Local
import br.com.usinasantafe.pcp.infra.models.retrofit.stable.LocalRetrofitModel
import br.com.usinasantafe.pcp.utils.WEB_ALL_LOCAL
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface LocalApi {

    @GET(WEB_ALL_LOCAL)
    suspend fun all(@Header("Authorization") auth: String): Response<List<LocalRetrofitModel>>

}