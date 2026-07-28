package br.com.usinasantafe.pcp.external.room.dao.variable

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.pcp.infra.models.room.variable.MovEquipVisitTercPassagRoomModel
import br.com.usinasantafe.pcp.lib.TB_MOV_EQUIP_VISIT_TERC_PASSAG

@Dao
interface MovEquipVisitTercPassagDao {

    @Insert
    suspend fun insert(movEquipVisitTercPassagRoomModel: MovEquipVisitTercPassagRoomModel)

    @Insert
    suspend fun insertAll(list: List<MovEquipVisitTercPassagRoomModel>)

    @Query("DELETE FROM $TB_MOV_EQUIP_VISIT_TERC_PASSAG WHERE idMovEquipVisitTerc = :idMov")
    suspend fun deleteByIdMov(idMov: Int)

    @Query("DELETE FROM $TB_MOV_EQUIP_VISIT_TERC_PASSAG WHERE idMovEquipVisitTerc = :idMov AND idVisitTerc = :idVisitTerc")
    suspend fun deleteByIdMovAndIdVisitTerc(idMov: Int, idVisitTerc: Int)

    @Query("SELECT * FROM $TB_MOV_EQUIP_VISIT_TERC_PASSAG WHERE idMovEquipVisitTerc = :idMov")
    suspend fun list(idMov: Int): List<MovEquipVisitTercPassagRoomModel>

}