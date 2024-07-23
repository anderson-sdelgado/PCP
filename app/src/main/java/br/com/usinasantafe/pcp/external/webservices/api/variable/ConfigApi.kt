package br.com.usinasantafe.pcp.external.webservices.api.variable

import br.com.usinasantafe.pcp.utils.WEB_SAVE_TOKEN
import br.com.usinasantafe.pcp.infra.models.webservice.ConfigWebServiceModelInput
import br.com.usinasantafe.pcp.infra.models.webservice.ConfigWebServiceModelOutput
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ConfigApi {

    @POST(WEB_SAVE_TOKEN)
    suspend fun send(@Body config: ConfigWebServiceModelOutput): Response<ConfigWebServiceModelInput>

}