package br.com.usinasantafe.pcp.external.retrofit.datasource.stable

import br.com.usinasantafe.pcp.domain.errors.DatasourceException
import br.com.usinasantafe.pcp.external.retrofit.api.stable.FluxoApi
import br.com.usinasantafe.pcp.infra.datasource.retrofit.stable.FluxoRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.stable.FluxoRetrofitModel

class IFluxoRetrofitDatasource(
    private val fluxoApi: FluxoApi
): FluxoRetrofitDatasource {

    override suspend fun recoverAll(token: String): Result<List<FluxoRetrofitModel>> {
        try {
            val response = fluxoApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "FluxoRetrofitDatasourceImpl.recoverAll",
                    cause = e
                )
            )
        }
    }

}