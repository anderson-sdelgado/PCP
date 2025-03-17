package br.com.usinasantafe.pcp.infra.models.room.variable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.pcp.domain.entities.variable.MovChave
import br.com.usinasantafe.pcp.utils.StatusData
import br.com.usinasantafe.pcp.utils.StatusForeigner
import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.utils.TB_MOV_CHAVE
import br.com.usinasantafe.pcp.utils.TypeMovKey
import java.util.Date

@Entity(tableName = TB_MOV_CHAVE)
data class MovChaveRoomModel(
    @PrimaryKey(autoGenerate = true)
    var idMovChave: Int? = null,
    var uuidMainMovChave: String,
    var matricVigiaMovChave: Int,
    var idLocalMovChave: Int,
    var tipoMovChave: TypeMovKey,
    var dthrMovChave: Long,
    var idChaveMovChave: Int,
    var matricColabMovChave: Int,
    var observMovChave: String?,
    var statusMovChave: StatusData,
    var statusSendMovChave: StatusSend,
    var statusForeignerMovChave: StatusForeigner
)

fun MovChaveRoomModel.roomModelToEntity(): MovChave {
    return with(this){
        MovChave(
            uuidMainMovChave = this.uuidMainMovChave,
            idMovChave = this.idMovChave,
            matricVigiaMovChave = this.matricVigiaMovChave,
            idLocalMovChave = this.idLocalMovChave,
            idChaveMovChave = this.idChaveMovChave,
            dthrMovChave = Date(this.dthrMovChave),
            tipoMovChave = this.tipoMovChave,
            matricColabMovChave = this.matricColabMovChave,
            observMovChave = this.observMovChave,
            statusMovChave = this.statusMovChave,
            statusSendMovChave = this.statusSendMovChave,
            statusForeignerMovChave = this.statusForeignerMovChave,
        )
    }
}

fun MovChave.entityToRoomModel(
    matricVigia: Int,
    idLocal: Int,
    uuid: String
): MovChaveRoomModel {
    return with(this){
        MovChaveRoomModel(
            uuidMainMovChave = uuid,
            idMovChave = this.idMovChave,
            matricVigiaMovChave = matricVigia,
            idLocalMovChave = idLocal,
            idChaveMovChave = this.idChaveMovChave!!,
            dthrMovChave = this.dthrMovChave.time,
            tipoMovChave = this.tipoMovChave!!,
            matricColabMovChave = this.matricColabMovChave!!,
            observMovChave = this.observMovChave,
            statusMovChave = this.statusMovChave,
            statusSendMovChave = this.statusSendMovChave,
            statusForeignerMovChave = this.statusForeignerMovChave,
        )
    }
}
