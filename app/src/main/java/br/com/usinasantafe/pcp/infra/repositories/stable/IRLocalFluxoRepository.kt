package br.com.usinasantafe.pcp.infra.repositories.stable

import br.com.usinasantafe.pcp.domain.entities.stable.RLocalFluxo
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.RLocalFluxoRepository
import br.com.usinasantafe.pcp.infra.datasource.retrofit.stable.RLocalFluxoRetrofitDatasource
import br.com.usinasantafe.pcp.infra.datasource.room.stable.RLocalFluxoRoomDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.pcp.infra.models.room.stable.entityToRoomModel
import br.com.usinasantafe.pcp.infra.models.room.stable.roomModelToEntity

class IRLocalFluxoRepository(
    private val rLocalFluxoRoomDatasource: RLocalFluxoRoomDatasource,
    private val rLocalFluxoRetrofitDatasource: RLocalFluxoRetrofitDatasource
): RLocalFluxoRepository {

    override suspend fun addAll(list: List<RLocalFluxo>): Result<Boolean> {
        try {
            val roomModelList = list.map { it.entityToRoomModel() }
            val result = rLocalFluxoRoomDatasource.addAll(roomModelList)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IRLocalFluxoRepository.addAll",
                    message = e.message,
                    cause = e
                )
            }
            return result
        } catch (e: Exception){
            return resultFailure(
                context = "IRLocalFluxoRepository.add",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        val result = rLocalFluxoRoomDatasource.deleteAll()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IRLocalFluxoRepository.deleteAll",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun list(idLocal: Int): Result<List<RLocalFluxo>> {
        try {
            val result = rLocalFluxoRoomDatasource.list(idLocal)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IRLocalFluxoRepository.list",
                    message = e.message,
                    cause = e
                )
            }
            val entityList = result.getOrNull()!!.map { it.roomModelToEntity() }
            return Result.success(entityList)
        } catch (e: Exception) {
            return resultFailure(
                context = "IRLocalFluxoRepository.list",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun recoverAll(token: String): Result<List<RLocalFluxo>> {
        try {
            val result = rLocalFluxoRetrofitDatasource.recoverAll(token)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IRLocalFluxoRepository.recoverAll",
                    message = e.message,
                    cause = e
                )
            }
            val entityList = result.getOrNull()!!.map { it.retrofitModelToEntity() }
            return Result.success(entityList)
        } catch (e: Exception) {
            return resultFailure(
                context = "IRLocalFluxoRepository.recoverAll",
                message = "-",
                cause = e
            )
        }
    }

}