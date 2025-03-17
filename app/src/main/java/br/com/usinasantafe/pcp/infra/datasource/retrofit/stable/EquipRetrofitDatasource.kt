package br.com.usinasantafe.pcp.infra.datasource.retrofit.stable

import br.com.usinasantafe.pcp.infra.models.retrofit.stable.EquipRetrofitModel

interface EquipRetrofitDatasource {
    suspend fun recoverAll(token: String): Result<List<EquipRetrofitModel>>
}