package br.com.usinasantafe.pcp.infra.datasource.retrofit.stable

import br.com.usinasantafe.pcp.infra.models.retrofit.stable.ColabRetrofitModel

interface ColabRetrofitDatasource {
    suspend fun recoverAll(token: String): Result<List<ColabRetrofitModel>>
}