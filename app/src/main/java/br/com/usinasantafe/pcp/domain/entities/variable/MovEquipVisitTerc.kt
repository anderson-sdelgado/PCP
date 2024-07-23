package br.com.usinasantafe.pcp.domain.entities.variable

import br.com.usinasantafe.pcp.utils.StatusData
import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.utils.StatusForeigner
import br.com.usinasantafe.pcp.utils.TypeMov
import br.com.usinasantafe.pcp.utils.TypeVisitTerc
import java.util.Date

data class MovEquipVisitTerc(
    var idMovEquipVisitTerc: Long? = null,
    var nroAparelhoMovEquipVisitTerc: Long? = null,
    var nroMatricVigiaMovEquipVisitTerc: Long? = null,
    var idLocalMovEquipVisitTerc: Long? = null,
    var dthrMovEquipVisitTerc: Date = Date(),
    var tipoMovEquipVisitTerc: TypeMov? = null,
    var idVisitTercMovEquipVisitTerc: Long? = null,
    var tipoVisitTercMovEquipVisitTerc: TypeVisitTerc? = null,
    var veiculoMovEquipVisitTerc: String? = null,
    var placaMovEquipVisitTerc: String? = null,
    var destinoMovEquipVisitTerc: String? = null,
    var observMovEquipVisitTerc: String? = null,
    var statusMovEquipVisitTerc: StatusData = StatusData.OPEN,
    var statusSendMovEquipVisitTerc: StatusSend = StatusSend.SEND,
    var statusMovEquipForeigVisitTerc: StatusForeigner = StatusForeigner.INSIDE,
    var movEquipVisitTercPassagList: List<MovEquipVisitTercPassag>? = emptyList(),
)
