package br.com.usinasantafe.pcp.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.pcp.utils.TB_EQUIP
import br.com.usinasantafe.pcp.domain.entities.stable.Equip

@Entity(tableName = TB_EQUIP)
data class EquipRoomModel (
    @PrimaryKey
    val idEquip: Int,
    val nroEquip: Long,
    val descrEquip: String
)

fun Equip.entityToRoomModel(): EquipRoomModel{
    return with(this){
        EquipRoomModel(
            idEquip = this.idEquip,
            nroEquip = this.nroEquip,
            descrEquip = this.descrEquip
        )
    }
}

fun EquipRoomModel.roomModelToEntity(): Equip{
    return with(this){
        Equip(
            idEquip = this.idEquip,
            nroEquip = this.nroEquip,
            descrEquip = this.descrEquip
        )
    }
}