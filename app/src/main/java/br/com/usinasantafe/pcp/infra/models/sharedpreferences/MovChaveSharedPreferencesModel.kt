package br.com.usinasantafe.pcp.infra.models.sharedpreferences

import br.com.usinasantafe.pcp.domain.entities.variable.MovChave
import br.com.usinasantafe.pcp.lib.TypeMovKey
import java.util.Date

data class MovChaveSharedPreferencesModel(
    var dthrMovChave: Date = Date(),
    var tipoMovChave: TypeMovKey? =TypeMovKey.REMOVE,
    var idChaveMovChave: Int? = null,
    var matricColabMovChave: Int? = null,
    var observMovChave: String? = null,
)

fun MovChaveSharedPreferencesModel.sharedPreferencesModelToEntity(): MovChave {
    return with(this) {
        MovChave(
            dthrMovChave = this.dthrMovChave,
            tipoMovChave = this.tipoMovChave,
            idChaveMovChave = this.idChaveMovChave,
            matricColabMovChave = this.matricColabMovChave,
            observMovChave = this.observMovChave,
        )
    }
}

fun MovChave.sharedPreferencesModelToEntity(): MovChaveSharedPreferencesModel {
    return with(this) {
        MovChaveSharedPreferencesModel(
            dthrMovChave = this.dthrMovChave,
            tipoMovChave = this.tipoMovChave,
            idChaveMovChave = this.idChaveMovChave,
            matricColabMovChave = this.matricColabMovChave,
            observMovChave = this.observMovChave,
        )
    }
}