package br.com.usinasantafe.pcp.external.retrofit.datasource.stable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.external.retrofit.api.stable.FluxoApi
import br.com.usinasantafe.pcp.infra.datasource.retrofit.stable.FluxoRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.stable.FluxoRetrofitModel
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.result
import javax.inject.Inject

class IFluxoRetrofitDatasource @Inject constructor(
    private val fluxoApi: FluxoApi
): FluxoRetrofitDatasource {

    override suspend fun listAll(token: String): Result<List<FluxoRetrofitModel>> =
        result(getClassAndMethod()) {
            fluxoApi.all(token).body()!!
        }

}