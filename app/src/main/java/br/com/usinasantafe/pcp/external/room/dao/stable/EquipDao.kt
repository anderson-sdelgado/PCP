package br.com.usinasantafe.pcp.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.pcp.infra.models.room.stable.EquipRoomModel
import br.com.usinasantafe.pcp.utils.TB_EQUIP

@Dao
interface EquipDao {

    @Insert
    fun insertAll(list: List<EquipRoomModel>)

    @Query("DELETE FROM $TB_EQUIP")
    suspend fun deleteAll()

    @Query("SELECT count(*) FROM $TB_EQUIP WHERE nroEquip = :nroEquip")
    suspend fun checkNro(nroEquip: Long): Int

    @Query("SELECT * FROM $TB_EQUIP WHERE idEquip = :id")
    suspend fun get(id: Int): EquipRoomModel

    @Query("SELECT nroEquip FROM $TB_EQUIP WHERE idEquip = :id")
    suspend fun getNro(id: Int): Long

    @Query("SELECT idEquip FROM $TB_EQUIP WHERE nroEquip = :nro")
    suspend fun getId(nro: Long): Int

    @Query("SELECT * FROM $TB_EQUIP")
    suspend fun listAll(): List<EquipRoomModel>

}