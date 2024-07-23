package br.com.usinasantafe.pcp.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.pcp.utils.TB_EQUIP
import br.com.usinasantafe.pcp.infra.models.room.stable.EquipRoomModel

@Dao
interface EquipDao {

    @Insert
    fun insertAll(vararg equipRoomModels: EquipRoomModel)

    @Query("DELETE FROM $TB_EQUIP")
    suspend fun deleteAll()

    @Query("SELECT count(*) FROM $TB_EQUIP WHERE nroEquip = :nro")
    suspend fun checkEquipNro(nro: Long): Int

    @Query("SELECT * FROM $TB_EQUIP WHERE nroEquip = :nro")
    suspend fun getEquipNro(nro: Long): EquipRoomModel

    @Query("SELECT * FROM $TB_EQUIP WHERE idEquip = :id")
    suspend fun getEquipId(id: Long): EquipRoomModel

}