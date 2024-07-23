package br.com.usinasantafe.pcp.infra.datasource.room.stable

import br.com.usinasantafe.pcp.infra.models.room.stable.VisitanteRoomModel

interface VisitanteRoomDatasource {

    suspend fun addAllVisitante(vararg visitanteRoomModels: VisitanteRoomModel)

    suspend fun checkCPFVisitante(cpf: String): Boolean

    suspend fun deleteAllVisitante()

    suspend fun getVisitanteCPF(cpf: String): VisitanteRoomModel

    suspend fun getVisitanteId(id: Long): VisitanteRoomModel

}