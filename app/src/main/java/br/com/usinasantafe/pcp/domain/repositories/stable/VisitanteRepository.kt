package br.com.usinasantafe.pcp.domain.repositories.stable

import br.com.usinasantafe.pcp.domain.entities.stable.Visitante
import kotlinx.coroutines.flow.Flow

interface VisitanteRepository {

    suspend fun addAllVisitante(list: List<Visitante>)

    suspend fun checkCPFVisitante(cpf: String): Boolean

    suspend fun deleteAllVisitante()

    suspend fun getVisitanteCPF(cpf: String): Visitante

    suspend fun getVisitanteId(id: Long): Visitante

    suspend fun recoverAllVisitante(token: String): Flow<Result<List<Visitante>>>

}