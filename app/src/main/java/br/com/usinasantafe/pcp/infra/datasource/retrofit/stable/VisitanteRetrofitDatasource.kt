package br.com.usinasantafe.pcp.infra.datasource.retrofit.stable

import br.com.usinasantafe.pcp.infra.models.retrofit.stable.VisitanteRetrofitModel

interface VisitanteRetrofitDatasource {
    suspend fun recoverAll(token: String): Result<List<VisitanteRetrofitModel>>
}