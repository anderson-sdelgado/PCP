package br.com.usinasantafe.pcp.infra.models.retrofit.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovChave
import java.text.SimpleDateFormat
import java.util.Locale

data class MovChaveRetrofitModelOutput(
    var uuidMainMovChave: String,
    var idMovChave: Int,
    var nroAparelhoMovChave: Long,
    var matricVigiaMovChave: Int,
    var idLocalMovChave: Int,
    var tipoMovChave: Int,
    var dthrMovChave: String,
    var idChaveMovChave: Int,
    var matricColabMovChave: Int,
    var observMovChave: String?,
)

data class MovChaveRetrofitModelInput(
    val idMovChave: Int
)

fun MovChave.entityToRetrofitModelOutput(nroAparelho: Long): MovChaveRetrofitModelOutput {
    return with(this){
        MovChaveRetrofitModelOutput(
            uuidMainMovChave = this.uuidMainMovChave!!,
            idMovChave = this.idMovChave!!,
            nroAparelhoMovChave = nroAparelho,
            matricVigiaMovChave = this.matricVigiaMovChave!!,
            idLocalMovChave = this.idLocalMovChave!!,
            tipoMovChave = this.tipoMovChave!!.ordinal + 1,
            dthrMovChave = SimpleDateFormat(
                "dd/MM/yyyy HH:mm",
                Locale("pt", "BR")
            ).format(this.dthrMovChave),
            idChaveMovChave = this.idChaveMovChave!!,
            matricColabMovChave = this.matricColabMovChave!!,
            observMovChave = this.observMovChave,
        )
    }
}

fun MovChaveRetrofitModelInput.retrofitModelInputToEntity(): MovChave {
    return with(this){
        MovChave(
            idMovChave = this.idMovChave,
        )
    }
}

