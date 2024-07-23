package br.com.usinasantafe.pcp.infra.datasource.webservice.variable

import br.com.usinasantafe.pcp.infra.models.webservice.MovEquipVisitTercWebServiceModelInput
import br.com.usinasantafe.pcp.infra.models.webservice.MovEquipVisitTercWebServiceModelOutput

interface MovEquipVisitTercDatasourceWebService {

    suspend fun sendMovEquipVisitTerc(
        movEquipVisitTercList: List<MovEquipVisitTercWebServiceModelOutput>
        , token: String)
            : Result<List<MovEquipVisitTercWebServiceModelInput>>

}