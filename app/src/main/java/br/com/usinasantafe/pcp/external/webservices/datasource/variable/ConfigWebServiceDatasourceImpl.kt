package br.com.usinasantafe.pcp.external.webservices.datasource.variable

import br.com.usinasantafe.pcp.external.webservices.api.variable.ConfigApi
import br.com.usinasantafe.pcp.infra.datasource.webservice.variable.ConfigWebServiceDatasource
import br.com.usinasantafe.pcp.infra.models.webservice.ConfigWebServiceModelInput
import br.com.usinasantafe.pcp.infra.models.webservice.ConfigWebServiceModelOutput
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ConfigWebServiceDatasourceImpl @Inject constructor(
    private val configApi: ConfigApi
): ConfigWebServiceDatasource {

    override suspend fun recoverToken(config: ConfigWebServiceModelOutput) : Flow<Result<ConfigWebServiceModelInput>> = flow {
            try {
                val response = configApi.send(config)
                if(!response.isSuccessful)
                    emit(Result.failure(Throwable("Erro recebimento de dados")))
                emit(Result.success(response.body()!!))
            } catch (exception: Exception) {
                emit(Result.failure(exception))
            }
    }
}