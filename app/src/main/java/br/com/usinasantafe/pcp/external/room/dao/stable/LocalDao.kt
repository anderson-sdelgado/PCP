package br.com.usinasantafe.pcp.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.pcp.infra.models.room.stable.LocalRoomModel
import br.com.usinasantafe.pcp.utils.TB_LOCAL

@Dao
interface LocalDao {

    @Insert
    fun insertAll(list: List<LocalRoomModel>)

    @Query("DELETE FROM $TB_LOCAL")
    suspend fun deleteAll()

    @Query("SELECT descrLocal FROM $TB_LOCAL WHERE idLocal = :id")
    suspend fun getDescr(id: Int): String

    @Query("SELECT * FROM $TB_LOCAL ORDER BY idLocal ASC")
    suspend fun listAll(): List<LocalRoomModel>

}