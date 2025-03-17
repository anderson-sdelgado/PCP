package br.com.usinasantafe.pcp.infra.datasource.retrofit.variable

import br.com.usinasantafe.pcp.infra.models.retrofit.variable.MovEquipResidenciaRetrofitModelInput
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.MovEquipResidenciaRetrofitModelOutput

interface MovEquipResidenciaRetrofitDatasource {

    suspend fun send(
        list: List<MovEquipResidenciaRetrofitModelOutput>
        , token: String)
            : Result<List<MovEquipResidenciaRetrofitModelInput>>

}