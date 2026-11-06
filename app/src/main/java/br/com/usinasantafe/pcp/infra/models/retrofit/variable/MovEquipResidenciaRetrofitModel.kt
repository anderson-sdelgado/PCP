package br.com.usinasantafe.pcp.infra.models.retrofit.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipResidencia
import br.com.usinasantafe.pcp.utils.required
import java.text.SimpleDateFormat
import java.util.Locale

data class MovEquipResidenciaRetrofitModelOutput(
    val idMovEquipResidencia: Int,
    val nroAparelhoMovEquipResidencia: Long,
    val nroMatricVigiaMovEquipResidencia: Int,
    val idLocalMovEquipResidencia: Int,
    val dthrMovEquipResidencia:  String,
    val tipoMovEquipResidencia: Int,
    val motoristaMovEquipResidencia: String,
    val veiculoMovEquipResidencia: String,
    val placaMovEquipResidencia: String,
    val observMovEquipResidencia: String?,
)

data class MovEquipResidenciaRetrofitModelInput(
    var idMovEquipResidencia: Int
)

fun MovEquipResidencia.entityToRetrofitModelOutput(nroAparelho: Long): MovEquipResidenciaRetrofitModelOutput {
    return with(this){
        MovEquipResidenciaRetrofitModelOutput(
            idMovEquipResidencia = this::idMovEquipResidencia.required(),
            nroAparelhoMovEquipResidencia = nroAparelho,
            nroMatricVigiaMovEquipResidencia = this::matricVigiaMovEquipResidencia.required(),
            idLocalMovEquipResidencia = this::idLocalMovEquipResidencia.required(),
            dthrMovEquipResidencia = SimpleDateFormat(
                "dd/MM/yyyy HH:mm",
                Locale("pt", "BR")
            ).format(this.dthrMovEquipResidencia),
            tipoMovEquipResidencia = this::tipoMovEquipResidencia.required().ordinal + 1,
            motoristaMovEquipResidencia = this::motoristaMovEquipResidencia.required(),
            veiculoMovEquipResidencia = this::veiculoMovEquipResidencia.required(),
            placaMovEquipResidencia = this::placaMovEquipResidencia.required(),
            observMovEquipResidencia = this.observMovEquipResidencia,
        )
    }
}

fun MovEquipResidenciaRetrofitModelInput.retrofitModelInputToEntity(): MovEquipResidencia {
    return with(this){
        MovEquipResidencia(
            idMovEquipResidencia = this.idMovEquipResidencia,
        )
    }
}
