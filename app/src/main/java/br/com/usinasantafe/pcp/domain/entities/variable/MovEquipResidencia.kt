package br.com.usinasantafe.pcp.domain.entities.variable

import br.com.usinasantafe.pcp.utils.StatusData
import br.com.usinasantafe.pcp.utils.StatusForeigner
import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.utils.TypeMovEquip
import java.util.Date

data class MovEquipResidencia(
    var idMovEquipResidencia: Int? = null,
    var nroAparelhoMovEquipResidencia: Int? = null,
    var matricVigiaMovEquipResidencia: Int? = null,
    var idLocalMovEquipResidencia: Int? = null,
    var dthrMovEquipResidencia: Date = Date(),
    var tipoMovEquipResidencia: TypeMovEquip? = null,
    var veiculoMovEquipResidencia: String? = null,
    var placaMovEquipResidencia: String? = null,
    var motoristaMovEquipResidencia: String? = null,
    var observMovEquipResidencia: String? = null,
    var statusMovEquipResidencia: StatusData = StatusData.OPEN,
    var statusSendMovEquipResidencia: StatusSend = StatusSend.SEND,
    var statusMovEquipForeignerResidencia: StatusForeigner = StatusForeigner.INSIDE,
)
