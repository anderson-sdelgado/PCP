package br.com.usinasantafe.pcp.infra.models.retrofit.stable

import br.com.usinasantafe.pcp.domain.entities.stable.Fluxo

data class FluxoRetrofitModel(
    val idFluxo: Int,
    val descrFluxo: String,
)

fun FluxoRetrofitModel.retrofitModelToEntity(): Fluxo {
    return with(this) {
        Fluxo (
            idFluxo = this.idFluxo,
            descrFluxo = this.descrFluxo,
        )
    }
}