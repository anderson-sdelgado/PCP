package br.com.usinasantafe.pcp.infra.repositories.stable

import br.com.usinasantafe.pcp.domain.repositories.stable.TerceiroRepository
import br.com.usinasantafe.pcp.infra.datasource.room.stable.TerceiroRoomDatasource
import br.com.usinasantafe.pcp.infra.datasource.webservice.stable.TerceiroRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.room.stable.toTerceiro
import br.com.usinasantafe.pcp.infra.models.room.stable.toTerceiroModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TerceiroRepositoryImpl @Inject constructor(
    private val terceiroRoomDatasource: TerceiroRoomDatasource,
    private val terceiroRetrofitDatasource: TerceiroRetrofitDatasource,
): TerceiroRepository {

    override suspend fun addAllTerceiro(list: List<br.com.usinasantafe.pcp.domain.entities.stable.Terceiro>) {
        terceiroRoomDatasource.addAllTerceiro(*list.map { it.toTerceiroModel() }.toTypedArray())
    }

    override suspend fun checkCPFTerceiro(cpf: String): Boolean {
        return terceiroRoomDatasource.checkCPFTerceiro(cpf)
    }

    override suspend fun deleteAllTerceiro() {
        terceiroRoomDatasource.deleteAllTerceiro()
    }

    override suspend fun getTerceiroCPF(cpf: String): br.com.usinasantafe.pcp.domain.entities.stable.Terceiro {
        return terceiroRoomDatasource.getTerceiroCPF(cpf).toTerceiro()
    }

    override suspend fun getTerceiroListCPF(cpf: String): List<br.com.usinasantafe.pcp.domain.entities.stable.Terceiro> {
        return terceiroRoomDatasource.getTerceiroListCPF(cpf).map { it.toTerceiro() }
    }

    override suspend fun recoverAllTerceiro(token: String): Flow<Result<List<br.com.usinasantafe.pcp.domain.entities.stable.Terceiro>>> = flow {
        terceiroRetrofitDatasource.getAllTerceiro(token)
            .catch { exception -> emit(Result.failure(exception)) }
            .collect { result ->
                result.fold(
                    onSuccess = { terceiroModelList ->
                        emit(Result.success(terceiroModelList.map { it.toTerceiro() }))
                    },
                    onFailure = { exception -> emit(Result.failure(exception)) }
                )
            }
    }

    override suspend fun getTerceiroId(id: Long): br.com.usinasantafe.pcp.domain.entities.stable.Terceiro {
        return terceiroRoomDatasource.getTerceiroId(id).toTerceiro()
    }

}