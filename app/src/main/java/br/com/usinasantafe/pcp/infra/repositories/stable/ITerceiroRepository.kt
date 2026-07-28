package br.com.usinasantafe.pcp.infra.repositories.stable

import br.com.usinasantafe.pcp.domain.entities.stable.Terceiro
import br.com.usinasantafe.pcp.domain.repositories.stable.TerceiroRepository
import br.com.usinasantafe.pcp.infra.datasource.room.stable.TerceiroRoomDatasource
import br.com.usinasantafe.pcp.infra.datasource.retrofit.stable.TerceiroRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.pcp.infra.models.room.stable.entityToRoomModel
import br.com.usinasantafe.pcp.infra.models.room.stable.roomModelToEntity
import br.com.usinasantafe.pcp.utils.EmptyResult
import br.com.usinasantafe.pcp.utils.call
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import javax.inject.Inject

class ITerceiroRepository @Inject constructor(
    private val terceiroRoomDatasource: TerceiroRoomDatasource,
    private val terceiroRetrofitDatasource: TerceiroRetrofitDatasource
): TerceiroRepository {

    override suspend fun addAll(list: List<Terceiro>): EmptyResult =
        call(getClassAndMethod()) {
            val modelList = list.map { it.entityToRoomModel() }
            terceiroRoomDatasource.addAll(modelList).getOrThrow()
        }

    override suspend fun hasCPF(cpf: String): Result<Boolean> =
        call(getClassAndMethod()) {
            terceiroRoomDatasource.hasCpf(cpf).getOrThrow()
        }

    override suspend fun deleteAll(): EmptyResult =
        call(getClassAndMethod()) {
            terceiroRoomDatasource.deleteAll().getOrThrow()
        }

    override suspend fun getById(id: Int): Result<Terceiro> =
        call(getClassAndMethod()) {
            val model = terceiroRoomDatasource.getById(id).getOrThrow()
            model.roomModelToEntity()
        }

    override suspend fun getCpfById(id: Int): Result<String> =
        call(getClassAndMethod()) {
            terceiroRoomDatasource.getById(id).getOrThrow().cpfTerceiro
        }

    override suspend fun getIdByCpf(cpf: String): Result<Int> =
        call(getClassAndMethod()) {
            terceiroRoomDatasource.getByCpf(cpf).getOrThrow().first().idBDTerceiro
        }

    override suspend fun getNomeByCpf(cpf: String): Result<String> =
        call(getClassAndMethod()) {
            terceiroRoomDatasource.getByCpf(cpf).getOrThrow().first().nomeTerceiro
        }

    override suspend fun getEmpresasByCpf(cpf: String): Result<String> =
        call(getClassAndMethod()) {
            val modelList = terceiroRoomDatasource.getByCpf(cpf).getOrThrow()
            modelList.joinToString("\n") { it.empresaTerceiro }
        }

    override suspend fun listAll(token: String): Result<List<Terceiro>> =
        call(getClassAndMethod()) {
            val modelList = terceiroRetrofitDatasource.listAll(token).getOrThrow()
            modelList.map { it.retrofitModelToEntity() }
        }

}
