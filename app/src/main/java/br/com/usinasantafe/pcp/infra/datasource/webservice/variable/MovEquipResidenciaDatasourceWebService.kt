package br.com.usinasantafe.pcp.infra.datasource.webservice.variable

import br.com.usinasantafe.pcp.infra.models.webservice.MovEquipResidenciaWebServiceModelInput
import br.com.usinasantafe.pcp.infra.models.webservice.MovEquipResidenciaWebServiceModelOutput


interface MovEquipResidenciaDatasourceWebService {

    suspend fun sendMovEquipResidencia(
        movEquipResidenciaList: List<MovEquipResidenciaWebServiceModelOutput>
        , token: String)
            : Result<List<MovEquipResidenciaWebServiceModelInput>>

}