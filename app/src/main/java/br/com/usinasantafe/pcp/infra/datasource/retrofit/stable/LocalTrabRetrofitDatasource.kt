package br.com.usinasantafe.pcp.infra.datasource.retrofit.stable

import br.com.usinasantafe.pcp.infra.models.retrofit.stable.LocalTrabRetrofitModel

interface LocalTrabRetrofitDatasource {
    suspend fun recoverAll(token: String): Result<List<LocalTrabRetrofitModel>>
}
