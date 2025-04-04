package br.com.usinasantafe.pcp.infra.datasource.room.stable

import br.com.usinasantafe.pcp.infra.models.room.stable.EquipRoomModel

interface EquipRoomDatasource {
    suspend fun addAll(list: List<EquipRoomModel>): Result<Boolean>
    suspend fun checkNro(nroEquip: Long): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun get(idEquip: Int): Result<EquipRoomModel>
    suspend fun getId(nroEquip: Long): Result<Int>
    suspend fun getNro(idEquip: Int): Result<Long>
}