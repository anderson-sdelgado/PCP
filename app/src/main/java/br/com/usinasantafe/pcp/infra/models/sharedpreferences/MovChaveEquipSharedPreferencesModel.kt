package br.com.usinasantafe.pcp.infra.models.sharedpreferences

import br.com.usinasantafe.pcp.domain.entities.variable.MovChaveEquip
import br.com.usinasantafe.pcp.utils.TypeMovKey
import java.util.Date

data class MovChaveEquipSharedPreferencesModel(
    var dthrMovChaveEquip: Date = Date(),
    var tipoMovChaveEquip: TypeMovKey? =TypeMovKey.RECEIPT,
    var idEquipMovChaveEquip: Int? = null,
    var matricColabMovChaveEquip: Int? = null,
    var observMovChaveEquip: String? = null,
)


fun MovChaveEquipSharedPreferencesModel.entityToSharedPreferencesModel(): MovChaveEquip {
    return with(this) {
        MovChaveEquip(
            dthrMovChaveEquip = this.dthrMovChaveEquip,
            tipoMovChaveEquip = this.tipoMovChaveEquip,
            idEquipMovChaveEquip = this.idEquipMovChaveEquip,
            matricColabMovChaveEquip = this.matricColabMovChaveEquip,
            observMovChaveEquip = this.observMovChaveEquip,
        )
    }
}

fun MovChaveEquip.entityToSharedPreferencesModel(): MovChaveEquipSharedPreferencesModel {
    return with(this) {
        MovChaveEquipSharedPreferencesModel(
            dthrMovChaveEquip = this.dthrMovChaveEquip,
            tipoMovChaveEquip = this.tipoMovChaveEquip,
            idEquipMovChaveEquip = this.idEquipMovChaveEquip,
            matricColabMovChaveEquip = this.matricColabMovChaveEquip,
            observMovChaveEquip = this.observMovChaveEquip,
        )
    }
}