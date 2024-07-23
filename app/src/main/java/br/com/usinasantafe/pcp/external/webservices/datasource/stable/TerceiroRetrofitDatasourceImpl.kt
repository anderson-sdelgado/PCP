package br.com.usinasantafe.pcp.external.webservices.datasource.stable

import br.com.usinasantafe.pcp.external.webservices.api.stable.TerceiroApi
import br.com.usinasantafe.pcp.infra.datasource.webservice.stable.TerceiroRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.room.stable.TerceiroRoomModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TerceiroRetrofitDatasourceImpl @Inject constructor (
    private val terceiroApi: TerceiroApi,
): TerceiroRetrofitDatasource {

    override suspend fun getAllTerceiro(token: String): Flow<Result<List<TerceiroRoomModel>>> = flow {
        val response = terceiroApi.all(token)
        if (!response.isSuccessful)
            emit(Result.failure(Throwable("Erro recebimento de dados")))
        emit(Result.success(response.body()!!))
    }

}