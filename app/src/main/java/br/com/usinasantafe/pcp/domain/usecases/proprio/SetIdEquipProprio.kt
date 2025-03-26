package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioEquipSegRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeEquip

interface SetIdEquipProprio {
    suspend operator fun invoke(
        nroEquip: String,
        flowApp: FlowApp,
        typeEquip: TypeEquip,
        id: Int
    ): Result<Boolean>
}

class ISetIdEquipProprio(
    private val equipRepository: EquipRepository,
    private val movEquipProprioRepository: MovEquipProprioRepository,
    private val movEquipProprioEquipSegRepository: MovEquipProprioEquipSegRepository,
    private val startProcessSendData: StartProcessSendData
) : SetIdEquipProprio {

    override suspend fun invoke(
        nroEquip: String,
        flowApp: FlowApp,
        typeEquip: TypeEquip,
        id: Int
    ): Result<Boolean> {
        try {
            val resultId = equipRepository.getId(nroEquip.toLong())
            if (resultId.isFailure) {
                val e = resultId.exceptionOrNull()!!
                return resultFailure(
                    context = "ISetIdEquipProprio",
                    message = e.message,
                    cause = e
                )
            }
            val idEquip = resultId.getOrNull()!!
            when (typeEquip) {
                TypeEquip.VEICULO -> {
                    val resultSet = movEquipProprioRepository.setIdEquip(
                        idEquip = idEquip,
                        flowApp = flowApp,
                        id = id
                    )
                    if (resultSet.isFailure) {
                        val e = resultId.exceptionOrNull()!!
                        return resultFailure(
                            context = "ISetIdEquipProprio",
                            message = e.message,
                            cause = e
                        )
                    }
                }

                TypeEquip.VEICULOSEG -> {
                    val resultAdd = movEquipProprioEquipSegRepository.add(
                        idEquip = idEquip,
                        flowApp = flowApp,
                        id = id
                    )
                    if (resultAdd.isFailure) {
                        val e = resultAdd.exceptionOrNull()!!
                        return resultFailure(
                            context = "ISetIdEquipProprio",
                            message = e.message,
                            cause = e
                        )
                    }
                    if(flowApp == FlowApp.CHANGE) {
                        val resultSend = movEquipProprioRepository.setSend(id)
                        if (resultSend.isFailure) {
                            val e = resultSend.exceptionOrNull()!!
                            return resultFailure(
                                context = "ISetIdEquipProprio",
                                message = e.message,
                                cause = e
                            )
                        }
                    }
                }
            }
            if(flowApp == FlowApp.CHANGE){
                startProcessSendData()
            }
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "ISetIdEquipProprio",
                message = "-",
                cause = e
            )
        }
    }

}