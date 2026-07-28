package br.com.usinasantafe.pcp.infra.repositories.stable

import br.com.usinasantafe.pcp.domain.entities.stable.Equip
import br.com.usinasantafe.pcp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcp.infra.datasource.room.stable.EquipRoomDatasource
import br.com.usinasantafe.pcp.infra.datasource.retrofit.stable.EquipRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.pcp.infra.models.room.stable.entityToRoomModel
import br.com.usinasantafe.pcp.infra.models.room.stable.roomModelToEntity
import br.com.usinasantafe.pcp.utils.EmptyResult
import br.com.usinasantafe.pcp.utils.call
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import javax.inject.Inject

class IEquipRepository @Inject constructor(
    private val equipRoomDatasource: EquipRoomDatasource,
    private val equipRetrofitDatasource: EquipRetrofitDatasource
): EquipRepository {
    
    override suspend fun addAll(list: List<Equip>): EmptyResult =
        call(getClassAndMethod()) {
            val equipModelList = list.map { it.entityToRoomModel() }
            equipRoomDatasource.addAll(equipModelList)
        }

    override suspend fun hasNro(nroEquip: Long): Result<Boolean> =
        call(getClassAndMethod()) {
            equipRoomDatasource.hasNro(nroEquip).getOrThrow()
        }

    override suspend fun deleteAll(): EmptyResult =
        call(getClassAndMethod()) {
            equipRoomDatasource.deleteAll().getOrThrow()
        }

    override suspend fun getById(idEquip: Int): Result<Equip> =
        call(getClassAndMethod()) {
            val model = equipRoomDatasource.getById(idEquip).getOrThrow()
            model.roomModelToEntity()
        }

    override suspend fun getIdByNro(nroEquip: Long): Result<Int> =
        call(getClassAndMethod()) {
            val id = equipRoomDatasource.getIdByNro(nroEquip).getOrThrow()
            if (id == 0) throw Exception("Id is 0")
            id
        }

    override suspend fun getNroById(idEquip: Int): Result<Long> =
        call(getClassAndMethod()) {
            val nro = equipRoomDatasource.getNroById(idEquip).getOrThrow()
            if (nro == 0L) throw Exception("Nro is 0")
            nro
        }

    override suspend fun getDescrById(idEquip: Int): Result<String> =
        call(getClassAndMethod()) {
            val model = equipRoomDatasource.getById(idEquip).getOrThrow()
            val entity = model.roomModelToEntity()
            "${entity.nroEquip} - ${entity.descrEquip}"
        }

    override suspend fun listAll(token: String): Result<List<Equip>> =
        call(getClassAndMethod()) {
            val modelList = equipRetrofitDatasource.listAll(token).getOrThrow()
            modelList.map { it.retrofitModelToEntity() }
        }
}