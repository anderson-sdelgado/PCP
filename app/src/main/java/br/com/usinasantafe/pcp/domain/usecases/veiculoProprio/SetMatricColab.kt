package br.com.usinasantafe.pcp.domain.usecases.veiculoProprio

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioPassagRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.lib.TypeOcupante
import javax.inject.Inject

interface SetMatricColab {
    suspend operator fun invoke(
        matricColab: String,
        flowApp: FlowApp,
        typeOcupante: TypeOcupante,
        id: Int
    ): Result<Boolean>
}

class ISetMatricColab @Inject constructor(
    private val movEquipProprioRepository: MovEquipProprioRepository,
    private val movEquipProprioPassagRepository: MovEquipProprioPassagRepository,
    private val startProcessSendData: StartProcessSendData
) : SetMatricColab {

    override suspend fun invoke(
        matricColab: String,
        flowApp: FlowApp,
        typeOcupante: TypeOcupante,
        id: Int
    ): Result<Boolean> {
        try {
            when (typeOcupante) {
                TypeOcupante.MOTORISTA -> {
                    val resultSet = movEquipProprioRepository.setMatricColab(
                        matricColab = matricColab.toInt(),
                        flowApp = flowApp,
                        id = id
                    )
                    if (resultSet.isFailure) {
                        val e = resultSet.exceptionOrNull()!!
                        return resultFailure(
                            context = "ISetMatricColab",
                            message = e.message,
                            cause = e.cause
                        )
                    }
                }

                TypeOcupante.PASSAGEIRO -> {
                    val resultAdd = movEquipProprioPassagRepository.add(
                        matricColab = matricColab.toInt(),
                        flowApp = flowApp,
                        id = id
                    )
                    if (resultAdd.isFailure) {
                        val e = resultAdd.exceptionOrNull()!!
                        return resultFailure(
                            context = "ISetMatricColab",
                            message = e.message,
                            cause = e.cause
                        )
                    }
                    if (flowApp == FlowApp.CHANGE) {
                        val resultSend = movEquipProprioRepository.setSend(id)
                        if (resultSend.isFailure) {
                            val e = resultSend.exceptionOrNull()!!
                            return resultFailure(
                                context = "ISetMatricColab",
                                message = e.message,
                                cause = e.cause
                            )
                        }
                    }
                }
            }
            if (flowApp == FlowApp.CHANGE) {
                startProcessSendData()
            }
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "ISetMatricColab",
                message = "-",
                cause = e
            )
        }
    }

}