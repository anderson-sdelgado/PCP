package br.com.usinasantafe.pcp.infra.models.webservice

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipVisitTercPassag
import kotlinx.serialization.Serializable

@Serializable
data class MovEquipVisitTercPassagWebServiceModelOutput(
    var idMovEquipVisitTercPassag: Long,
    var idMovEquipVisitTerc: Long,
    var idVisitTercMovEquipVisitTercPassag: Long,
)

fun MovEquipVisitTercPassag.entityToMovEquipVisitTercPassagWebServiceModel(): MovEquipVisitTercPassagWebServiceModelOutput {
    return with(this){
        MovEquipVisitTercPassagWebServiceModelOutput(
            idMovEquipVisitTercPassag = this.idMovEquipVisitTercPassag!!,
            idMovEquipVisitTerc = this.idMovEquipVisitTerc!!,
            idVisitTercMovEquipVisitTercPassag = this.idVisitTercMovEquipVisitTercPassag!!,
        )
    }
}
