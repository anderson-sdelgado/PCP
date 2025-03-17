package br.com.usinasantafe.pcp.external.retrofit.datasource.variable

import br.com.usinasantafe.pcp.domain.errors.DatasourceException
import br.com.usinasantafe.pcp.external.retrofit.api.variable.MovChaveApi
import br.com.usinasantafe.pcp.infra.datasource.retrofit.variable.MovChaveRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.MovChaveRetrofitModelInput
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.MovChaveRetrofitModelOutput

class IMovChaveRetrofitDatasource(
    private val api: MovChaveApi
): MovChaveRetrofitDatasource {

    override suspend fun send(
        list: List<MovChaveRetrofitModelOutput>,
        token: String
    ): Result<List<MovChaveRetrofitModelInput>> {
        try {
            val response = api.send(
                auth = token,
                data = list
            )
            return Result.success(response.body()!!)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "IMovChaveRetrofitDatasource.send",
                    cause = e
                )
            )
        }
    }
}