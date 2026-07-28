package br.com.usinasantafe.pcp.external.retrofit.datasource.stable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.external.retrofit.api.stable.RLocalFluxoApi
import br.com.usinasantafe.pcp.infra.datasource.retrofit.stable.RLocalFluxoRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.stable.RLocalFluxoRetrofitModel
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.result
import javax.inject.Inject

class IRLocalFluxoRetrofitDatasource @Inject constructor(
    private val rLocalFluxoApi: RLocalFluxoApi
): RLocalFluxoRetrofitDatasource {

    override suspend fun listAll(token: String): Result<List<RLocalFluxoRetrofitModel>> =
        result(getClassAndMethod()) {
            rLocalFluxoApi.all(token).body()!!
        }

}