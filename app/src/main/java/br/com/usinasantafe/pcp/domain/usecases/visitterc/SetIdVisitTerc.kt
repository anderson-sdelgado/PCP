package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.TerceiroRepository
import br.com.usinasantafe.pcp.domain.repositories.stable.VisitanteRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeOcupante
import br.com.usinasantafe.pcp.utils.TypeVisitTerc

interface SetIdVisitTerc {
    suspend operator fun invoke(
        cpf: String,
        flowApp: FlowApp,
        typeOcupante: TypeOcupante,
        id: Int
    ): Result<Boolean>
}

class ISetIdVisitTerc(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
    private val movEquipVisitTercPassagRepository: MovEquipVisitTercPassagRepository,
    private val terceiroRepository: TerceiroRepository,
    private val visitanteRepository: VisitanteRepository,
    private val startProcessSendData: StartProcessSendData
) : SetIdVisitTerc {

    override suspend fun invoke(
        cpf: String,
        flowApp: FlowApp,
        typeOcupante: TypeOcupante,
        id: Int
    ): Result<Boolean> {
        try {
            val resultGetType = movEquipVisitTercRepository.getTypeVisitTerc(
                flowApp = flowApp,
                id = id
            )
            if (resultGetType.isFailure) {
                val e = resultGetType.exceptionOrNull()!!
                return resultFailure(
                    context = "ISetIdVisitTerc",
                    message = e.message,
                    cause = e
                )
            }
            val typeVisitTerc = resultGetType.getOrNull()!!
            val resultGetId = when (typeVisitTerc) {
                TypeVisitTerc.VISITANTE -> visitanteRepository.getId(cpf)
                TypeVisitTerc.TERCEIRO -> terceiroRepository.getId(cpf)
            }
            if (resultGetId.isFailure) {
                val e = resultGetId.exceptionOrNull()!!
                return resultFailure(
                    context = "ISetIdVisitTerc",
                    message = e.message,
                    cause = e
                )
            }
            val idVisitTerc = resultGetId.getOrNull()!!
            when (typeOcupante) {
                TypeOcupante.MOTORISTA -> {
                    val resultSet = movEquipVisitTercRepository.setIdVisitTerc(
                        idVisitTerc = idVisitTerc,
                        flowApp = flowApp,
                        id = id
                    )
                    if (resultSet.isFailure) {
                        val e = resultSet.exceptionOrNull()!!
                        return resultFailure(
                            context = "ISetIdVisitTerc",
                            message = e.message,
                            cause = e
                        )
                    }
                }
                TypeOcupante.PASSAGEIRO -> {
                    val resultAdd = movEquipVisitTercPassagRepository.add(
                        idVisitTerc = idVisitTerc,
                        flowApp = flowApp,
                        id = id
                    )
                    if (resultAdd.isFailure) {
                        val e = resultAdd.exceptionOrNull()!!
                        return resultFailure(
                            context = "ISetIdVisitTerc",
                            message = e.message,
                            cause = e
                        )
                    }
                    if(flowApp == FlowApp.CHANGE){
                        val resultSend = movEquipVisitTercRepository.setSend(id)
                        if (resultSend.isFailure) {
                            val e = resultSend.exceptionOrNull()!!
                            return resultFailure(
                                context = "ISetIdVisitTerc",
                                message = e.message,
                                cause = e
                            )
                        }
                    }
                }
            }
            if(flowApp == FlowApp.CHANGE)
                startProcessSendData()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "ISetIdVisitTerc",
                message = "-",
                cause = e
            )
        }
    }

}