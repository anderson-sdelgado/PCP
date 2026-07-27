package br.com.usinasantafe.pcp.infra.datasource.sharepreferences

import br.com.usinasantafe.pcp.utils.EmptyResult

interface MovEquipVisitTercPassagSharedPreferencesDatasource {
    suspend fun add(idVisitTerc: Int): EmptyResult
    suspend fun clean(): EmptyResult
    suspend fun delete(idVisitTerc: Int): EmptyResult
    suspend fun list(): Result<List<Int>>
}