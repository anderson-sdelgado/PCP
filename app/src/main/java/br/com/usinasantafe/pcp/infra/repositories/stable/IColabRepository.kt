package br.com.usinasantafe.pcp.infra.repositories.stable

import br.com.usinasantafe.pcp.domain.entities.stable.Colab
import br.com.usinasantafe.pcp.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.infra.datasource.room.stable.ColabRoomDatasource
import br.com.usinasantafe.pcp.infra.datasource.retrofit.stable.ColabRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.pcp.infra.models.room.stable.entityToRoomModel

class IColabRepository(
    private val colabRoomDatasource: ColabRoomDatasource,
    private val colabRetrofitDatasource: ColabRetrofitDatasource
): ColabRepository {

    override suspend fun addAll(list: List<Colab>): Result<Boolean> {
        try {
            val colabModelList = list.map { it.entityToRoomModel() }
            val result = colabRoomDatasource.addAll(colabModelList)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IColabRepository.addAll",
                    message = e.message,
                    cause = e
                )
            }
            return result
        } catch (e: Exception){
            return resultFailure(
                context = "IColabRepository.add",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun checkMatric(matric: Int): Result<Boolean> {
        val result = colabRoomDatasource.checkMatric(matric)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IColabRepository.checkMatric",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun deleteAll(): Result<Boolean> {
        val result = colabRoomDatasource.deleteAll()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IColabRepository.deleteAll",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun getNome(matric: Int): Result<String> {
        val result = colabRoomDatasource.getNome(matric)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IColabRepository.getNome",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun recoverAll(token: String): Result<List<Colab>> {
        try {
            val recoverAll = colabRetrofitDatasource.recoverAll(token)
            if (recoverAll.isFailure) {
                val e = recoverAll.exceptionOrNull()!!
                return resultFailure(
                    context = "IColabRepository.recoverAll",
                    message = e.message,
                    cause = e
                )
            }
            val resultAll = recoverAll.getOrNull()!!
            val entityList = resultAll.map { it.retrofitModelToEntity() }
            return Result.success(entityList)
        } catch (e: Exception) {
            return resultFailure(
                context = "IColabRepository.recoverAll",
                message = "-",
                cause = e
            )
        }
    }

}