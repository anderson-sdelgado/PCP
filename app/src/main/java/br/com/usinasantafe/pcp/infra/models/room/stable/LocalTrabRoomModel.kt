package br.com.usinasantafe.pcp.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.pcp.domain.entities.stable.LocalTrab
import br.com.usinasantafe.pcp.domain.entities.stable.RLocalFluxo
import br.com.usinasantafe.pcp.utils.TB_LOCAL_TRAB

@Entity(tableName = TB_LOCAL_TRAB)
data class LocalTrabRoomModel(
    @PrimaryKey
    val idLocalTrab: Int,
    val descrLocalTrab: String,
)

fun LocalTrabRoomModel.roomModelToEntity(): LocalTrab {
    return with(this) {
        LocalTrab(
            idLocalTrab = this.idLocalTrab,
            descrLocalTrab = this.descrLocalTrab,
        )
    }
}

fun LocalTrab.entityToRoomModel(): LocalTrabRoomModel {
    return with(this) {
        LocalTrabRoomModel(
            idLocalTrab = this.idLocalTrab,
            descrLocalTrab = this.descrLocalTrab,
        )
    }
}