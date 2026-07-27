package br.com.usinasantafe.pcp.presenter.view.veiculoResidencia.detalhe

data class DetalheResidenciaModel(
    val id: Int,
    val dthr: String,
    val tipoMov: String,
    val veiculo: String,
    val placa: String,
    val motorista: String,
    val observ: String?,
)
