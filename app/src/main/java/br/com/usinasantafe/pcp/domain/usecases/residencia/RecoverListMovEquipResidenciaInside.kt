package br.com.usinasantafe.pcp.domain.usecases.residencia

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcp.presenter.residencia.movequip.MovEquipResidenciaModel
import br.com.usinasantafe.pcp.presenter.residencia.movequip.toMovEquipResidenciaModel
import javax.inject.Inject

interface RecoverListMovEquipResidenciaInside {
    suspend operator fun invoke(): List<MovEquipResidenciaModel>
}

class RecoverListMovEquipResidenciaInsideImpl @Inject constructor(
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository,
): RecoverListMovEquipResidenciaInside {

    override suspend fun invoke(): List<MovEquipResidenciaModel> {
        return movEquipResidenciaRepository.listMovEquipResidenciaInside().map { it.toMovEquipResidenciaModel() }
    }

}