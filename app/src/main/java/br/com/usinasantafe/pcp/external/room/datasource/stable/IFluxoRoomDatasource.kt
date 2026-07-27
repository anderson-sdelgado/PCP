package br.com.usinasantafe.pcp.external.room.datasource.stable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.external.room.dao.stable.FluxoDao
import br.com.usinasantafe.pcp.infra.datasource.room.stable.FluxoRoomDatasource
import br.com.usinasantafe.pcp.infra.models.room.stable.FluxoRoomModel
import br.com.usinasantafe.pcp.utils.EmptyResult
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.result
import javax.inject.Inject

class IFluxoRoomDatasource @Inject constructor(
    private val fluxoDao: FluxoDao
) : FluxoRoomDatasource {

    override suspend fun addAll(list: List<FluxoRoomModel>): EmptyResult =
        result(getClassAndMethod()) {
            fluxoDao.insertAll(list)
        }

    override suspend fun deleteAll(): EmptyResult =
        result(getClassAndMethod()) {
            fluxoDao.deleteAll()
        }

    override suspend fun getById(id: Int): Result<FluxoRoomModel> =
        result(getClassAndMethod()) {
            fluxoDao.getById(id)
        }
}