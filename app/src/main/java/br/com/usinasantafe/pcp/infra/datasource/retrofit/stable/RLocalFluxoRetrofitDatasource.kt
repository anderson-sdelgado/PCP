package br.com.usinasantafe.pcp.infra.datasource.retrofit.stable

import br.com.usinasantafe.pcp.infra.models.retrofit.stable.RLocalFluxoRetrofitModel

interface RLocalFluxoRetrofitDatasource {
    suspend fun recoverAll(token: String): Result<List<RLocalFluxoRetrofitModel>>
}