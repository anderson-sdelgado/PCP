package br.com.usinasantafe.pcp.external.retrofit.api.stable

import br.com.usinasantafe.pcp.domain.entities.stable.Colab
import br.com.usinasantafe.pcp.infra.models.retrofit.stable.ColabRetrofitModel
import br.com.usinasantafe.pcp.utils.WEB_ALL_COLAB
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ColabApi {

    @GET(WEB_ALL_COLAB)
    suspend fun all(@Header("Authorization") auth: String): Response<List<ColabRetrofitModel>>

}