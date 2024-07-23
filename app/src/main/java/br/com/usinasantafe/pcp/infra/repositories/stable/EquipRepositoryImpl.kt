package br.com.usinasantafe.pcp.infra.repositories.stable

import br.com.usinasantafe.pcp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcp.infra.datasource.room.stable.EquipRoomDatasource
import br.com.usinasantafe.pcp.infra.datasource.webservice.stable.EquipRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.room.stable.toEquip
import br.com.usinasantafe.pcp.infra.models.room.stable.toEquipModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EquipRepositoryImpl @Inject constructor(
    private val equipRoomDatasource: EquipRoomDatasource,
    private val equipRetrofitDatasource: EquipRetrofitDatasource,
): EquipRepository {

    override suspend fun addAllEquip(list: List<br.com.usinasantafe.pcp.domain.entities.stable.Equip>) {
        equipRoomDatasource.addAllEquip(*list.map { it.toEquipModel() }.toTypedArray())
    }

    override suspend fun deleteAllEquip() {
        equipRoomDatasource.deleteAllEquip()
    }

    override suspend fun recoverAllEquip(token: String): Flow<Result<List<br.com.usinasantafe.pcp.domain.entities.stable.Equip>>> = flow {
        equipRetrofitDatasource.getAllEquip(token)
            .catch { exception -> emit(Result.failure(exception)) }
            .collect { result ->
                result.fold(
                    onSuccess = { equipModelList ->
                        emit(Result.success(equipModelList.map { it.toEquip() }))
                    },
                    onFailure = { exception -> emit(Result.failure(exception)) }
                )
            }
    }

    override suspend fun checkEquipNro(nro: Long): Boolean {
        return equipRoomDatasource.checkEquipNro(nro)
    }

    override suspend fun getEquipNro(nro: Long): br.com.usinasantafe.pcp.domain.entities.stable.Equip {
        return equipRoomDatasource.getEquipNro(nro).toEquip()
    }

    override suspend fun getEquipId(id: Long): br.com.usinasantafe.pcp.domain.entities.stable.Equip {
        return equipRoomDatasource.getEquipId(id).toEquip()
    }

}