package br.com.usinasantafe.pcp.external.webservices.datasource.stable

import br.com.usinasantafe.pcp.external.webservices.api.stable.VisitanteApi
import br.com.usinasantafe.pcp.infra.datasource.webservice.stable.VisitanteRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.room.stable.VisitanteRoomModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VisitanteRetrofitDatasourceImpl @Inject constructor (
    private val visitanteApi: VisitanteApi,
): VisitanteRetrofitDatasource {

    override suspend fun getAllVisitante(token: String): Flow<Result<List<VisitanteRoomModel>>> = flow {
        val response = visitanteApi.all(token)
        if (!response.isSuccessful)
            emit(Result.failure(Throwable("Erro recebimento de dados")))
        emit(Result.success(response.body()!!))
    }

}