package br.com.usinasantafe.pcp.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.pcp.utils.TB_COLAB
import br.com.usinasantafe.pcp.infra.models.room.stable.ColabRoomModel

@Dao
interface ColabDao {

    @Insert
    fun insertAll(vararg colabRoomModels: ColabRoomModel)

    @Query("DELETE FROM $TB_COLAB")
    suspend fun deleteAll()

    @Query("SELECT count(*) FROM $TB_COLAB WHERE matricColab = :matric")
    suspend fun checkColabMatric(matric: Long): Int

    @Query("SELECT * FROM $TB_COLAB WHERE matricColab = :matric")
    suspend fun getColabMatric(matric: Long): ColabRoomModel

}