package br.com.usinasantafe.pcp.infra.datasource.retrofit.stable

import br.com.usinasantafe.pcp.infra.models.retrofit.stable.ColabRetrofitModel

interface ColabRetrofitDatasource {
    suspend fun listAll(token: String): Result<List<ColabRetrofitModel>>
}