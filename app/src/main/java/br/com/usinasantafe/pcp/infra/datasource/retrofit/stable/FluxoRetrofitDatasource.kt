package br.com.usinasantafe.pcp.infra.datasource.retrofit.stable

import br.com.usinasantafe.pcp.infra.models.retrofit.stable.FluxoRetrofitModel

interface FluxoRetrofitDatasource {
    suspend fun recoverAll(token: String): Result<List<FluxoRetrofitModel>>
}