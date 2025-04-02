package br.com.usinasantafe.pcp.external.retrofit.datasource.variable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.external.retrofit.api.variable.MovEquipVisitTercApi
import br.com.usinasantafe.pcp.infra.datasource.retrofit.variable.MovEquipVisitTercRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.MovEquipVisitTercRetrofitModelInput
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.MovEquipVisitTercRetrofitModelOutput

class IMovEquipVisitTercRetrofitDatasource(
    private val api: MovEquipVisitTercApi
): MovEquipVisitTercRetrofitDatasource {

    override suspend fun send(
        list: List<MovEquipVisitTercRetrofitModelOutput>,
        token: String
    ): Result<List<MovEquipVisitTercRetrofitModelInput>> {
        try {
            val response = api.send(
                auth = token,
                data = list
            )
            return Result.success(response.body()!!)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipVisitTercRetrofitDatasource.send",
                message = "-",
                cause = e
            )
        }
    }

}