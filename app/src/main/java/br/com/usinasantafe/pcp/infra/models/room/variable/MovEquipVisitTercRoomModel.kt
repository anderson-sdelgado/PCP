package br.com.usinasantafe.pcp.infra.models.room.variable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipVisitTerc
import br.com.usinasantafe.pcp.utils.StatusData
import br.com.usinasantafe.pcp.utils.StatusForeigner
import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.utils.TB_MOV_EQUIP_VISIT_TERC
import br.com.usinasantafe.pcp.utils.TypeMovEquip
import br.com.usinasantafe.pcp.utils.TypeVisitTerc
import java.util.Date

@Entity(tableName = TB_MOV_EQUIP_VISIT_TERC)
data class MovEquipVisitTercRoomModel(
    @PrimaryKey(autoGenerate = true)
    var idMovEquipVisitTerc: Int? = null,
    var nroMatricVigiaMovEquipVisitTerc: Int,
    var idLocalMovEquipVisitTerc: Int,
    var tipoMovEquipVisitTerc: TypeMovEquip,
    var idVisitTercMovEquipVisitTerc: Int,
    var tipoVisitTercMovEquipVisitTerc: TypeVisitTerc,
    var dthrMovEquipVisitTerc: Long,
    var veiculoMovEquipVisitTerc: String,
    var placaMovEquipVisitTerc: String,
    var destinoMovEquipVisitTerc: String?,
    var observMovEquipVisitTerc: String?,
    var statusMovEquipVisitTerc: StatusData,
    var statusSendMovEquipVisitTerc: StatusSend,
    var statusMovEquipForeigVisitTerc: StatusForeigner,
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
            tipoMovEquipVisitTerc = this.tipoMovEquipVisitTerc!!,
            idVisitTercMovEquipVisitTerc = this.idVisitTercMovEquipVisitTerc!!,
            tipoVisitTercMovEquipVisitTerc = this.tipoVisitTercMovEquipVisitTerc!!,
            dthrMovEquipVisitTerc = this.dthrMovEquipVisitTerc.time,
            veiculoMovEquipVisitTerc = this.veiculoMovEquipVisitTerc!!,
            placaMovEquipVisitTerc = this.placaMovEquipVisitTerc!!,
            destinoMovEquipVisitTerc = this.destinoMovEquipVisitTerc,
            observMovEquipVisitTerc = this.observMovEquipVisitTerc,
            statusMovEquipVisitTerc = this.statusMovEquipVisitTerc,
            statusSendMovEquipVisitTerc = this.statusSendMovEquipVisitTerc,
            statusMovEquipForeigVisitTerc = this.statusMovEquipForeignerVisitTerc,
        )
    }
}