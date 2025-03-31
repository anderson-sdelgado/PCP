package br.com.usinasantafe.pcp.infra.repositories.stable

import br.com.usinasantafe.pcp.domain.entities.stable.Terceiro
import br.com.usinasantafe.pcp.domain.repositories.stable.TerceiroRepository
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.infra.datasource.room.stable.TerceiroRoomDatasource
import br.com.usinasantafe.pcp.infra.datasource.retrofit.stable.TerceiroRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.pcp.infra.models.room.stable.entityToRoomModel
import br.com.usinasantafe.pcp.infra.models.room.stable.roomModelToEntity

class ITerceiroRepository(
    private val terceiroRoomDatasource: TerceiroRoomDatasource,
    private val terceiroRetrofitDatasource: TerceiroRetrofitDatasource
): TerceiroRepository {

    override suspend fun addAll(list: List<Terceiro>): Result<Boolean> {
        try {
            val terceiroModelList = list.map { it.entityToRoomModel() }
            val result = terceiroRoomDatasource.addAll(terceiroModelList)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "ITerceiroRepository.addAll",
                    message = e.message,
                    cause = e
                )
            }
            return result
        } catch (e: Exception){
            return resultFailure(
                context = "ITerceiroRepository.add",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun checkCPF(cpf: String): Result<Boolean> {
        val result = terceiroRoomDatasource.checkCpf(cpf)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ITerceiroRepository.checkCPF",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun deleteAll(): Result<Boolean> {
        val result = terceiroRoomDatasource.deleteAll()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ITerceiroRepository.deleteAll",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun get(id: Int): Result<Terceiro> {
        try {
            val result = terceiroRoomDatasource.get(id)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "ITerceiroRepository.get",
                    message = e.message,
                    cause = e
                )
            }
            val list = result.getOrNull()!!
            return Result.success(list[0].roomModelToEntity())
        } catch (e: Exception) {
            return resultFailure(
                context = "ITerceiroRepository.get",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getCpf(id: Int): Result<String> {
        try {
            val result = terceiroRoomDatasource.get(id)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "ITerceiroRepository.getCpf",
                    message = e.message,
                    cause = e
                )
            }
            val list = result.getOrNull()!!
            return Result.success(list[0].roomModelToEntity().cpfTerceiro)
        } catch (e: Exception) {
            return resultFailure(
                context = "ITerceiroRepository.getCpf",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getId(cpf: String): Result<Int> {
        try {
            val result = terceiroRoomDatasource.get(cpf)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "ITerceiroRepository.getId",
                    message = e.message,
                    cause = e
                )
            }
            val list = result.getOrNull()!!
            return Result.success(list[0].roomModelToEntity().idBDTerceiro)
        } catch (e: Exception) {
            return resultFailure(
                context = "ITerceiroRepository.getId",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getNome(cpf: String): Result<String> {
        try {
            val result = terceiroRoomDatasource.get(cpf)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "ITerceiroRepository.getNome",
                    message = e.message,
                    cause = e
                )
            }
            val list = result.getOrNull()!!
            return Result.success(list[0].roomModelToEntity().nomeTerceiro)
        } catch (e: Exception) {
            return resultFailure(
                context = "ITerceiroRepository.getId",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getEmpresas(cpf: String): Result<String> {
        try {
            val result = terceiroRoomDatasource.get(cpf)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "ITerceiroRepository.getEmpresas",
                    message = e.message,
                    cause = e
                )
            }
            val list = result.getOrNull()!!
            var empresas = ""
            for(roomModel in list) {
                if(empresas != ""){
                    empresas += "\n"
                }
                empresas += roomModel.roomModelToEntity().empresaTerceiro
            }
            return Result.success(empresas)
        } catch (e: Exception) {
            return resultFailure(
                context = "ITerceiroRepository.getEmpresas",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun recoverAll(token: String): Result<List<Terceiro>> {
        try {
            val result = terceiroRetrofitDatasource.recoverAll(token)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "ITerceiroRepository.recoverAll",
                    message = e.message,
                    cause = e
                )
            }
            val entityList = result.getOrNull()!!.map { it.retrofitModelToEntity() }
            return Result.success(entityList)
        } catch (e: Exception) {
            return resultFailure(
                context = "ITerceiroRepository.recoverAll",
                message = "-",
                cause = e
            )
        }
    }

}