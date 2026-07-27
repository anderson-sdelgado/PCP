package br.com.usinasantafe.pcp.external.retrofit.datasource.stable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.external.retrofit.api.stable.ColabApi
import br.com.usinasantafe.pcp.infra.datasource.retrofit.stable.ColabRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.stable.ColabRetrofitModel
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.result
import javax.inject.Inject

class IColabRetrofitDatasource @Inject constructor(
    private val colabApi: ColabApi
): ColabRetrofitDatasource {

    override suspend fun recoverAll(token: String): Result<List<ColabRetrofitModel>> =
        result(getClassAndMethod()) {
            colabApi.all(token).body()!!
        }

}