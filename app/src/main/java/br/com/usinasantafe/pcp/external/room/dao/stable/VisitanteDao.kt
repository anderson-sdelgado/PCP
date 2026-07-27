package br.com.usinasantafe.pcp.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.pcp.infra.models.room.stable.VisitanteRoomModel
import br.com.usinasantafe.pcp.lib.TB_VISITANTE

@Dao
interface VisitanteDao {

    @Insert
    fun insertAll(list: List<VisitanteRoomModel>)

    @Query("DELETE FROM $TB_VISITANTE")
    suspend fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM $TB_VISITANTE WHERE cpfVisitante = :cpf)")
    suspend fun has(cpf: String): Boolean

    @Query("SELECT * FROM $TB_VISITANTE WHERE cpfVisitante = :cpf")
    suspend fun getByCpf(cpf: String): VisitanteRoomModel

    @Query("SELECT * FROM $TB_VISITANTE WHERE idVisitante = :id")
    suspend fun getById(id: Int): VisitanteRoomModel

    @Query("SELECT * FROM $TB_VISITANTE")
    suspend fun listAll(): List<VisitanteRoomModel>

}