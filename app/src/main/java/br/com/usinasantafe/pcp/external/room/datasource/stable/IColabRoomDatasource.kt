package br.com.usinasantafe.pcp.external.room.datasource.stable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.external.room.dao.stable.ColabDao
import br.com.usinasantafe.pcp.infra.datasource.room.stable.ColabRoomDatasource
import br.com.usinasantafe.pcp.infra.models.room.stable.ColabRoomModel

class IColabRoomDatasource(
    private val colabDao: ColabDao
): ColabRoomDatasource {

    override suspend fun addAll(list: List<ColabRoomModel>): Result<Boolean> {
        try {
            colabDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IColabRoomDatasource.addAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun checkMatric(matric: Int): Result<Boolean> {
        try {
            val result = colabDao.check(matric) > 0
            return Result.success(result)
        } catch (e: Exception) {
            return resultFailure(
                context = "IColabRoomDatasource.checkMatric",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getNome(matric: Int): Result<String> {
        try {
            val nome = colabDao.getNome(matric)
            return Result.success(nome)
        } catch (e: Exception) {
            return resultFailure(
                context = "IColabRoomDatasource.getNome",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            colabDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IColabRoomDatasource.deleteAll",
                message = "-",
                cause = e
            )
        }
    }

}