package br.com.usinasantafe.pcp.external.room.dao.variable

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.usinasantafe.pcp.utils.StatusData
import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.utils.TB_MOV_EQUIP_RESIDENCIA
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipResidenciaRoomModel
import br.com.usinasantafe.pcp.utils.StatusForeigner

@Dao
interface MovEquipResidenciaDao {

    @Insert
    suspend fun insert(movEquipResidenciaRoomModel: MovEquipResidenciaRoomModel)

    @Update
    suspend fun update(movEquipResidenciaRoomModel: MovEquipResidenciaRoomModel)

    @Delete
    suspend fun delete(movEquipResidenciaRoomModel: MovEquipResidenciaRoomModel)

    @Query("SELECT * FROM $TB_MOV_EQUIP_RESIDENCIA WHERE statusMovEquipResidencia = :status")
    suspend fun listMovStatus(status: StatusData): List<MovEquipResidenciaRoomModel>

    @Query("SELECT * FROM $TB_MOV_EQUIP_RESIDENCIA WHERE statusMovEquipForeigResidencia = :statusForeigner")
    suspend fun listMovStatusForeigner(statusForeigner: StatusForeigner): List<MovEquipResidenciaRoomModel>

    @Query("SELECT * FROM $TB_MOV_EQUIP_RESIDENCIA WHERE statusSendMovEquipResidencia = :statusEnvio")
    suspend fun listMovStatusEnvio(statusEnvio: StatusSend): List<MovEquipResidenciaRoomModel>

    @Query("SELECT * FROM $TB_MOV_EQUIP_RESIDENCIA WHERE statusMovEquipResidencia = :status AND statusSendMovEquipResidencia = :statusEnvio")
    suspend fun listMovStatusAndStatusEnvio(status: StatusData, statusEnvio: StatusSend): List<MovEquipResidenciaRoomModel>

    @Query("SELECT MAX(idMovEquipResidencia) FROM $TB_MOV_EQUIP_RESIDENCIA WHERE statusMovEquipResidencia = :status")
    suspend fun lastIdMovStatus(status: StatusData): Long

    @Query("SELECT * FROM $TB_MOV_EQUIP_RESIDENCIA WHERE idMovEquipResidencia = :idMov")
    suspend fun listMovId(idMov: Long): List<MovEquipResidenciaRoomModel>

}