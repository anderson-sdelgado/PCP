package br.com.usinasantafe.pcp.external.room.datasource.stable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.external.room.dao.stable.ChaveDao
import br.com.usinasantafe.pcp.infra.datasource.room.stable.ChaveRoomDatasource
import br.com.usinasantafe.pcp.infra.models.room.stable.ChaveRoomModel
import br.com.usinasantafe.pcp.utils.EmptyResult
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.result
import javax.inject.Inject

class IChaveRoomDatasource @Inject constructor(
    private val chaveDao: ChaveDao
): ChaveRoomDatasource {

    override suspend fun addAll(list: List<ChaveRoomModel>): EmptyResult =
        result(getClassAndMethod()) {
            chaveDao.insertAll(list)
        }

    override suspend fun deleteAll(): EmptyResult =
        result(getClassAndMethod()) {
            chaveDao.deleteAll()
        }

    override suspend fun get(id: Int): Result<ChaveRoomModel> =
        result(getClassAndMethod()) {
            chaveDao.get(id)
        }

    override suspend fun listAll(): Result<List<ChaveRoomModel>> =
        result(getClassAndMethod()) {
            chaveDao.listAll()
        }
}