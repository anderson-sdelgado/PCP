package br.com.usinasantafe.pcp.infra.models.room.variable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipProprio
import br.com.usinasantafe.pcp.lib.StatusData
import br.com.usinasantafe.pcp.lib.StatusSend
import br.com.usinasantafe.pcp.lib.TB_MOV_EQUIP_PROPRIO
import br.com.usinasantafe.pcp.lib.TypeMovEquip
import br.com.usinasantafe.pcp.utils.required
import java.util.Date

@Entity(tableName = TB_MOV_EQUIP_PROPRIO)
data class MovEquipProprioRoomModel(
    @PrimaryKey(autoGenerate = true)
    var idMovEquipProprio: Int? = null,
    val matricVigiaMovEquipProprio: Int,
    val idLocalMovEquipProprio: Int,
    val tipoMovEquipProprio: TypeMovEquip,
    val dthrMovEquipProprio: Long,
    val idEquipMovEquipProprio: Int,
    val matricColabMovEquipProprio: Int,
    val destinoMovEquipProprio: String,
    val notaFiscalMovEquipProprio: Int?,
    val observMovEquipProprio: String?,
    val statusMovEquipProprio: StatusData,
    val statusSendMovEquipProprio: StatusSend,
)

fun MovEquipProprioRoomModel.roomModelToEntity(): MovEquipProprio {
    return with(this){
        MovEquipProprio(
            idMovEquipProprio = this.idMovEquipProprio,
            matricVigiaMovEquipProprio = this.matricVigiaMovEquipProprio,
            idLocalMovEquipProprio = this.idLocalMovEquipProprio,
            tipoMovEquipProprio = this.tipoMovEquipProprio,
            dthrMovEquipProprio = Date(this.dthrMovEquipProprio),
            idEquipMovEquipProprio = this.idEquipMovEquipProprio,
            matricColabMovEquipProprio = this.matricColabMovEquipProprio,
            destinoMovEquipProprio = this.destinoMovEquipProprio,
            notaFiscalMovEquipProprio = this.notaFiscalMovEquipProprio,
            observMovEquipProprio = this.observMovEquipProprio,
            statusMovEquipProprio = this.statusMovEquipProprio,
            statusSendMovEquipProprio = this.statusSendMovEquipProprio,
        )
    }
}

fun MovEquipProprio.entityToRoomModel(
    matricVigia: Int,
    idLocal: Int
): MovEquipProprioRoomModel{
    return with(this){
        MovEquipProprioRoomModel(
            idMovEquipProprio = idMovEquipProprio,
            matricVigiaMovEquipProprio = matricVigia,
            idLocalMovEquipProprio = idLocal,
            tipoMovEquipProprio = this::tipoMovEquipProprio.required(),
            dthrMovEquipProprio = this.dthrMovEquipProprio.time,
            idEquipMovEquipProprio = this::idEquipMovEquipProprio.required(),
            matricColabMovEquipProprio = this::matricColabMovEquipProprio.required(),
            destinoMovEquipProprio = this::destinoMovEquipProprio.required(),
            notaFiscalMovEquipProprio = this.notaFiscalMovEquipProprio,
            observMovEquipProprio = this.observMovEquipProprio,
            statusMovEquipProprio = this.statusMovEquipProprio,
            statusSendMovEquipProprio = this.statusSendMovEquipProprio,
        )
    }
}