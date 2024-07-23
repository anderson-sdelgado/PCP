package br.com.usinasantafe.pcp.infra.models.room.variable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.pcp.utils.TB_MOV_EQUIP_PROPRIO_SEG
import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipProprioSeg
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = TB_MOV_EQUIP_PROPRIO_SEG)
data class MovEquipProprioSegRoomModel(
    @PrimaryKey(autoGenerate = true)
    var idMovEquipProprioSeg: Long? = null,
    var idMovEquipProprio: Long,
    var idEquipMovEquipProprioSeg: Long,
)

fun MovEquipProprioSeg.entityToMovEquipProprioSegRoomModel(idMov: Long): MovEquipProprioSegRoomModel{
    return with(this){
        MovEquipProprioSegRoomModel(
            idMovEquipProprio = idMov,
            idEquipMovEquipProprioSeg = this.idEquipMovEquipProprioSeg!!,
        )
    }
}

fun MovEquipProprioSegRoomModel.modelRoomToMovEquipSegPassag(): MovEquipProprioSeg {
    return with(this){
        MovEquipProprioSeg(
            idMovEquipProprioSeg = this.idMovEquipProprioSeg,
            idMovEquipProprio = this.idMovEquipProprio,
            idEquipMovEquipProprioSeg = this.idEquipMovEquipProprioSeg,
        )
    }
}
