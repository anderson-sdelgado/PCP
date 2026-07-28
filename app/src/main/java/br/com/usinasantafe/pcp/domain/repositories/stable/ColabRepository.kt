package br.com.usinasantafe.pcp.domain.repositories.stable

import br.com.usinasantafe.pcp.domain.entities.stable.Colab
import br.com.usinasantafe.pcp.utils.EmptyResult

interface ColabRepository {
    suspend fun addAll(list: List<Colab>): EmptyResult
    suspend fun hasMatric(matric: Int): Result<Boolean>
    suspend fun deleteAll(): EmptyResult
    suspend fun getNomeByMatric(matric: Int): Result<String>
    suspend fun listAll(token: String): Result<List<Colab>>
}