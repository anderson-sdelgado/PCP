package br.com.usinasantafe.pcp.domain.repositories.stable

import br.com.usinasantafe.pcp.domain.entities.stable.RLocalFluxo
import br.com.usinasantafe.pcp.utils.EmptyResult

interface RLocalFluxoRepository {
    suspend fun addAll(list: List<RLocalFluxo>): EmptyResult
    suspend fun deleteAll(): EmptyResult
    suspend fun listByIdLocal(idLocal: Int): Result<List<RLocalFluxo>>
    suspend fun listAll(token: String): Result<List<RLocalFluxo>>
}