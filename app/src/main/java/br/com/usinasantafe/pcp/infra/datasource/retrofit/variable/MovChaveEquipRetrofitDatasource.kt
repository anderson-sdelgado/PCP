package br.com.usinasantafe.pcp.infra.datasource.retrofit.variable

import br.com.usinasantafe.pcp.infra.models.retrofit.variable.MovChaveEquipRetrofitModelInput
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.MovChaveEquipRetrofitModelOutput

interface MovChaveEquipRetrofitDatasource {

    suspend fun send(
        list: List<MovChaveEquipRetrofitModelOutput>,
        token: String
    ): Result<List<MovChaveEquipRetrofitModelInput>>

}