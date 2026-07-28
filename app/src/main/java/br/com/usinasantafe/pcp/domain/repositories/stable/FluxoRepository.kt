package br.com.usinasantafe.pcp.domain.repositories.stable

import br.com.usinasantafe.pcp.domain.entities.stable.Fluxo
import br.com.usinasantafe.pcp.utils.EmptyResult

interface FluxoRepository {
    suspend fun addAll(list: List<Fluxo>): EmptyResult
    suspend fun deleteAll(): EmptyResult
    suspend fun getById(id: Int): Result<Fluxo>
    suspend fun listAll(token: String): Result<List<Fluxo>>
}