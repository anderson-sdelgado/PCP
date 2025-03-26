package br.com.usinasantafe.pcp.infra.repositories.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipProprioPassag
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioPassagRepository
import br.com.usinasantafe.pcp.infra.datasource.room.variable.MovEquipProprioPassagRoomDatasource
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.MovEquipProprioPassagSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipProprioPassagRoomModel
import br.com.usinasantafe.pcp.infra.models.room.variable.modelRoomToEntity
import br.com.usinasantafe.pcp.utils.FlowApp

class IMovEquipProprioPassagRepository(
    private val movEquipProprioPassagSharedPreferencesDatasource: MovEquipProprioPassagSharedPreferencesDatasource,
    private val movEquipProprioPassagRoomDatasource: MovEquipProprioPassagRoomDatasource,
) : MovEquipProprioPassagRepository {

    override suspend fun add(
        matricColab: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        try {
            val result =
                when (flowApp) {
                    FlowApp.ADD -> movEquipProprioPassagSharedPreferencesDatasource.add(matricColab)
                    FlowApp.CHANGE -> movEquipProprioPassagRoomDatasource.add(matricColab, id)
                }
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipProprioPassagRepository.add",
                    message = e.message,
                    cause = e
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipProprioPassagRepository.add",
                message = "-",
                cause = e
            )
        }

    }

    override suspend fun clear(): Result<Boolean> {
        val result = movEquipProprioPassagSharedPreferencesDatasource.clear()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMovEquipProprioPassagRepository.clear",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun delete(id: Int): Result<Boolean> {
        val result = movEquipProprioPassagRoomDatasource.delete(id)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMovEquipProprioPassagRepository.delete",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun delete(
        matricColab: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        try {
            val result =
                when (flowApp) {
                    FlowApp.ADD -> movEquipProprioPassagSharedPreferencesDatasource.delete(matricColab)
                    FlowApp.CHANGE -> movEquipProprioPassagRoomDatasource.delete(matricColab, id)
                }
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipProprioPassagRepository.delete",
                    message = e.message,
                    cause = e
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipProprioPassagRepository.delete",
                message = "-",
                cause = e
            )
        }

    }

    override suspend fun list(
        flowApp: FlowApp,
        id: Int
    ): Result<List<MovEquipProprioPassag>> {
        try {
            when (flowApp) {
                FlowApp.ADD -> {
                    val result = movEquipProprioPassagSharedPreferencesDatasource.list()
                    if (result.isFailure) {
                        val e = result.exceptionOrNull()!!
                        return resultFailure(
                            context = "IMovEquipProprioPassagRepository.list",
                            message = e.message,
                            cause = e
                        )
                    }
                    val list = result.getOrNull()!!
                    val movEquipProprioPassagList = list.map {
                        MovEquipProprioPassag(
                            matricColab = it
                        )
                    }
                    return Result.success(movEquipProprioPassagList)
                }

                FlowApp.CHANGE -> {
                    val result = movEquipProprioPassagRoomDatasource.list(id)
                    if (result.isFailure) {
                        val e = result.exceptionOrNull()!!
                        return resultFailure(
                            context = "IMovEquipProprioPassagRepository.list",
                            message = e.message,
                            cause = e
                        )
                    }
                    val list = result.getOrNull()!!
                    val movEquipProprioPassagList = list.map { it.modelRoomToEntity() }
                    return Result.success(movEquipProprioPassagList)
                }
            }
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipProprioPassagRepository.list",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun save(id: Int): Result<Boolean> {
        try {
            val resultList = movEquipProprioPassagSharedPreferencesDatasource.list()
            if (resultList.isFailure) {
                val e = resultList.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipProprioPassagRepository.save",
                    message = e.message,
                    cause = e
                )
            }
            val list = resultList.getOrNull()!!
            val modelRoomList = list.map {
                MovEquipProprioPassagRoomModel(
                    idMovEquipProprio = id,
                    matricColab = it
                )
            }
            val resultAddAll =
                movEquipProprioPassagRoomDatasource.addAll(modelRoomList)
            if (resultAddAll.isFailure) {
                val e = resultAddAll.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipProprioPassagRepository.save",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipProprioPassagRepository.save",
                message = "-",
                cause = e
            )
        }
    }

}