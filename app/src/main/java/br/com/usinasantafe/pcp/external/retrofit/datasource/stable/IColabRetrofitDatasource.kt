package br.com.usinasantafe.pcp.external.retrofit.datasource.stable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.external.retrofit.api.stable.ColabApi
import br.com.usinasantafe.pcp.infra.datasource.retrofit.stable.ColabRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.stable.ColabRetrofitModel

class IColabRetrofitDatasource(
    private val colabApi: ColabApi
): ColabRetrofitDatasource {

    override suspend fun recoverAll(token: String): Result<List<ColabRetrofitModel>> {
        try {
            val response = colabApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailure(
                context = "IColabRetrofitDatasource.recoverAll",
                message = "-",
                cause = e
            )
        }
    }

}