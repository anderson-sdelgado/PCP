package br.com.usinasantafe.pcp.external.webservices.api.variable

import br.com.usinasantafe.pcp.utils.WEB_SAVE_MOV_EQUIP_PROPRIO
import br.com.usinasantafe.pcp.infra.models.webservice.MovEquipProprioWebServiceModelInput
import br.com.usinasantafe.pcp.infra.models.webservice.MovEquipProprioWebServiceModelOutput
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface MovEquipProprioApi {

    @POST(WEB_SAVE_MOV_EQUIP_PROPRIO)
    suspend fun send(@Header("Authorization") auth: String, @Body data: List<MovEquipProprioWebServiceModelOutput>): Response<List<MovEquipProprioWebServiceModelInput>>

}