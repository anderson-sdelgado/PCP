package br.com.usinasantafe.pcp.infra.repositories.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipProprioEquipSeg
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioEquipSegRepository
import br.com.usinasantafe.pcp.infra.datasource.room.variable.MovEquipProprioEquipSegRoomDatasource
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.MovEquipProprioEquipSegSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipProprioEquipSegRoomModel
import br.com.usinasantafe.pcp.infra.models.room.variable.modelRoomToEntity
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.utils.EmptyResult
import br.com.usinasantafe.pcp.utils.call
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import javax.inject.Inject

class IMovEquipProprioEquipSegRepository @Inject constructor(
    private val movEquipProprioEquipSegSharedPreferencesDatasource: MovEquipProprioEquipSegSharedPreferencesDatasource,
    private val movEquipProprioEquipSegRoomDatasource: MovEquipProprioEquipSegRoomDatasource
) : MovEquipProprioEquipSegRepository {

    override suspend fun add(idEquip: Int, flowApp: FlowApp, id: Int): EmptyResult =
        call(getClassAndMethod()) {
            when (flowApp) {
                FlowApp.ADD -> movEquipProprioEquipSegSharedPreferencesDatasource.add(idEquip).getOrThrow()
                FlowApp.CHANGE -> movEquipProprioEquipSegRoomDatasource.add(idEquip, id).getOrThrow()
            }
        }

    override suspend fun clean(): EmptyResult =
        call(getClassAndMethod()) {
            movEquipProprioEquipSegSharedPreferencesDatasource.clean().getOrThrow()
        }

    override suspend fun delete(id: Int): EmptyResult =
        call(getClassAndMethod()) {
            movEquipProprioEquipSegRoomDatasource.delete(id).getOrThrow()
        }

    override suspend fun list(flowApp: FlowApp, id: Int): Result<List<MovEquipProprioEquipSeg>> =
        call(getClassAndMethod()) {
            when(flowApp) {
                FlowApp.ADD -> {
                    val list = movEquipProprioEquipSegSharedPreferencesDatasource.list().getOrThrow()
                    list.map { MovEquipProprioEquipSeg(idEquip = it) }
                }
                FlowApp.CHANGE -> {
                    val list = movEquipProprioEquipSegRoomDatasource.list(id).getOrThrow()
                    list.map { it.modelRoomToEntity() }
                }
            }
        }

    override suspend fun delete(idEquip: Int, flowApp: FlowApp, id: Int): EmptyResult =
        call(getClassAndMethod()) {
            when (flowApp) {
                FlowApp.ADD -> movEquipProprioEquipSegSharedPreferencesDatasource.delete(idEquip).getOrThrow()
                FlowApp.CHANGE -> movEquipProprioEquipSegRoomDatasource.delete(idEquip, id).getOrThrow()
            }
        }

    override suspend fun save(id: Int): EmptyResult =
        call(getClassAndMethod()) {
            val list = movEquipProprioEquipSegSharedPreferencesDatasource.list().getOrThrow()
            val movEquipProprioEquipSegRoomModelList = list.map {
                MovEquipProprioEquipSegRoomModel(
                    idMovEquipProprio = id,
                    idEquip = it
                )
            }
            movEquipProprioEquipSegRoomDatasource.addAll(movEquipProprioEquipSegRoomModelList).getOrThrow()
        }

}