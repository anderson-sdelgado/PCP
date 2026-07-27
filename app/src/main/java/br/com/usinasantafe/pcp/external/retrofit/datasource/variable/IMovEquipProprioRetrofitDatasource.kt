package br.com.usinasantafe.pcp.external.retrofit.datasource.variable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.external.retrofit.api.variable.MovEquipProprioApi
import br.com.usinasantafe.pcp.infra.datasource.retrofit.variable.MovEquipProprioRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.MovEquipProprioRetrofitModelInput
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.MovEquipProprioRetrofitModelOutput
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.result
import javax.inject.Inject

class IMovEquipProprioRetrofitDatasource @Inject constructor(
    private val api: MovEquipProprioApi
): MovEquipProprioRetrofitDatasource {

    override suspend fun send(
        list: List<MovEquipProprioRetrofitModelOutput>,
        token: String
    ): Result<List<MovEquipProprioRetrofitModelInput>> =
        result(getClassAndMethod()) {
            api.send(token,list).body()!!
        }

}