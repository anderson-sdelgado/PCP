package br.com.usinasantafe.pcp.external.retrofit.api.variable

import br.com.usinasantafe.pcp.infra.models.retrofit.variable.ConfigRetrofitModelInput
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.ConfigRetrofitModelOutput
import br.com.usinasantafe.pcp.utils.WEB_SAVE_TOKEN
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ConfigApi {

    @POST(WEB_SAVE_TOKEN)
    suspend fun send(@Body config: ConfigRetrofitModelOutput): Response<ConfigRetrofitModelInput>

}