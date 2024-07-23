package br.com.usinasantafe.pcp.domain.usecases.residencia

import android.util.Log
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeMov
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcp.utils.StatusForeigner
import java.util.Date
import javax.inject.Inject

interface SetObservResidencia {
    suspend operator fun invoke(observ: String?, typeMov: TypeMov, pos: Int, flowApp: FlowApp): Boolean
}

class SetObservResidenciaImpl @Inject constructor(
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository,
    private val saveMovEquipResidencia: SaveMovEquipResidencia,
) : SetObservResidencia {

    override suspend fun invoke(
        observ: String?,
        typeMov: TypeMov,
        pos: Int,
        flowApp: FlowApp
    ): Boolean {
        when (flowApp) {
            FlowApp.ADD -> {
                when (typeMov) {
                    TypeMov.INPUT -> {
                        if (!movEquipResidenciaRepository.setObservMovEquipResidencia(observ)) return false
                        return saveMovEquipResidencia()
                    }

                    TypeMov.OUTPUT -> {
                        val movEquipResidencia =
                            movEquipResidenciaRepository.listMovEquipResidenciaInside()[pos]
                        if (!movEquipResidenciaRepository.setStatusOutsideMov(movEquipResidencia)) return false
                        movEquipResidencia.observMovEquipResidencia = observ
                        movEquipResidencia.tipoMovEquipResidencia = TypeMov.OUTPUT
                        movEquipResidencia.dthrMovEquipResidencia = Date()
                        movEquipResidencia.statusMovEquipForeigResidencia = StatusForeigner.OUTSIDE
                        return saveMovEquipResidencia(movEquipResidencia)
                    }
                    else -> {
                        return false
                    }
                }
            }
            FlowApp.CHANGE -> {
                val movEquip = movEquipResidenciaRepository.listMovEquipResidenciaOpen()[pos]
                return movEquipResidenciaRepository.updateObservMovEquipResidencia(observ, movEquip)
            }
        }
    }

}