package br.com.usinasantafe.pcp.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.pcp.utils.TB_VISITANTE
import br.com.usinasantafe.pcp.infra.models.room.stable.VisitanteRoomModel

@Dao
interface VisitanteDao {

    @Insert
    fun insertAll(vararg visitanteRoomModels: VisitanteRoomModel)

    @Query("DELETE FROM $TB_VISITANTE")
    suspend fun deleteAll()

    @Query("SELECT count(*) FROM $TB_VISITANTE WHERE cpfVisitante = :cpf")
    suspend fun checkCPFVisitante(cpf: String): Int

    @Query("SELECT * FROM $TB_VISITANTE WHERE cpfVisitante = :cpf")
    suspend fun getVisitanteCPF(cpf: String): VisitanteRoomModel

    @Query("SELECT * FROM $TB_VISITANTE WHERE idVisitante = :id")
    suspend fun getVisitanteId(id: Long): VisitanteRoomModel

}