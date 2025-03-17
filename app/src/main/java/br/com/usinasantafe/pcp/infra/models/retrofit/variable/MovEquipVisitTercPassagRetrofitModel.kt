package br.com.usinasantafe.pcp.infra.models.retrofit.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipVisitTercPassag

data class MovEquipVisitTercPassagRetrofitModelOutput(
    var idMovEquipVisitTercPassag: Int,
    var idMovEquipVisitTerc: Int,
    var idVisitTerc: Int,
)


fun MovEquipVisitTercPassag.entityToRetrofitModel(): MovEquipVisitTercPassagRetrofitModelOutput {
    return with(this){
        MovEquipVisitTercPassagRetrofitModelOutput(
            idMovEquipVisitTercPassag = this.idMovEquipVisitTercPassag!!,
            idMovEquipVisitTerc = this.idMovEquipVisitTerc!!,
            idVisitTerc = this.idVisitTerc!!,
        )
    }
}
