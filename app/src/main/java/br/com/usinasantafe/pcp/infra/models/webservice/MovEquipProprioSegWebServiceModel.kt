package br.com.usinasantafe.pcp.infra.models.webservice

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipProprioSeg
import kotlinx.serialization.Serializable

@Serializable
data class MovEquipProprioSegWebServiceModelOutput(
    var idMovEquipProprioSeg: Long,
    var idMovEquipProprio: Long,
    var idEquipMovEquipProprioSeg: Long,
)

fun MovEquipProprioSeg.entityToMovEquipProprioSegWebServiceModel(): MovEquipProprioSegWebServiceModelOutput {
    return with(this){
        MovEquipProprioSegWebServiceModelOutput(
            idMovEquipProprioSeg = this.idMovEquipProprioSeg!!,
            idMovEquipProprio = this.idMovEquipProprio!!,
            idEquipMovEquipProprioSeg = this.idEquipMovEquipProprioSeg!!,
        )
    }
}