package br.com.usinasantafe.pcp.infra.models.sharedpreferences

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipProprio
import br.com.usinasantafe.pcp.utils.TypeMovEquip
import java.util.Date


data class MovEquipProprioSharedPreferencesModel(
    var dthrMovEquipProprio: Date = Date(),
    var tipoMovEquipProprio: TypeMovEquip,
    var idEquipMovEquipProprio: Int? = null,
    var matricColabMovEquipProprio: Int? = null,
    var destinoMovEquipProprio: String? = null,
    var notaFiscalMovEquipProprio: Int? = null,
    var observMovEquipProprio: String? = null,
)

fun MovEquipProprioSharedPreferencesModel.entityToSharedPreferencesModel(): MovEquipProprio {
    return with(this){
        MovEquipProprio(
            dthrMovEquipProprio = this.dthrMovEquipProprio,
            tipoMovEquipProprio = this.tipoMovEquipProprio,
            idEquipMovEquipProprio = this.idEquipMovEquipProprio,
            matricColabMovEquipProprio = this.matricColabMovEquipProprio,
            destinoMovEquipProprio = this.destinoMovEquipProprio,
            notaFiscalMovEquipProprio = this.notaFiscalMovEquipProprio,
            observMovEquipProprio = this.observMovEquipProprio,
        )
    }
}


