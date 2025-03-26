package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.StatusForeigner
import br.com.usinasantafe.pcp.utils.TypeMovEquip
import java.util.Date

interface StartOutputMovEquipVisitTerc {
    suspend operator fun invoke(id: Int): Result<Boolean>
}

class IStartOutputMovEquipVisitTerc(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
    private val movEquipVisitTercPassagRepository: MovEquipVisitTercPassagRepository,
) : StartOutputMovEquipVisitTerc {

    override suspend fun invoke(id: Int): Result<Boolean> {
        try {
            val resultMov = movEquipVisitTercRepository.get(id)
            if (resultMov.isFailure) {
                val e = resultMov.exceptionOrNull()!!
                return resultFailure(
                    context = "IStartOutputMovEquipVisitTerc",
                    message = e.message,
                    cause = e
                )
            }
            val movEquipVisitTerc = resultMov.getOrNull()!!
            movEquipVisitTerc.observMovEquipVisitTerc = null
            movEquipVisitTerc.tipoMovEquipVisitTerc = TypeMovEquip.OUTPUT
            movEquipVisitTerc.dthrMovEquipVisitTerc = Date()
            movEquipVisitTerc.destinoMovEquipVisitTerc = null
            movEquipVisitTerc.statusMovEquipForeignerVisitTerc = StatusForeigner.OUTSIDE
            val resultStart = movEquipVisitTercRepository.start(movEquipVisitTerc)
            if (resultStart.isFailure) {
                val e = resultStart.exceptionOrNull()!!
                return resultFailure(
                    context = "IStartOutputMovEquipVisitTerc",
                    message = e.message,
                    cause = e
                )
            }
            val resultPassagList = movEquipVisitTercPassagRepository.list(
                flowApp = FlowApp.CHANGE,
                id = id
            )
            if (resultPassagList.isFailure) {
                val e = resultPassagList.exceptionOrNull()!!
                return resultFailure(
                    context = "IStartOutputMovEquipVisitTerc",
                    message = e.message,
                    cause = e
                )
            }
            val passagList = resultPassagList.getOrNull()
            if (passagList != null) {
                for (passag in passagList) {
                    val resultAdd = movEquipVisitTercPassagRepository.add(
                        idVisitTerc = passag.idVisitTerc!!,
                        flowApp = FlowApp.ADD,
                        id = 0
                    )
                    if (resultAdd.isFailure) {
                        val e = resultAdd.exceptionOrNull()!!
                        return resultFailure(
                            context = "IStartOutputMovEquipVisitTerc",
                            message = e.message,
                            cause = e
                        )
                    }
                }
            }
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IStartOutputMovEquipVisitTerc",
                message = "-",
                cause = e
            )
        }
    }

}