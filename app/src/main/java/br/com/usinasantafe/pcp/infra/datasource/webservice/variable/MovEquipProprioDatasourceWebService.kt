package br.com.usinasantafe.pcp.infra.datasource.webservice.variable

import br.com.usinasantafe.pcp.infra.models.webservice.MovEquipProprioWebServiceModelInput
import br.com.usinasantafe.pcp.infra.models.webservice.MovEquipProprioWebServiceModelOutput

interface MovEquipProprioDatasourceWebService {

    suspend fun sendMovEquipProprio(
        movEquipProprioList: List<MovEquipProprioWebServiceModelOutput>
        , token: String)
    : Result<List<MovEquipProprioWebServiceModelInput>>

}