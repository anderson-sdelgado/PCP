package br.com.usinasantafe.pcp.external.room.datasource.stable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.external.room.dao.stable.ColabDao
import br.com.usinasantafe.pcp.infra.datasource.room.stable.ColabRoomDatasource
import br.com.usinasantafe.pcp.infra.models.room.stable.ColabRoomModel
import br.com.usinasantafe.pcp.utils.EmptyResult
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.result
import javax.inject.Inject

class IColabRoomDatasource @Inject constructor(
    private val colabDao: ColabDao
): ColabRoomDatasource {

    override suspend fun addAll(list: List<ColabRoomModel>): EmptyResult =
        result(getClassAndMethod()) {
            colabDao.insertAll(list)
        }

    override suspend fun checkMatric(matric: Int): Result<Boolean> =
        result(getClassAndMethod()) {
            colabDao.check(matric) > 0
        }

    override suspend fun getNomeByMatric(matric: Int): Result<String> =
        result(getClassAndMethod()) {
            colabDao.getNomeByMatric(matric)
        }

    override suspend fun deleteAll(): EmptyResult =
        result(getClassAndMethod()) {
            colabDao.deleteAll()
        }

}