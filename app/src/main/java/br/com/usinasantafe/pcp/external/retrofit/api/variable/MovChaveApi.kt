package br.com.usinasantafe.pcp.external.retrofit.api.variable

import br.com.usinasantafe.pcp.infra.models.retrofit.variable.MovChaveRetrofitModelInput
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.MovChaveRetrofitModelOutput
import br.com.usinasantafe.pcp.utils.WEB_SAVE_MOV_CHAVE
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface MovChaveApi {

    @POST(WEB_SAVE_MOV_CHAVE)
    suspend fun send(
        @Header("Authorization") auth: String,
        @Body data: List<MovChaveRetrofitModelOutput>
    ): Response<List<MovChaveRetrofitModelInput>>

}