package br.com.usinasantafe.pcp.infra.datasource.room.stable

import br.com.usinasantafe.pcp.infra.models.room.stable.RLocalFluxoRoomModel

interface RLocalFluxoRoomDatasource {
    suspend fun addAll(list: List<RLocalFluxoRoomModel>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun list(idLocal: Int): Result<List<RLocalFluxoRoomModel>>
}