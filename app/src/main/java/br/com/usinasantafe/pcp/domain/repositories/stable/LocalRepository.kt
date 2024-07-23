package br.com.usinasantafe.pcp.domain.repositories.stable

import br.com.usinasantafe.pcp.domain.entities.stable.Local
import kotlinx.coroutines.flow.Flow

interface LocalRepository {

    suspend fun addAllLocal(list: List<Local>)

    suspend fun deleteAllLocal()

    suspend fun getLocalId(id: Long): Local

    suspend fun listAllLocal(): List<Local>

    suspend fun recoverAllLocal(token: String): Flow<Result<List<Local>>>

}