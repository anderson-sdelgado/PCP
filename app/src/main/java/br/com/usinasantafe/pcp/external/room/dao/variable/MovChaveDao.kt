package br.com.usinasantafe.pcp.external.room.dao.variable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.usinasantafe.pcp.infra.models.room.variable.MovChaveRoomModel
import br.com.usinasantafe.pcp.lib.StatusData
import br.com.usinasantafe.pcp.lib.StatusForeigner
import br.com.usinasantafe.pcp.lib.StatusSend
import br.com.usinasantafe.pcp.lib.TB_MOV_CHAVE

@Dao
interface MovChaveDao {

    @Insert
    suspend fun insert(movChaveRoomModel: MovChaveRoomModel): Long

    @Update
    suspend fun update(movChaveRoomModel: MovChaveRoomModel)

    @Query("SELECT * FROM $TB_MOV_CHAVE WHERE idMovChave = :id")
    suspend fun getById(id: Int): MovChaveRoomModel

    @Query("SELECT EXISTS (SELECT * FROM $TB_MOV_CHAVE WHERE statusMovChave = :status)")
    suspend fun hasByStatusData(status: StatusData): Boolean

    @Query("SELECT * FROM $TB_MOV_CHAVE WHERE statusMovChave = :status")
    suspend fun listByStatusData(status: StatusData): List<MovChaveRoomModel>

    @Query("SELECT * FROM $TB_MOV_CHAVE WHERE statusForeignerMovChave = :statusForeigner")
    suspend fun listByStatusForeigner(statusForeigner: StatusForeigner): List<MovChaveRoomModel>

    @Query("SELECT EXISTS (SELECT * FROM $TB_MOV_CHAVE WHERE statusSendMovChave = :status)")
    suspend fun hasByStatusSend(status: StatusSend): Boolean

    @Query("SELECT * FROM $TB_MOV_CHAVE WHERE statusSendMovChave = :status")
    suspend fun listByStatusSend(status: StatusSend): List<MovChaveRoomModel>

}