package br.com.usinasantafe.pcp.external.retrofit.datasource.stable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.external.retrofit.api.stable.ChaveApi
import br.com.usinasantafe.pcp.infra.datasource.retrofit.stable.ChaveRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.stable.ChaveRetrofitModel

class IChaveRetrofitDatasource(
    private val chaveApi: ChaveApi
): ChaveRetrofitDatasource {

    override suspend fun recoverAll(token: String): Result<List<ChaveRetrofitModel>> {
        try {
            val response = chaveApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailure(
                context = "IChaveRetrofitDatasource.recoverAll",
                message = "-",
                cause = e
            )
        }
    }

}