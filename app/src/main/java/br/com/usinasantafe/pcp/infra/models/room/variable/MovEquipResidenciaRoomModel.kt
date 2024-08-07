package br.com.usinasantafe.pcp.infra.models.room.variable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.pcp.utils.StatusData
import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.utils.TB_MOV_EQUIP_RESIDENCIA
import br.com.usinasantafe.pcp.utils.TypeMov
import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipResidencia
import br.com.usinasantafe.pcp.utils.StatusForeigner
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
@Entity(tableName = TB_MOV_EQUIP_RESIDENCIA)
data class MovEquipResidenciaRoomModel(
    @PrimaryKey(autoGenerate = true)
    var idMovEquipResidencia: Long? = null,
    var nroMatricVigiaMovEquipResidencia: Long,
    var idLocalMovEquipResidencia: Long,
    var tipoMovEquipResidencia: TypeMov,
    var dthrMovEquipResidencia: Long,
    var motoristaMovEquipResidencia: String,
    var veiculoMovEquipResidencia: String,
    var placaMovEquipResidencia: String,
    var observMovEquipResidencia: String?,
    var statusMovEquipResidencia: StatusData,
    var statusSendMovEquipResidencia: StatusSend,
    var statusMovEquipForeigResidencia: StatusForeigner,
)

fun MovEquipResidenciaRoomModel.modelRoomToMovEquipResidencia(): MovEquipResidencia {
    return with(this){
        MovEquipResidencia(
            idMovEquipResidencia = this.idMovEquipResidencia,
            nroMatricVigiaMovEquipResidencia = this.nroMatricVigiaMovEquipResidencia,
            idLocalMovEquipResidencia = this.idLocalMovEquipResidencia,
            dthrMovEquipResidencia = Date(this.dthrMovEquipResidencia),
            tipoMovEquipResidencia = this.tipoMovEquipResidencia,
            motoristaMovEquipResidencia = this.motoristaMovEquipResidencia,
            veiculoMovEquipResidencia = this.veiculoMovEquipResidencia,
            placaMovEquipResidencia = this.placaMovEquipResidencia,
            observMovEquipResidencia = this.observMovEquipResidencia,
            statusMovEquipResidencia = this.statusMovEquipResidencia,
            statusSendMovEquipResidencia = this.statusSendMovEquipResidencia,
            statusMovEquipForeigResidencia = this.statusMovEquipForeigResidencia,
        )
    }
}

fun MovEquipResidencia.entityToMovEquipResidenciaRoomModel(matricVigia: Long, idLocal: Long): MovEquipResidenciaRoomModel{
    return with(this){
        MovEquipResidenciaRoomModel(
            idMovEquipResidencia = this.idMovEquipResidencia,
            nroMatricVigiaMovEquipResidencia = matricVigia,
            idLocalMovEquipResidencia = idLocal,
            dthrMovEquipResidencia = this.dthrMovEquipResidencia.time,
            tipoMovEquipResidencia = this.tipoMovEquipResidencia!!,
            motoristaMovEquipResidencia = this.motoristaMovEquipResidencia!!,
            veiculoMovEquipResidencia = this.veiculoMovEquipResidencia!!,
            placaMovEquipResidencia = this.placaMovEquipResidencia!!,
            observMovEquipResidencia = this.observMovEquipResidencia,
            statusMovEquipResidencia = this.statusMovEquipResidencia,
            statusSendMovEquipResidencia = this.statusSendMovEquipResidencia,
            statusMovEquipForeigResidencia = this.statusMovEquipForeigResidencia,
        )
    }
}
