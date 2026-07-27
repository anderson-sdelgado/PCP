package br.com.usinasantafe.pcp.presenter.view.chaveEquip.model

data class ControleChaveEquipModel(
    val id: Int,
    val dthr: String,
    val tipoMov: String? = null,
    val equip: String,
    val colab: String,
)