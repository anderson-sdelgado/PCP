package br.com.usinasantafe.pcp.infra.models.retrofit.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipProprioEquipSeg
import br.com.usinasantafe.pcp.utils.required

data class MovEquipProprioEquipSegWebServiceModelOutput(
    val idMovEquipProprioSeg: Int,
    val idMovEquipProprio: Int,
    val idEquip: Int,
)

fun MovEquipProprioEquipSeg.entityToRetrofitModel(): MovEquipProprioEquipSegWebServiceModelOutput {
    return with(this){
        MovEquipProprioEquipSegWebServiceModelOutput(
            idMovEquipProprioSeg = this::idMovEquipProprioEquipSeg.required(),
            idMovEquipProprio = this::idMovEquipProprio.required(),
            idEquip = this::idEquip.required(),
        )
    }
}