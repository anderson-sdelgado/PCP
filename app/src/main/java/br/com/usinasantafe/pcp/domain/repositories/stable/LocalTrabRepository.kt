package br.com.usinasantafe.pcp.domain.repositories.stable

import br.com.usinasantafe.pcp.domain.entities.stable.LocalTrab

interface LocalTrabRepository {
    suspend fun addAll(list: List<LocalTrab>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun getDescr(id: Int): Result<String>
    suspend fun recoverAll(token: String): Result<List<LocalTrab>>
}