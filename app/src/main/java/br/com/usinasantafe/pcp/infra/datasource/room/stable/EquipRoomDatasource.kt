package br.com.usinasantafe.pcp.infra.datasource.room.stable

import br.com.usinasantafe.pcp.infra.models.room.stable.EquipRoomModel

interface EquipRoomDatasource {

    suspend fun addAllEquip(vararg equipRoomModels: EquipRoomModel)

    suspend fun deleteAllEquip()

    suspend fun checkEquipNro(nro: Long): Boolean

    suspend fun getEquipNro(nro: Long): EquipRoomModel

    suspend fun getEquipId(id: Long): EquipRoomModel

}