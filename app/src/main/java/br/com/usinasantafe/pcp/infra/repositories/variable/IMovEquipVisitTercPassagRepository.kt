package br.com.usinasantafe.pcp.infra.repositories.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipVisitTercPassag
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import br.com.usinasantafe.pcp.infra.datasource.room.variable.MovEquipVisitTercPassagRoomDatasource
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.MovEquipVisitTercPassagSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipVisitTercPassagRoomModel
import br.com.usinasantafe.pcp.infra.models.room.variable.modelRoomToEntity
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.utils.EmptyResult
import br.com.usinasantafe.pcp.utils.call
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import javax.inject.Inject

class IMovEquipVisitTercPassagRepository @Inject constructor(
    private val movEquipVisitTercPassagSharedPreferencesDatasource: MovEquipVisitTercPassagSharedPreferencesDatasource,
    private val movEquipVisitTercPassagRoomDatasource: MovEquipVisitTercPassagRoomDatasource,
): MovEquipVisitTercPassagRepository {

    override suspend fun add(idVisitTerc: Int, flowApp: FlowApp, id: Int): EmptyResult =
        call(getClassAndMethod()) {
            when(flowApp) {
                FlowApp.ADD -> movEquipVisitTercPassagSharedPreferencesDatasource.add(idVisitTerc).getOrThrow()
                FlowApp.CHANGE -> movEquipVisitTercPassagRoomDatasource.add(idVisitTerc, id).getOrThrow()
            }
        }

    override suspend fun clean(): EmptyResult =
        call(getClassAndMethod()) {
            movEquipVisitTercPassagSharedPreferencesDatasource.clean().getOrThrow()
        }

    override suspend fun delete(id: Int): EmptyResult =
        call(getClassAndMethod()) {
            movEquipVisitTercPassagRoomDatasource.delete(id).getOrThrow()
        }

    override suspend fun delete(idVisitTerc: Int, flowApp: FlowApp, id: Int): EmptyResult =
        call(getClassAndMethod()) {
            when (flowApp) {
                FlowApp.ADD -> movEquipVisitTercPassagSharedPreferencesDatasource.delete(idVisitTerc).getOrThrow()
                FlowApp.CHANGE -> movEquipVisitTercPassagRoomDatasource.delete(idVisitTerc, id).getOrThrow()
            }
        }

    override suspend fun list(flowApp: FlowApp, id: Int): Result<List<MovEquipVisitTercPassag>> =
        call(getClassAndMethod()) {
            when(flowApp) {
                FlowApp.ADD -> {
                    val list = movEquipVisitTercPassagSharedPreferencesDatasource.list().getOrThrow()
                    list.map { MovEquipVisitTercPassag(idVisitTerc = it) }
                }
                FlowApp.CHANGE -> {
                    val list = movEquipVisitTercPassagRoomDatasource.list(id).getOrThrow()
                    list.map { it.modelRoomToEntity() }
                }
            }
        }

    override suspend fun save(id: Int): EmptyResult =
        call(getClassAndMethod()) {
            val list = movEquipVisitTercPassagSharedPreferencesDatasource.list().getOrThrow()
            val modelRoomList = list.map {
                MovEquipVisitTercPassagRoomModel(
                    idMovEquipVisitTerc = id,
                    idVisitTerc = it
                )
            }
            movEquipVisitTercPassagRoomDatasource.addAll(modelRoomList).getOrThrow()
        }
}