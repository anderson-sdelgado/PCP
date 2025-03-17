package br.com.usinasantafe.pcp.infra.models.retrofit.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovChaveEquip
import java.text.SimpleDateFormat
import java.util.Locale

data class MovChaveEquipRetrofitModelOutput(
    var uuidMainMovChaveEquip: String,
    var idMovChaveEquip: Int,
    var nroAparelhoMovChaveEquip: Long,
    var matricVigiaMovChaveEquip: Int,
    var idLocalMovChaveEquip: Int,
    var tipoMovChaveEquip: Int,
    var dthrMovChaveEquip: String,
    var idEquipMovChaveEquip: Int,
    var matricColabMovChaveEquip: Int,
    var observMovChaveEquip: String?,
)

data class MovChaveEquipRetrofitModelInput(
    var idMovChaveEquip: Int
)

fun MovChaveEquip.entityToRetrofitModelOutput(nroAparelho: Long): MovChaveEquipRetrofitModelOutput {
    return with(this){
        MovChaveEquipRetrofitModelOutput(
            uuidMainMovChaveEquip = this.uuidMainMovChaveEquip!!,
            idMovChaveEquip = this.idMovChaveEquip!!,
            nroAparelhoMovChaveEquip = nroAparelho,
            matricVigiaMovChaveEquip = this.matricVigiaMovChaveEquip!!,
            idLocalMovChaveEquip = this.idLocalMovChaveEquip!!,
            tipoMovChaveEquip = this.tipoMovChaveEquip!!.ordinal + 1,
            dthrMovChaveEquip = SimpleDateFormat(
                "dd/MM/yyyy HH:mm",
                Locale("pt", "BR")
            ).format(this.dthrMovChaveEquip),
            idEquipMovChaveEquip = this.idEquipMovChaveEquip!!,
            matricColabMovChaveEquip = this.matricColabMovChaveEquip!!,
            observMovChaveEquip = this.observMovChaveEquip,
        )
    }
}

fun MovChaveEquipRetrofitModelInput.retrofitModelInputToEntity(): MovChaveEquip {
    return with(this){
        MovChaveEquip(
            idMovChaveEquip = this.idMovChaveEquip,
        )
    }
}
