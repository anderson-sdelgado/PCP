package br.com.usinasantafe.pcp.infra.repositories.stable

import br.com.usinasantafe.pcp.domain.entities.stable.Visitante
import br.com.usinasantafe.pcp.domain.repositories.stable.VisitanteRepository
import br.com.usinasantafe.pcp.infra.datasource.room.stable.VisitanteRoomDatasource
import br.com.usinasantafe.pcp.infra.datasource.retrofit.stable.VisitanteRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.pcp.infra.models.room.stable.roomModelToEntity
import br.com.usinasantafe.pcp.infra.models.room.stable.entityToRoomModel
import br.com.usinasantafe.pcp.utils.EmptyResult
import br.com.usinasantafe.pcp.utils.call
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import javax.inject.Inject

class IVisitanteRepository @Inject constructor(
    private val visitanteRoomDatasource: VisitanteRoomDatasource,
    private val visitanteRetrofitDatasource: VisitanteRetrofitDatasource
): VisitanteRepository {
    
    override suspend fun addAll(list: List<Visitante>): EmptyResult =
        call(getClassAndMethod()) {
            val modelList = list.map { it.entityToRoomModel() }
            visitanteRoomDatasource.addAll(modelList).getOrThrow()
        }

    override suspend fun hasCPF(cpf: String): Result<Boolean> =
        call(getClassAndMethod()) {
            visitanteRoomDatasource.checkCpf(cpf).getOrThrow()
        }

    override suspend fun deleteAll(): EmptyResult =
        call(getClassAndMethod()) {
            visitanteRoomDatasource.deleteAll().getOrThrow()
        }

    override suspend fun get(id: Int): Result<Visitante> =
        call(getClassAndMethod()) {
            visitanteRoomDatasource.getById(id).getOrThrow().roomModelToEntity()
        }

    override suspend fun getCpfById(id: Int): Result<String> =
        call(getClassAndMethod()) {
            visitanteRoomDatasource.getById(id).getOrThrow().cpfVisitante
        }

    override suspend fun getIdByCpf(cpf: String): Result<Int> =
        call(getClassAndMethod()) {
            visitanteRoomDatasource.getByCpf(cpf).getOrThrow().idVisitante
        }

    override suspend fun getNomeByCpf(cpf: String): Result<String> =
        call(getClassAndMethod()) {
            visitanteRoomDatasource.getByCpf(cpf).getOrThrow().nomeVisitante
        }

    override suspend fun getEmpresasByCpf(cpf: String): Result<String> =
        call(getClassAndMethod()) {
            visitanteRoomDatasource.getByCpf(cpf).getOrThrow().empresaVisitante
        }

    override suspend fun listAll(token: String): Result<List<Visitante>> =
        call(getClassAndMethod()) {
            val modelList = visitanteRetrofitDatasource.listAll(token).getOrThrow()
            modelList.map { it.retrofitModelToEntity() }
        }

}
