package br.com.usinasantafe.pcp.infra.models.sharedpreferences

import br.com.usinasantafe.pcp.utils.TypeMov
import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipResidencia
import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipVisitTerc
import java.util.Date

data class MovEquipResidenciaSharedPreferencesModel(
    var dthrMovEquipResidencia: Date = Date(),
    var tipoMovEquipResidencia: TypeMov = TypeMov.INPUT,
    var motoristaMovEquipResidencia: String? = null,
    var veiculoMovEquipResidencia: String? = null,
    var placaMovEquipResidencia: String? = null,
    var observMovEquipResidencia: String? = null,
)

fun MovEquipResidenciaSharedPreferencesModel.modelSharedPreferencesToMovEquipResidencia(): MovEquipResidencia {
    return with(this){
        MovEquipResidencia(
            dthrMovEquipResidencia = this.dthrMovEquipResidencia,
            tipoMovEquipResidencia = this.tipoMovEquipResidencia,
            motoristaMovEquipResidencia = this.motoristaMovEquipResidencia,
            veiculoMovEquipResidencia = this.veiculoMovEquipResidencia,
            placaMovEquipResidencia = this.placaMovEquipResidencia,
            observMovEquipResidencia = this.observMovEquipResidencia,
        )
    }
}

