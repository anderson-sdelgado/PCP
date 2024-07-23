package br.com.usinasantafe.pcp.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.pcp.utils.TB_TERCEIRO
import br.com.usinasantafe.pcp.infra.models.room.stable.TerceiroRoomModel

@Dao
interface TerceiroDao {

    @Insert
    fun insertAll(vararg terceiroRoomModels: TerceiroRoomModel)

    @Query("DELETE FROM $TB_TERCEIRO")
    suspend fun deleteAll()

    @Query("SELECT count(*) FROM $TB_TERCEIRO WHERE cpfTerceiro = :cpf")
    suspend fun checkCPFTerceiro(cpf: String): Int

    @Query("SELECT * FROM $TB_TERCEIRO WHERE cpfTerceiro = :cpf")
    suspend fun getTerceiroCPF(cpf: String): List<TerceiroRoomModel>

    @Query("SELECT * FROM $TB_TERCEIRO WHERE idBDTerceiro = :id")
    suspend fun getTerceiroId(id: Long): List<TerceiroRoomModel>

}