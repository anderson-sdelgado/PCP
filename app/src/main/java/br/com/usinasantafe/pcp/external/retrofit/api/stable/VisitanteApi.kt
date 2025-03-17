package br.com.usinasantafe.pcp.external.retrofit.api.stable

import br.com.usinasantafe.pcp.domain.entities.stable.Visitante
import br.com.usinasantafe.pcp.infra.models.retrofit.stable.VisitanteRetrofitModel
import br.com.usinasantafe.pcp.utils.WEB_ALL_VISITANTE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface VisitanteApi {

    @GET(WEB_ALL_VISITANTE)
    suspend fun all(@Header("Authorization") auth: String): Response<List<VisitanteRetrofitModel>>

}