package br.com.usinasantafe.pcp.external.room.datasource.stable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.external.room.dao.stable.RLocalFluxoDao
import br.com.usinasantafe.pcp.infra.datasource.room.stable.RLocalFluxoRoomDatasource
import br.com.usinasantafe.pcp.infra.models.room.stable.RLocalFluxoRoomModel
import br.com.usinasantafe.pcp.utils.EmptyResult
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.result
import javax.inject.Inject

class IRLocalFluxoRoomDatasource @Inject constructor(
    private val rLocalFluxoDao: RLocalFluxoDao
): RLocalFluxoRoomDatasource {

    override suspend fun addAll(list: List<RLocalFluxoRoomModel>): EmptyResult =
        result(getClassAndMethod()) {
            rLocalFluxoDao.insertAll(list)
        }

    override suspend fun deleteAll(): EmptyResult =
        result(getClassAndMethod()) {
            rLocalFluxoDao.deleteAll()
        }

    override suspend fun list(idLocal: Int): Result<List<RLocalFluxoRoomModel>> =
        result(getClassAndMethod()) {
            rLocalFluxoDao.list(idLocal)
        }

}