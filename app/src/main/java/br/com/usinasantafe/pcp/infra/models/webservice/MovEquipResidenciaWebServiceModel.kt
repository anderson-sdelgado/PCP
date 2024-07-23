package br.com.usinasantafe.pcp.infra.models.webservice

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipResidencia
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.Locale

@Serializable
data class MovEquipResidenciaWebServiceModelOutput(
    var idMovEquipResidencia: Long,
    var nroAparelhoMovEquipResidencia: Long,
    var nroMatricVigiaMovEquipResidencia: Long,
    var idLocalMovEquipResidencia: Long,
    var dthrMovEquipResidencia:  String,
    var tipoMovEquipResidencia: Long,
    var nomeVisitanteMovEquipResidencia: String,
    var veiculoMovEquipResidencia: String,
    var placaMovEquipResidencia: String,
    var observMovEquipResidencia: String?,
)

@Serializable
data class MovEquipResidenciaWebServiceModelInput(
    var idMovEquipResidencia: Long,
)

fun MovEquipResidencia.entityToMovEquipResidenciaWebServiceModel(nroAparelho: Long): MovEquipResidenciaWebServiceModelOutput {
    return with(this){
        MovEquipResidenciaWebServiceModelOutput(
            idMovEquipResidencia = this.idMovEquipResidencia!!,
            nroAparelhoMovEquipResidencia = nroAparelho,
            nroMatricVigiaMovEquipResidencia = this.nroMatricVigiaMovEquipResidencia!!,
            idLocalMovEquipResidencia = this.idLocalMovEquipResidencia!!,
            dthrMovEquipResidencia = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt", "BR")).format(this.dthrMovEquipResidencia),
            tipoMovEquipResidencia = this.tipoMovEquipResidencia!!.ordinal.toLong() + 1L,
            nomeVisitanteMovEquipResidencia = this.motoristaMovEquipResidencia!!,
            veiculoMovEquipResidencia = this.veiculoMovEquipResidencia!!,
            placaMovEquipResidencia = this.placaMovEquipResidencia!!,
            observMovEquipResidencia = this.observMovEquipResidencia,
        )
    }
}

fun MovEquipResidenciaWebServiceModelInput.modelWebServiceToMovEquipResidencia(): MovEquipResidencia {
    return with(this){
        MovEquipResidencia(
            idMovEquipResidencia = this.idMovEquipResidencia,
        )
    }
}
