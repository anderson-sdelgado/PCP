package br.com.usinasantafe.pcp.external.webservices.api.stable

import br.com.usinasantafe.pcp.utils.WEB_ALL_EQUIP
import br.com.usinasantafe.pcp.infra.models.room.stable.EquipRoomModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface EquipApi {

    @GET(WEB_ALL_EQUIP)
    suspend fun all(@Header("Authorization") auth: String): Response<List<EquipRoomModel>>

}