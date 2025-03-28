package br.com.usinasantafe.pcp.infra.models.retrofit.stable

import br.com.usinasantafe.pcp.domain.entities.stable.Equip

data class EquipRetrofitModel (
    val idEquip: Int,
    val nroEquip: Long,
    val descrEquip: String,
)

fun EquipRetrofitModel.retrofitModelToEntity(): Equip {
    return with(this){
        Equip(
            idEquip = this.idEquip,
            nroEquip = this.nroEquip,
            descrEquip = this.descrEquip,
        )
    }
}