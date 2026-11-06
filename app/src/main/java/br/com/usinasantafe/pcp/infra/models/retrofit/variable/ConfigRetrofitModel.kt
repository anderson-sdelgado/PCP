package br.com.usinasantafe.pcp.infra.models.retrofit.variable

import br.com.usinasantafe.pcp.domain.entities.variable.Config
import br.com.usinasantafe.pcp.utils.required

data class ConfigRetrofitModelOutput(
    val number: Long,
    val version: String,
)

data class ConfigRetrofitModelInput(
    var idServ: Int,
)

fun Config.entityToRetrofitModel(): ConfigRetrofitModelOutput {
    return with(this){
        ConfigRetrofitModelOutput(
            number = this::number.required(),
            version = this::version.required(),
        )
    }
}

fun ConfigRetrofitModelInput.retrofitToEntity(): Config {
    return with(this){
        Config(
            idServ = this.idServ
        )
    }
}