package br.com.usinasantafe.pcp.external.room.dao.variable

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.usinasantafe.pcp.utils.TB_MOV_EQUIP_PROPRIO_SEG
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipProprioRoomModel
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipProprioSegRoomModel

@Dao
interface MovEquipProprioSegDao {

    @Insert
    suspend fun insertAll(vararg movEquipProprioSegRoomModels: MovEquipProprioSegRoomModel)

    @Insert
    suspend fun insert(movEquipProprioSegRoomModels: MovEquipProprioSegRoomModel)

    @Delete
    suspend fun delete(movEquipProprioSegRoomModels: MovEquipProprioSegRoomModel)

    @Query("SELECT * FROM $TB_MOV_EQUIP_PROPRIO_SEG WHERE idMovEquipProprio = :idMov")
    suspend fun listMovEquipProprioSegIdMov(idMov: Long): List<MovEquipProprioSegRoomModel>
}