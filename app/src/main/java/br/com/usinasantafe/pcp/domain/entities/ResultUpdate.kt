package br.com.usinasantafe.pcp.domain.entities

import br.com.usinasantafe.pcp.lib.Errors

data class ResultUpdate(
    val flagDialog: Boolean = false,
    val flagFailure: Boolean = false,
    val errors: Errors = Errors.FIELD_EMPTY,
    val failure: String = "",
    val flagProgress: Boolean = false,
    val msgProgress: String = "",
    val currentProgress: Float = 0.0f,
)