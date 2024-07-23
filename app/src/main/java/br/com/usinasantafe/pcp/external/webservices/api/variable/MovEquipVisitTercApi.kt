package br.com.usinasantafe.pcp.external.webservices.api.variable

import br.com.usinasantafe.pcp.utils.WEB_SAVE_MOV_EQUIP_VISIT_TERC
import br.com.usinasantafe.pcp.infra.models.webservice.MovEquipVisitTercWebServiceModelInput
import br.com.usinasantafe.pcp.infra.models.webservice.MovEquipVisitTercWebServiceModelOutput
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface MovEquipVisitTercApi {

    @POST(WEB_SAVE_MOV_EQUIP_VISIT_TERC)
    suspend fun send(@Header("Authorization") auth: String, @Body data: List<MovEquipVisitTercWebServiceModelOutput>): Response<List<MovEquipVisitTercWebServiceModelInput>>

}