package br.com.usinasantafe.pcp.infra.datasource.retrofit.variable

import br.com.usinasantafe.pcp.infra.models.retrofit.variable.ConfigRetrofitModelInput
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.ConfigRetrofitModelOutput

interface ConfigRetrofitDatasource {
    suspend fun recoverToken(config: ConfigRetrofitModelOutput): Result<ConfigRetrofitModelInput>
}