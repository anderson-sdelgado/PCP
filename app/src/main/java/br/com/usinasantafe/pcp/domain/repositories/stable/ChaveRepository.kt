package br.com.usinasantafe.pcp.domain.repositories.stable

import br.com.usinasantafe.pcp.domain.entities.stable.Chave
import br.com.usinasantafe.pcp.utils.EmptyResult

interface ChaveRepository {
    suspend fun addAll(list: List<Chave>): EmptyResult
    suspend fun deleteAll(): EmptyResult
    suspend fun get(id: Int): Result<Chave>
    suspend fun listAll(): Result<List<Chave>>
    suspend fun listAll(token: String): Result<List<Chave>>
}