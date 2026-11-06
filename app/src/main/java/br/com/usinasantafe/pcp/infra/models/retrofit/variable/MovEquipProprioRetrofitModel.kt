package br.com.usinasantafe.pcp.infra.models.retrofit.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipProprio
import br.com.usinasantafe.pcp.utils.required
import java.text.SimpleDateFormat
import java.util.Locale

data class MovEquipProprioRetrofitModelOutput(
    val idMovEquipProprio: Int,
    val nroAparelhoMovEquipProprio: Long,
    val matricVigiaMovEquipProprio: Int,
    val idLocalMovEquipProprio: Int,
    val dthrMovEquipProprio: String,
    val tipoMovEquipProprio: Int,
    val idEquipMovEquipProprio: Int,
    val matricColabMovEquipProprio: Int,
    val destinoMovEquipProprio: String,
    val notaFiscalMovEquipProprio: Int?,
    val observMovEquipProprio: String?,
    val movEquipProprioEquipSegList: List<MovEquipProprioEquipSegWebServiceModelOutput>?,
    val movEquipProprioPassagList: List<MovEquipProprioPassagRetrofitModelOutput>?,
)

data class MovEquipProprioRetrofitModelInput(
    val idMovEquipProprio: Int
)

fun MovEquipProprio.entityToRetrofitModelOutput(nroAparelho: Long): MovEquipProprioRetrofitModelOutput {
    return with(this){
        MovEquipProprioRetrofitModelOutput(
            idMovEquipProprio = this::idMovEquipProprio.required(),
            nroAparelhoMovEquipProprio = nroAparelho,
            matricVigiaMovEquipProprio = this::matricVigiaMovEquipProprio.required(),
            idLocalMovEquipProprio = this::idLocalMovEquipProprio.required(),
            dthrMovEquipProprio = SimpleDateFormat(
                "dd/MM/yyyy HH:mm",
                Locale("pt", "BR")
            ).format(this.dthrMovEquipProprio),
            tipoMovEquipProprio = this::tipoMovEquipProprio.required().ordinal + 1,
            idEquipMovEquipProprio = this::idEquipMovEquipProprio.required(),
            matricColabMovEquipProprio = this::matricColabMovEquipProprio.required(),
            destinoMovEquipProprio = this::destinoMovEquipProprio.required(),
            notaFiscalMovEquipProprio = this.notaFiscalMovEquipProprio,
            observMovEquipProprio = this.observMovEquipProprio,
            movEquipProprioEquipSegList = this.movEquipProprioEquipSegList?.map { it.entityToRetrofitModel() },
            movEquipProprioPassagList = this.movEquipProprioPassagList?.map { it.entityToRetrofitModel() }
        )
    }
}

fun MovEquipProprioRetrofitModelInput.retrofitModelInputToEntity(): MovEquipProprio {
    return with(this){
        MovEquipProprio(
            idMovEquipProprio = this.idMovEquipProprio,
        )
    }
}