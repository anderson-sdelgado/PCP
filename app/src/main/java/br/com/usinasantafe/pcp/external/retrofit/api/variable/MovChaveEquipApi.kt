package br.com.usinasantafe.pcp.external.retrofit.api.variable

import br.com.usinasantafe.pcp.infra.models.retrofit.variable.MovChaveEquipRetrofitModelInput
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.MovChaveEquipRetrofitModelOutput
import br.com.usinasantafe.pcp.utils.WEB_SAVE_MOV_CHAVE_EQUIP
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface MovChaveEquipApi {

    @POST(WEB_SAVE_MOV_CHAVE_EQUIP)
    suspend fun send(
        @Header("Authorization") auth: String,
        @Body data: List<MovChaveEquipRetrofitModelOutput>
    ): Response<List<MovChaveEquipRetrofitModelInput>>

}