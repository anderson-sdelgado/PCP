package br.com.usinasantafe.pcp.infra.models.retrofit.stable

import br.com.usinasantafe.pcp.domain.entities.stable.LocalTrab

data class LocalTrabRetrofitModel(
    val idLocalTrab: Int,
    val descrLocalTrab: String,
)

fun LocalTrabRetrofitModel.retrofitModelToEntity(): LocalTrab {
    return with(this) {
        LocalTrab(
            idLocalTrab = this.idLocalTrab,
            descrLocalTrab = this.descrLocalTrab
        )
    }
}
