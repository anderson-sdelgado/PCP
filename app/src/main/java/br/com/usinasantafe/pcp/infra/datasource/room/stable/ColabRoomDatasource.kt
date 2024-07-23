package br.com.usinasantafe.pcp.infra.datasource.room.stable

import br.com.usinasantafe.pcp.infra.models.room.stable.ColabRoomModel

interface ColabRoomDatasource {

    suspend fun addAllColab(vararg colabRoomModels: ColabRoomModel)

    suspend fun checkColabMatric(matric: Long): Boolean

    suspend fun deleteAllColab()

    suspend fun getColabMatric(matric: Long): ColabRoomModel

}