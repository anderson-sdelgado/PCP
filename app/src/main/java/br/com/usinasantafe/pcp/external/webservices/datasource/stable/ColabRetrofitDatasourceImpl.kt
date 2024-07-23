package br.com.usinasantafe.pcp.external.webservices.datasource.stable

import br.com.usinasantafe.pcp.infra.datasource.webservice.stable.ColabRetrofitDatasource
import br.com.usinasantafe.pcp.external.webservices.api.stable.ColabApi
import br.com.usinasantafe.pcp.infra.models.room.stable.ColabRoomModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ColabRetrofitDatasourceImpl @Inject constructor (
    private val colabApi: ColabApi,
): ColabRetrofitDatasource {

    override suspend fun recoverAll(token: String): Flow<Result<List<ColabRoomModel>>> = flow {
        val response = colabApi.all(token)
        if (!response.isSuccessful)
            emit(Result.failure(Throwable("Erro recebimento de dados")))
        emit(Result.success(response.body()!!))
    }

}