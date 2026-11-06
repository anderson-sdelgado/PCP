package br.com.usinasantafe.pcp.infra.models.retrofit.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipVisitTercPassag
import br.com.usinasantafe.pcp.utils.required

data class MovEquipVisitTercPassagRetrofitModelOutput(
    val idMovEquipVisitTercPassag: Int,
    val idMovEquipVisitTerc: Int,
    val idVisitTerc: Int,
)


fun MovEquipVisitTercPassag.entityToRetrofitModel(): MovEquipVisitTercPassagRetrofitModelOutput {
    return with(this){
        MovEquipVisitTercPassagRetrofitModelOutput(
            idMovEquipVisitTercPassag = this::idMovEquipVisitTercPassag.required(),
            idMovEquipVisitTerc = this::idMovEquipVisitTerc.required(),
            idVisitTerc = this::idVisitTerc.required(),
        )
    }
}
