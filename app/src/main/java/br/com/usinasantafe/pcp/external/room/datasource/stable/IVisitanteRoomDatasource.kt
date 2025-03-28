package br.com.usinasantafe.pcp.external.room.datasource.stable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.external.room.dao.stable.VisitanteDao
import br.com.usinasantafe.pcp.infra.datasource.room.stable.VisitanteRoomDatasource
import br.com.usinasantafe.pcp.infra.models.room.stable.VisitanteRoomModel

class IVisitanteRoomDatasource(
    private val visitanteDao: VisitanteDao
): VisitanteRoomDatasource {

    override suspend fun addAll(list: List<VisitanteRoomModel>): Result<Boolean> {
        try {
            visitanteDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IVisitanteRoomDatasource.addAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun checkCpf(cpf: String): Result<Boolean> {
        try {
            val result = visitanteDao.check(cpf) > 0
            return Result.success(result)
        } catch (e: Exception) {
            return resultFailure(
                context = "IVisitanteRoomDatasource.checkCpf",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            visitanteDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IVisitanteRoomDatasource.deleteAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun get(id: Int): Result<VisitanteRoomModel> {
        try{
            val result = visitanteDao.get(id)
            return Result.success(result)
        } catch (e: Exception) {
            return resultFailure(
                context = "IVisitanteRoomDatasource.get(id)",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun get(cpf: String): Result<VisitanteRoomModel> {
        try{
            val result = visitanteDao.get(cpf)
            return Result.success(result)
        } catch (e: Exception) {
            return resultFailure(
                context = "IVisitanteRoomDatasource.get(cpf)",
                message = "-",
                cause = e
            )
        }
    }
}