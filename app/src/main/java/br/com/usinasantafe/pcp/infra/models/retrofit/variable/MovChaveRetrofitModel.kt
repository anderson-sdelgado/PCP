package br.com.usinasantafe.pcp.infra.models.retrofit.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovChave
import br.com.usinasantafe.pcp.utils.required
import java.text.SimpleDateFormat
import java.util.Locale

data class MovChaveRetrofitModelOutput(
    val uuidMainMovChave: String,
    val idMovChave: Int,
    val nroAparelhoMovChave: Long,
    val matricVigiaMovChave: Int,
    val idLocalMovChave: Int,
    val tipoMovChave: Int,
    val dthrMovChave: String,
    val idChaveMovChave: Int,
    val matricColabMovChave: Int,
    val observMovChave: String?,
)

data class MovChaveRetrofitModelInput(
    val idMovChave: Int
)

fun MovChave.entityToRetrofitModelOutput(nroAparelho: Long): MovChaveRetrofitModelOutput {
    return with(this){
        MovChaveRetrofitModelOutput(
            uuidMainMovChave = this::uuidMainMovChave.required(),
            idMovChave = this::idMovChave.required(),
            nroAparelhoMovChave = nroAparelho,
            matricVigiaMovChave = this::matricVigiaMovChave.required(),
            idLocalMovChave = this::idLocalMovChave.required(),
            tipoMovChave = this::tipoMovChave.required().ordinal + 1,
            dthrMovChave = SimpleDateFormat(
                "dd/MM/yyyy HH:mm",
                Locale("pt", "BR")
            ).format(this.dthrMovChave),
            idChaveMovChave = this::idChaveMovChave.required(),
            matricColabMovChave = this::matricColabMovChave.required(),
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

