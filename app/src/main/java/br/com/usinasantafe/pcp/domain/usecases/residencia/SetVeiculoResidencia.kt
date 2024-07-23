package br.com.usinasantafe.pcp.domain.usecases.residencia

import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository
import javax.inject.Inject

interface SetVeiculoResidencia {
    suspend operator fun invoke(veiculo: String, flowApp: FlowApp, pos: Int): Boolean
}

class SetVeiculoResidenciaImpl @Inject constructor(
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository,
): SetVeiculoResidencia {

    override suspend fun invoke(veiculo: String, flowApp: FlowApp, pos: Int): Boolean {
        return try {
            when (flowApp) {
                FlowApp.ADD -> movEquipResidenciaRepository.setVeiculoMovEquipResidencia(veiculo)
                FlowApp.CHANGE -> {
                    val movEquip = movEquipResidenciaRepository.listMovEquipResidenciaOpen()[pos]
                    movEquipResidenciaRepository.updateVeiculoMovEquipResidencia(veiculo, movEquip)
                }
            }
        } catch (exception: Exception) {
            false
        }
    }

}