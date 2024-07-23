package br.com.usinasantafe.pcp.infra.models.room.variable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.pcp.utils.TB_MOV_EQUIP_VISIT_TERC_PASSAG
import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipVisitTercPassag
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = TB_MOV_EQUIP_VISIT_TERC_PASSAG)
data class MovEquipVisitTercPassagRoomModel(
    @PrimaryKey(autoGenerate = true)
    var idMovEquipVisitTercPassag: Long? = null,
    var idMovEquipVisitTerc: Long,
    var idVisitTercMovEquipVisitTercPassag: Long,
)

fun MovEquipVisitTercPassag.entityToMovEquipVisitTercPassagRoomModel(idMov: Long): MovEquipVisitTercPassagRoomModel{
    return with(this){
        MovEquipVisitTercPassagRoomModel(
            idMovEquipVisitTerc = idMov,
            idVisitTercMovEquipVisitTercPassag = this.idVisitTercMovEquipVisitTercPassag!!,
        )
    }
}

fun MovEquipVisitTercPassagRoomModel.modelRoomToMovEquipVisitTercPassag(): MovEquipVisitTercPassag {
    return with(this){
        MovEquipVisitTercPassag(
            idMovEquipVisitTercPassag = this.idMovEquipVisitTercPassag,
            idMovEquipVisitTerc = this.idMovEquipVisitTerc,
            idVisitTercMovEquipVisitTercPassag = this.idVisitTercMovEquipVisitTercPassag,
        )
    }
}
