package br.com.usinasantafe.pcp.infra.models.room.variable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipVisitTercPassag
import br.com.usinasantafe.pcp.lib.TB_MOV_EQUIP_VISIT_TERC_PASSAG

@Entity(tableName = TB_MOV_EQUIP_VISIT_TERC_PASSAG)
data class MovEquipVisitTercPassagRoomModel(
    @PrimaryKey(autoGenerate = true)
    val idMovEquipVisitTercPassag: Int? = null,
    val idMovEquipVisitTerc: Int,
    val idVisitTerc: Int,
)


fun MovEquipVisitTercPassagRoomModel.modelRoomToEntity(): MovEquipVisitTercPassag {
    return with(this){
        MovEquipVisitTercPassag(
            idMovEquipVisitTercPassag = this.idMovEquipVisitTercPassag,
            idMovEquipVisitTerc = this.idMovEquipVisitTerc,
            idVisitTerc = this.idVisitTerc,
        )
    }
}
