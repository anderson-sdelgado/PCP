package br.com.usinasantafe.pcp.external.webservices.datasource.stable

import br.com.usinasantafe.pcp.external.webservices.api.stable.LocalApi
import br.com.usinasantafe.pcp.infra.datasource.webservice.stable.LocalRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.room.stable.LocalRoomModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocalRetrofitDatasourceImpl @Inject constructor (
    private val localApi: LocalApi,
): LocalRetrofitDatasource {

    override suspend fun getAllLocal(token: String): Flow<Result<List<LocalRoomModel>>> = flow {
        val response = localApi.all(token)
        if (!response.isSuccessful)
            emit(Result.failure(Throwable("Erro recebimento de dados")))
        emit(Result.success(response.body()!!))
    }

}