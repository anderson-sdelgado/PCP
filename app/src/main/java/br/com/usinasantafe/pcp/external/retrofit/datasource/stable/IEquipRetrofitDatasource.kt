package br.com.usinasantafe.pcp.external.retrofit.datasource.stable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.external.retrofit.api.stable.EquipApi
import br.com.usinasantafe.pcp.infra.datasource.retrofit.stable.EquipRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.stable.EquipRetrofitModel

class IEquipRetrofitDatasource(
    private val equipApi: EquipApi
): EquipRetrofitDatasource {

    override suspend fun recoverAll(token: String): Result<List<EquipRetrofitModel>> {
        try {
            val response = equipApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailure(
                context = "IEquipRetrofitDatasource.recoverAll",
                message = "-",
                cause = e
            )
        }
    }

}