package br.com.usinasantafe.pcp.external.retrofit.datasource.variable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.external.retrofit.api.variable.MovEquipResidenciaApi
import br.com.usinasantafe.pcp.infra.datasource.retrofit.variable.MovEquipResidenciaRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.MovEquipResidenciaRetrofitModelInput
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.MovEquipResidenciaRetrofitModelOutput
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.result
import javax.inject.Inject

class IMovEquipResidenciaRetrofitDatasource @Inject constructor(
    private val api: MovEquipResidenciaApi
) : MovEquipResidenciaRetrofitDatasource {

    override suspend fun send(
        list: List<MovEquipResidenciaRetrofitModelOutput>,
        token: String
    ): Result<List<MovEquipResidenciaRetrofitModelInput>> =
        result(getClassAndMethod()) {
            api.send(token, list).body()!!
        }

}