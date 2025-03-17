package br.com.usinasantafe.pcp.presenter.chave.model

data class ControleChaveModel(
    val id: Int,
    val dthr: String,
    val tipoMov: String? = null,
    val chave: String,
    val colab: String,
)
