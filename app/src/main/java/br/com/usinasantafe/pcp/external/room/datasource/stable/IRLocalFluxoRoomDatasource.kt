package br.com.usinasantafe.pcp.external.room.datasource.stable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.external.room.dao.stable.RLocalFluxoDao
import br.com.usinasantafe.pcp.infra.datasource.room.stable.RLocalFluxoRoomDatasource
import br.com.usinasantafe.pcp.infra.models.room.stable.RLocalFluxoRoomModel

class IRLocalFluxoRoomDatasource(
    private val rLocalFluxoDao: RLocalFluxoDao
): RLocalFluxoRoomDatasource {

    override suspend fun addAll(list: List<RLocalFluxoRoomModel>): Result<Boolean> {
        try {
            rLocalFluxoDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IRLocalFluxoRoomDatasource.addAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            rLocalFluxoDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IRLocalFluxoRoomDatasource.deleteAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun list(idLocal: Int): Result<List<RLocalFluxoRoomModel>> {
        return try {
            Result.success(
                rLocalFluxoDao.list(idLocal)
            )
        } catch (e: Exception) {
            return resultFailure(
                context = "IRLocalFluxoRoomDatasource.list",
                message = "-",
                cause = e
            )
        }
    }

}