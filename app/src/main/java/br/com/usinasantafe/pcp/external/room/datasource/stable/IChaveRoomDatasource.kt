package br.com.usinasantafe.pcp.external.room.datasource.stable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.external.room.dao.stable.ChaveDao
import br.com.usinasantafe.pcp.infra.datasource.room.stable.ChaveRoomDatasource
import br.com.usinasantafe.pcp.infra.models.room.stable.ChaveRoomModel

class IChaveRoomDatasource(
    private val chaveDao: ChaveDao
): ChaveRoomDatasource {

    override suspend fun addAll(list: List<ChaveRoomModel>): Result<Boolean> {
        try {
            chaveDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IChaveRoomDatasource.addAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            chaveDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IChaveRoomDatasource.deleteAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun get(id: Int): Result<ChaveRoomModel> {
        try {
            val model = chaveDao.get(id)
            return Result.success(model)
        } catch (e: Exception) {
            return resultFailure(
                context = "IChaveRoomDatasource.get",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun listAll(): Result<List<ChaveRoomModel>> {
        return try{
            Result.success(chaveDao.listAll())
        } catch (e: Exception) {
            return resultFailure(
                context = "IChaveRoomDatasource.listAll",
                message = "-",
                cause = e
            )
        }
    }
}