package br.com.usinasantafe.pcp.external.room.dao.variable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.usinasantafe.pcp.infra.models.room.variable.MovChaveEquipRoomModel
import br.com.usinasantafe.pcp.utils.StatusData
import br.com.usinasantafe.pcp.utils.StatusForeigner
import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.utils.TB_MOV_CHAVE_EQUIP

@Dao
interface MovChaveEquipDao {

    @Insert
    suspend fun insert(movChaveEquipRoomModel: MovChaveEquipRoomModel): Long

    @Update
    suspend fun update(movChaveEquipRoomModel: MovChaveEquipRoomModel)

    @Query("SELECT * FROM $TB_MOV_CHAVE_EQUIP WHERE idMovChaveEquip = :id")
    suspend fun get(id: Int): MovChaveEquipRoomModel

    @Query("SELECT * FROM $TB_MOV_CHAVE_EQUIP WHERE statusMovChaveEquip = :status")
    suspend fun listStatusData(status: StatusData): List<MovChaveEquipRoomModel>

    @Query("SELECT * FROM $TB_MOV_CHAVE_EQUIP WHERE statusForeignerMovChaveEquip = :statusForeigner")
    suspend fun listStatusForeigner(statusForeigner: StatusForeigner): List<MovChaveEquipRoomModel>

    @Query("SELECT * FROM $TB_MOV_CHAVE_EQUIP WHERE statusSendMovChaveEquip = :statusSend")
    suspend fun listStatusSend(statusSend: StatusSend): List<MovChaveEquipRoomModel>

}