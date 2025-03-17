package br.com.usinasantafe.pcp.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.pcp.domain.entities.stable.Colab
import br.com.usinasantafe.pcp.utils.TB_COLAB

@Entity(tableName = TB_COLAB)
data class ColabRoomModel(
    @PrimaryKey
    val matricColab: Int,
    val nomeColab: String,
)

fun Colab.entityToRoomModel(): ColabRoomModel {
    return with(this) {
        ColabRoomModel(
            matricColab = this.matricColab,
            nomeColab = this.nomeColab,
        )
    }
}