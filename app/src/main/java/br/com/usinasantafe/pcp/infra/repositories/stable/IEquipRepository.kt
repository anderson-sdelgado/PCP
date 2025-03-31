package br.com.usinasantafe.pcp.infra.repositories.stable

import br.com.usinasantafe.pcp.domain.entities.stable.Equip
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcp.infra.datasource.room.stable.EquipRoomDatasource
import br.com.usinasantafe.pcp.infra.datasource.retrofit.stable.EquipRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.pcp.infra.models.room.stable.entityToRoomModel
import br.com.usinasantafe.pcp.infra.models.room.stable.roomModelToEntity

class IEquipRepository(
    private val equipRoomDatasource: EquipRoomDatasource,
    private val equipRetrofitDatasource: EquipRetrofitDatasource
): EquipRepository {
    
    override suspend fun addAll(list: List<Equip>): Result<Boolean> {
        try {
            val equipModelList = list.map { it.entityToRoomModel() }
            val result = equipRoomDatasource.addAll(equipModelList)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IEquipRepository.addAll",
                    message = e.message,
                    cause = e
                )
            }
            return result
        } catch (e: Exception){
            return resultFailure(
                context = "IEquipRepository.add",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun checkNro(nroEquip: Long): Result<Boolean> {
        val result = equipRoomDatasource.checkNro(nroEquip)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IEquipRepository.checkNro",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun deleteAll(): Result<Boolean> {
        val result = equipRoomDatasource.deleteAll()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IEquipRepository.deleteAll",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun get(idEquip: Int): Result<Equip> {
        try{
            val result = equipRoomDatasource.get(idEquip)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IEquipRepository.get",
                    message = e.message,
                    cause = e
                )
            }
            val entity = result.getOrNull()!!.roomModelToEntity()
            return Result.success(entity)
        } catch (e: Exception) {
            return resultFailure(
                context = "IEquipRepository.get",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getId(nroEquip: Long): Result<Int> {
        try{
            val result = equipRoomDatasource.getId(nroEquip)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IEquipRepository.getId",
                    message = e.message,
                    cause = e
                )
            }
            val id = result.getOrNull()!!
            if (id == 0)
                return resultFailure(
                    context = "IEquipRepository.getId",
                    message = "-",
                    cause = Exception("Id is 0")
                )
            return Result.success(result.getOrNull()!!)
        } catch (e: Exception) {
            return resultFailure(
                context = "IEquipRepository.getId",
                message = "-",
                cause = Exception("Id is 0")
            )
        }
    }

    override suspend fun getNro(idEquip: Int): Result<Long> {
        try{
            val result = equipRoomDatasource.getNro(idEquip)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IEquipRepository.getNro",
                    message = e.message,
                    cause = e
                )
            }
            val id = result.getOrNull()!!
            if (id == 0L)
                return resultFailure(
                    context = "IEquipRepository.getNro",
                    message = "-",
                    cause = Exception("Nro is 0")
                )
            return Result.success(result.getOrNull()!!)
        } catch (e: Exception) {
            return resultFailure(
                context = "IEquipRepository.getNro",
                message = e.message,
                cause = e
            )
        }
    }

    override suspend fun getDescr(idEquip: Int): Result<String> {
        try{
            val result = equipRoomDatasource.get(idEquip)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IEquipRepository.getDescr",
                    message = e.message,
                    cause = e
                )
            }
            val entity = result.getOrNull()!!.roomModelToEntity()
            return Result.success("${entity.nroEquip} - ${entity.descrEquip}")
        } catch (e: Exception) {
            return resultFailure(
                context = "IEquipRepository.getDescr",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun recoverAll(token: String): Result<List<Equip>> {
        try {
            val result =  equipRetrofitDatasource.recoverAll(token)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IEquipRepository.recoverAll",
                    message = e.message,
                    cause = e
                )
            }
            val entityList = result.getOrNull()!!.map { it.retrofitModelToEntity() }
            return Result.success(entityList)
        } catch (e: Exception) {
            return resultFailure(
                context = "IEquipRepository.recoverAll",
                message = "-",
                cause = e
            )
        }
    }
}