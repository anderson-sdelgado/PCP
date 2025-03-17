package br.com.usinasantafe.pcp.external.retrofit.datasource.stable

import br.com.usinasantafe.pcp.domain.errors.DatasourceException
import br.com.usinasantafe.pcp.external.retrofit.api.stable.LocalTrabApi
import br.com.usinasantafe.pcp.infra.datasource.retrofit.stable.LocalTrabRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.stable.LocalTrabRetrofitModel

class ILocalTrabRetrofitDatasource(
    private val localTrabApi: LocalTrabApi
): LocalTrabRetrofitDatasource {

    override suspend fun recoverAll(token: String): Result<List<LocalTrabRetrofitModel>> {
        try {
            val response = localTrabApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return Result.failure(
                DatasourceException(
                    function = "ILocalTrabRetrofitDatasource.recoverAll",
                    cause = e
                )
            )
        }
    }

}