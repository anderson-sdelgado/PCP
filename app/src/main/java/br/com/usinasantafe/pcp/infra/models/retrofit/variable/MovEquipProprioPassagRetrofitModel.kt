package br.com.usinasantafe.pcp.infra.models.retrofit.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipProprioPassag
import br.com.usinasantafe.pcp.utils.required

data class MovEquipProprioPassagRetrofitModelOutput(
    val idMovEquipProprioPassag: Int,
    val idMovEquipProprio: Int,
    val matricColab: Int,
)

fun MovEquipProprioPassag.entityToRetrofitModel(): MovEquipProprioPassagRetrofitModelOutput {
    return with(this){
        MovEquipProprioPassagRetrofitModelOutput(
            idMovEquipProprioPassag = this::idMovEquipProprioPassag.required(),
            idMovEquipProprio = this::idMovEquipProprio.required(),
            matricColab = this::matricColab.required(),
        )
    }
}