package br.com.usinasantafe.pcp.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.pcp.domain.entities.stable.Chave
import br.com.usinasantafe.pcp.utils.TB_CHAVE

@Entity(tableName = TB_CHAVE)
data class ChaveRoomModel(
    @PrimaryKey
    val idChave: Int,
    val descrChave: String,
    val idLocalTrab: Int,
)

fun ChaveRoomModel.roomModelToEntity(): Chave {
    return with(this) {
        Chave(
            idChave = this.idChave,
            descrChave = this.descrChave,
            idLocalTrab = this.idLocalTrab,
        )
    }
}

fun Chave.entityToRoomModel(): ChaveRoomModel {
    return with(this) {
        ChaveRoomModel(
            idChave = this.idChave,
            descrChave = this.descrChave,
            idLocalTrab = this.idLocalTrab,
        )
    }
}