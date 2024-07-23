package br.com.usinasantafe.pcp.external.webservices.datasource.variable

import android.util.Log
import br.com.usinasantafe.pcp.utils.token
import br.com.usinasantafe.pcp.external.webservices.api.variable.MovEquipProprioApi

import br.com.usinasantafe.pcp.infra.datasource.webservice.variable.MovEquipProprioDatasourceWebService
import br.com.usinasantafe.pcp.infra.models.webservice.MovEquipProprioWebServiceModelInput
import br.com.usinasantafe.pcp.infra.models.webservice.MovEquipProprioWebServiceModelOutput
import javax.inject.Inject

class MovEquipProprioDatasourceWebServiceImpl @Inject constructor(
    private val movEquipProprioApi: MovEquipProprioApi
): MovEquipProprioDatasourceWebService {

    override suspend fun sendMovEquipProprio(
        movEquipProprioList: List<MovEquipProprioWebServiceModelOutput>,
        token: String
    ): Result<List<MovEquipProprioWebServiceModelInput>> {
        val response = movEquipProprioApi.send(token, movEquipProprioList)
        return if (response.isSuccessful) {
            Result.success(response.body()!!)
        } else {
            Result.failure(Throwable("Erro envio de dados"))
        }
    }

}