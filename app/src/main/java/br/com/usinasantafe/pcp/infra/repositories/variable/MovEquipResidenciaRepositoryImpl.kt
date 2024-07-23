package br.com.usinasantafe.pcp.infra.repositories.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipResidencia
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcp.infra.datasource.room.variable.MovEquipResidenciaDatasourceRoom
import br.com.usinasantafe.pcp.infra.datasource.sharedpreferences.MovEquipResidenciaSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.datasource.webservice.variable.MovEquipResidenciaDatasourceWebService
import br.com.usinasantafe.pcp.infra.models.room.variable.entityToMovEquipResidenciaRoomModel
import br.com.usinasantafe.pcp.infra.models.room.variable.modelRoomToMovEquipResidencia
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.modelSharedPreferencesToMovEquipResidencia
import br.com.usinasantafe.pcp.infra.models.webservice.entityToMovEquipResidenciaWebServiceModel
import br.com.usinasantafe.pcp.infra.models.webservice.modelWebServiceToMovEquipResidencia
import javax.inject.Inject

class MovEquipResidenciaRepositoryImpl @Inject constructor(
    private val movEquipResidenciaDatasourceRoom: MovEquipResidenciaDatasourceRoom,
    private val movEquipResidenciaSharedPreferencesDatasource: MovEquipResidenciaSharedPreferencesDatasource,
    private val movEquipResidenciaDatasourceWebService: MovEquipResidenciaDatasourceWebService,
) : MovEquipResidenciaRepository {

    override suspend fun checkMovSend(): Boolean {
        return movEquipResidenciaDatasourceRoom.checkMovSend()
    }

    override suspend fun deleteMovEquipResidencia(movEquipResidencia: MovEquipResidencia): Boolean {
        try {
            movEquipResidenciaDatasourceRoom.deleteMovEquipResidencia(
                movEquipResidencia.entityToMovEquipResidenciaRoomModel(
                    movEquipResidencia.nroMatricVigiaMovEquipResidencia!!,
                    movEquipResidencia.idLocalMovEquipResidencia!!
                )
            )
            return true
        } catch (exception: Exception) {
            return false
        }
    }

    override suspend fun listMovEquipResidenciaInside(): List<MovEquipResidencia> {
        return movEquipResidenciaDatasourceRoom.listMovEquipResidenciaInside()
            .map { it.modelRoomToMovEquipResidencia() }
    }

    override suspend fun listMovEquipResidenciaOpen(): List<MovEquipResidencia> {
        return movEquipResidenciaDatasourceRoom.listMovEquipResidenciaOpen()
            .map { it.modelRoomToMovEquipResidencia() }
    }

    override suspend fun listMovEquipResidenciaSend(): List<MovEquipResidencia> {
        return movEquipResidenciaDatasourceRoom.listMovEquipResidenciaSend()
            .map { it.modelRoomToMovEquipResidencia() }
    }

    override suspend fun listMovEquipResidenciaSent(): List<MovEquipResidencia> {
        return movEquipResidenciaDatasourceRoom.listMovEquipResidenciaSent()
            .map { it.modelRoomToMovEquipResidencia() }
    }

    override suspend fun receiverSentMovEquipResidencia(movEquipList: List<MovEquipResidencia>): Boolean {
        for (movEquip in movEquipList) {
            if (!movEquipResidenciaDatasourceRoom.updateStatusSentMovEquipResidencia(movEquip.idMovEquipResidencia!!)) return false
        }
        return true
    }

    override suspend fun saveMovEquipResidencia(matricVigia: Long, idLocal: Long): Long {
        try {
            val movEquipResidencia =
                movEquipResidenciaSharedPreferencesDatasource.getMovEquipResidencia()
                    .modelSharedPreferencesToMovEquipResidencia()
            val movEquipResidenciaRoomModel =
                movEquipResidencia.entityToMovEquipResidenciaRoomModel(matricVigia, idLocal)
            if (!movEquipResidenciaDatasourceRoom.insertMovEquipResidenciaOpen(
                    movEquipResidenciaRoomModel
                )
            ) return 0L
            if (!movEquipResidenciaSharedPreferencesDatasource.clearMovEquipResidencia()) return 0L
            return movEquipResidenciaDatasourceRoom.lastIdMovStatusOpen()
        } catch (exception: Exception) {
            return 0L
        }
    }

    override suspend fun saveMovEquipResidencia(
        matricVigia: Long,
        idLocal: Long,
        movEquipResidencia: MovEquipResidencia
    ): Long {
        try {
            val movEquipResidenciaRoomModel =
                movEquipResidencia.entityToMovEquipResidenciaRoomModel(matricVigia, idLocal)
            if (!movEquipResidenciaDatasourceRoom.insertMovEquipResidenciaOutside(
                    movEquipResidenciaRoomModel
                )
            ) return 0L
            if (!movEquipResidenciaSharedPreferencesDatasource.clearMovEquipResidencia()) return 0L
            return movEquipResidenciaDatasourceRoom.lastIdMovStatusOpen()
        } catch (exception: Exception) {
            return 0L
        }
    }

    override suspend fun sendMovEquipResidencia(
        movEquipList: List<MovEquipResidencia>,
        nroAparelho: Long,
        token: String
    ): Result<List<MovEquipResidencia>> {
        val result =
            movEquipResidenciaDatasourceWebService.sendMovEquipResidencia(
                movEquipList.map {
                    it.entityToMovEquipResidenciaWebServiceModel(nroAparelho)
                },
                token,
            )
        if (result.isSuccess) {
            return result.map { listMovEquip ->
                listMovEquip.map { movEquip ->
                    movEquip.modelWebServiceToMovEquipResidencia()
                }
            }
        }
        return Result.failure(Throwable(result.exceptionOrNull()))
    }

    override suspend fun setMotoristaMovEquipResidencia(motorista: String): Boolean {
        return try {
            movEquipResidenciaSharedPreferencesDatasource.setMotoristaMovEquipResidencia(motorista)
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun setObservMovEquipResidencia(observ: String?): Boolean {
        return try {
            movEquipResidenciaSharedPreferencesDatasource.setObservMovEquipResidencia(observ)
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun setPlacaMovEquipResidencia(placa: String): Boolean {
        return try {
            movEquipResidenciaSharedPreferencesDatasource.setPlacaMovEquipResidencia(placa)
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun setStatusOutsideMov(movEquipResidencia: MovEquipResidencia): Boolean {
        try {
            movEquipResidenciaDatasourceRoom.updateStatusMovEquipResidenciaOutside(
                movEquipResidencia.entityToMovEquipResidenciaRoomModel(
                    movEquipResidencia.nroMatricVigiaMovEquipResidencia!!,
                    movEquipResidencia.idLocalMovEquipResidencia!!
                )
            )
            return true
        } catch (exception: Exception) {
            return false
        }
    }

    override suspend fun setStatusCloseMov(movEquipResidencia: MovEquipResidencia): Boolean {
        try {
            movEquipResidenciaDatasourceRoom.updateStatusMovEquipResidenciaClose(
                movEquipResidencia.entityToMovEquipResidenciaRoomModel(
                    movEquipResidencia.nroMatricVigiaMovEquipResidencia!!,
                    movEquipResidencia.idLocalMovEquipResidencia!!
                )
            )
            return true
        } catch (exception: Exception) {
            return false
        }
    }

    override suspend fun setVeiculoMovEquipResidencia(veiculo: String): Boolean {
        return try {
            movEquipResidenciaSharedPreferencesDatasource.setVeiculoMovEquipResidencia(veiculo)
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun startMovEquipResidencia(): Boolean {
        return try {
            movEquipResidenciaSharedPreferencesDatasource.startMovEquipResidencia()
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun updateVeiculoMovEquipResidencia(
        veiculo: String,
        movEquipResidencia: MovEquipResidencia
    ): Boolean {
        return try {
            movEquipResidenciaDatasourceRoom.updateVeiculoMovEquipResidencia(
                veiculo,
                movEquipResidencia.entityToMovEquipResidenciaRoomModel(
                    movEquipResidencia.nroMatricVigiaMovEquipResidencia!!,
                    movEquipResidencia.idLocalMovEquipResidencia!!
                )
            )
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun updatePlacaMovEquipResidencia(
        placa: String,
        movEquipResidencia: MovEquipResidencia
    ): Boolean {
        return try {
            movEquipResidenciaDatasourceRoom.updatePlacaMovEquipResidencia(
                placa,
                movEquipResidencia.entityToMovEquipResidenciaRoomModel(
                    movEquipResidencia.nroMatricVigiaMovEquipResidencia!!,
                    movEquipResidencia.idLocalMovEquipResidencia!!
                )
            )
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun updateMotoristaMovEquipResidencia(
        motorista: String,
        movEquipResidencia: MovEquipResidencia
    ): Boolean {
        return try {
            movEquipResidenciaDatasourceRoom.updateMotoristaMovEquipResidencia(
                motorista,
                movEquipResidencia.entityToMovEquipResidenciaRoomModel(
                    movEquipResidencia.nroMatricVigiaMovEquipResidencia!!,
                    movEquipResidencia.idLocalMovEquipResidencia!!
                )
            )
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun updateObservMovEquipResidencia(
        observ: String?,
        movEquipResidencia: MovEquipResidencia
    ): Boolean {
        return try {
            movEquipResidenciaDatasourceRoom.updateObservMovEquipResidencia(
                observ,
                movEquipResidencia.entityToMovEquipResidenciaRoomModel(
                    movEquipResidencia.nroMatricVigiaMovEquipResidencia!!,
                    movEquipResidencia.idLocalMovEquipResidencia!!
                )
            )
        } catch (exception: Exception) {
            false
        }
    }

}