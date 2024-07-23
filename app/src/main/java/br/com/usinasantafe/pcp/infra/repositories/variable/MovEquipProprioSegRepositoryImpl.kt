package br.com.usinasantafe.pcp.infra.repositories.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipProprioSeg
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioSegRepository
import br.com.usinasantafe.pcp.infra.datasource.room.variable.MovEquipProprioSegDatasourceRoom
import br.com.usinasantafe.pcp.infra.datasource.sharedpreferences.MovEquipProprioSegSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipProprioSegRoomModel
import br.com.usinasantafe.pcp.infra.models.room.variable.entityToMovEquipProprioSegRoomModel
import br.com.usinasantafe.pcp.infra.models.room.variable.modelRoomToMovEquipSegPassag
import javax.inject.Inject

class MovEquipProprioSegRepositoryImpl @Inject constructor (
    private val movEquipProprioSegSharedPreferencesDatasource: MovEquipProprioSegSharedPreferencesDatasource,
    private val movEquipProprioSegDatasourceRoom: MovEquipProprioSegDatasourceRoom,
) : MovEquipProprioSegRepository {

    override suspend fun addEquipSeg(idEquip: Long): Boolean {
        return try {
            movEquipProprioSegSharedPreferencesDatasource.addEquipSeg(idEquip)
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun addEquipSeg(idEquip: Long, idMov: Long): Boolean {
        return movEquipProprioSegDatasourceRoom.addMovEquipProprioSeg(MovEquipProprioSegRoomModel(idMovEquipProprio = idMov, idEquipMovEquipProprioSeg = idEquip))
    }

    override suspend fun clearEquipSeg(): Boolean {
        return try {
            movEquipProprioSegSharedPreferencesDatasource.clearEquipSeg()
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun deleteEquipSeg(pos: Int): Boolean {
        return try {
            movEquipProprioSegSharedPreferencesDatasource.deleteEquipSeg(pos)
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun deleteEquipSeg(pos: Int, idMov: Long): Boolean {
        return try {
            val equipSeg = movEquipProprioSegDatasourceRoom.listMovEquipProprioSegIdMov(idMov)[pos]
            movEquipProprioSegDatasourceRoom.deleteMovEquipProprioSeg(equipSeg)
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun deleteEquipSeg(idMov: Long): Boolean {
        try {
            val equipSegList = movEquipProprioSegDatasourceRoom.listMovEquipProprioSegIdMov(idMov)
            for(equipSeg in equipSegList){
                movEquipProprioSegDatasourceRoom.deleteMovEquipProprioSeg(equipSeg)
            }
        } catch (exception: Exception) {
            return false
        }
        return true
    }

    override suspend fun listEquipSeg(): List<MovEquipProprioSeg> {
        return movEquipProprioSegSharedPreferencesDatasource.listEquipSeg().map {
            MovEquipProprioSeg(
                idEquipMovEquipProprioSeg = it
            )
        }
    }

    override suspend fun listEquipSeg(idMov: Long): List<MovEquipProprioSeg> {
        return movEquipProprioSegDatasourceRoom.listMovEquipProprioSegIdMov(idMov).map { it.modelRoomToMovEquipSegPassag() }
    }

    override suspend fun saveEquipSeg(idMov: Long): Boolean {
        if(!movEquipProprioSegDatasourceRoom.addAllMovEquipProprioSeg(*listEquipSeg().map {
            it.entityToMovEquipProprioSegRoomModel(idMov)
        }.toTypedArray())) return false
        return movEquipProprioSegSharedPreferencesDatasource.clearEquipSeg()
    }

}