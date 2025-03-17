package br.com.usinasantafe.pcp.domain.usecases.initial

import br.com.usinasantafe.pcp.domain.entities.stable.Fluxo
import br.com.usinasantafe.pcp.domain.errors.UsecaseException
import br.com.usinasantafe.pcp.domain.repositories.stable.FluxoRepository
import br.com.usinasantafe.pcp.domain.repositories.stable.RLocalFluxoRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository

interface GetFlowList {
    suspend operator fun invoke(): Result<List<Fluxo>>
}

class IGetFlowList(
    private val configRepository: ConfigRepository,
    private val rLocalFluxoRepository: RLocalFluxoRepository,
    private val fluxoRepository: FluxoRepository
): GetFlowList {

    override suspend fun invoke(): Result<List<Fluxo>> {
        try {
            val resultConfig = configRepository.getConfig()
            if(resultConfig.isFailure)
                return Result.failure(resultConfig.exceptionOrNull()!!)
            val config = resultConfig.getOrNull()!!
            val resultRLocalFluxo = rLocalFluxoRepository.list(
                config.idLocal!!
            )
            if(resultRLocalFluxo.isFailure)
                return Result.failure(resultRLocalFluxo.exceptionOrNull()!!)
            val rLocalFluxoList = resultRLocalFluxo.getOrNull()!!
            val fluxoList = rLocalFluxoList.map {
                val resultFluxo = fluxoRepository.get(it.idFluxo)
                if(resultFluxo.isFailure)
                    return Result.failure(resultFluxo.exceptionOrNull()!!)
                return@map resultFluxo.getOrNull()!!
            }
            return Result.success(fluxoList)
        } catch (e: Exception){
            return Result.failure(
                UsecaseException(
                    function = "GetFlowList",
                    cause = e
                )
            )
        }
    }

}