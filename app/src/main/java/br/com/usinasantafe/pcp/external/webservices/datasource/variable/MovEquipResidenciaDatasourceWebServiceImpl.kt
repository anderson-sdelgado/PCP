package br.com.usinasantafe.pcp.external.webservices.datasource.variable

import br.com.usinasantafe.pcp.utils.token
import br.com.usinasantafe.pcp.external.webservices.api.variable.MovEquipResidenciaApi
import br.com.usinasantafe.pcp.infra.datasource.webservice.variable.MovEquipResidenciaDatasourceWebService
import br.com.usinasantafe.pcp.infra.models.webservice.MovEquipResidenciaWebServiceModelInput
import br.com.usinasantafe.pcp.infra.models.webservice.MovEquipResidenciaWebServiceModelOutput
import javax.inject.Inject

class MovEquipResidenciaDatasourceWebServiceImpl @Inject constructor(
    private val movEquipResidenciaApi: MovEquipResidenciaApi,
): MovEquipResidenciaDatasourceWebService {

    override suspend fun sendMovEquipResidencia(
        movEquipResidenciaList: List<MovEquipResidenciaWebServiceModelOutput>,
        token: String
    ): Result<List<MovEquipResidenciaWebServiceModelInput>> {
        val response = movEquipResidenciaApi.send(token, movEquipResidenciaList)
        return if (response.isSuccessful) {
            Result.success(response.body()!!)
        } else {
            Result.failure(Throwable("Erro envio de dados"))
        }
    }

}