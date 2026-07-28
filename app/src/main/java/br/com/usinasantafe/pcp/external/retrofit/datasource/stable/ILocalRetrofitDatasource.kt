package br.com.usinasantafe.pcp.external.retrofit.datasource.stable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.external.retrofit.api.stable.LocalApi
import br.com.usinasantafe.pcp.infra.datasource.retrofit.stable.LocalRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.stable.LocalRetrofitModel
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.result
import javax.inject.Inject

class ILocalRetrofitDatasource @Inject constructor(
    private val localApi: LocalApi
): LocalRetrofitDatasource {

    override suspend fun listAll(token: String): Result<List<LocalRetrofitModel>> =
        result(getClassAndMethod()) {
            localApi.all(token).body()!!
        }

}