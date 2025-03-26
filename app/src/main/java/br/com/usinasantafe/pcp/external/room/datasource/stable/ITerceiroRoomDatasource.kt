package br.com.usinasantafe.pcp.external.room.datasource.stable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.external.room.dao.stable.TerceiroDao
import br.com.usinasantafe.pcp.infra.datasource.room.stable.TerceiroRoomDatasource
import br.com.usinasantafe.pcp.infra.models.room.stable.TerceiroRoomModel

class ITerceiroRoomDatasource(
    private val terceiroDao: TerceiroDao
): TerceiroRoomDatasource {

    override suspend fun addAll(list: List<TerceiroRoomModel>): Result<Boolean> {
        try {
            terceiroDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "ITerceiroRoomDatasource.addAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun checkCpf(cpf: String): Result<Boolean> {
        try {
            val result = terceiroDao.check(cpf) > 0
            return Result.success(result)
        } catch (e: Exception) {
            return resultFailure(
                context = "ITerceiroRoomDatasource.checkCpf",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            terceiroDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "ITerceiroRoomDatasource.deleteAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun get(id: Int): Result<List<TerceiroRoomModel>> {
        try {
            val result = terceiroDao.get(id)
            return Result.success(result)
        } catch (e: Exception) {
            return resultFailure(
                context = "ITerceiroRoomDatasource.get(id)",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun get(cpf: String): Result<List<TerceiroRoomModel>> {
        try {
            val result = terceiroDao.get(cpf)
            return Result.success(result)
        } catch (e: Exception) {
            return resultFailure(
                context = "ITerceiroRoomDatasource.get(cpf)",
                message = "-",
                cause = e
            )
        }
    }
}