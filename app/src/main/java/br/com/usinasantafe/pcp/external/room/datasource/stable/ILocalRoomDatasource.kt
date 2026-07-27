package br.com.usinasantafe.pcp.external.room.datasource.stable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.external.room.dao.stable.LocalDao
import br.com.usinasantafe.pcp.infra.datasource.room.stable.LocalRoomDatasource
import br.com.usinasantafe.pcp.infra.models.room.stable.LocalRoomModel
import br.com.usinasantafe.pcp.utils.EmptyResult
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.result
import javax.inject.Inject

class ILocalRoomDatasource @Inject constructor(
    private val localDao: LocalDao
): LocalRoomDatasource {

    override suspend fun addAll(list: List<LocalRoomModel>): EmptyResult =
        result(getClassAndMethod()) {
            localDao.insertAll(list)
        }

    override suspend fun getDescrById(id: Int): Result<String> =
        result(getClassAndMethod()) {
            localDao.getDescrById(id)
        }

    override suspend fun deleteAll(): EmptyResult =
        result(getClassAndMethod()) {
            localDao.deleteAll()
        }

    override suspend fun listAll(): Result<List<LocalRoomModel>> =
        result(getClassAndMethod()) {
            localDao.listAll()
        }
}