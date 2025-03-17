package br.com.usinasantafe.pcp.infra.models.retrofit.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipProprioPassag

data class MovEquipProprioPassagRetrofitModelOutput(
    var idMovEquipProprioPassag: Int,
    var idMovEquipProprio: Int,
    var matricColab: Int,
)

fun MovEquipProprioPassag.entityToRetrofitModel(): MovEquipProprioPassagRetrofitModelOutput {
    return with(this){
        MovEquipProprioPassagRetrofitModelOutput(
            idMovEquipProprioPassag = this.idMovEquipProprioPassag!!,
            idMovEquipProprio = this.idMovEquipProprio!!,
            matricColab = this.matricColab!!,
        )
    }
}