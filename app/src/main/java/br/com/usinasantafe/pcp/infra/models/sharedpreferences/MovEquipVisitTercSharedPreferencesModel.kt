package br.com.usinasantafe.pcp.infra.models.sharedpreferences

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipVisitTerc
import br.com.usinasantafe.pcp.utils.TypeMovEquip
import br.com.usinasantafe.pcp.utils.TypeVisitTerc
import java.util.Date

data class MovEquipVisitTercSharedPreferencesModel(
    var dthrMovEquipVisitTerc: Date = Date(),
    var tipoMovEquipVisitTerc: TypeMovEquip = TypeMovEquip.INPUT,
    var idVisitTercMovEquipVisitTerc: Int? = null,
    var tipoVisitTercMovEquipVisitTerc: TypeVisitTerc? = null,
    var veiculoMovEquipVisitTerc: String? = null,
    var placaMovEquipVisitTerc: String? = null,
    var destinoMovEquipVisitTerc: String? = null,
    var observMovEquipVisitTerc: String? = null,
)

fun MovEquipVisitTercSharedPreferencesModel.entityToSharedPreferencesModel(): MovEquipVisitTerc {
    return with(this){
        MovEquipVisitTerc(
            dthrMovEquipVisitTerc = this.dthrMovEquipVisitTerc,
            tipoMovEquipVisitTerc = this.tipoMovEquipVisitTerc,
            idVisitTercMovEquipVisitTerc = this.idVisitTercMovEquipVisitTerc,
            tipoVisitTercMovEquipVisitTerc = this.tipoVisitTercMovEquipVisitTerc,
            veiculoMovEquipVisitTerc = this.veiculoMovEquipVisitTerc,
            placaMovEquipVisitTerc = this.placaMovEquipVisitTerc,
            destinoMovEquipVisitTerc = this.destinoMovEquipVisitTerc,
            observMovEquipVisitTerc = this.observMovEquipVisitTerc,
        )
    }
}

fun MovEquipVisitTerc.entityToSharedPreferencesModel(): MovEquipVisitTercSharedPreferencesModel {
    return with(this){
        MovEquipVisitTercSharedPreferencesModel(
            dthrMovEquipVisitTerc = this.dthrMovEquipVisitTerc,
            tipoMovEquipVisitTerc = this.tipoMovEquipVisitTerc!!,
            idVisitTercMovEquipVisitTerc = this.idVisitTercMovEquipVisitTerc,
            tipoVisitTercMovEquipVisitTerc = this.tipoVisitTercMovEquipVisitTerc,
            veiculoMovEquipVisitTerc = this.veiculoMovEquipVisitTerc,
            placaMovEquipVisitTerc = this.placaMovEquipVisitTerc,
            destinoMovEquipVisitTerc = this.destinoMovEquipVisitTerc,
            observMovEquipVisitTerc = this.observMovEquipVisitTerc,
        )
    }
}
