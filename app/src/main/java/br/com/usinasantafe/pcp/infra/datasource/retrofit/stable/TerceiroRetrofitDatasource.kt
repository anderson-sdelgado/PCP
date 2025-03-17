package br.com.usinasantafe.pcp.infra.datasource.retrofit.stable

import br.com.usinasantafe.pcp.infra.models.retrofit.stable.TerceiroRetrofitModel

interface TerceiroRetrofitDatasource {
    suspend fun recoverAll(token: String): Result<List<TerceiroRetrofitModel>>
}