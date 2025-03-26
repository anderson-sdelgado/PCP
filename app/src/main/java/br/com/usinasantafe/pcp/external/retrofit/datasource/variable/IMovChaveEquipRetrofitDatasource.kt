package br.com.usinasantafe.pcp.external.retrofit.datasource.variable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.external.retrofit.api.variable.MovChaveEquipApi
import br.com.usinasantafe.pcp.infra.datasource.retrofit.variable.MovChaveEquipRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.MovChaveEquipRetrofitModelInput
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.MovChaveEquipRetrofitModelOutput

class IMovChaveEquipRetrofitDatasource(
    private val api: MovChaveEquipApi
): MovChaveEquipRetrofitDatasource {

    override suspend fun send(
        list: List<MovChaveEquipRetrofitModelOutput>,
        token: String
    ): Result<List<MovChaveEquipRetrofitModelInput>> {
        try {
            val response = api.send(
                auth = token,
                data = list
            )
            return Result.success(response.body()!!)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovChaveEquipRetrofitDatasource.recoverAll",
                message = "-",
                cause = e
            )
        }
    }
}