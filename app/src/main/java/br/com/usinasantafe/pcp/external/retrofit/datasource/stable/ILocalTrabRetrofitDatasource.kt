package br.com.usinasantafe.pcp.external.retrofit.datasource.stable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.external.retrofit.api.stable.LocalTrabApi
import br.com.usinasantafe.pcp.infra.datasource.retrofit.stable.LocalTrabRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.stable.LocalTrabRetrofitModel
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.result
import javax.inject.Inject

class ILocalTrabRetrofitDatasource @Inject constructor(
    private val localTrabApi: LocalTrabApi
): LocalTrabRetrofitDatasource {

    override suspend fun recoverAll(token: String): Result<List<LocalTrabRetrofitModel>> =
        result(getClassAndMethod()) {
            localTrabApi.all(token).body()!!
        }

}