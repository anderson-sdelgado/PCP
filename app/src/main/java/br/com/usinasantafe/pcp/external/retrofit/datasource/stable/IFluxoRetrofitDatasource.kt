package br.com.usinasantafe.pcp.external.retrofit.datasource.stable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.external.retrofit.api.stable.FluxoApi
import br.com.usinasantafe.pcp.infra.datasource.retrofit.stable.FluxoRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.stable.FluxoRetrofitModel
import javax.inject.Inject

class IFluxoRetrofitDatasource @Inject constructor(
    private val fluxoApi: FluxoApi
): FluxoRetrofitDatasource {

    override suspend fun recoverAll(token: String): Result<List<FluxoRetrofitModel>> {
        try {
            val response = fluxoApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception) {
            return resultFailure(
                context = "IFluxoRetrofitDatasource.recoverAll",
                message = "-",
                cause = e
            )
        }
    }

}