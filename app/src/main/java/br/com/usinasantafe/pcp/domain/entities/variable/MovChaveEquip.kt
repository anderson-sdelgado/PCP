package br.com.usinasantafe.pcp.domain.entities.variable

import br.com.usinasantafe.pcp.utils.StatusData
import br.com.usinasantafe.pcp.utils.StatusForeigner
import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.utils.TypeMovKey
import java.util.Date

data class MovChaveEquip(
    var uuidMainMovChaveEquip: String? = null,
    var idMovChaveEquip: Int? = null,
    var matricVigiaMovChaveEquip: Int? = null,
    var idLocalMovChaveEquip: Int? = null,
    var dthrMovChaveEquip: Date = Date(),
    var tipoMovChaveEquip: TypeMovKey? = null,
    var idEquipMovChaveEquip: Int? = null,
    var matricColabMovChaveEquip: Int? = null,
    var observMovChaveEquip: String? = null,
    var statusMovChaveEquip: StatusData = StatusData.OPEN,
    var statusSendMovChaveEquip: StatusSend = StatusSend.SEND,
    var statusForeignerMovChaveEquip: StatusForeigner = StatusForeigner.INSIDE,
)
