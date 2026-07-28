package br.com.usinasantafe.pcp.external.retrofit.datasource.stable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.external.retrofit.api.stable.EquipApi
import br.com.usinasantafe.pcp.infra.datasource.retrofit.stable.EquipRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.stable.EquipRetrofitModel
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.result
import javax.inject.Inject

class IEquipRetrofitDatasource @Inject constructor(
    private val equipApi: EquipApi
): EquipRetrofitDatasource {

    override suspend fun listAll(token: String): Result<List<EquipRetrofitModel>> =
        result(getClassAndMethod()) {
            equipApi.all(token).body()!!
        }

}