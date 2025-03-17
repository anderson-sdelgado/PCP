package br.com.usinasantafe.pcp.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.pcp.domain.entities.stable.Fluxo
import br.com.usinasantafe.pcp.utils.TB_FLUXO

@Entity(tableName = TB_FLUXO)
data class FluxoRoomModel(
    @PrimaryKey
    val idFluxo: Int,
    val descrFluxo: String,
)

fun FluxoRoomModel.roomModelToEntity(): Fluxo {
    return with(this) {
        Fluxo(
            idFluxo = this.idFluxo,
            descrFluxo = this.descrFluxo,
        )
    }
}

fun Fluxo.entityToRoomModel(): FluxoRoomModel {
    return with(this) {
        FluxoRoomModel(
            idFluxo = this.idFluxo,
            descrFluxo = this.descrFluxo,
        )
    }
}