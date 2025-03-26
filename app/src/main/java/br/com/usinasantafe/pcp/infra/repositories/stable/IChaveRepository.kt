package br.com.usinasantafe.pcp.infra.repositories.stable

import br.com.usinasantafe.pcp.domain.entities.stable.Chave
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.ChaveRepository
import br.com.usinasantafe.pcp.infra.datasource.retrofit.stable.ChaveRetrofitDatasource
import br.com.usinasantafe.pcp.infra.datasource.room.stable.ChaveRoomDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.pcp.infra.models.room.stable.entityToRoomModel
import br.com.usinasantafe.pcp.infra.models.room.stable.roomModelToEntity

class IChaveRepository(
    private val chaveRoomDatasource: ChaveRoomDatasource,
    private val chaveRetrofitDatasource: ChaveRetrofitDatasource
): ChaveRepository {

    override suspend fun addAll(list: List<Chave>): Result<Boolean> {
        try {
            val roomModelList = list.map { it.entityToRoomModel() }
            val result = chaveRoomDatasource.addAll(roomModelList)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IChaveRepository.addAll",
                    message = e.message,
                    cause = e
                )
            }
            return result
        } catch (e: Exception){
            return resultFailure(
                context = "IChaveRepository.addAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        val result = chaveRoomDatasource.deleteAll()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IChaveRepository.deleteAll",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun get(id: Int): Result<Chave> {
        try {
            val result = chaveRoomDatasource.get(id).map { it.roomModelToEntity() }
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IChaveRepository.get",
                    message = e.message,
                    cause = e
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = "IChaveRepository.get",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun listAll(): Result<List<Chave>> {
        try {
            val resultRoomList = chaveRoomDatasource.listAll()
            if (resultRoomList.isFailure) {
                val e = resultRoomList.exceptionOrNull()!!
                return resultFailure(
                    context = "IChaveRepository.listAll",
                    message = e.message,
                    cause = e
                )
            }
            val entityList = resultRoomList.getOrNull()!!.map { it.roomModelToEntity() }
            return Result.success(entityList)
        } catch (e: Exception) {
            return resultFailure(
                context = "IChaveRepository.listAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun recoverAll(token: String): Result<List<Chave>> {
        try {
            val result = chaveRetrofitDatasource.recoverAll(token)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IChaveRepository.recoverAll",
                    message = e.message,
                    cause = e
                )
            }
            val entityList = result.getOrNull()!!.map { it.retrofitModelToEntity() }
            return Result.success(entityList)
        } catch (e: Exception) {
            return resultFailure(
                context = "IChaveRepository.recoverAll",
                message = "-",
                cause = e
            )
        }
    }


}