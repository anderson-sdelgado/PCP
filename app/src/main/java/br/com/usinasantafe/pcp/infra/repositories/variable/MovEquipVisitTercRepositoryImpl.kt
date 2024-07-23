package br.com.usinasantafe.pcp.infra.repositories.variable

import br.com.usinasantafe.pcp.utils.TypeVisitTerc
import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipVisitTerc
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcp.infra.datasource.room.variable.MovEquipVisitTercDatasourceRoom
import br.com.usinasantafe.pcp.infra.datasource.sharedpreferences.MovEquipVisitTercSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.datasource.webservice.variable.MovEquipVisitTercDatasourceWebService
import br.com.usinasantafe.pcp.infra.models.room.variable.entityToMovEquipVisitTercRoomModel
import br.com.usinasantafe.pcp.infra.models.room.variable.modelRoomToMovEquipVisitTerc
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.modelSharedPreferencesToMovEquipVisitTerc
import br.com.usinasantafe.pcp.infra.models.webservice.entityToMovEquipVisitTercWebServiceModel
import br.com.usinasantafe.pcp.infra.models.webservice.modelWebServiceToMovEquipVisitTerc
import javax.inject.Inject

class MovEquipVisitTercRepositoryImpl @Inject constructor(
    private val movEquipVisitTercDatasourceRoom: MovEquipVisitTercDatasourceRoom,
    private val movEquipVisitTercSharedPreferencesDatasource: MovEquipVisitTercSharedPreferencesDatasource,
    private val movEquipVisitTercDatasourceWebService: MovEquipVisitTercDatasourceWebService,
) : MovEquipVisitTercRepository {

    override suspend fun checkMovSend(): Boolean {
        return movEquipVisitTercDatasourceRoom.checkMovSend()
    }

    override suspend fun deleteMovEquipVisitTerc(movEquipVisitTerc: MovEquipVisitTerc): Boolean {
        try {
            movEquipVisitTercDatasourceRoom.deleteMovEquipVisitTerc(
                movEquipVisitTerc.entityToMovEquipVisitTercRoomModel(
                    movEquipVisitTerc.nroMatricVigiaMovEquipVisitTerc!!,
                    movEquipVisitTerc.idLocalMovEquipVisitTerc!!
                )
            )
            return true
        } catch (exception: Exception) {
            return false
        }
    }

    override suspend fun setStatusCloseMov(movEquipVisitTerc: MovEquipVisitTerc): Boolean {
        try {
            movEquipVisitTercDatasourceRoom.updateStatusMovEquipVisitTercClose(
                movEquipVisitTerc.entityToMovEquipVisitTercRoomModel(
                    movEquipVisitTerc.nroMatricVigiaMovEquipVisitTerc!!,
                    movEquipVisitTerc.idLocalMovEquipVisitTerc!!
                )
            )
            return true
        } catch (exception: Exception) {
            return false
        }
    }

    override suspend fun setStatusSendMov(movEquipVisitTerc: MovEquipVisitTerc): Boolean {
        try {
            movEquipVisitTercDatasourceRoom.updateStatusMovEquipVisitTercSend(
                movEquipVisitTerc.entityToMovEquipVisitTercRoomModel(
                    movEquipVisitTerc.nroMatricVigiaMovEquipVisitTerc!!,
                    movEquipVisitTerc.idLocalMovEquipVisitTerc!!
                )
            )
            return true
        } catch (exception: Exception) {
            return false
        }
    }

    override suspend fun getTipoVisitTercMovEquipVisitTerc(): TypeVisitTerc {
        return movEquipVisitTercSharedPreferencesDatasource.getMovEquipVisitTerc().tipoVisitTercMovEquipVisitTerc!!
    }

    override suspend fun listMovEquipVisitTercInside(): List<MovEquipVisitTerc> {
        return movEquipVisitTercDatasourceRoom.listMovEquipVisitTercInside()
            .map { it.modelRoomToMovEquipVisitTerc() }
    }

    override suspend fun listMovEquipVisitTercOpen(): List<MovEquipVisitTerc> {
        return movEquipVisitTercDatasourceRoom.listMovEquipVisitTercOpen()
            .map { it.modelRoomToMovEquipVisitTerc() }
    }

    override suspend fun listMovEquipVisitTercSend(): List<MovEquipVisitTerc> {
        return movEquipVisitTercDatasourceRoom.listMovEquipVisitTercSend()
            .map { it.modelRoomToMovEquipVisitTerc() }
    }

    override suspend fun listMovEquipVisitTercSent(): List<MovEquipVisitTerc> {
        return movEquipVisitTercDatasourceRoom.listMovEquipVisitTercSent()
            .map { it.modelRoomToMovEquipVisitTerc() }
    }

    override suspend fun receiverSentMovEquipVisitTerc(movEquipList: List<MovEquipVisitTerc>): Boolean {
        for (movEquip in movEquipList) {
            if (!movEquipVisitTercDatasourceRoom.updateStatusMovEquipVisitTercSent(movEquip.idMovEquipVisitTerc!!)) return false
        }
        return true
    }

    override suspend fun saveMovEquipVisitTerc(matricVigia: Long, idLocal: Long): Long {
        try {
            val movEquipVisitTerc =
                movEquipVisitTercSharedPreferencesDatasource.getMovEquipVisitTerc()
                    .modelSharedPreferencesToMovEquipVisitTerc()
            val movEquipVisitTercRoomModel =
                movEquipVisitTerc.entityToMovEquipVisitTercRoomModel(matricVigia, idLocal)
            if (!movEquipVisitTercDatasourceRoom.insertMovEquipVisitTercOpen(
                    movEquipVisitTercRoomModel
                )
            ) return 0L
            if (!movEquipVisitTercSharedPreferencesDatasource.clearMovEquipVisitTerc()) return 0L
            return movEquipVisitTercDatasourceRoom.lastIdMovStatusOpen()
        } catch (exception: Exception) {
            return 0L
        }
    }

    override suspend fun saveMovEquipVisitTerc(
        matricVigia: Long,
        idLocal: Long,
        movEquipVisitTerc: MovEquipVisitTerc
    ): Long {
        try {
            val movEquipVisitTercRoomModel =
                movEquipVisitTerc.entityToMovEquipVisitTercRoomModel(matricVigia, idLocal)
            if (!movEquipVisitTercDatasourceRoom.insertMovEquipVisitTercOutside(
                    movEquipVisitTercRoomModel
                )
            ) return 0L
            if (!movEquipVisitTercSharedPreferencesDatasource.clearMovEquipVisitTerc()) return 0L
            return movEquipVisitTercDatasourceRoom.lastIdMovStatusOpen()
        } catch (exception: Exception) {
            return 0L
        }
    }

    override suspend fun sendMovEquipVisitTerc(
        movEquipList: List<MovEquipVisitTerc>,
        nroAparelho: Long,
        token: String
    ): Result<List<MovEquipVisitTerc>> {
        val result = movEquipVisitTercDatasourceWebService.sendMovEquipVisitTerc(
            movEquipList.map {
                it.entityToMovEquipVisitTercWebServiceModel(nroAparelho)
            },
            token,
        )
        if (result.isSuccess) {
            return result.map { listMovEquip ->
                listMovEquip.map { movEquip ->
                    movEquip.modelWebServiceToMovEquipVisitTerc()
                }
            }
        }
        return Result.failure(Throwable(result.exceptionOrNull()))
    }

    override suspend fun setDestinoMovEquipVisitTerc(destino: String): Boolean {
        return try {
            movEquipVisitTercSharedPreferencesDatasource.setDestinoMovEquipVisitTerc(destino)
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun setIdVisitTercMovEquipVisitTerc(idVisitTerc: Long): Boolean {
        return try {
            movEquipVisitTercSharedPreferencesDatasource.setIdVisitTercMovEquipVisitTerc(idVisitTerc)
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun setObservMovEquipVisitTerc(observ: String?): Boolean {
        return try {
            movEquipVisitTercSharedPreferencesDatasource.setObservMovEquipVisitTerc(observ)
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun setPlacaMovEquipVisitTerc(placa: String): Boolean {
        return try {
            movEquipVisitTercSharedPreferencesDatasource.setPlacaMovEquipVisitTerc(placa)
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun setTipoVisitTercMovEquipVisitTerc(typeVisitTerc: TypeVisitTerc): Boolean {
        return try {
            movEquipVisitTercSharedPreferencesDatasource.setTipoVisitTercMovEquipVisitTerc(
                typeVisitTerc
            )
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun setStatusOutsideMov(movEquipVisitTerc: MovEquipVisitTerc): Boolean {
        try {
            movEquipVisitTercDatasourceRoom.updateStatusMovEquipVisitTercOutSide(
                movEquipVisitTerc.entityToMovEquipVisitTercRoomModel(
                    movEquipVisitTerc.nroMatricVigiaMovEquipVisitTerc!!,
                    movEquipVisitTerc.idLocalMovEquipVisitTerc!!
                )
            )
            return true
        } catch (exception: Exception) {
            return false
        }
    }

    override suspend fun setVeiculoMovEquipVisitTerc(veiculo: String): Boolean {
        return try {
            movEquipVisitTercSharedPreferencesDatasource.setVeiculoMovEquipVisitTerc(veiculo)
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun startMovEquipVisitTerc(): Boolean {
        return try {
            movEquipVisitTercSharedPreferencesDatasource.startMovEquipVisitTerc()
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun updateVeiculoMovEquipVisitTerc(
        veiculo: String,
        movEquipVisitTerc: MovEquipVisitTerc
    ): Boolean {
        return try {
            movEquipVisitTercDatasourceRoom.updateVeiculoMovEquipVisitTerc(
                veiculo,
                movEquipVisitTerc.entityToMovEquipVisitTercRoomModel(
                    movEquipVisitTerc.nroMatricVigiaMovEquipVisitTerc!!,
                    movEquipVisitTerc.idLocalMovEquipVisitTerc!!
                )
            )
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun updatePlacaMovEquipVisitTerc(
        placa: String,
        movEquipVisitTerc: MovEquipVisitTerc
    ): Boolean {
        return try {
            movEquipVisitTercDatasourceRoom.updatePlacaMovEquipVisitTerc(
                placa,
                movEquipVisitTerc.entityToMovEquipVisitTercRoomModel(
                    movEquipVisitTerc.nroMatricVigiaMovEquipVisitTerc!!,
                    movEquipVisitTerc.idLocalMovEquipVisitTerc!!
                )
            )
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun updateMotoristaMovEquipVisitTerc(
        idVisitTerc: Long,
        movEquipVisitTerc: MovEquipVisitTerc
    ): Boolean {
        return try {
            movEquipVisitTercDatasourceRoom.updateMotoristaMovEquipVisitTerc(
                idVisitTerc,
                movEquipVisitTerc.entityToMovEquipVisitTercRoomModel(
                    movEquipVisitTerc.nroMatricVigiaMovEquipVisitTerc!!,
                    movEquipVisitTerc.idLocalMovEquipVisitTerc!!
                )
            )
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun updateDestinoMovEquipVisitTerc(
        destino: String,
        movEquipVisitTerc: MovEquipVisitTerc
    ): Boolean {
        return try {
            movEquipVisitTercDatasourceRoom.updateDestinoMovEquipVisitTerc(
                destino,
                movEquipVisitTerc.entityToMovEquipVisitTercRoomModel(
                    movEquipVisitTerc.nroMatricVigiaMovEquipVisitTerc!!,
                    movEquipVisitTerc.idLocalMovEquipVisitTerc!!
                )
            )
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun updateObservMovEquipVisitTerc(
        observ: String?,
        movEquipVisitTerc: MovEquipVisitTerc
    ): Boolean {
        return try {
            movEquipVisitTercDatasourceRoom.updateObservMovEquipVisitTerc(
                observ,
                movEquipVisitTerc.entityToMovEquipVisitTercRoomModel(
                    movEquipVisitTerc.nroMatricVigiaMovEquipVisitTerc!!,
                    movEquipVisitTerc.idLocalMovEquipVisitTerc!!
                )
            )
        } catch (exception: Exception) {
            false
        }
    }

}