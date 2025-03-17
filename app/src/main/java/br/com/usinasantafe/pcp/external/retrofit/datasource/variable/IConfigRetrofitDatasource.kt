package br.com.usinasantafe.pcp.external.retrofit.datasource.variable

import br.com.usinasantafe.pcp.domain.errors.DatasourceException
import br.com.usinasantafe.pcp.external.retrofit.api.variable.ConfigApi
import br.com.usinasantafe.pcp.infra.datasource.retrofit.variable.ConfigRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.ConfigRetrofitModelInput
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.ConfigRetrofitModelOutput

class IConfigRetrofitDatasource(
    private val configApi: ConfigApi
): ConfigRetrofitDatasource {

        override suspend fun recoverToken(config: ConfigRetrofitModelOutput): Result<ConfigRetrofitModelInput> {
        try {
            val response = configApi.send(config)
            return Result.success(response.body()!!)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "ConfigRetrofitDatasourceImpl.recoverToken",
                    cause = e
                )
            )
        }
    }

}