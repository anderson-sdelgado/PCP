package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeMov
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcp.utils.StatusForeigner
import java.util.Date
import javax.inject.Inject

interface SetObservVisitTerc {
    suspend operator fun invoke(observ: String?, typeMov: TypeMov, pos: Int, flowApp: FlowApp): Boolean
}

class SetObservVisitTercImpl @Inject constructor(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
    private val saveMovEquipVisitTerc: SaveMovEquipVisitTerc,
) : SetObservVisitTerc {
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
                        if (!movEquipVisitTercRepository.setObservMovEquipVisitTerc(observ)) return false
                        return saveMovEquipVisitTerc()
                    }
                    TypeMov.OUTPUT -> {
                        val movEquipVisitTerc =
                            movEquipVisitTercRepository.listMovEquipVisitTercInside()[pos]
                        if (!movEquipVisitTercRepository.setStatusOutsideMov(movEquipVisitTerc)) return false
                        movEquipVisitTerc.observMovEquipVisitTerc = observ
                        movEquipVisitTerc.tipoMovEquipVisitTerc = TypeMov.OUTPUT
                        movEquipVisitTerc.dthrMovEquipVisitTerc = Date()
                        movEquipVisitTerc.destinoMovEquipVisitTerc = null
                        movEquipVisitTerc.statusMovEquipForeigVisitTerc = StatusForeigner.OUTSIDE
                        return saveMovEquipVisitTerc(movEquipVisitTerc)
                    }
                    else -> {
                        return false
                    }
                }
            }

            FlowApp.CHANGE -> {
                val movEquip = movEquipVisitTercRepository.listMovEquipVisitTercOpen()[pos]
                return movEquipVisitTercRepository.updateObservMovEquipVisitTerc(observ, movEquip)
            }
        }
    }
}