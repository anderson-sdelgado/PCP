package br.com.usinasantafe.pcp.domain.entities.variable

import br.com.usinasantafe.pcp.utils.FlagUpdate
import br.com.usinasantafe.pcp.utils.StatusData
import br.com.usinasantafe.pcp.utils.StatusSend

data class Config(
    var number: Long? = null,
    var password: String? = null,
    var idBD: Int? = null,
    var version: String? = null,
    var flagUpdate: FlagUpdate = FlagUpdate.OUTDATED,
    var matricVigia: Int? = null,
    var idLocal: Int? = null,
    var statusSend: StatusSend = StatusSend.STARTED
)
