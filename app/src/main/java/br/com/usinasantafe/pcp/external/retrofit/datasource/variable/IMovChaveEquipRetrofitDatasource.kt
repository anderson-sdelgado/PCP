package br.com.usinasantafe.pcp.external.retrofit.datasource.variable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.external.retrofit.api.variable.MovChaveEquipApi
import br.com.usinasantafe.pcp.infra.datasource.retrofit.variable.MovChaveEquipRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.MovChaveEquipRetrofitModelInput
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.MovChaveEquipRetrofitModelOutput
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.result
import javax.inject.Inject

class IMovChaveEquipRetrofitDatasource @Inject constructor(
    private val api: MovChaveEquipApi
): MovChaveEquipRetrofitDatasource {

    override suspend fun send(
        list: List<MovChaveEquipRetrofitModelOutput>,
        token: String
    ): Result<List<MovChaveEquipRetrofitModelInput>> =
        result(getClassAndMethod()) {
            api.send(token, list).body()!!
        }
}