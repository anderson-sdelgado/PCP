package br.com.usinasantafe.pcp.domain.repositories.stable

import br.com.usinasantafe.pcp.domain.entities.stable.Local
import br.com.usinasantafe.pcp.utils.EmptyResult


interface LocalRepository {
    suspend fun addAll(list: List<Local>): EmptyResult
    suspend fun deleteAll(): EmptyResult
    suspend fun getDescrById(id: Int): Result<String>
    suspend fun listAll(): Result<List<Local>>
    suspend fun listAll(token: String): Result<List<Local>>
}