package br.com.usinasantafe.pcp.external.room.dao.variable

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.pcp.utils.TB_MOV_EQUIP_PROPRIO_PASSAG
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipProprioPassagRoomModel

@Dao
interface MovEquipProprioPassagDao {

    @Insert
    suspend fun insertAll(vararg movEquipProprioPassagRoomModels: MovEquipProprioPassagRoomModel)

    @Insert
    suspend fun insert(movEquipProprioPassagRoomModel: MovEquipProprioPassagRoomModel)

    @Delete
    suspend fun delete(movEquipProprioPassagRoomModel: MovEquipProprioPassagRoomModel)

    @Query("SELECT * FROM $TB_MOV_EQUIP_PROPRIO_PASSAG WHERE idMovEquipProprio = :idMov")
    suspend fun listMovEquipProprioPassagIdMov(idMov: Long): List<MovEquipProprioPassagRoomModel>

}