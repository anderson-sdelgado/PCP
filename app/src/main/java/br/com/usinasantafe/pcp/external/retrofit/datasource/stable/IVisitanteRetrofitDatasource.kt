package br.com.usinasantafe.pcp.external.retrofit.datasource.stable

import br.com.usinasantafe.pcp.domain.errors.DatasourceException
import br.com.usinasantafe.pcp.external.retrofit.api.stable.VisitanteApi
import br.com.usinasantafe.pcp.infra.datasource.retrofit.stable.VisitanteRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.stable.VisitanteRetrofitModel

class IVisitanteRetrofitDatasource(
    private val visitanteApi: VisitanteApi
): VisitanteRetrofitDatasource {

    override suspend fun recoverAll(token: String): Result<List<VisitanteRetrofitModel>> {
        try {
            val response = visitanteApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return Result.failure(
                DatasourceException(
                    function = "VisitanteRetrofitDatasourceImpl.recoverAll",
                    cause = e
                )
            )
        }
    }

}