package br.com.usinasantafe.pcp.infra.datasource.retrofit.stable

import br.com.usinasantafe.pcp.infra.models.retrofit.stable.LocalRetrofitModel

interface LocalRetrofitDatasource {
    suspend fun recoverAll(token: String): Result<List<LocalRetrofitModel>>
}