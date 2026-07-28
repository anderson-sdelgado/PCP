package br.com.usinasantafe.pcp.infra.datasource.room.stable

import br.com.usinasantafe.pcp.infra.models.room.stable.TerceiroRoomModel
import br.com.usinasantafe.pcp.utils.EmptyResult

interface TerceiroRoomDatasource {
    suspend fun addAll(list: List<TerceiroRoomModel>): EmptyResult
    suspend fun hasCpf(cpf: String): Result<Boolean>
    suspend fun deleteAll(): EmptyResult
    suspend fun getById(id: Int): Result<TerceiroRoomModel>
    suspend fun getByCpf(cpf: String): Result<List<TerceiroRoomModel>>
}