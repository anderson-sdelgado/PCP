package br.com.usinasantafe.pcp.external.room.datasource.stable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.external.room.dao.stable.FluxoDao
import br.com.usinasantafe.pcp.infra.datasource.room.stable.FluxoRoomDatasource
import br.com.usinasantafe.pcp.infra.models.room.stable.FluxoRoomModel

class IFluxoRoomDatasource(
    private val fluxoDao: FluxoDao
) : FluxoRoomDatasource {

    override suspend fun addAll(list: List<FluxoRoomModel>): Result<Boolean> {
        try {
            fluxoDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IFluxoRoomDatasource.addAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            fluxoDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IFluxoRoomDatasource.deleteAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun get(id: Int): Result<FluxoRoomModel> {
        return try {
            Result.success(
                fluxoDao.get(id)
            )
        } catch (e: Exception) {
            return resultFailure(
                context = "IFluxoRoomDatasource.get",
                message = "-",
                cause = e
            )
        }
    }
}