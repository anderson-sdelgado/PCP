package br.com.usinasantafe.pcp.infra.repositories.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipProprioEquipSeg
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioEquipSegRepository
import br.com.usinasantafe.pcp.infra.datasource.room.variable.MovEquipProprioEquipSegRoomDatasource
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.MovEquipProprioEquipSegSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipProprioEquipSegRoomModel
import br.com.usinasantafe.pcp.infra.models.room.variable.modelRoomToEntity
import br.com.usinasantafe.pcp.utils.FlowApp

class IMovEquipProprioEquipSegRepository(
    private val movEquipProprioEquipSegSharedPreferencesDatasource: MovEquipProprioEquipSegSharedPreferencesDatasource,
    private val movEquipProprioEquipSegRoomDatasource: MovEquipProprioEquipSegRoomDatasource
) : MovEquipProprioEquipSegRepository {

    override suspend fun add(
        idEquip: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        val result =
            when (flowApp) {
                FlowApp.ADD -> movEquipProprioEquipSegSharedPreferencesDatasource.add(idEquip)
                FlowApp.CHANGE -> movEquipProprioEquipSegRoomDatasource.add(idEquip, id)
            }
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMovEquipProprioEquipSegRepository.add",
                message = "-",
                cause = e
            )
        }
        return result
    }

    override suspend fun clear(): Result<Boolean> {
        val result = movEquipProprioEquipSegSharedPreferencesDatasource.clear()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMovEquipProprioEquipSegRepository.clear",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun delete(id: Int): Result<Boolean> {
        val result = movEquipProprioEquipSegRoomDatasource.delete(id)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMovEquipProprioEquipSegRepository.delete",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun list(
        flowApp: FlowApp,
        id: Int
    ): Result<List<MovEquipProprioEquipSeg>> {
        when(flowApp) {
            FlowApp.ADD -> {
                val result = movEquipProprioEquipSegSharedPreferencesDatasource.list()
                if (result.isFailure) {
                    val e = result.exceptionOrNull()!!
                    return resultFailure(
                        context = "IMovEquipProprioEquipSegRepository.list",
                        message = e.message,
                        cause = e
                    )
                }
                val list = result.getOrNull()!!
                val movEquipProprioEquipSegList = list.map {
                    MovEquipProprioEquipSeg(
                        idEquip = it
                    )
                }
                return Result.success(movEquipProprioEquipSegList)
            }
            FlowApp.CHANGE -> {
                val result = movEquipProprioEquipSegRoomDatasource.list(id)
                if (result.isFailure) {
                    val e = result.exceptionOrNull()!!
                    return resultFailure(
                        context = "IMovEquipProprioEquipSegRepository.list",
                        message = e.message,
                        cause = e
                    )
                }
                val list = result.getOrNull()!!
                val movEquipProprioEquipSegList = list.map { it.modelRoomToEntity() }
                return Result.success(movEquipProprioEquipSegList)
            }
        }
    }

    override suspend fun delete(
        idEquip: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        val result =
            when (flowApp) {
                FlowApp.ADD -> movEquipProprioEquipSegSharedPreferencesDatasource.delete(idEquip)
                FlowApp.CHANGE -> movEquipProprioEquipSegRoomDatasource.delete(idEquip, id)
            }
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMovEquipProprioEquipSegRepository.delete",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun save(id: Int): Result<Boolean> {
        try {
            val resultList = movEquipProprioEquipSegSharedPreferencesDatasource.list()
            if (resultList.isFailure) {
                val e = resultList.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipProprioEquipSegRepository.save",
                    message = e.message,
                    cause = e
                )
            }
            val list = resultList.getOrNull()!!
            val movEquipProprioEquipSegRoomModelList = list.map {
                MovEquipProprioEquipSegRoomModel(
                    idMovEquipProprio = id,
                    idEquip = it
                )
            }
            val resultAddAll =
                movEquipProprioEquipSegRoomDatasource.addAll(movEquipProprioEquipSegRoomModelList)
            if (resultAddAll.isFailure) {
                val e = resultAddAll.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipProprioEquipSegRepository.save",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipProprioEquipSegRepository.save",
                message = "-",
                cause = e
            )
        }
    }

}