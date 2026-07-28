package br.com.usinasantafe.pcp.external.room.dao.variable

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipProprioPassagRoomModel
import br.com.usinasantafe.pcp.lib.TB_MOV_EQUIP_PROPRIO_PASSAG

@Dao
interface MovEquipProprioPassagDao {

    @Insert
    suspend fun insert(movEquipProprioPassagRoomModel: MovEquipProprioPassagRoomModel)

    @Insert
    suspend fun insertAll(list: List<MovEquipProprioPassagRoomModel>)

    @Query("DELETE FROM $TB_MOV_EQUIP_PROPRIO_PASSAG WHERE idMovEquipProprio = :idMov")
    suspend fun deleteByIdMov(idMov: Int)

    @Query("DELETE FROM $TB_MOV_EQUIP_PROPRIO_PASSAG WHERE idMovEquipProprio = :idMov AND matricColab = :matricColab")
    suspend fun deleteByIdMovAndMatric(idMov: Int, matricColab: Int)

    @Query("SELECT * FROM $TB_MOV_EQUIP_PROPRIO_PASSAG WHERE idMovEquipProprio = :idMov")
    suspend fun listById(idMov: Int): List<MovEquipProprioPassagRoomModel>

}