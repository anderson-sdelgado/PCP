package br.com.usinasantafe.pcp.infra.models.room.variable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.pcp.domain.entities.variable.MovChaveEquip
import br.com.usinasantafe.pcp.lib.StatusData
import br.com.usinasantafe.pcp.lib.StatusForeigner
import br.com.usinasantafe.pcp.lib.StatusSend
import br.com.usinasantafe.pcp.lib.TB_MOV_CHAVE_EQUIP
import br.com.usinasantafe.pcp.lib.TypeMovKey
import java.util.Date

@Entity(tableName = TB_MOV_CHAVE_EQUIP)
data class MovChaveEquipRoomModel(
    @PrimaryKey(autoGenerate = true)
    val idMovChaveEquip: Int? = null,
    val uuidMainMovChaveEquip: String,
    val matricVigiaMovChaveEquip: Int,
    val idLocalMovChaveEquip: Int,
    val dthrMovChaveEquip: Long,
    val tipoMovChaveEquip: TypeMovKey,
    val idEquipMovChaveEquip: Int,
    val matricColabMovChaveEquip: Int,
    val observMovChaveEquip: String?,
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