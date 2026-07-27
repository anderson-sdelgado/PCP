package br.com.usinasantafe.pcp.external.retrofit.datasource.variable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.external.retrofit.api.variable.MovEquipVisitTercApi
import br.com.usinasantafe.pcp.infra.datasource.retrofit.variable.MovEquipVisitTercRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.MovEquipVisitTercRetrofitModelInput
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.MovEquipVisitTercRetrofitModelOutput
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.result
import javax.inject.Inject

class IMovEquipVisitTercRetrofitDatasource @Inject constructor(
    private val api: MovEquipVisitTercApi
): MovEquipVisitTercRetrofitDatasource {

    override suspend fun send(
        list: List<MovEquipVisitTercRetrofitModelOutput>,
        token: String
    ): Result<List<MovEquipVisitTercRetrofitModelInput>> =
        result(getClassAndMethod()) {
            api.send(token, list).body()!!
        }

}