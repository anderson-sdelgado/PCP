package br.com.usinasantafe.pcp.infra.models.retrofit.stable

import br.com.usinasantafe.pcp.domain.entities.stable.Chave

data class ChaveRetrofitModel(
    val idChave: Int,
    val descrChave: String,
    val idLocalTrab: Int
)

fun ChaveRetrofitModel.retrofitModelToEntity(): Chave {
    return with(this) {
        Chave(
            idChave = this.idChave,
            descrChave = this.descrChave,
            idLocalTrab = this.idLocalTrab
        )
    }
}
