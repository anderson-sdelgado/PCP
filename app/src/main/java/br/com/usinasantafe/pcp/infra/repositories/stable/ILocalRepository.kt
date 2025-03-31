package br.com.usinasantafe.pcp.infra.repositories.stable

import br.com.usinasantafe.pcp.domain.entities.stable.Local
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.LocalRepository
import br.com.usinasantafe.pcp.infra.datasource.room.stable.LocalRoomDatasource
import br.com.usinasantafe.pcp.infra.datasource.retrofit.stable.LocalRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.pcp.infra.models.room.stable.entityToRoomModel
import br.com.usinasantafe.pcp.infra.models.room.stable.roomModelToEntity

class ILocalRepository(
    private val localRoomDatasource: LocalRoomDatasource,
    private val localRetrofitDatasource: LocalRetrofitDatasource
): LocalRepository {
    
    override suspend fun addAll(list: List<Local>): Result<Boolean> {
        try {
            val localModelList = list.map { it.entityToRoomModel() }
            val result = localRoomDatasource.addAll(localModelList)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "ILocalRepository.addAll",
                    message = e.message,
                    cause = e
                )
            }
            return result
        } catch (e: Exception){
            return resultFailure(
                context = "ILocalRepository.add",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        val result = localRoomDatasource.deleteAll()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ILocalRepository.deleteAll",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun list(): Result<List<Local>> {
        try{
            val result = localRoomDatasource.listAll()
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "ILocalRepository.list",
                    message = e.message,
                    cause = e
                )
            }
            val localRoomModels = result.getOrNull()!!
            val locals = localRoomModels.map { it.roomModelToEntity() }
            return Result.success(locals)
        } catch (e: Exception){
            return resultFailure(
                context = "ILocalRepository.list",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getDescr(id: Int): Result<String> {
        val result = localRoomDatasource.getDescr(id)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ILocalRepository.getDescr",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun recoverAll(token: String): Result<List<Local>> {
        try {
            val result = localRetrofitDatasource.recoverAll(token)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "ILocalRepository.recoverAll",
                    message = e.message,
                    cause = e
                )
            }
            val entityList = result.getOrNull()!!.map { it.retrofitModelToEntity() }
            return Result.success(entityList)
        } catch (e: Exception) {
            return resultFailure(
                context = "ILocalRepository.recoverAll",
                message = "-",
                cause = e
            )
        }
    }

}