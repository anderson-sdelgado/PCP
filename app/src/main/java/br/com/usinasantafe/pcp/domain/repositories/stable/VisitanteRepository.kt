package br.com.usinasantafe.pcp.domain.repositories.stable

import br.com.usinasantafe.pcp.domain.entities.stable.Visitante
import br.com.usinasantafe.pcp.utils.EmptyResult

interface VisitanteRepository {
    suspend fun addAll(list: List<Visitante>): EmptyResult
    suspend fun hasCPF(cpf: String): Result<Boolean>
    suspend fun deleteAll(): EmptyResult
    suspend fun get(id: Int): Result<Visitante>
    suspend fun getCpfById(id: Int): Result<String>
    suspend fun getIdByCpf(cpf: String): Result<Int>
    suspend fun getNomeByCpf(cpf: String): Result<String>
    suspend fun getEmpresasByCpf(cpf: String): Result<String>
    suspend fun listAll(token: String): Result<List<Visitante>>
}