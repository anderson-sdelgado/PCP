package br.com.usinasantafe.pcp.presenter.initial.config

import br.com.usinasantafe.pcp.domain.entities.variable.Config

data class ConfigModel(
    val nroAparelho: Long,
    val senha: String,
)

fun Config.toConfigModel(): ConfigModel {
    return with(this){
        ConfigModel(
            nroAparelho = this.nroAparelhoConfig!!,
            senha = this.passwordConfig!!,
        )
    }
}
