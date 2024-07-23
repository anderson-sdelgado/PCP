package br.com.usinasantafe.pcp.infra.repositories.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipProprio
import br.com.usinasantafe.pcp.utils.TypeMov
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcp.infra.datasource.room.variable.MovEquipProprioDatasourceRoom
import br.com.usinasantafe.pcp.infra.datasource.sharedpreferences.MovEquipProprioSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.datasource.webservice.variable.MovEquipProprioDatasourceWebService
import br.com.usinasantafe.pcp.infra.models.room.variable.modelRoomToMovEquipProprio
import br.com.usinasantafe.pcp.infra.models.room.variable.entityToMovEquipProprioRoomModel
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.modelSharedPreferencesToMovEquipProprio
import br.com.usinasantafe.pcp.infra.models.webservice.entityToMovEquipProprioWebServiceModel
import br.com.usinasantafe.pcp.infra.models.webservice.modelWebServiceToMovEquipProprio
import javax.inject.Inject

class MovEquipProprioRepositoryImpl @Inject constructor(
    private val movEquipProprioDatasourceRoom: MovEquipProprioDatasourceRoom,
    private val movEquipProprioSharedPreferencesDatasource: MovEquipProprioSharedPreferencesDatasource,
    private val movEquipProprioDatasourceWebService: MovEquipProprioDatasourceWebService,
) : MovEquipProprioRepository {

    override suspend fun checkAddMotoristaMovEquipProprio(): Boolean {
        val movEquipProprio = movEquipProprioSharedPreferencesDatasource.getMovEquipProprio()
        return movEquipProprio.nroMatricColabMovEquipProprio == null
    }

    override suspend fun checkAddVeiculoMovEquipProprio(): Boolean {
        val movEquipProprio = movEquipProprioSharedPreferencesDatasource.getMovEquipProprio()
        return movEquipProprio.idEquipMovEquipProprio == null
    }

    override suspend fun checkMovSend(): Boolean {
        return movEquipProprioDatasourceRoom.checkMovSend()
    }

    override suspend fun deleteMovEquipProprio(movEquipProprio: MovEquipProprio): Boolean {
        return try {
            movEquipProprioDatasourceRoom.deleteMov(
                movEquipProprio.entityToMovEquipProprioRoomModel(
                    movEquipProprio.nroMatricVigiaMovEquipProprio!!,
                    movEquipProprio.idLocalMovEquipProprio!!
                )
            )
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun setStatusCloseMov(movEquipProprio: MovEquipProprio): Boolean {
        return try {
            movEquipProprioDatasourceRoom.updateStatusMovEquipProprioClose(
                movEquipProprio.entityToMovEquipProprioRoomModel(
                    movEquipProprio.nroMatricVigiaMovEquipProprio!!,
                    movEquipProprio.idLocalMovEquipProprio!!
                )
            )
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun getMatricMotoristaMovEquipProprio(): Long {
        return movEquipProprioSharedPreferencesDatasource.getMovEquipProprio().nroMatricColabMovEquipProprio!!
    }

    override suspend fun getTipoMovEquipProprio(): TypeMov {
        return movEquipProprioSharedPreferencesDatasource.getMovEquipProprio().tipoMovEquipProprio
    }

    override suspend fun listMovEquipProprioOpen(): List<MovEquipProprio> {
        return movEquipProprioDatasourceRoom.listMovEquipProprioOpen()
            .map { it.modelRoomToMovEquipProprio() }
    }

    override suspend fun listMovEquipProprioSend(): List<MovEquipProprio> {
        return movEquipProprioDatasourceRoom.listMovEquipProprioSend()
            .map { it.modelRoomToMovEquipProprio() }
    }

    override suspend fun listMovEquipProprioSent(): List<MovEquipProprio> {
        return movEquipProprioDatasourceRoom.listMovEquipProprioSent()
            .map { it.modelRoomToMovEquipProprio() }
    }

    override suspend fun receiverSentMovEquipProprio(movEquipList: List<MovEquipProprio>): Boolean {
        for (movEquipProprio in movEquipList) {
            if (!movEquipProprioDatasourceRoom.updateStatusMovEquipProprioSent(movEquipProprio.idMovEquipProprio!!)) return false
        }
        return true
    }

    override suspend fun saveMovEquipProprio(matricVigia: Long, idLocal: Long): Long {
        try {
            val movEquipProprio = movEquipProprioSharedPreferencesDatasource.getMovEquipProprio()
                .modelSharedPreferencesToMovEquipProprio()
            val movEquipProprioRoomModel =
                movEquipProprio.entityToMovEquipProprioRoomModel(matricVigia, idLocal)
            if (!movEquipProprioDatasourceRoom.saveMovEquipProprioOpen(movEquipProprioRoomModel)) return 0L
            if (!movEquipProprioSharedPreferencesDatasource.clearMovEquipProprio()) return 0L
            return movEquipProprioDatasourceRoom.lastIdMovStatusOpen()
        } catch (exception: Exception) {
            return 0
        }
    }

    override suspend fun sendMovEquipProprio(
        movEquipList: List<MovEquipProprio>,
        nroAparelho: Long,
        token: String
    ): Result<List<MovEquipProprio>> {
        val result = movEquipProprioDatasourceWebService.sendMovEquipProprio(movEquipList.map {
            it.entityToMovEquipProprioWebServiceModel(nroAparelho)
        }, token)
        if (result.isSuccess) {
            return result.map { listMovEquip ->
                listMovEquip.map { movEquip ->
                    movEquip.modelWebServiceToMovEquipProprio()
                }
            }
        }
        return Result.failure(Throwable(result.exceptionOrNull()))
    }

    override suspend fun setDestinoMovEquipProprio(destino: String): Boolean {
        return try {
            movEquipProprioSharedPreferencesDatasource.setDestinoMovEquipProprio(destino)
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun setMotoristaMovEquipProprio(nroMatric: Long): Boolean {
        return try {
            movEquipProprioSharedPreferencesDatasource.setMotoristaMovEquipProprio(nroMatric)
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun setNotaFiscalMovEquipProprio(notaFiscal: Long): Boolean {
        return try {
            movEquipProprioSharedPreferencesDatasource.setNotaFiscalMovEquipProprio(notaFiscal)
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun setObservMovEquipProprio(observ: String?): Boolean {
        return try {
            movEquipProprioSharedPreferencesDatasource.setObservMovEquipProprio(observ)
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun setVeiculoMovEquipProprio(idEquip: Long): Boolean {
        return try {
            movEquipProprioSharedPreferencesDatasource.setVeiculoMovEquipProprio(idEquip)
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun startMovEquipProprio(typeMov: TypeMov): Boolean {
        return try {
            movEquipProprioSharedPreferencesDatasource.startMovEquipProprio(typeMov)
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun updateStatusSendMovEquipProprio(movEquipProprio: MovEquipProprio): Boolean {
        return try {
            movEquipProprioDatasourceRoom.updateStatusSendMovEquipProprio(
                movEquipProprio.entityToMovEquipProprioRoomModel(
                    movEquipProprio.nroMatricVigiaMovEquipProprio!!,
                    movEquipProprio.idLocalMovEquipProprio!!
                )
            )
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun updateDestinoMovEquipProprio(
        destino: String,
        movEquipProprio: MovEquipProprio
    ): Boolean {
        return try {
            movEquipProprioDatasourceRoom.updateDestinoMovEquipProprio(
                destino,
                movEquipProprio.entityToMovEquipProprioRoomModel(
                    movEquipProprio.nroMatricVigiaMovEquipProprio!!,
                    movEquipProprio.idLocalMovEquipProprio!!
                )
            )
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun updateMotoristaMovEquipProprio(
        nroMatric: Long,
        movEquipProprio: MovEquipProprio
    ): Boolean {
        return try {
            movEquipProprioDatasourceRoom.updateNroColabMovEquipProprio(
                nroMatric,
                movEquipProprio.entityToMovEquipProprioRoomModel(
                    movEquipProprio.nroMatricVigiaMovEquipProprio!!,
                    movEquipProprio.idLocalMovEquipProprio!!
                )
            )
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun updateNotaFiscalMovEquipProprio(
        notaFiscal: Long,
        movEquipProprio: MovEquipProprio
    ): Boolean {
        return try {
            movEquipProprioDatasourceRoom.updateNotaFiscalMovEquipProprio(
                notaFiscal,
                movEquipProprio.entityToMovEquipProprioRoomModel(
                    movEquipProprio.nroMatricVigiaMovEquipProprio!!,
                    movEquipProprio.idLocalMovEquipProprio!!
                )
            )
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun updateVeiculoMovEquipProprio(
        idEquip: Long,
        movEquipProprio: MovEquipProprio
    ): Boolean {
        return try {
            movEquipProprioDatasourceRoom.updateIdEquipMovEquipProprio(
                idEquip,
                movEquipProprio.entityToMovEquipProprioRoomModel(
                    movEquipProprio.nroMatricVigiaMovEquipProprio!!,
                    movEquipProprio.idLocalMovEquipProprio!!
                )
            )
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun updateObservMovEquipProprio(
        observ: String?,
        movEquipProprio: MovEquipProprio
    ): Boolean {
        return try {
            movEquipProprioDatasourceRoom.updateObservMovEquipProprio(
                observ,
                movEquipProprio.entityToMovEquipProprioRoomModel(
                    movEquipProprio.nroMatricVigiaMovEquipProprio!!,
                    movEquipProprio.idLocalMovEquipProprio!!
                )
            )
        } catch (exception: Exception) {
            false
        }
    }

}