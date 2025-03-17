package br.com.usinasantafe.pcp.infra.models.retrofit.variable

import br.com.usinasantafe.pcp.domain.entities.variable.Config

data class ConfigRetrofitModelOutput(
    val number: Long,
    val version: String,
)

data class ConfigRetrofitModelInput(
    var idBD: Int,
)

fun Config.entityToRetrofitModel(): ConfigRetrofitModelOutput {
    return with(this){
        ConfigRetrofitModelOutput(
            number = this.number!!,
            version = this.version!!,
        )
    }
}

fun ConfigRetrofitModelInput.retrofitToEntity(): Config {
    return with(this){
        Config(
            idBD = this.idBD
        )
    }
}