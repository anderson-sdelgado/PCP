package br.com.usinasantafe.pcp.external.retrofit.datasource.stable

import br.com.usinasantafe.pcp.domain.errors.DatasourceException
import br.com.usinasantafe.pcp.external.retrofit.api.stable.TerceiroApi
import br.com.usinasantafe.pcp.infra.datasource.retrofit.stable.TerceiroRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.stable.TerceiroRetrofitModel

class ITerceiroRetrofitDatasource(
    private val terceiroApi: TerceiroApi
): TerceiroRetrofitDatasource {

    override suspend fun recoverAll(token: String): Result<List<TerceiroRetrofitModel>> {
        try {
            val response = terceiroApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return Result.failure(
                DatasourceException(
                    function = "TerceiroRetrofitDatasourceImpl.recoverAll",
                    cause = e
                )
            )
        }
    }

}