package br.com.usinasantafe.pcp.infra.datasource.room.stable

import br.com.usinasantafe.pcp.infra.models.room.stable.VisitanteRoomModel
import br.com.usinasantafe.pcp.utils.EmptyResult

interface VisitanteRoomDatasource {
    suspend fun addAll(list: List<VisitanteRoomModel>): EmptyResult
    suspend fun checkCpf(cpf: String): Result<Boolean>
    suspend fun deleteAll(): EmptyResult
    suspend fun getById(id: Int): Result<VisitanteRoomModel>
    suspend fun getByCpf(cpf: String): Result<VisitanteRoomModel>
}