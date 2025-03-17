package br.com.usinasantafe.pcp.infra.datasource.retrofit.variable

import br.com.usinasantafe.pcp.infra.models.retrofit.variable.MovChaveRetrofitModelInput
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.MovChaveRetrofitModelOutput

interface MovChaveRetrofitDatasource {

    suspend fun send(
        list: List<MovChaveRetrofitModelOutput>,
        token: String
    ): Result<List<MovChaveRetrofitModelInput>>
}