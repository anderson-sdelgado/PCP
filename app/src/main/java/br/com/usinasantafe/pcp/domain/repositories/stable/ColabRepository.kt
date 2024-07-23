package br.com.usinasantafe.pcp.domain.repositories.stable

import br.com.usinasantafe.pcp.domain.entities.stable.Colab
import kotlinx.coroutines.flow.Flow

interface ColabRepository {

    suspend fun addAll(list: List<Colab>)

    suspend fun checkColabMatric(matric: Long): Boolean

    suspend fun deleteAll()

    suspend fun getColabMatric(matric: Long): Colab

    suspend fun recoverAll(token: String): Flow<Result<List<Colab>>>

}