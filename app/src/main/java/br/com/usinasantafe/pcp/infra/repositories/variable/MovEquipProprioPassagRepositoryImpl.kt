package br.com.usinasantafe.pcp.infra.repositories.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipProprioPassag
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioPassagRepository
import br.com.usinasantafe.pcp.infra.datasource.room.variable.MovEquipProprioPassagDatasourceRoom
import br.com.usinasantafe.pcp.infra.datasource.sharedpreferences.MovEquipProprioPassagSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipProprioPassagRoomModel
import br.com.usinasantafe.pcp.infra.models.room.variable.modelRoomToMovEquipProprioPassag
import br.com.usinasantafe.pcp.infra.models.room.variable.entityToMovEquipProprioPassagRoomModel
import javax.inject.Inject

class MovEquipProprioPassagRepositoryImpl @Inject constructor (
    private val movEquipProprioPassagSharedPreferencesDatasource: MovEquipProprioPassagSharedPreferencesDatasource,
    private val movEquipProprioPassagDatasourceRoom: MovEquipProprioPassagDatasourceRoom,
) : MovEquipProprioPassagRepository {

    override suspend fun addPassag(nroMatric: Long): Boolean {
        return try {
            movEquipProprioPassagSharedPreferencesDatasource.addPassag(nroMatric)
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun addPassag(nroMatric: Long, idMov: Long): Boolean {
        return try {
            movEquipProprioPassagDatasourceRoom.addMovEquipProprioPassag(
                MovEquipProprioPassagRoomModel(idMovEquipProprio = idMov, nroMatricMovEquipProprioPassag = nroMatric)
            )
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun clearPassag(): Boolean {
        return try {
            movEquipProprioPassagSharedPreferencesDatasource.clearPassag()
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun deletePassag(pos: Int): Boolean {
        return try {
            movEquipProprioPassagSharedPreferencesDatasource.deletePassag(pos)
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun deletePassag(pos: Int, idMov: Long): Boolean {
        return try {
            val passag = movEquipProprioPassagDatasourceRoom.listMovEquipProprioPassagIdMov(idMov)[pos]
            movEquipProprioPassagDatasourceRoom.deleteMovEquipProprioPassag(passag)
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun deletePassag(idMov: Long): Boolean {
        try {
            val passagList = movEquipProprioPassagDatasourceRoom.listMovEquipProprioPassagIdMov(idMov)
            for(passag in passagList){
                movEquipProprioPassagDatasourceRoom.deleteMovEquipProprioPassag(passag)
            }
        } catch (exception: Exception) {
            return false
        }
        return true
    }

    override suspend fun listPassag(): List<MovEquipProprioPassag> {
        return movEquipProprioPassagSharedPreferencesDatasource.listPassag().map {
            MovEquipProprioPassag(
                nroMatricMovEquipProprioPassag = it
            )
        }
    }

    override suspend fun listPassag(idMov: Long): List<MovEquipProprioPassag> {
        return movEquipProprioPassagDatasourceRoom.listMovEquipProprioPassagIdMov(idMov).map { it.modelRoomToMovEquipProprioPassag() }
    }

    override suspend fun savePassag(idMov: Long): Boolean {
        if(!movEquipProprioPassagDatasourceRoom.addAllMovEquipProprioPassag(*listPassag().map {
                it.entityToMovEquipProprioPassagRoomModel(idMov)
        }.toTypedArray())) return false
        return movEquipProprioPassagSharedPreferencesDatasource.clearPassag()
    }

}