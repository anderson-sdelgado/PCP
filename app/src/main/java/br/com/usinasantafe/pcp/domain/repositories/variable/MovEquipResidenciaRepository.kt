package br.com.usinasantafe.pcp.domain.repositories.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipResidencia


interface MovEquipResidenciaRepository {

    suspend fun checkMovSend(): Boolean

    suspend fun deleteMovEquipResidencia(movEquipResidencia: MovEquipResidencia): Boolean

    suspend fun listMovEquipResidenciaInside(): List<MovEquipResidencia>

    suspend fun listMovEquipResidenciaOpen(): List<MovEquipResidencia>

    suspend fun listMovEquipResidenciaSend(): List<MovEquipResidencia>

    suspend fun listMovEquipResidenciaSent(): List<MovEquipResidencia>

    suspend fun receiverSentMovEquipResidencia(movEquipList: List<MovEquipResidencia>): Boolean

    suspend fun saveMovEquipResidencia(matricVigia: Long, idLocal: Long): Long

    suspend fun saveMovEquipResidencia(
        matricVigia: Long,
        idLocal: Long,
        movEquipResidencia: MovEquipResidencia
    ): Long

    suspend fun sendMovEquipResidencia(
        movEquipList: List<MovEquipResidencia>,
        nroAparelho: Long,
        token: String,
    ): Result<List<MovEquipResidencia>>

    suspend fun setMotoristaMovEquipResidencia(motorista: String): Boolean

    suspend fun setObservMovEquipResidencia(observ: String?): Boolean

    suspend fun setPlacaMovEquipResidencia(placa: String): Boolean

    suspend fun setStatusOutsideMov(movEquipResidencia: MovEquipResidencia): Boolean

    suspend fun setStatusCloseMov(movEquipResidencia: MovEquipResidencia): Boolean

    suspend fun setVeiculoMovEquipResidencia(veiculo: String): Boolean

    suspend fun startMovEquipResidencia(): Boolean

    suspend fun updateVeiculoMovEquipResidencia(
        veiculo: String,
        movEquipResidencia: MovEquipResidencia
    ): Boolean

    suspend fun updatePlacaMovEquipResidencia(
        placa: String,
        movEquipResidencia: MovEquipResidencia
    ): Boolean

    suspend fun updateMotoristaMovEquipResidencia(
        motorista: String,
        movEquipResidencia: MovEquipResidencia
    ): Boolean

    suspend fun updateObservMovEquipResidencia(
        observ: String?,
        movEquipResidencia: MovEquipResidencia
    ): Boolean

}