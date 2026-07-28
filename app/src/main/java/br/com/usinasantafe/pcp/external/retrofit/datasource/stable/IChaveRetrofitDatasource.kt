package br.com.usinasantafe.pcp.external.retrofit.datasource.stable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.external.retrofit.api.stable.ChaveApi
import br.com.usinasantafe.pcp.infra.datasource.retrofit.stable.ChaveRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.stable.ChaveRetrofitModel
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.result
import javax.inject.Inject

class IChaveRetrofitDatasource @Inject constructor(
    private val chaveApi: ChaveApi
): ChaveRetrofitDatasource {

    override suspend fun listAll(token: String): Result<List<ChaveRetrofitModel>> =
        result(getClassAndMethod()) {
            chaveApi.all(token).body()!!
        }

}