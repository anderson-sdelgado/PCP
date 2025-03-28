package br.com.usinasantafe.pcp.external.room.dao.variable

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipResidenciaRoomModel
import br.com.usinasantafe.pcp.utils.StatusData
import br.com.usinasantafe.pcp.utils.StatusForeigner
import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.utils.TB_MOV_EQUIP_RESIDENCIA

@Dao
interface MovEquipResidenciaDao {

    @Insert
    suspend fun insert(movEquipResidenciaRoomModel: MovEquipResidenciaRoomModel): Long

    @Update
    suspend fun update(movEquipResidenciaRoomModel: MovEquipResidenciaRoomModel)

    @Delete
    suspend fun delete(movEquipResidenciaRoomModel: MovEquipResidenciaRoomModel)

    @Query("SELECT * FROM $TB_MOV_EQUIP_RESIDENCIA WHERE idMovEquipResidencia = :idMov")
    suspend fun get(idMov: Int): MovEquipResidenciaRoomModel

    @Query("SELECT * FROM $TB_MOV_EQUIP_RESIDENCIA WHERE statusMovEquipResidencia = :status")
    suspend fun listStatusData(status: StatusData): List<MovEquipResidenciaRoomModel>

    @Query("SELECT * FROM $TB_MOV_EQUIP_RESIDENCIA WHERE statusMovEquipForeignerResidencia = :statusForeigner")
    suspend fun listStatusForeigner(statusForeigner: StatusForeigner): List<MovEquipResidenciaRoomModel>

    @Query("SELECT * FROM $TB_MOV_EQUIP_RESIDENCIA WHERE statusSendMovEquipResidencia = :statusEnvio")
    suspend fun listStatusSend(statusEnvio: StatusSend): List<MovEquipResidenciaRoomModel>


}