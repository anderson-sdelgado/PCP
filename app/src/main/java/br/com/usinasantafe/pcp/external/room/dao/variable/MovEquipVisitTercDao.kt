package br.com.usinasantafe.pcp.external.room.dao.variable

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipVisitTercRoomModel
import br.com.usinasantafe.pcp.utils.StatusData
import br.com.usinasantafe.pcp.utils.StatusForeigner
import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.utils.TB_MOV_EQUIP_VISIT_TERC

@Dao
interface MovEquipVisitTercDao {

    @Insert
    suspend fun insert(movEquipVisitTercRoomModel: MovEquipVisitTercRoomModel): Long

    @Update
    suspend fun update(movEquipVisitTercRoomModel: MovEquipVisitTercRoomModel)

    @Delete
    suspend fun delete(movEquipVisitTercRoomModel: MovEquipVisitTercRoomModel)

    @Query("SELECT * FROM $TB_MOV_EQUIP_VISIT_TERC WHERE statusMovEquipVisitTerc = :status")
    suspend fun listStatusData(status: StatusData): List<MovEquipVisitTercRoomModel>

    @Query("SELECT * FROM $TB_MOV_EQUIP_VISIT_TERC WHERE statusMovEquipForeigVisitTerc = :statusForeigner")
    suspend fun listStatusForeigner(statusForeigner: StatusForeigner): List<MovEquipVisitTercRoomModel>

    @Query("SELECT * FROM $TB_MOV_EQUIP_VISIT_TERC WHERE statusSendMovEquipVisitTerc = :statusEnvio")
    suspend fun listStatusSend(statusEnvio: StatusSend): List<MovEquipVisitTercRoomModel>

    @Query("SELECT * FROM $TB_MOV_EQUIP_VISIT_TERC WHERE idMovEquipVisitTerc = :idMov")
    suspend fun get(idMov: Int): MovEquipVisitTercRoomModel

}