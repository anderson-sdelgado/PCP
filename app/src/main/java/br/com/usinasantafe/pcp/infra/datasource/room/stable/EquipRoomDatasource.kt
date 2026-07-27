package br.com.usinasantafe.pcp.infra.datasource.room.stable

import br.com.usinasantafe.pcp.infra.models.room.stable.EquipRoomModel
import br.com.usinasantafe.pcp.utils.EmptyResult

interface EquipRoomDatasource {
    suspend fun addAll(list: List<EquipRoomModel>): EmptyResult
    suspend fun checkNro(nroEquip: Long): Result<Boolean>
    suspend fun deleteAll(): EmptyResult
    suspend fun getById(idEquip: Int): Result<EquipRoomModel>
    suspend fun getIdByNro(nroEquip: Long): Result<Int>
    suspend fun getNroById(idEquip: Int): Result<Long>
}