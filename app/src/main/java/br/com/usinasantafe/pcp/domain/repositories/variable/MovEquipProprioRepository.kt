package br.com.usinasantafe.pcp.domain.repositories.variable

import br.com.usinasantafe.pcp.utils.TypeMov
import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipProprio

interface MovEquipProprioRepository {

    suspend fun checkAddMotoristaMovEquipProprio(): Boolean

    suspend fun checkAddVeiculoMovEquipProprio(): Boolean

    suspend fun checkMovSend(): Boolean

    suspend fun deleteMovEquipProprio(movEquipProprio: MovEquipProprio): Boolean

    suspend fun getMatricMotoristaMovEquipProprio(): Long

    suspend fun getTipoMovEquipProprio(): TypeMov

    suspend fun listMovEquipProprioOpen(): List<MovEquipProprio>

    suspend fun listMovEquipProprioSend(): List<MovEquipProprio>

    suspend fun listMovEquipProprioSent(): List<MovEquipProprio>

    suspend fun receiverSentMovEquipProprio(movEquipList: List<MovEquipProprio>): Boolean

    suspend fun saveMovEquipProprio(matricVigia: Long, idLocal: Long): Long

    suspend fun sendMovEquipProprio(
        movEquipList: List<MovEquipProprio>,
        nroAparelho: Long,
        token: String
    ): Result<List<MovEquipProprio>>

    suspend fun setDestinoMovEquipProprio(destino: String): Boolean

    suspend fun setMotoristaMovEquipProprio(nroMatric: Long): Boolean

    suspend fun setNotaFiscalMovEquipProprio(notaFiscal: Long): Boolean

    suspend fun setObservMovEquipProprio(observ: String?): Boolean

    suspend fun setStatusCloseMov(movEquipProprio: MovEquipProprio): Boolean

    suspend fun setVeiculoMovEquipProprio(idEquip: Long): Boolean

    suspend fun startMovEquipProprio(typeMov: TypeMov): Boolean

    suspend fun updateStatusSendMovEquipProprio(
        movEquipProprio: MovEquipProprio
    ): Boolean

    suspend fun updateDestinoMovEquipProprio(
        destino: String,
        movEquipProprio: MovEquipProprio
    ): Boolean

    suspend fun updateMotoristaMovEquipProprio(
        nroMatric: Long,
        movEquipProprio: MovEquipProprio
    ): Boolean

    suspend fun updateNotaFiscalMovEquipProprio(
        notaFiscal: Long,
        movEquipProprio: MovEquipProprio
    ): Boolean

    suspend fun updateVeiculoMovEquipProprio(
        idEquip: Long,
        movEquipProprio: MovEquipProprio
    ): Boolean

    suspend fun updateObservMovEquipProprio(
        observ: String?,
        movEquipProprio: MovEquipProprio
    ): Boolean

}