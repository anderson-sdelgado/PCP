package br.com.usinasantafe.pcp.external.webservices.api.variable

import br.com.usinasantafe.pcp.utils.WEB_SAVE_MOV_EQUIP_RESIDENCIA
import br.com.usinasantafe.pcp.infra.models.webservice.MovEquipResidenciaWebServiceModelInput
import br.com.usinasantafe.pcp.infra.models.webservice.MovEquipResidenciaWebServiceModelOutput
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface MovEquipResidenciaApi {

    @POST(WEB_SAVE_MOV_EQUIP_RESIDENCIA)
    suspend fun send(@Header("Authorization") auth: String, @Body data: List<MovEquipResidenciaWebServiceModelOutput>): Response<List<MovEquipResidenciaWebServiceModelInput>>

}