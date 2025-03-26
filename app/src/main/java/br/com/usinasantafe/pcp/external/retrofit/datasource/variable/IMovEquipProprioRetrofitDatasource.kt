package br.com.usinasantafe.pcp.external.retrofit.datasource.variable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.external.retrofit.api.variable.MovEquipProprioApi
import br.com.usinasantafe.pcp.infra.datasource.retrofit.variable.MovEquipProprioRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.MovEquipProprioRetrofitModelInput
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.MovEquipProprioRetrofitModelOutput

class IMovEquipProprioRetrofitDatasource(
    private val api: MovEquipProprioApi
): MovEquipProprioRetrofitDatasource {

    override suspend fun send(
        list: List<MovEquipProprioRetrofitModelOutput>,
        token: String
    ): Result<List<MovEquipProprioRetrofitModelInput>> {
        try {
            val response = api.send(
                auth = token,
                data = list
            )
            return Result.success(response.body()!!)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipProprioRetrofitDatasource.recoverAll",
                message = "-",
                cause = e
            )
        }
    }

}