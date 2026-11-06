package br.com.usinasantafe.pcp.infra.models.room.variable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipResidencia
import br.com.usinasantafe.pcp.lib.StatusData
import br.com.usinasantafe.pcp.lib.StatusForeigner
import br.com.usinasantafe.pcp.lib.StatusSend
import br.com.usinasantafe.pcp.lib.TB_MOV_EQUIP_RESIDENCIA
import br.com.usinasantafe.pcp.lib.TypeMovEquip
import br.com.usinasantafe.pcp.utils.required
import java.util.Date

@Entity(tableName = TB_MOV_EQUIP_RESIDENCIA)
data class MovEquipResidenciaRoomModel(
    @PrimaryKey(autoGenerate = true)
    var idMovEquipResidencia: Int? = null,
    val matricVigiaMovEquipResidencia: Int,
    val idLocalMovEquipResidencia: Int,
    val tipoMovEquipResidencia: TypeMovEquip,
    val dthrMovEquipResidencia: Long,
    val motoristaMovEquipResidencia: String,
    val veiculoMovEquipResidencia: String,
    val placaMovEquipResidencia: String,
    val observMovEquipResidencia: String?,
    val statusMovEquipResidencia: StatusData,
    val statusSendMovEquipResidencia: StatusSend,
    val statusMovEquipForeignerResidencia: StatusForeigner,
)

fun MovEquipResidenciaRoomModel.roomModelToEntity(): MovEquipResidencia {
    return with(this){
        MovEquipResidencia(
            idMovEquipResidencia = this.idMovEquipResidencia,
            matricVigiaMovEquipResidencia = this.matricVigiaMovEquipResidencia,
            idLocalMovEquipResidencia = this.idLocalMovEquipResidencia,
            dthrMovEquipResidencia = Date(this.dthrMovEquipResidencia),
            tipoMovEquipResidencia = this.tipoMovEquipResidencia,
            motoristaMovEquipResidencia = this.motoristaMovEquipResidencia,
            veiculoMovEquipResidencia = this.veiculoMovEquipResidencia,
            placaMovEquipResidencia = this.placaMovEquipResidencia,
            observMovEquipResidencia = this.observMovEquipResidencia,
            statusMovEquipResidencia = this.statusMovEquipResidencia,
            statusSendMovEquipResidencia = this.statusSendMovEquipResidencia,
            statusMovEquipForeignerResidencia = this.statusMovEquipForeignerResidencia,
        )
    }
}

fun MovEquipResidencia.entityToRoomModel(matricVigia: Int, idLocal: Int): MovEquipResidenciaRoomModel{
    return with(this){
        MovEquipResidenciaRoomModel(
            idMovEquipResidencia = this.idMovEquipResidencia,
            matricVigiaMovEquipResidencia = matricVigia,
            idLocalMovEquipResidencia = idLocal,
            dthrMovEquipResidencia = this.dthrMovEquipResidencia.time,
            tipoMovEquipResidencia = this::tipoMovEquipResidencia.required(),
            motoristaMovEquipResidencia = this::motoristaMovEquipResidencia.required(),
            veiculoMovEquipResidencia = this::veiculoMovEquipResidencia.required(),
            placaMovEquipResidencia = this::placaMovEquipResidencia.required(),
            observMovEquipResidencia = this.observMovEquipResidencia,
            statusMovEquipResidencia = this.statusMovEquipResidencia,
            statusSendMovEquipResidencia = this.statusSendMovEquipResidencia,
            statusMovEquipForeignerResidencia = this.statusMovEquipForeignerResidencia,
        )
    }
}
