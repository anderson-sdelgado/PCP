package br.com.usinasantafe.pcp.infra.repositories.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipProprioPassag
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioPassagRepository
import br.com.usinasantafe.pcp.infra.datasource.room.variable.MovEquipProprioPassagRoomDatasource
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.MovEquipProprioPassagSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipProprioPassagRoomModel
import br.com.usinasantafe.pcp.infra.models.room.variable.modelRoomToEntity
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.utils.EmptyResult
import br.com.usinasantafe.pcp.utils.call
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import javax.inject.Inject

class IMovEquipProprioPassagRepository @Inject constructor(
    private val movEquipProprioPassagSharedPreferencesDatasource: MovEquipProprioPassagSharedPreferencesDatasource,
    private val movEquipProprioPassagRoomDatasource: MovEquipProprioPassagRoomDatasource,
) : MovEquipProprioPassagRepository {

    override suspend fun add(matricColab: Int, flowApp: FlowApp, id: Int): EmptyResult =
        call(getClassAndMethod()) {
            when (flowApp) {
                FlowApp.ADD -> movEquipProprioPassagSharedPreferencesDatasource.add(matricColab).getOrThrow()
                FlowApp.CHANGE -> movEquipProprioPassagRoomDatasource.add(matricColab, id).getOrThrow()
            }
        }

    override suspend fun clean(): EmptyResult =
        call(getClassAndMethod()) {
            movEquipProprioPassagSharedPreferencesDatasource.clean().getOrThrow()
        }

    override suspend fun delete(id: Int): EmptyResult =
        call(getClassAndMethod()) {
            movEquipProprioPassagRoomDatasource.delete(id).getOrThrow()
        }

    override suspend fun delete(matricColab: Int, flowApp: FlowApp, id: Int): EmptyResult =
        call(getClassAndMethod()) {
            when (flowApp) {
                FlowApp.ADD -> movEquipProprioPassagSharedPreferencesDatasource.delete(matricColab).getOrThrow()
                FlowApp.CHANGE -> movEquipProprioPassagRoomDatasource.delete(matricColab, id).getOrThrow()
            }
        }

    override suspend fun list(flowApp: FlowApp, id: Int): Result<List<MovEquipProprioPassag>> =
        call(getClassAndMethod()) {
            when (flowApp) {
                FlowApp.ADD -> {
                    val list = movEquipProprioPassagSharedPreferencesDatasource.list().getOrThrow()
                    list.map { MovEquipProprioPassag(matricColab = it) }
                }
                FlowApp.CHANGE -> {
                    val list = movEquipProprioPassagRoomDatasource.list(id).getOrThrow()
                    list.map { it.modelRoomToEntity() }
                }
            }
        }

    override suspend fun save(id: Int): EmptyResult =
        call(getClassAndMethod()) {
            val list = movEquipProprioPassagSharedPreferencesDatasource.list().getOrThrow()
            val modelRoomList = list.map {
                MovEquipProprioPassagRoomModel(
                    idMovEquipProprio = id,
                    matricColab = it
                )
            }
            movEquipProprioPassagRoomDatasource.addAll(modelRoomList).getOrThrow()
        }

}