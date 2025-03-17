package br.com.usinasantafe.pcp.infra.models.room.variable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.pcp.domain.entities.variable.MovChaveEquip
import br.com.usinasantafe.pcp.utils.StatusData
import br.com.usinasantafe.pcp.utils.StatusForeigner
import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.utils.TB_MOV_CHAVE_EQUIP
import br.com.usinasantafe.pcp.utils.TypeMovKey
import java.util.Date

@Entity(tableName = TB_MOV_CHAVE_EQUIP)
data class MovChaveEquipRoomModel(
    @PrimaryKey(autoGenerate = true)
    var idMovChaveEquip: Int? = null,
    var uuidMainMovChaveEquip: String,
    var matricVigiaMovChaveEquip: Int,
    var idLocalMovChaveEquip: Int,
    var dthrMovChaveEquip: Long,
    var tipoMovChaveEquip: TypeMovKey,
    var idEquipMovChaveEquip: Int,
    var matricColabMovChaveEquip: Int,
    var observMovChaveEquip: String?,
    var statusMovChaveEquip: StatusData,
    var statusSendMovChaveEquip: StatusSend,
    var statusForeignerMovChaveEquip: StatusForeigner,
)

fun MovChaveEquipRoomModel.roomModelToEntity(): MovChaveEquip {
    return with(this){
        MovChaveEquip(
            idMovChaveEquip = this.idMovChaveEquip,
            uuidMainMovChaveEquip = this.uuidMainMovChaveEquip,
            matricVigiaMovChaveEquip = this.matricVigiaMovChaveEquip,
            idLocalMovChaveEquip = this.idLocalMovChaveEquip,
            idEquipMovChaveEquip = this.idEquipMovChaveEquip,
            dthrMovChaveEquip = Date(this.dthrMovChaveEquip),
            tipoMovChaveEquip = this.tipoMovChaveEquip,
            matricColabMovChaveEquip = this.matricColabMovChaveEquip,
            observMovChaveEquip = this.observMovChaveEquip,
            statusMovChaveEquip = this.statusMovChaveEquip,
            statusSendMovChaveEquip = this.statusSendMovChaveEquip,
            statusForeignerMovChaveEquip = this.statusForeignerMovChaveEquip,
        )
    }
}

fun MovChaveEquip.entityToRoomModel(
    matricVigia: Int,
    idLocal: Int,
    uuid: String
): MovChaveEquipRoomModel {
    return with(this){
        MovChaveEquipRoomModel(
            idMovChaveEquip = this.idMovChaveEquip,
            uuidMainMovChaveEquip = uuid,
            matricVigiaMovChaveEquip = matricVigia,
            idLocalMovChaveEquip = idLocal,
            idEquipMovChaveEquip = this.idEquipMovChaveEquip!!,
            dthrMovChaveEquip = this.dthrMovChaveEquip.time,
            tipoMovChaveEquip = this.tipoMovChaveEquip!!,
            matricColabMovChaveEquip = this.matricColabMovChaveEquip!!,
            observMovChaveEquip = this.observMovChaveEquip,
            statusMovChaveEquip = this.statusMovChaveEquip,
            statusSendMovChaveEquip = this.statusSendMovChaveEquip,
            statusForeignerMovChaveEquip = this.statusForeignerMovChaveEquip,
        )
    }
}