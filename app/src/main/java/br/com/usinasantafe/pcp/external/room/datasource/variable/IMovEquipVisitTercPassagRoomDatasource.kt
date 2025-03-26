package br.com.usinasantafe.pcp.external.room.datasource.variable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.external.room.dao.variable.MovEquipVisitTercPassagDao
import br.com.usinasantafe.pcp.infra.datasource.room.variable.MovEquipVisitTercPassagRoomDatasource
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipVisitTercPassagRoomModel

class IMovEquipVisitTercPassagRoomDatasource(
    private val movEquipVisitTercPassagDao: MovEquipVisitTercPassagDao
) : MovEquipVisitTercPassagRoomDatasource {

    override suspend fun add(
        idVisitTerc: Int,
        id: Int
    ): Result<Boolean> {
        try {
            movEquipVisitTercPassagDao.insert(
                MovEquipVisitTercPassagRoomModel(
                    idMovEquipVisitTerc = id,
                    idVisitTerc = idVisitTerc
                )
            )
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipVisitTercPassagRoomDatasource.add",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun addAll(list: List<MovEquipVisitTercPassagRoomModel>): Result<Boolean> {
        try {
            movEquipVisitTercPassagDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipVisitTercPassagRoomDatasource.addAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun delete(id: Int): Result<Boolean> {
        try {
            val list = movEquipVisitTercPassagDao.get(id)
            for(mov in list) {
                movEquipVisitTercPassagDao.delete(mov)
            }
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipVisitTercPassagRoomDatasource.delete(id)",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun delete(
        idVisitTerc: Int,
        id: Int
    ): Result<Boolean> {
        try {
            val mov = movEquipVisitTercPassagDao.get(
                idVisitTerc = idVisitTerc,
                idMov = id
            )
            movEquipVisitTercPassagDao.delete(mov)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipVisitTercPassagRoomDatasource.delete(idVisitTerc, id)",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun list(id: Int): Result<List<MovEquipVisitTercPassagRoomModel>> {
        return try {
            Result.success(movEquipVisitTercPassagDao.list(id))
        } catch (e: Exception) {
            resultFailure(
                context = "IMovEquipVisitTercPassagRoomDatasource.list",
                message = "-",
                cause = e
            )
        }
    }

}