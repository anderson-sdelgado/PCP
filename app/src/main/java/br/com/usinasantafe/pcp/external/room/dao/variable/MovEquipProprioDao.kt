package br.com.usinasantafe.pcp.external.room.dao.variable

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.usinasantafe.pcp.utils.StatusData
import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.utils.TB_MOV_EQUIP_PROPRIO
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipProprioRoomModel

@Dao
interface MovEquipProprioDao {

    @Insert
    suspend fun insert(movEquipProprioRoomModel: MovEquipProprioRoomModel)

    @Update
    suspend fun update(movEquipProprioRoomModel: MovEquipProprioRoomModel)

    @Delete
    suspend fun delete(movEquipProprioRoomModel: MovEquipProprioRoomModel)

    @Query("SELECT * FROM $TB_MOV_EQUIP_PROPRIO WHERE statusMovEquipProprio = :status")
    suspend fun listMovStatus(status: StatusData): List<MovEquipProprioRoomModel>

    @Query("SELECT * FROM $TB_MOV_EQUIP_PROPRIO WHERE statusSendMovEquipProprio = :statusEnvio")
    suspend fun listMovStatusEnvio(statusEnvio: StatusSend): List<MovEquipProprioRoomModel>

    @Query("SELECT MAX(idMovEquipProprio) FROM $TB_MOV_EQUIP_PROPRIO WHERE statusMovEquipProprio = :status")
    suspend fun lastIdMovStatus(status: StatusData): Long

    @Query("SELECT * FROM $TB_MOV_EQUIP_PROPRIO WHERE idMovEquipProprio = :idMov")
    suspend fun listMovId(idMov: Long): List<MovEquipProprioRoomModel>

}