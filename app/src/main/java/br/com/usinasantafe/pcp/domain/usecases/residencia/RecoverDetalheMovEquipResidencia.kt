package br.com.usinasantafe.pcp.domain.usecases.residencia

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcp.presenter.residencia.detalhemov.DetalheMovEquipResidenciaModel
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

interface RecoverDetalheMovEquipResidencia {
    suspend operator fun invoke(pos: Int): DetalheMovEquipResidenciaModel
}

class RecoverDetalheMovEquipResidenciaImpl @Inject constructor(
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository,
): RecoverDetalheMovEquipResidencia {

    override suspend fun invoke(pos: Int): DetalheMovEquipResidenciaModel {
        val mov = movEquipResidenciaRepository.listMovEquipResidenciaOpen()[pos]
        val dthr = "DATA/HORA: ${SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt", "BR")).format(mov.dthrMovEquipResidencia)}"
        val tipoMov = if (mov.tipoMovEquipResidencia!!.ordinal == 0) "ENTRADA" else "SAÍDA"
        val veiculo = "VEÍCULO: ${mov.veiculoMovEquipResidencia}"
        val placa = "PLACA: ${mov.placaMovEquipResidencia}"
        val motorista = "MOTORISTA: ${mov.motoristaMovEquipResidencia}"
        val observ = "OBSERV.: ${mov.observMovEquipResidencia}"
        return DetalheMovEquipResidenciaModel(dthr, tipoMov, veiculo, placa, motorista, observ)
    }

}