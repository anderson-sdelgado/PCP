package br.com.usinasantafe.pcp.external.room.datasource.stable

import br.com.usinasantafe.pcp.external.room.dao.stable.VisitanteDao
import br.com.usinasantafe.pcp.infra.datasource.room.stable.VisitanteRoomDatasource
import br.com.usinasantafe.pcp.infra.models.room.stable.VisitanteRoomModel
import br.com.usinasantafe.pcp.utils.EmptyResult
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.result
import javax.inject.Inject

class IVisitanteRoomDatasource @Inject constructor(
    private val visitanteDao: VisitanteDao
): VisitanteRoomDatasource {

    override suspend fun addAll(list: List<VisitanteRoomModel>): EmptyResult =
        result(getClassAndMethod()) {
            visitanteDao.insertAll(list)
        }

    override suspend fun checkCpf(cpf: String): Result<Boolean> =
        result(getClassAndMethod()) {
            visitanteDao.has(cpf)
        }

    override suspend fun deleteAll(): EmptyResult =
        result(getClassAndMethod()) {
            visitanteDao.deleteAll()
        }


    override suspend fun getById(id: Int): Result<VisitanteRoomModel> =
        result(getClassAndMethod()) {
            visitanteDao.getById(id)
        }

    override suspend fun getByCpf(cpf: String): Result<VisitanteRoomModel> =
        result(getClassAndMethod()) {
            visitanteDao.getByCpf(cpf)
        }

}