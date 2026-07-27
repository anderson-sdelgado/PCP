package br.com.usinasantafe.pcp.external.retrofit.datasource.stable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.external.retrofit.api.stable.TerceiroApi
import br.com.usinasantafe.pcp.infra.datasource.retrofit.stable.TerceiroRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.stable.TerceiroRetrofitModel
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.result
import javax.inject.Inject

class ITerceiroRetrofitDatasource @Inject constructor(
    private val terceiroApi: TerceiroApi
): TerceiroRetrofitDatasource {

    override suspend fun recoverAll(token: String): Result<List<TerceiroRetrofitModel>> =
        result(getClassAndMethod()) {
            terceiroApi.all(token).body()!!
        }

}