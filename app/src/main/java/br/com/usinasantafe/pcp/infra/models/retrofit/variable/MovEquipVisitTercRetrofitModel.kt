package br.com.usinasantafe.pcp.infra.models.retrofit.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipVisitTerc
import br.com.usinasantafe.pcp.utils.required
import java.text.SimpleDateFormat
import java.util.Locale

data class MovEquipVisitTercRetrofitModelOutput(
    val idMovEquipVisitTerc: Int,
    val nroAparelhoMovEquipVisitTerc: Long,
    val nroMatricVigiaMovEquipVisitTerc: Int,
    val idLocalMovEquipVisitTerc: Int,
    val dthrMovEquipVisitTerc: String,
    val tipoMovEquipVisitTerc: Int,
    val idVisitTercMovEquipVisitTerc: Int,
    val tipoVisitTercMovEquipVisitTerc: Int,
    val veiculoMovEquipVisitTerc: String,
    val placaMovEquipVisitTerc: String,
    val destinoMovEquipVisitTerc: String?,
    val observMovEquipVisitTerc: String?,
    val movEquipVisitTercPassagList: List<MovEquipVisitTercPassagRetrofitModelOutput>?,
)

data class MovEquipVisitTercRetrofitModelInput(
    val idMovEquipVisitTerc: Int
)

fun MovEquipVisitTerc.entityToRetrofitModelOutput(nroAparelho: Long): MovEquipVisitTercRetrofitModelOutput {
    return with(this){
        MovEquipVisitTercRetrofitModelOutput(
            idMovEquipVisitTerc = this::idMovEquipVisitTerc.required(),
            nroAparelhoMovEquipVisitTerc = nroAparelho,
            nroMatricVigiaMovEquipVisitTerc = this::nroMatricVigiaMovEquipVisitTerc.required(),
            idLocalMovEquipVisitTerc = this::idLocalMovEquipVisitTerc.required(),
            dthrMovEquipVisitTerc = SimpleDateFormat(
                "dd/MM/yyyy HH:mm",
                Locale("pt", "BR")
            ).format(this.dthrMovEquipVisitTerc),
            tipoMovEquipVisitTerc = this::tipoMovEquipVisitTerc.required().ordinal + 1,
            idVisitTercMovEquipVisitTerc = this::idVisitTercMovEquipVisitTerc.required(),
            tipoVisitTercMovEquipVisitTerc = this::tipoVisitTercMovEquipVisitTerc.required().ordinal + 1,
            veiculoMovEquipVisitTerc = this::veiculoMovEquipVisitTerc.required(),
            placaMovEquipVisitTerc = this::placaMovEquipVisitTerc.required(),
            destinoMovEquipVisitTerc = this.destinoMovEquipVisitTerc,
            observMovEquipVisitTerc = this.observMovEquipVisitTerc,
            movEquipVisitTercPassagList = this.movEquipVisitTercPassagList?.map { it.entityToRetrofitModel() }
        )
    }
}

fun MovEquipVisitTercRetrofitModelInput.retrofitModelInputToEntity(): MovEquipVisitTerc {
    return with(this){
        MovEquipVisitTerc(
            idMovEquipVisitTerc = this.idMovEquipVisitTerc,
        )
    }
}