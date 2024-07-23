package br.com.usinasantafe.pcp.presenter.visitterc.movequip

data class MovEquipVisitTercModel(
    val dthr: String,
    val motorista: String,
    val veiculo: String,
    val placa: String,
    val tipo: String? = null,
)
