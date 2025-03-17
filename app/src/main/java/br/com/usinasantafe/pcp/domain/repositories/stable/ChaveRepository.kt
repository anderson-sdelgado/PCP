package br.com.usinasantafe.pcp.domain.repositories.stable

import br.com.usinasantafe.pcp.domain.entities.stable.Chave

interface ChaveRepository {
    suspend fun addAll(list: List<Chave>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun get(id: Int): Result<Chave>
    suspend fun listAll(): Result<List<Chave>>
    suspend fun recoverAll(token: String): Result<List<Chave>>
}