package br.com.usinasantafe.pcp.domain.entities.variable

import br.com.usinasantafe.pcp.utils.FlagUpdate
import br.com.usinasantafe.pcp.utils.StatusData
import br.com.usinasantafe.pcp.utils.StatusSend


data class Config(
    var nroAparelhoConfig: Long? = null,
    var passwordConfig: String? = null,
    var idBDConfig: Long? = null,
    var version: String? = null,
    var flagUpdate: FlagUpdate = FlagUpdate.OUTDATED,
    var matricVigia: Long? = null,
    var idLocal: Long? = null,
    var statusEnvio: StatusSend = StatusSend.SENT,
    var statusApont: StatusData = StatusData.CLOSE,
)
