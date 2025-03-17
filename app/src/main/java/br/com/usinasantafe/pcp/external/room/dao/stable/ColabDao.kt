package br.com.usinasantafe.pcp.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.pcp.infra.models.room.stable.ColabRoomModel
import br.com.usinasantafe.pcp.utils.TB_COLAB

@Dao
interface ColabDao {

    @Insert
    suspend fun insertAll(list: List<ColabRoomModel>)

    @Query("DELETE FROM $TB_COLAB")
    suspend fun deleteAll()

    @Query("SELECT count(*) FROM $TB_COLAB WHERE matricColab = :matric")
    suspend fun check(matric: Int): Int

    @Query("SELECT nomeColab FROM $TB_COLAB WHERE matricColab = :matric")
    suspend fun getNome(matric: Int): String

    @Query("SELECT * FROM $TB_COLAB")
    suspend fun listAll(): List<ColabRoomModel>

}