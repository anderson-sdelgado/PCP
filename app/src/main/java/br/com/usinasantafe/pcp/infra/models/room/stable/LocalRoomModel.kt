package br.com.usinasantafe.pcp.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.pcp.utils.TB_LOCAL
import br.com.usinasantafe.pcp.domain.entities.stable.Local

@Entity(tableName = TB_LOCAL)
data class LocalRoomModel (
    @PrimaryKey
    val idLocal: Int,
    val descrLocal: String,
)

fun LocalRoomModel.roomModelToEntity(): Local {
    return with(this){
        Local(
            idLocal = this.idLocal,
            descrLocal = this.descrLocal,
        )
    }
}

fun Local.entityToRoomModel(): LocalRoomModel{
    return with(this){
        LocalRoomModel(
            idLocal = this.idLocal,
            descrLocal = this.descrLocal,
        )
    }
}