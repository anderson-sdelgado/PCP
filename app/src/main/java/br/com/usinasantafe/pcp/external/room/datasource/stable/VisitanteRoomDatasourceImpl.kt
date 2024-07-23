package br.com.usinasantafe.pcp.external.room.datasource.stable

import br.com.usinasantafe.pcp.external.room.dao.stable.VisitanteDao
import br.com.usinasantafe.pcp.infra.datasource.room.stable.VisitanteRoomDatasource
import br.com.usinasantafe.pcp.infra.models.room.stable.VisitanteRoomModel
import javax.inject.Inject

class VisitanteRoomDatasourceImpl @Inject constructor (
    private val visitanteDao: VisitanteDao,
): VisitanteRoomDatasource {

    override suspend fun addAllVisitante(vararg visitanteRoomModels: VisitanteRoomModel) {
        visitanteDao.insertAll(*visitanteRoomModels)
    }

    override suspend fun checkCPFVisitante(cpf: String): Boolean {
        return visitanteDao.checkCPFVisitante(cpf) > 0
    }

    override suspend fun deleteAllVisitante() {
        visitanteDao.deleteAll()
    }

    override suspend fun getVisitanteCPF(cpf: String): VisitanteRoomModel {
        return visitanteDao.getVisitanteCPF(cpf)
    }

    override suspend fun getVisitanteId(id: Long): VisitanteRoomModel {
        return visitanteDao.getVisitanteId(id)
    }

}