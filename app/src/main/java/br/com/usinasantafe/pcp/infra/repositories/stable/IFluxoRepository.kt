package br.com.usinasantafe.pcp.infra.repositories.stable

import br.com.usinasantafe.pcp.domain.entities.stable.Fluxo
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.FluxoRepository
import br.com.usinasantafe.pcp.infra.datasource.retrofit.stable.FluxoRetrofitDatasource
import br.com.usinasantafe.pcp.infra.datasource.room.stable.FluxoRoomDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.pcp.infra.models.room.stable.entityToRoomModel
import br.com.usinasantafe.pcp.infra.models.room.stable.roomModelToEntity

class IFluxoRepository(
    private val fluxoRoomDatasource: FluxoRoomDatasource,
    private val fluxoRetrofitDatasource: FluxoRetrofitDatasource
): FluxoRepository {

    override suspend fun addAll(list: List<Fluxo>): Result<Boolean> {
        try {
            val roomModelList = list.map { it.entityToRoomModel() }
            val result = fluxoRoomDatasource.addAll(roomModelList)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IFluxoRepository.add",
                    message = e.message,
                    cause = e
                )
            }
            return result
        } catch (e: Exception){
            return resultFailure(
                context = "IFluxoRepository.add",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        val result = fluxoRoomDatasource.deleteAll()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IFluxoRepository.deleteAll",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun get(id: Int): Result<Fluxo> {
        try {
            val result = fluxoRoomDatasource.get(id).map { it.roomModelToEntity() }
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IFluxoRepository.get",
                    message = e.message,
                    cause = e
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = "IFluxoRepository.get",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun recoverAll(token: String): Result<List<Fluxo>> {
        try {
            val result = fluxoRetrofitDatasource.recoverAll(token)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IFluxoRepository.recoverAll",
                    message = e.message,
                    cause = e
                )
            }
            val entityList = result.getOrNull()!!.map { it.retrofitModelToEntity() }
            return Result.success(entityList)
        } catch (e: Exception) {
            return resultFailure(
                context = "IFluxoRepository.recoverAll",
                message = "-",
                cause = e
            )
        }
    }

}