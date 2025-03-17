package br.com.usinasantafe.pcp.external.retrofit.datasource.stable

import br.com.usinasantafe.pcp.domain.errors.DatasourceException
import br.com.usinasantafe.pcp.external.retrofit.api.stable.LocalApi
import br.com.usinasantafe.pcp.infra.datasource.retrofit.stable.LocalRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.stable.LocalRetrofitModel

class ILocalRetrofitDatasource(
    private val localApi: LocalApi
): LocalRetrofitDatasource {

    override suspend fun recoverAll(token: String): Result<List<LocalRetrofitModel>> {
        try {
            val response = localApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return Result.failure(
                DatasourceException(
                    function = "LocalRetrofitDatasourceImpl.recoverAll",
                    cause = e
                )
            )
        }
    }

}