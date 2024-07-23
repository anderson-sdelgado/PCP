package br.com.usinasantafe.pcp.infra.models.webservice

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipProprio
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.Locale

@Serializable
data class MovEquipProprioWebServiceModelOutput(
    var idMovEquipProprio: Long,
    var nroAparelhoMovEquipProprio: Long,
    var nroMatricVigiaMovEquipProprio: Long,
    var idLocalMovEquipProprio: Long,
    var dthrMovEquipProprio: String,
    var tipoMovEquipProprio: Long,
    var idEquipMovEquipProprio: Long,
    var nroMatricColabMovEquipProprio: Long,
    var destinoMovEquipProprio: String,
    var nroNotaFiscalMovEquipProprio: Long?,
    var observMovEquipProprio: String?,
    var movEquipProprioSegList: List<MovEquipProprioSegWebServiceModelOutput>?,
    var movEquipProprioPassagList: List<MovEquipProprioPassagWebServiceModelOutput>?,
)

@Serializable
data class MovEquipProprioWebServiceModelInput(
    val idMovEquipProprio: Long,
)

fun MovEquipProprio.entityToMovEquipProprioWebServiceModel(nroAparelho: Long): MovEquipProprioWebServiceModelOutput {
    return with(this){
        MovEquipProprioWebServiceModelOutput(
            idMovEquipProprio = this.idMovEquipProprio!!,
            nroAparelhoMovEquipProprio = nroAparelho,
            nroMatricVigiaMovEquipProprio = this.nroMatricVigiaMovEquipProprio!!,
            idLocalMovEquipProprio = this.idLocalMovEquipProprio!!,
            dthrMovEquipProprio = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt", "BR")).format(this.dthrMovEquipProprio),
            tipoMovEquipProprio = this.tipoMovEquipProprio!!.ordinal.toLong() + 1L,
            idEquipMovEquipProprio = this.idEquipMovEquipProprio!!,
            nroMatricColabMovEquipProprio = this.nroMatricColabMovEquipProprio!!,
            destinoMovEquipProprio = this.destinoMovEquipProprio!!,
            nroNotaFiscalMovEquipProprio = this.nroNotaFiscalMovEquipProprio,
            observMovEquipProprio = this.observMovEquipProprio,
            movEquipProprioSegList = this.movEquipProprioSegList?.map { it.entityToMovEquipProprioSegWebServiceModel() },
            movEquipProprioPassagList = this.movEquipProprioPassagList?.map { it.entityToMovEquipProprioPassagWebServiceModel() }
        )
    }
}

fun MovEquipProprioWebServiceModelInput.modelWebServiceToMovEquipProprio(): MovEquipProprio {
    return with(this){
        MovEquipProprio(
            idMovEquipProprio = this.idMovEquipProprio,
        )
    }
}
