package br.com.usinasantafe.pcp.external.retrofit.datasource.stable

import br.com.usinasantafe.pcp.domain.errors.DatasourceException
import br.com.usinasantafe.pcp.external.retrofit.api.stable.RLocalFluxoApi
import br.com.usinasantafe.pcp.infra.datasource.retrofit.stable.RLocalFluxoRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.stable.RLocalFluxoRetrofitModel

class IRLocalFluxoRetrofitDatasource(
    private val rLocalFluxoApi: RLocalFluxoApi
): RLocalFluxoRetrofitDatasource {

    override suspend fun recoverAll(token: String): Result<List<RLocalFluxoRetrofitModel>> {
        try {
            val response = rLocalFluxoApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "RLocalFluxoRetrofitDatasourceImpl.recoverAll",
                    cause = e
                )
            )
        }
    }

}