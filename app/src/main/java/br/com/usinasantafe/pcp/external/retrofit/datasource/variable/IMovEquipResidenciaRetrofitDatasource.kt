package br.com.usinasantafe.pcp.external.retrofit.datasource.variable

import br.com.usinasantafe.pcp.domain.errors.DatasourceException
import br.com.usinasantafe.pcp.external.retrofit.api.variable.MovEquipResidenciaApi
import br.com.usinasantafe.pcp.infra.datasource.retrofit.variable.MovEquipResidenciaRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.MovEquipResidenciaRetrofitModelInput
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.MovEquipResidenciaRetrofitModelOutput

class IMovEquipResidenciaRetrofitDatasource(
    private val api: MovEquipResidenciaApi
) : MovEquipResidenciaRetrofitDatasource {

    override suspend fun send(
        list: List<MovEquipResidenciaRetrofitModelOutput>,
        token: String
    ): Result<List<MovEquipResidenciaRetrofitModelInput>> {
        try {
            val response = api.send(
                auth = token,
                data = list
            )
            return Result.success(response.body()!!)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipResidenciaRetrofitDatasourceImpl.send",
                    cause = e
                )
            )
        }
    }

}