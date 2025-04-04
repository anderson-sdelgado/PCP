package br.com.usinasantafe.pcp.domain.entities.variable

import br.com.usinasantafe.pcp.utils.StatusData
import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.utils.TypeMovEquip
import java.util.Date

data class MovEquipProprio(
    var idMovEquipProprio: Int? = null,
    var tipoMovEquipProprio: TypeMovEquip? = null,
    var idEquipMovEquipProprio: Int? = null,
    var idLocalMovEquipProprio: Int? = null,
    var dthrMovEquipProprio: Date = Date(),
    var matricVigiaMovEquipProprio: Int? = null,
    var matricColabMovEquipProprio: Int? = null,
    var destinoMovEquipProprio: String? = null,
    var notaFiscalMovEquipProprio: Int? = null,
    var observMovEquipProprio: String? = null,
    var statusMovEquipProprio: StatusData = StatusData.OPEN,
    var statusSendMovEquipProprio: StatusSend = StatusSend.SEND,
    var movEquipProprioEquipSegList: List<MovEquipProprioEquipSeg>? = emptyList(),
    var movEquipProprioPassagList: List<MovEquipProprioPassag>? = emptyList(),
)
