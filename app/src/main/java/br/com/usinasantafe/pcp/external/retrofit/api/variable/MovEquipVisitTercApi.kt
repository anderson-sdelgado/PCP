package br.com.usinasantafe.pcp.external.retrofit.api.variable

import br.com.usinasantafe.pcp.infra.models.retrofit.variable.MovEquipVisitTercRetrofitModelInput
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.MovEquipVisitTercRetrofitModelOutput
import br.com.usinasantafe.pcp.utils.WEB_SAVE_MOV_EQUIP_VISIT_TERC
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface MovEquipVisitTercApi {

    @POST(WEB_SAVE_MOV_EQUIP_VISIT_TERC)
    suspend fun send(
        @Header("Authorization") auth: String,
        @Body data: List<MovEquipVisitTercRetrofitModelOutput>
    ): Response<List<MovEquipVisitTercRetrofitModelInput>>

}