package br.com.usinasantafe.pcp.external.webservices.datasource.variable

import br.com.usinasantafe.pcp.utils.token
import br.com.usinasantafe.pcp.external.webservices.api.variable.MovEquipVisitTercApi
import br.com.usinasantafe.pcp.infra.datasource.webservice.variable.MovEquipVisitTercDatasourceWebService
import br.com.usinasantafe.pcp.infra.models.webservice.MovEquipVisitTercWebServiceModelInput
import br.com.usinasantafe.pcp.infra.models.webservice.MovEquipVisitTercWebServiceModelOutput
import javax.inject.Inject

class MovEquipVisitTercDatasourceWebServiceImpl @Inject constructor(
    private val movEquipVisitTercApi: MovEquipVisitTercApi,
): MovEquipVisitTercDatasourceWebService {

    override suspend fun sendMovEquipVisitTerc(
        movEquipVisitTercList: List<MovEquipVisitTercWebServiceModelOutput>,
        token: String
    ): Result<List<MovEquipVisitTercWebServiceModelInput>> {
        val response = movEquipVisitTercApi.send(token, movEquipVisitTercList)
        return if (response.isSuccessful) {
            Result.success(response.body()!!)
        } else {
            Result.failure(Throwable("Erro envio de dados"))
        }
    }
}