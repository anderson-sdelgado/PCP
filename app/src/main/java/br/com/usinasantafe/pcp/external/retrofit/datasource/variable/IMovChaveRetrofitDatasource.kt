package br.com.usinasantafe.pcp.external.retrofit.datasource.variable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.external.retrofit.api.variable.MovChaveApi
import br.com.usinasantafe.pcp.infra.datasource.retrofit.variable.MovChaveRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.MovChaveRetrofitModelInput
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.MovChaveRetrofitModelOutput
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.result
import javax.inject.Inject

class IMovChaveRetrofitDatasource @Inject constructor(
    private val api: MovChaveApi
): MovChaveRetrofitDatasource {

    override suspend fun send(
        list: List<MovChaveRetrofitModelOutput>,
        token: String
    ): Result<List<MovChaveRetrofitModelInput>> =
        result(getClassAndMethod()) {
            api.send(token, list).body()!!
        }
}