package br.com.usinasantafe.pcp.external.webservices.datasource.stable

import br.com.usinasantafe.pcp.external.webservices.api.stable.EquipApi
import br.com.usinasantafe.pcp.infra.datasource.webservice.stable.EquipRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.room.stable.EquipRoomModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EquipRetrofitDatasourceImpl @Inject constructor (
    private val equipApi: EquipApi,
): EquipRetrofitDatasource {

    override suspend fun getAllEquip(token: String): Flow<Result<List<EquipRoomModel>>> = flow {
        val response = equipApi.all(token)
        if (!response.isSuccessful)
            emit(Result.failure(Throwable("Erro recebimento de dados")))
        emit(Result.success(response.body()!!))
    }

}