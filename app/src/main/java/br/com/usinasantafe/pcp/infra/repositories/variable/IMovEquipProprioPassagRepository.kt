package br.com.usinasantafe.pcp.infra.repositories.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipProprioPassag
import br.com.usinasantafe.pcp.domain.errors.RepositoryException
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
        return try {
            when (flowApp) {
                FlowApp.ADD -> movEquipProprioPassagSharedPreferencesDatasource.add(matricColab)
                FlowApp.CHANGE -> movEquipProprioPassagRoomDatasource.add(matricColab, id)
            }
        } catch (e: Exception) {
            Result.failure(
                RepositoryException(
                    function = "MovEquipProprioPassagRepositoryImpl.add",
                    cause = e
                )
            )
        }

    }

    override suspend fun clear(): Result<Boolean> {
        return movEquipProprioPassagSharedPreferencesDatasource.clear()
    }

    override suspend fun delete(id: Int): Result<Boolean> {
        return movEquipProprioPassagRoomDatasource.delete(id)
    }

    override suspend fun delete(
        matricColab: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        return try {
            when (flowApp) {
                FlowApp.ADD -> movEquipProprioPassagSharedPreferencesDatasource.delete(matricColab)
                FlowApp.CHANGE -> movEquipProprioPassagRoomDatasource.delete(matricColab, id)
            }
        } catch (e: Exception) {
            Result.failure(
                RepositoryException(
                    function = "MovEquipProprioPassagRepositoryImpl.delete",
                    cause = e
                )
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
                    val resultList = movEquipProprioPassagSharedPreferencesDatasource.list()
                    if (resultList.isFailure)
                        return Result.failure(resultList.exceptionOrNull()!!)
                    val list = resultList.getOrNull()!!
                    val movEquipProprioPassagList = list.map {
                        MovEquipProprioPassag(
                            matricColab = it
                        )
                    }
                    return Result.success(movEquipProprioPassagList)
                }

                FlowApp.CHANGE -> {
                    val resultList = movEquipProprioPassagRoomDatasource.list(id)
                    if (resultList.isFailure)
                        return Result.failure(resultList.exceptionOrNull()!!)
                    val list = resultList.getOrNull()!!
                    val movEquipProprioPassagList = list.map { it.modelRoomToEntity() }
                    return Result.success(movEquipProprioPassagList)
                }
            }
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "MovEquipProprioPassagRepositoryImpl.list",
                    cause = e
                )
            )
        }
    }

    override suspend fun save(id: Int): Result<Boolean> {
        try {
            val resultList = movEquipProprioPassagSharedPreferencesDatasource.list()
            if (resultList.isFailure)
                return Result.failure(resultList.exceptionOrNull()!!)
            val list = resultList.getOrNull()!!
            val modelRoomList = list.map {
                MovEquipProprioPassagRoomModel(
                    idMovEquipProprio = id,
                    matricColab = it
                )
            }
            val result =
                movEquipProprioPassagRoomDatasource.addAll(modelRoomList)
            if (result.isFailure)
                return Result.failure(result.exceptionOrNull()!!)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "MovEquipProprioPassagRepositoryImpl.save",
                    cause = e
                )
            )
        }
    }

}