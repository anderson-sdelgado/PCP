package br.com.usinasantafe.pcp.infra.repositories.stable

import br.com.usinasantafe.pcp.domain.entities.stable.Visitante
import br.com.usinasantafe.pcp.domain.repositories.stable.VisitanteRepository
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.infra.datasource.room.stable.VisitanteRoomDatasource
import br.com.usinasantafe.pcp.infra.datasource.retrofit.stable.VisitanteRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.pcp.infra.models.room.stable.roomModelToEntity
import br.com.usinasantafe.pcp.infra.models.room.stable.entityToRoomModel

class IVisitanteRepository(
    private val visitanteRoomDatasource: VisitanteRoomDatasource,
    private val visitanteRetrofitDatasource: VisitanteRetrofitDatasource
): VisitanteRepository {
    
    override suspend fun addAll(list: List<Visitante>): Result<Boolean> {
        try {
            val visitanteModelList = list.map { it.entityToRoomModel() }
            val result = visitanteRoomDatasource.addAll(visitanteModelList)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IVisitanteRepository.addAll",
                    message = e.message,
                    cause = e
                )
            }
            return result
        } catch (e: Exception){
            return resultFailure(
                context = "IVisitanteRepository.addAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun checkCPF(cpf: String): Result<Boolean> {
        val result = visitanteRoomDatasource.checkCpf(cpf)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IVisitanteRepository.checkCPF",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun deleteAll(): Result<Boolean> {
        val result = visitanteRoomDatasource.deleteAll()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IVisitanteRepository.deleteAll",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun get(id: Int): Result<Visitante> {
        try {
            val result = visitanteRoomDatasource.get(id)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IVisitanteRepository.get",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(result.getOrNull()!!.roomModelToEntity())
        } catch (e: Exception) {
            return resultFailure(
                context = "IVisitanteRepository.get",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getCpf(id: Int): Result<String> {
        try {
            val result = visitanteRoomDatasource.get(id)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IVisitanteRepository.getCpf",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(result.getOrNull()!!.roomModelToEntity().cpfVisitante)
        } catch (e: Exception) {
            return resultFailure(
                context = "IVisitanteRepository.getCpf",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getId(cpf: String): Result<Int> {
        try {
            val result = visitanteRoomDatasource.get(cpf)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IVisitanteRepository.getId",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(result.getOrNull()!!.roomModelToEntity().idVisitante)
        } catch (e: Exception) {
            return resultFailure(
                context = "IVisitanteRepository.getId",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getNome(cpf: String): Result<String> {
        try {
            val result = visitanteRoomDatasource.get(cpf)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IVisitanteRepository.getNome",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(result.getOrNull()!!.roomModelToEntity().nomeVisitante)
        } catch (e: Exception) {
            return resultFailure(
                context = "IVisitanteRepository.getNome",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getEmpresas(cpf: String): Result<String> {
        try {
            val result = visitanteRoomDatasource.get(cpf)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IVisitanteRepository.getEmpresas",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(result.getOrNull()!!.roomModelToEntity().empresaVisitante)
        } catch (e: Exception) {
            return resultFailure(
                context = "IVisitanteRepository.getEmpresas",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun recoverAll(token: String): Result<List<Visitante>> {
        try {
            val result = visitanteRetrofitDatasource.recoverAll(token)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IVisitanteRepository.recoverAll",
                    message = e.message,
                    cause = e
                )
            }
            val entityList = result.getOrNull()!!.map { it.retrofitModelToEntity() }
            return Result.success(entityList)
        } catch (e: Exception) {
            return resultFailure(
                context = "IVisitanteRepository.recoverAll",
                message = "-",
                cause = e
            )
        }
    }

}