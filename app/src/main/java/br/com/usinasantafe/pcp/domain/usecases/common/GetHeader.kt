package br.com.usinasantafe.pcp.domain.usecases.common

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pcp.domain.repositories.stable.LocalRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.presenter.model.HeaderModel

interface GetHeader {
    suspend operator fun invoke(): Result<HeaderModel>
}

class IGetHeader(
    private val configRepository: ConfigRepository,
    private val colabRepository: ColabRepository,
    private val localRepository: LocalRepository,
): GetHeader {

    override suspend fun invoke(): Result<HeaderModel> {
        try {
            val resultConfig = configRepository.getConfig()
            if (resultConfig.isFailure) {
                val e = resultConfig.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetHeader",
                    message = e.message,
                    cause = e
                )
            }
            val config = resultConfig.getOrNull()!!
            val resultNomeColab = colabRepository.getNome(config.matricVigia!!)
            if (resultNomeColab.isFailure) {
                val e = resultNomeColab.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetHeader",
                    message = e.message,
                    cause = e
                )
            }
            val nomeColab = resultNomeColab.getOrNull()!!
            val resultLocal = localRepository.getDescr(config.idLocal!!)
            if (resultLocal.isFailure) {
                val e = resultLocal.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetHeader",
                    message = e.message,
                    cause = e
                )
            }
            val descrLocal = resultLocal.getOrNull()!!
            return Result.success(
                HeaderModel(
                    descrVigia = "${config.matricVigia} - $nomeColab",
                    descrLocal = descrLocal,
                )
            )
        } catch (e: Exception){
            return resultFailure(
                context = "IGetHeader",
                message = "-",
                cause = e
            )
        }
    }

}