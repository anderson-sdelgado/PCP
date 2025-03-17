package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.errors.UsecaseException
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioPassagRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeOcupante

interface SetMatricColab {
    suspend operator fun invoke(
        matricColab: String,
        flowApp: FlowApp,
        typeOcupante: TypeOcupante,
        id: Int
    ): Result<Boolean>
}

class ISetMatricColab(
    private val movEquipProprioRepository: MovEquipProprioRepository,
    private val movEquipProprioPassagRepository: MovEquipProprioPassagRepository,
    private val startProcessSendData: StartProcessSendData
): SetMatricColab {

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
                    if (resultSet.isFailure)
                        return Result.failure(resultSet.exceptionOrNull()!!)
                }

                TypeOcupante.PASSAGEIRO -> {
                    val resultAdd = movEquipProprioPassagRepository.add(
                        matricColab = matricColab.toInt(),
                        flowApp = flowApp,
                        id = id
                    )
                    if (resultAdd.isFailure)
                        return Result.failure(resultAdd.exceptionOrNull()!!)
                    if(flowApp == FlowApp.CHANGE){
                        val resultSend = movEquipProprioRepository.setSend(id)
                        if (resultSend.isFailure)
                            return Result.failure(resultSend.exceptionOrNull()!!)
                    }
                }
            }
            if(flowApp == FlowApp.CHANGE){
                startProcessSendData()
            }
            return Result.success(true)
        } catch (e: Exception){
            return Result.failure(
                UsecaseException(
                    function = "SetMatricMotorista",
                    cause = e
                )
            )
        }
    }

}