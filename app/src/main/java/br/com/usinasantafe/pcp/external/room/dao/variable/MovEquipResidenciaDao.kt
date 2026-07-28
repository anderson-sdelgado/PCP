package br.com.usinasantafe.pcp.external.room.dao.variable

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipResidenciaRoomModel
import br.com.usinasantafe.pcp.lib.StatusData
import br.com.usinasantafe.pcp.lib.StatusForeigner
import br.com.usinasantafe.pcp.lib.StatusSend
import br.com.usinasantafe.pcp.lib.TB_MOV_EQUIP_RESIDENCIA

@Dao
interface MovEquipResidenciaDao {

    @Insert
    suspend fun insert(movEquipResidenciaRoomModel: MovEquipResidenciaRoomModel): Long

    @Update
    suspend fun update(movEquipResidenciaRoomModel: MovEquipResidenciaRoomModel)

    @Delete
    suspend fun delete(movEquipResidenciaRoomModel: MovEquipResidenciaRoomModel)

    @Query("SELECT EXISTS (SELECT * FROM $TB_MOV_EQUIP_RESIDENCIA WHERE statusMovEquipResidencia = :status)")
    suspend fun hasByStatusData(status: StatusData): Boolean

    @Query("SELECT EXISTS (SELECT * FROM $TB_MOV_EQUIP_RESIDENCIA WHERE statusSendMovEquipResidencia = :statusEnvio)")
    suspend fun hasByStatusSend(statusEnvio: StatusSend): Boolean

    @Query("SELECT * FROM $TB_MOV_EQUIP_RESIDENCIA WHERE idMovEquipResidencia = :idMov")
    suspend fun get(idMov: Int): MovEquipResidenciaRoomModel

    @Query("SELECT * FROM $TB_MOV_EQUIP_RESIDENCIA WHERE statusMovEquipResidencia = :status")
    suspend fun listByStatusData(status: StatusData): List<MovEquipResidenciaRoomModel>

    @Query("SELECT * FROM $TB_MOV_EQUIP_RESIDENCIA WHERE statusMovEquipForeignerResidencia = :statusForeigner")
    suspend fun listByStatusForeigner(statusForeigner: StatusForeigner): List<MovEquipResidenciaRoomModel>

    @Query("SELECT * FROM $TB_MOV_EQUIP_RESIDENCIA WHERE statusSendMovEquipResidencia = :statusEnvio")
    suspend fun listByStatusSend(statusEnvio: StatusSend): List<MovEquipResidenciaRoomModel>

}