package br.com.usinasantafe.pcp.domain.repositories.stable

import br.com.usinasantafe.pcp.domain.entities.stable.Fluxo

interface FluxoRepository {
    suspend fun addAll(list: List<Fluxo>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun get(id: Int): Result<Fluxo>
    suspend fun recoverAll(token: String): Result<List<Fluxo>>
}