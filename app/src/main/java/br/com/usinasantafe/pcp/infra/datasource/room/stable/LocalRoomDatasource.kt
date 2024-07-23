package br.com.usinasantafe.pcp.infra.datasource.room.stable

import br.com.usinasantafe.pcp.infra.models.room.stable.LocalRoomModel

interface LocalRoomDatasource {

    suspend fun addAllLocal(vararg localRoomModels: LocalRoomModel)

    suspend fun deleteAllLocal()

    suspend fun listAllLocal(): List<LocalRoomModel>

    suspend fun getLocalId(id: Long): LocalRoomModel

}