package br.com.usinasantafe.pcp.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.pcp.domain.entities.stable.RLocalFluxo
import br.com.usinasantafe.pcp.utils.TB_FLUXO
import br.com.usinasantafe.pcp.utils.TB_R_LOCAL_FLUXO

@Entity(tableName = TB_R_LOCAL_FLUXO)
data class RLocalFluxoRoomModel(
    @PrimaryKey
    val idRLocalFluxo: Int,
    val idLocal: Int,
    val idFluxo: Int,
)

fun RLocalFluxoRoomModel.roomModelToEntity(): RLocalFluxo {
    return with(this) {
        RLocalFluxo(
            idRLocalFluxo = this.idRLocalFluxo,
            idFluxo = this.idFluxo,
            idLocal = this.idLocal,
        )
    }
}

fun RLocalFluxo.entityToRoomModel(): RLocalFluxoRoomModel {
    return with(this) {
        RLocalFluxoRoomModel(
            idRLocalFluxo = this.idRLocalFluxo,
            idFluxo = this.idFluxo,
            idLocal = this.idLocal,
        )
    }
}