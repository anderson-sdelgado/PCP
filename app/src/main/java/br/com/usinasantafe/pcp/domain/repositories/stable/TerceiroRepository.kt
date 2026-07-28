package br.com.usinasantafe.pcp.domain.repositories.stable

import br.com.usinasantafe.pcp.domain.entities.stable.Terceiro
import br.com.usinasantafe.pcp.utils.EmptyResult

interface TerceiroRepository {
    suspend fun addAll(list: List<Terceiro>): EmptyResult
    suspend fun hasCPF(cpf: String): Result<Boolean>
    suspend fun deleteAll(): EmptyResult
    suspend fun getById(id: Int): Result<Terceiro>
    suspend fun getCpfById(id: Int): Result<String>
    suspend fun getIdByCpf(cpf: String): Result<Int>
    suspend fun getNomeByCpf(cpf: String): Result<String>
    suspend fun getEmpresasByCpf(cpf: String): Result<String>
    suspend fun listAll(token: String): Result<List<Terceiro>>
}