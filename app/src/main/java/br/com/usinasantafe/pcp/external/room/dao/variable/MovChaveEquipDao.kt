package br.com.usinasantafe.pcp.external.room.dao.variable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.usinasantafe.pcp.infra.models.room.variable.MovChaveEquipRoomModel
import br.com.usinasantafe.pcp.lib.StatusData
import br.com.usinasantafe.pcp.lib.StatusForeigner
import br.com.usinasantafe.pcp.lib.StatusSend
import br.com.usinasantafe.pcp.lib.TB_MOV_CHAVE_EQUIP

@Dao
interface MovChaveEquipDao {

    @Insert
    suspend fun insert(movChaveEquipRoomModel: MovChaveEquipRoomModel): Long

    @Update
    suspend fun update(movChaveEquipRoomModel: MovChaveEquipRoomModel)

    @Query("SELECT * FROM $TB_MOV_CHAVE_EQUIP WHERE idMovChaveEquip = :id")
    suspend fun getById(id: Int): MovChaveEquipRoomModel

    @Query("SELECT EXISTS(SELECT * FROM $TB_MOV_CHAVE_EQUIP WHERE statusMovChaveEquip = :status)")
    suspend fun hasByStatusData(status: StatusData): Boolean

    @Query("SELECT * FROM $TB_MOV_CHAVE_EQUIP WHERE statusMovChaveEquip = :status")
    suspend fun listByStatusData(status: StatusData): List<MovChaveEquipRoomModel>

    @Query("SELECT * FROM $TB_MOV_CHAVE_EQUIP WHERE statusForeignerMovChaveEquip = :statusForeigner")
    suspend fun listByStatusForeigner(statusForeigner: StatusForeigner): List<MovChaveEquipRoomModel>

    @Query("SELECT EXISTS(SELECT * FROM $TB_MOV_CHAVE_EQUIP WHERE statusSendMovChaveEquip = :statusSend)")
    suspend fun hasByStatusSend(statusSend: StatusSend): Boolean

    @Query("SELECT * FROM $TB_MOV_CHAVE_EQUIP WHERE statusSendMovChaveEquip = :statusSend")
    suspend fun listByStatusSend(statusSend: StatusSend): List<MovChaveEquipRoomModel>

}