package br.com.usinasantafe.pcp.infra.models.retrofit.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovChaveEquip
import br.com.usinasantafe.pcp.utils.required
import java.text.SimpleDateFormat
import java.util.Locale

data class MovChaveEquipRetrofitModelOutput(
    val uuidMainMovChaveEquip: String,
    val idMovChaveEquip: Int,
    val nroAparelhoMovChaveEquip: Long,
    val matricVigiaMovChaveEquip: Int,
    val idLocalMovChaveEquip: Int,
    val tipoMovChaveEquip: Int,
    val dthrMovChaveEquip: String,
    val idEquipMovChaveEquip: Int,
    val matricColabMovChaveEquip: Int,
    val observMovChaveEquip: String?,
)

data class MovChaveEquipRetrofitModelInput(
    val idMovChaveEquip: Int
)

fun MovChaveEquip.entityToRetrofitModelOutput(nroAparelho: Long): MovChaveEquipRetrofitModelOutput {
    return with(this){
        MovChaveEquipRetrofitModelOutput(
            uuidMainMovChaveEquip = this::uuidMainMovChaveEquip.required(),
            idMovChaveEquip = this::idMovChaveEquip.required(),
            nroAparelhoMovChaveEquip = nroAparelho,
            matricVigiaMovChaveEquip = this::matricVigiaMovChaveEquip.required(),
            idLocalMovChaveEquip = this::idLocalMovChaveEquip.required(),
            tipoMovChaveEquip = this::tipoMovChaveEquip.required().ordinal + 1,
            dthrMovChaveEquip = SimpleDateFormat(
                "dd/MM/yyyy HH:mm",
                Locale("pt", "BR")
            ).format(this.dthrMovChaveEquip),
            idEquipMovChaveEquip = this::idEquipMovChaveEquip.required(),
            matricColabMovChaveEquip = this::matricColabMovChaveEquip.required(),
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
