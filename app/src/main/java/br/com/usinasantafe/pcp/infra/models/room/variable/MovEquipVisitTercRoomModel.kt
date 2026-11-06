package br.com.usinasantafe.pcp.infra.models.room.variable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipVisitTerc
import br.com.usinasantafe.pcp.lib.StatusData
import br.com.usinasantafe.pcp.lib.StatusForeigner
import br.com.usinasantafe.pcp.lib.StatusSend
import br.com.usinasantafe.pcp.lib.TB_MOV_EQUIP_VISIT_TERC
import br.com.usinasantafe.pcp.lib.TypeMovEquip
import br.com.usinasantafe.pcp.lib.TypeVisitTerc
import br.com.usinasantafe.pcp.utils.required
import java.util.Date

@Entity(tableName = TB_MOV_EQUIP_VISIT_TERC)
data class MovEquipVisitTercRoomModel(
    @PrimaryKey(autoGenerate = true)
    var idMovEquipVisitTerc: Int? = null,
    val nroMatricVigiaMovEquipVisitTerc: Int,
    val idLocalMovEquipVisitTerc: Int,
    val tipoMovEquipVisitTerc: TypeMovEquip,
    val idVisitTercMovEquipVisitTerc: Int,
    val tipoVisitTercMovEquipVisitTerc: TypeVisitTerc,
    val dthrMovEquipVisitTerc: Long,
    val veiculoMovEquipVisitTerc: String,
    val placaMovEquipVisitTerc: String,
    val destinoMovEquipVisitTerc: String,
    val observMovEquipVisitTerc: String?,
    val statusMovEquipVisitTerc: StatusData,
    val statusSendMovEquipVisitTerc: StatusSend,
    val statusMovEquipForeigVisitTerc: StatusForeigner,
)

fun MovEquipVisitTercRoomModel.roomModelToEntity(): MovEquipVisitTerc {
    return with(this){
        MovEquipVisitTerc(
            idMovEquipVisitTerc = this.idMovEquipVisitTerc,
            nroMatricVigiaMovEquipVisitTerc = this.nroMatricVigiaMovEquipVisitTerc,
            idLocalMovEquipVisitTerc = this.idLocalMovEquipVisitTerc,
            tipoMovEquipVisitTerc = this.tipoMovEquipVisitTerc,
            idVisitTercMovEquipVisitTerc = this.idVisitTercMovEquipVisitTerc,
            tipoVisitTercMovEquipVisitTerc = this.tipoVisitTercMovEquipVisitTerc,
            dthrMovEquipVisitTerc = Date(this.dthrMovEquipVisitTerc),
            veiculoMovEquipVisitTerc = this.veiculoMovEquipVisitTerc,
            placaMovEquipVisitTerc = this.placaMovEquipVisitTerc,
            destinoMovEquipVisitTerc = this.destinoMovEquipVisitTerc,
            observMovEquipVisitTerc = this.observMovEquipVisitTerc,
            statusMovEquipVisitTerc = this.statusMovEquipVisitTerc,
            statusSendMovEquipVisitTerc = this.statusSendMovEquipVisitTerc,
            statusMovEquipForeignerVisitTerc = this.statusMovEquipForeigVisitTerc,
        )
    }
}

fun MovEquipVisitTerc.entityToRoomModel(
    matricVigia: Int,
    idLocal: Int
): MovEquipVisitTercRoomModel{
    return with(this){
        MovEquipVisitTercRoomModel(
            idMovEquipVisitTerc = this.idMovEquipVisitTerc,
            nroMatricVigiaMovEquipVisitTerc = matricVigia,
            idLocalMovEquipVisitTerc = idLocal,
            tipoMovEquipVisitTerc = this::tipoMovEquipVisitTerc.required(),
            idVisitTercMovEquipVisitTerc = this::idVisitTercMovEquipVisitTerc.required(),
            tipoVisitTercMovEquipVisitTerc = this::tipoVisitTercMovEquipVisitTerc.required(),
            dthrMovEquipVisitTerc = this.dthrMovEquipVisitTerc.time,
            veiculoMovEquipVisitTerc = this::veiculoMovEquipVisitTerc.required(),
            placaMovEquipVisitTerc = this::placaMovEquipVisitTerc.required(),
            destinoMovEquipVisitTerc = this::destinoMovEquipVisitTerc.required(),
            observMovEquipVisitTerc = this.observMovEquipVisitTerc,
            statusMovEquipVisitTerc = this.statusMovEquipVisitTerc,
            statusSendMovEquipVisitTerc = this.statusSendMovEquipVisitTerc,
            statusMovEquipForeigVisitTerc = this.statusMovEquipForeignerVisitTerc,
        )
    }
}