package br.com.usinasantafe.pcp.infra.repositories.stable

import br.com.usinasantafe.pcp.domain.repositories.stable.VisitanteRepository
import br.com.usinasantafe.pcp.infra.datasource.room.stable.VisitanteRoomDatasource
import br.com.usinasantafe.pcp.infra.datasource.webservice.stable.VisitanteRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.room.stable.toVisitante
import br.com.usinasantafe.pcp.infra.models.room.stable.toVisitanteModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VisitanteRepositoryImpl @Inject constructor(
    private val visitanteRoomDatasource: VisitanteRoomDatasource,
    private val visitanteRetrofitDatasource: VisitanteRetrofitDatasource,
): VisitanteRepository {

    override suspend fun addAllVisitante(list: List<br.com.usinasantafe.pcp.domain.entities.stable.Visitante>) {
        visitanteRoomDatasource.addAllVisitante(*list.map { it.toVisitanteModel() }.toTypedArray())
    }

    override suspend fun checkCPFVisitante(cpf: String): Boolean {
        return visitanteRoomDatasource.checkCPFVisitante(cpf)
    }

    override suspend fun deleteAllVisitante() {
        visitanteRoomDatasource.deleteAllVisitante()
    }

    override suspend fun getVisitanteCPF(cpf: String): br.com.usinasantafe.pcp.domain.entities.stable.Visitante {
        return visitanteRoomDatasource.getVisitanteCPF(cpf).toVisitante()
    }

    override suspend fun recoverAllVisitante(token: String): Flow<Result<List<br.com.usinasantafe.pcp.domain.entities.stable.Visitante>>> = flow {
        visitanteRetrofitDatasource.getAllVisitante(token)
            .catch { exception -> emit(Result.failure(exception)) }
            .collect { result ->
                result.fold (
                    onSuccess = { visitanteModelList ->
                        emit(Result.success(visitanteModelList.map { it.toVisitante() }))
                    },
                    onFailure = { exception -> emit(Result.failure(exception)) }
                )
            }
    }

    override suspend fun getVisitanteId(id: Long): br.com.usinasantafe.pcp.domain.entities.stable.Visitante {
        return visitanteRoomDatasource.getVisitanteId(id).toVisitante()
    }

}