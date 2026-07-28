package br.com.usinasantafe.pcp.infra.models.sharedpreferences

import br.com.usinasantafe.pcp.domain.entities.variable.Config
import br.com.usinasantafe.pcp.lib.FlagUpdate
import br.com.usinasantafe.pcp.lib.StatusSend

data class ConfigSharedPreferencesModel(
    var number: Long? = null,
    var password: String? = null,
    var idServ: Int? = null,
    var version: String? = null,
    var flagUpdate: FlagUpdate = FlagUpdate.OUTDATED,
    var matricVigia: Int? = null,
    var idLocal: Int? = null,
    var statusSend: StatusSend = StatusSend.STARTED
)

fun ConfigSharedPreferencesModel.sharedPreferencesModelToEntity(): Config {
    return with(this) {
        Config(
            number = number,
            password = password,
            idServ = idServ,
            version = version,
            flagUpdate = flagUpdate,
            matricVigia = matricVigia,
            idLocal = idLocal,
            statusSend = statusSend
        )
    }
}

fun Config.sharedPreferencesModelToEntity(): ConfigSharedPreferencesModel {
    return with(this) {
        ConfigSharedPreferencesModel(
            number = number,
            password = password,
            idServ = idServ,
            version = version,
            flagUpdate = flagUpdate,
            matricVigia = matricVigia,
            idLocal = idLocal,
            statusSend = statusSend
        )
    }
}