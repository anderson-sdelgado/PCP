package br.com.usinasantafe.pcp.domain.usecases.chave

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.ChaveRepository
import br.com.usinasantafe.pcp.domain.repositories.stable.LocalTrabRepository

interface GetDescrFullChave {
    suspend operator fun invoke(id: Int): Result<String>
}

class IGetDescrFullChave(
    private val chaveRepository: ChaveRepository,
    private val localTrabRepository: LocalTrabRepository,
): GetDescrFullChave {

    override suspend fun invoke(id: Int): Result<String> {
        try {
            val resultChave = chaveRepository.get(id)
            if (resultChave.isFailure) {
                val e = resultChave.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetDescrFullChave",
                    message = e.message,
                    cause = e
                )
            }
            val entity = resultChave.getOrNull()!!
            val resultDescrLocalTrab = localTrabRepository.getDescr(
                entity.idLocalTrab
            )
            if (resultDescrLocalTrab.isFailure) {
                val e = resultDescrLocalTrab.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetDescrFullChave",
                    message = e.message,
                    cause = e
                )
            }
            val descrLocalTrab = resultDescrLocalTrab.getOrNull()!!
            return Result.success("${entity.descrChave} - $descrLocalTrab")
        } catch (e: Exception) {
            return resultFailure(
                context = "IGetDescrFullChave",
                message = "-",
                cause = e
            )
        }
    }

}