package br.com.usinasantafe.pcp.infra.repositories.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipVisitTercPassag
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import br.com.usinasantafe.pcp.infra.datasource.room.variable.MovEquipVisitTercPassagRoomDatasource
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.MovEquipVisitTercPassagSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipVisitTercPassagRoomModel
import br.com.usinasantafe.pcp.infra.models.room.variable.modelRoomToEntity
import br.com.usinasantafe.pcp.utils.FlowApp

class IMovEquipVisitTercPassagRepository(
    private val movEquipVisitTercPassagSharedPreferencesDatasource: MovEquipVisitTercPassagSharedPreferencesDatasource,
    private val movEquipVisitTercPassagRoomDatasource: MovEquipVisitTercPassagRoomDatasource,
): MovEquipVisitTercPassagRepository {

    override suspend fun add(
        idVisitTerc: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        try {
            val result = when(flowApp) {
                FlowApp.ADD -> movEquipVisitTercPassagSharedPreferencesDatasource.add(idVisitTerc)
                FlowApp.CHANGE -> movEquipVisitTercPassagRoomDatasource.add(idVisitTerc, id)
            }
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipVisitTercPassagRepository.add",
                    message = e.message,
                    cause = e
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipVisitTercPassagRepository.add",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun clear(): Result<Boolean> {
        val result = movEquipVisitTercPassagSharedPreferencesDatasource.clear()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMovEquipVisitTercPassagRepository.clear",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun delete(id: Int): Result<Boolean> {
        val result = movEquipVisitTercPassagRoomDatasource.delete(id)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMovEquipVisitTercPassagRepository.delete",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun delete(
        idVisitTerc: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        try {
            val result =
                when (flowApp) {
                    FlowApp.ADD -> movEquipVisitTercPassagSharedPreferencesDatasource.delete(idVisitTerc)
                    FlowApp.CHANGE -> movEquipVisitTercPassagRoomDatasource.delete(idVisitTerc, id)
                }
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipVisitTercPassagRepository.delete",
                    message = e.message,
                    cause = e
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipVisitTercPassagRepository.delete",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun list(
        flowApp: FlowApp,
        id: Int
    ): Result<List<MovEquipVisitTercPassag>> {
        try {
            when(flowApp) {
                FlowApp.ADD -> {
                    val result =
                        movEquipVisitTercPassagSharedPreferencesDatasource.list()
                    if (result.isFailure) {
                        val e = result.exceptionOrNull()!!
                        return resultFailure(
                            context = "IMovEquipVisitTercPassagRepository.list",
                            message = e.message,
                            cause = e
                        )
                    }
                    val list = result.getOrNull()!!
                    val movEquipVisitTercPassagList = list.map {
                        MovEquipVisitTercPassag(
                            idVisitTerc = it
                        )
                    }
                    return Result.success(movEquipVisitTercPassagList)
                }
                FlowApp.CHANGE -> {
                    val result =
                        movEquipVisitTercPassagRoomDatasource.list(id)
                    if (result.isFailure) {
                        val e = result.exceptionOrNull()!!
                        return resultFailure(
                            context = "IMovEquipVisitTercPassagRepository.list",
                            message = e.message,
                            cause = e
                        )
                    }
                    val list = result.getOrNull()!!
                    val movEquipVisitTercPassagList = list.map { it.modelRoomToEntity() }
                    return Result.success(movEquipVisitTercPassagList)
                }
            }
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipVisitTercPassagRepository.list",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun save(id: Int): Result<Boolean> {
        try {
            val resultList = movEquipVisitTercPassagSharedPreferencesDatasource.list()
            if (resultList.isFailure) {
                val e = resultList.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipVisitTercPassagRepository.save",
                    message = e.message,
                    cause = e
                )
            }
            val list = resultList.getOrNull()!!
            val modelRoomList = list.map {
                MovEquipVisitTercPassagRoomModel(
                    idMovEquipVisitTerc = id,
                    idVisitTerc = it
                )
            }
            val resultAddAll = movEquipVisitTercPassagRoomDatasource.addAll(modelRoomList)
            if (resultAddAll.isFailure) {
                val e = resultAddAll.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipVisitTercPassagRepository.save",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipVisitTercPassagRepository.save",
                message = "-",
                cause = e
            )
        }
    }
}