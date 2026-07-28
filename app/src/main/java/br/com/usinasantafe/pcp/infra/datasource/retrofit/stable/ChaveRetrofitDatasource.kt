package br.com.usinasantafe.pcp.infra.datasource.retrofit.stable

import br.com.usinasantafe.pcp.infra.models.retrofit.stable.ChaveRetrofitModel

interface ChaveRetrofitDatasource {
    suspend fun listAll(token: String): Result<List<ChaveRetrofitModel>>
}