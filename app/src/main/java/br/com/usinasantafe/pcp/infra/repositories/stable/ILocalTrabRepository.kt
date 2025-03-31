package br.com.usinasantafe.pcp.infra.repositories.stable

import br.com.usinasantafe.pcp.domain.entities.stable.LocalTrab
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.LocalTrabRepository
import br.com.usinasantafe.pcp.infra.datasource.retrofit.stable.LocalTrabRetrofitDatasource
import br.com.usinasantafe.pcp.infra.datasource.room.stable.LocalTrabRoomDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.pcp.infra.models.room.stable.entityToRoomModel

class ILocalTrabRepository(
    private val localTrabRoomDatasource: LocalTrabRoomDatasource,
    private val localTrabRetrofitDatasource: LocalTrabRetrofitDatasource
): LocalTrabRepository {

    override suspend fun addAll(list: List<LocalTrab>): Result<Boolean> {
        try {
            val roomModelList = list.map { it.entityToRoomModel() }
            val result = localTrabRoomDatasource.addAll(roomModelList)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "ILocalTrabRepository.addAll",
                    message = e.message,
                    cause = e
                )
            }
            return result
        } catch (e: Exception){
            return resultFailure(
                context = "ILocalTrabRepository.add",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        val result = localTrabRoomDatasource.deleteAll()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ILocalTrabRepository.deleteAll",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun getDescr(id: Int): Result<String> {
        val result = localTrabRoomDatasource.getDescr(id)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ILocalTrabRepository.getDescr",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun recoverAll(token: String): Result<List<LocalTrab>> {
        try {
            val result = localTrabRetrofitDatasource.recoverAll(token)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "ILocalTrabRepository.recoverAll",
                    message = e.message,
                    cause = e
                )
            }
            val entityList = result.getOrNull()!!.map { it.retrofitModelToEntity() }
            return Result.success(entityList)
        } catch (e: Exception) {
            return resultFailure(
                context = "ILocalTrabRepository.recoverAll",
                message = "-",
                cause = e
            )
        }
    }

}