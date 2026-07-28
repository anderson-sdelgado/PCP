package br.com.usinasantafe.pcp.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.pcp.infra.models.room.stable.TerceiroRoomModel
import br.com.usinasantafe.pcp.lib.TB_TERCEIRO

@Dao
interface TerceiroDao {

    @Insert
    fun insertAll(list: List<TerceiroRoomModel>)

    @Query("DELETE FROM $TB_TERCEIRO")
    suspend fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM $TB_TERCEIRO WHERE cpfTerceiro = :cpf)")
    suspend fun has(cpf: String): Boolean

    @Query("SELECT * FROM $TB_TERCEIRO WHERE cpfTerceiro = :cpf")
    suspend fun getByCpf(cpf: String): List<TerceiroRoomModel>

    @Query("SELECT * FROM $TB_TERCEIRO WHERE idBDTerceiro = :id")
    suspend fun getById(id: Int): TerceiroRoomModel

    @Query("SELECT * FROM $TB_TERCEIRO")
    suspend fun listAll(): List<TerceiroRoomModel>

}