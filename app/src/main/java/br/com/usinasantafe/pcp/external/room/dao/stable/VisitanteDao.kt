package br.com.usinasantafe.pcp.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.pcp.infra.models.room.stable.VisitanteRoomModel
import br.com.usinasantafe.pcp.utils.TB_VISITANTE

@Dao
interface VisitanteDao {

    @Insert
    fun insertAll(list: List<VisitanteRoomModel>)

    @Query("DELETE FROM $TB_VISITANTE")
    suspend fun deleteAll()

    @Query("SELECT count(*) FROM $TB_VISITANTE WHERE cpfVisitante = :cpf")
    suspend fun check(cpf: String): Int

    @Query("SELECT * FROM $TB_VISITANTE WHERE cpfVisitante = :cpf")
    suspend fun get(cpf: String): VisitanteRoomModel

    @Query("SELECT * FROM $TB_VISITANTE WHERE idVisitante = :id")
    suspend fun get(id: Int): VisitanteRoomModel

    @Query("SELECT * FROM $TB_VISITANTE")
    suspend fun listAll(): List<VisitanteRoomModel>

}