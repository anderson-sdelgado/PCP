package br.com.usinasantafe.pcp.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.pcp.utils.TB_VISITANTE
import br.com.usinasantafe.pcp.domain.entities.stable.Visitante

@Entity(tableName = TB_VISITANTE)
data class VisitanteRoomModel(
    @PrimaryKey
    val idVisitante: Int,
    val cpfVisitante: String,
    val nomeVisitante: String,
    val empresaVisitante: String,
)

fun VisitanteRoomModel.roomModelToEntity(): Visitante {
    return with(this){
        Visitante(
            idVisitante = this.idVisitante,
            cpfVisitante = this.cpfVisitante,
            nomeVisitante = this.nomeVisitante,
            empresaVisitante = this.empresaVisitante,
        )
    }
}

fun Visitante.entityToRoomModel(): VisitanteRoomModel{
    return with(this){
        VisitanteRoomModel(
            idVisitante = this.idVisitante,
            cpfVisitante = this.cpfVisitante,
            nomeVisitante = this.nomeVisitante,
            empresaVisitante = this.empresaVisitante,
        )
    }
}