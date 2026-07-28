package br.com.usinasantafe.pcp.domain.repositories.stable

import br.com.usinasantafe.pcp.domain.entities.stable.LocalTrab
import br.com.usinasantafe.pcp.utils.EmptyResult

interface LocalTrabRepository {
    suspend fun addAll(list: List<LocalTrab>): EmptyResult
    suspend fun deleteAll(): EmptyResult
    suspend fun getDescrById(id: Int): Result<String>
    suspend fun listAll(token: String): Result<List<LocalTrab>>
}