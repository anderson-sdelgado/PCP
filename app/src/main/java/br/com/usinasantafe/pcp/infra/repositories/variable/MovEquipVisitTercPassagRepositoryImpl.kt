package br.com.usinasantafe.pcp.infra.repositories.variable

import android.util.Log
import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipVisitTercPassag
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import br.com.usinasantafe.pcp.infra.datasource.room.variable.MovEquipVisitTercPassagDatasourceRoom
import br.com.usinasantafe.pcp.infra.datasource.sharedpreferences.MovEquipVisitTercPassagSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipVisitTercPassagRoomModel
import br.com.usinasantafe.pcp.infra.models.room.variable.entityToMovEquipVisitTercPassagRoomModel
import br.com.usinasantafe.pcp.infra.models.room.variable.modelRoomToMovEquipVisitTercPassag
import javax.inject.Inject

class MovEquipVisitTercPassagRepositoryImpl @Inject constructor (
    private val movEquipVisitTercPassagSharedPreferencesDatasource: MovEquipVisitTercPassagSharedPreferencesDatasource,
    private val movEquipVisitTercPassagDatasourceRoom: MovEquipVisitTercPassagDatasourceRoom,
) : MovEquipVisitTercPassagRepository {

    override suspend fun addPassag(idVisitTerc: Long): Boolean {
        return try {
            movEquipVisitTercPassagSharedPreferencesDatasource.addPassag(idVisitTerc)
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun addPassag(idVisitTerc: Long, idMov: Long): Boolean {
        return try {
            movEquipVisitTercPassagDatasourceRoom.addMovEquipVisitTercPassag(
                MovEquipVisitTercPassagRoomModel(idMovEquipVisitTerc = idMov, idVisitTercMovEquipVisitTercPassag = idVisitTerc)
            )
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun deletePassag(pos: Int): Boolean {
        return try {
            movEquipVisitTercPassagSharedPreferencesDatasource.deletePassag(pos)
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun deletePassag(pos: Int, idMov: Long): Boolean {
        return try {
            val passag = movEquipVisitTercPassagDatasourceRoom.listMovEquipVisitTercPassagIdMov(idMov)[pos]
            movEquipVisitTercPassagDatasourceRoom.deleteMovEquipVisitTercPassag(passag)
        } catch (exception: Exception) {
            Log.i("PCP", "Erro = ${exception.toString()}")
            false
        }
    }

    override suspend fun deletePassag(idMov: Long): Boolean {
        try {
            val passagList = movEquipVisitTercPassagDatasourceRoom.listMovEquipVisitTercPassagIdMov(idMov)
            for (passag in passagList) {
                movEquipVisitTercPassagDatasourceRoom.deleteMovEquipVisitTercPassag(passag)
            }
        } catch (exception: Exception) {
            return false
        }
        return true
    }

    override suspend fun listPassag(): List<MovEquipVisitTercPassag> {
        return movEquipVisitTercPassagSharedPreferencesDatasource.listPassag().map {
            MovEquipVisitTercPassag(
                idVisitTercMovEquipVisitTercPassag = it
            )
        }
    }

    override suspend fun listPassag(idMov: Long): List<MovEquipVisitTercPassag> {
        return movEquipVisitTercPassagDatasourceRoom.listMovEquipVisitTercPassagIdMov(idMov).map { it.modelRoomToMovEquipVisitTercPassag() }
    }

    override suspend fun savePassag(idMov: Long): Boolean {
        if(!movEquipVisitTercPassagDatasourceRoom.addAllMovEquipVisitTercPassag(*listPassag().map {
                it.entityToMovEquipVisitTercPassagRoomModel(idMov)
            }.toTypedArray())) return false
        return movEquipVisitTercPassagSharedPreferencesDatasource.clearPassag()
    }

    override suspend fun savePassag(
        idMov: Long,
        passagList: List<MovEquipVisitTercPassag>
    ): Boolean {
        return movEquipVisitTercPassagDatasourceRoom.addAllMovEquipVisitTercPassag(*passagList.map {
                it.entityToMovEquipVisitTercPassagRoomModel(idMov)
            }.toTypedArray())
    }

}