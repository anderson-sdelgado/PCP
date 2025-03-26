package br.com.usinasantafe.pcp.domain.usecases.initial

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository

interface GetNomeVigia {
    suspend operator fun invoke(): Result<String>
}

class IGetNomeVigia(
    private val configRepository: ConfigRepository,
    private val colabRepository: ColabRepository,
): GetNomeVigia {

    override suspend fun invoke(): Result<String> {
        try {
            val resultGetConfig = configRepository.getConfig()
            if (resultGetConfig.isFailure) {
                val e = resultGetConfig.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetNomeVigia",
                    message = e.message,
                    cause = e
                )
            }
            val matric = resultGetConfig.getOrNull()!!.matricVigia!!
            val resultGetNome = colabRepository.getNome(matric)
            if (resultGetNome.isFailure) {
                val e = resultGetNome.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetNomeVigia",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(resultGetNome.getOrNull()!!)
        } catch (e: Exception) {
            return resultFailure(
                context = "IGetNomeVigia",
                message = "-",
                cause = e
            )
        }
    }

}