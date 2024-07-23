package br.com.usinasantafe.pcp.infra.models.webservice

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipVisitTerc
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.Locale

@Serializable
data class MovEquipVisitTercWebServiceModelOutput(
    var idMovEquipVisitTerc: Long,
    var nroAparelhoMovEquipVisitTerc: Long,
    var nroMatricVigiaMovEquipVisitTerc: Long,
    var idLocalMovEquipVisitTerc: Long,
    var dthrMovEquipVisitTerc: String,
    var tipoMovEquipVisitTerc: Long,
    var idVisitTercMovEquipVisitTerc: Long,
    var tipoVisitTercMovEquipVisitTerc: Long,
    var veiculoMovEquipVisitTerc: String,
    var placaMovEquipVisitTerc: String,
    var destinoMovEquipVisitTerc: String?,
    var observMovEquipVisitTerc: String?,
    var movEquipVisitTercPassagList: List<MovEquipVisitTercPassagWebServiceModelOutput>?,
)

@Serializable
data class MovEquipVisitTercWebServiceModelInput(
    val idMovEquipVisitTerc: Long,
)

fun MovEquipVisitTerc.entityToMovEquipVisitTercWebServiceModel(nroAparelho: Long): MovEquipVisitTercWebServiceModelOutput {
    return with(this){
        MovEquipVisitTercWebServiceModelOutput(
            idMovEquipVisitTerc = this.idMovEquipVisitTerc!!,
            nroAparelhoMovEquipVisitTerc = nroAparelho,
            nroMatricVigiaMovEquipVisitTerc = this.nroMatricVigiaMovEquipVisitTerc!!,
            idLocalMovEquipVisitTerc = this.idLocalMovEquipVisitTerc!!,
            dthrMovEquipVisitTerc = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt", "BR")).format(this.dthrMovEquipVisitTerc),
            tipoMovEquipVisitTerc = this.tipoMovEquipVisitTerc!!.ordinal.toLong() + 1L,
            idVisitTercMovEquipVisitTerc = this.idVisitTercMovEquipVisitTerc!!,
            tipoVisitTercMovEquipVisitTerc = this.tipoVisitTercMovEquipVisitTerc!!.ordinal.toLong() + 1L,
            veiculoMovEquipVisitTerc = this.veiculoMovEquipVisitTerc!!,
            placaMovEquipVisitTerc = this.placaMovEquipVisitTerc!!,
            destinoMovEquipVisitTerc = this.destinoMovEquipVisitTerc,
            observMovEquipVisitTerc = this.observMovEquipVisitTerc,
            movEquipVisitTercPassagList = this.movEquipVisitTercPassagList?.map { it.entityToMovEquipVisitTercPassagWebServiceModel() }
        )
    }
}

fun MovEquipVisitTercWebServiceModelInput.modelWebServiceToMovEquipVisitTerc(): MovEquipVisitTerc {
    return with(this){
        br.com.usinasantafe.pcp.domain.entities.variable.MovEquipVisitTerc(
            idMovEquipVisitTerc = this.idMovEquipVisitTerc,
        )
    }
}
