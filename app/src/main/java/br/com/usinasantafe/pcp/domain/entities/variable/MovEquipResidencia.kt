package br.com.usinasantafe.pcp.domain.entities.variable

import br.com.usinasantafe.pcp.utils.StatusData
import br.com.usinasantafe.pcp.utils.StatusForeigner
import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.utils.TypeMov
import java.util.Date

data class MovEquipResidencia(
    var idMovEquipResidencia: Long? = null,
    var nroAparelhoMovEquipResidencia: Long? = null,
    var nroMatricVigiaMovEquipResidencia: Long? = null,
    var idLocalMovEquipResidencia: Long? = null,
    var dthrMovEquipResidencia: Date = Date(),
    var tipoMovEquipResidencia: TypeMov? = null,
    var veiculoMovEquipResidencia: String? = null,
    var placaMovEquipResidencia: String? = null,
    var motoristaMovEquipResidencia: String? = null,
    var observMovEquipResidencia: String? = null,
    var statusMovEquipResidencia: StatusData = StatusData.OPEN,
    var statusSendMovEquipResidencia: StatusSend = StatusSend.SEND,
    var statusMovEquipForeigResidencia: StatusForeigner = StatusForeigner.INSIDE,
)
