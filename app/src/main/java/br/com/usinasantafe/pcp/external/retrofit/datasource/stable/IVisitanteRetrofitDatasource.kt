package br.com.usinasantafe.pcp.external.retrofit.datasource.stable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.external.retrofit.api.stable.VisitanteApi
import br.com.usinasantafe.pcp.infra.datasource.retrofit.stable.VisitanteRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.stable.VisitanteRetrofitModel
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.result
import javax.inject.Inject

class IVisitanteRetrofitDatasource @Inject constructor(
    private val visitanteApi: VisitanteApi
): VisitanteRetrofitDatasource {

    override suspend fun recoverAll(token: String): Result<List<VisitanteRetrofitModel>> =
        result(getClassAndMethod()) {
            visitanteApi.all(token).body()!!
        }

}