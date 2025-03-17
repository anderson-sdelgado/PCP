package br.com.usinasantafe.pcp.external.retrofit.api.variable

import br.com.usinasantafe.pcp.infra.models.retrofit.variable.MovEquipResidenciaRetrofitModelInput
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.MovEquipResidenciaRetrofitModelOutput
import br.com.usinasantafe.pcp.utils.WEB_SAVE_MOV_EQUIP_RESIDENCIA
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface MovEquipResidenciaApi {

    @POST(WEB_SAVE_MOV_EQUIP_RESIDENCIA)
    suspend fun send(
        @Header("Authorization") auth: String,
        @Body data: List<MovEquipResidenciaRetrofitModelOutput>
    ): Response<List<MovEquipResidenciaRetrofitModelInput>>

}