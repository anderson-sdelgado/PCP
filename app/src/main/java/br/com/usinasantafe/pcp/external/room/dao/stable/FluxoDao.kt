package br.com.usinasantafe.pcp.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.pcp.infra.models.room.stable.FluxoRoomModel
import br.com.usinasantafe.pcp.utils.TB_FLUXO

@Dao
interface FluxoDao {

    @Insert
    fun insertAll(list: List<FluxoRoomModel>)

    @Query("DELETE FROM $TB_FLUXO")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TB_FLUXO WHERE idFluxo = :id ORDER BY idFluxo ASC")
    suspend fun get(id: Int): FluxoRoomModel

    @Query("SELECT * FROM $TB_FLUXO ORDER BY idFluxo ASC")
    suspend fun listAll(): List<FluxoRoomModel>

}