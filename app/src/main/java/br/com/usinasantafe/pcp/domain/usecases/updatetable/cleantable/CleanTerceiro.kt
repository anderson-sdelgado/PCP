package br.com.usinasantafe.pcp.domain.usecases.updatetable.cleantable

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.TerceiroRepository

interface CleanTerceiro {
    suspend operator fun invoke(): Result<Boolean>
}

class ICleanTerceiro(
    private val terceiroRepository: TerceiroRepository
): CleanTerceiro {

    override suspend fun invoke(): Result<Boolean> {
        val result = terceiroRepository.deleteAll()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ICleanTerceiro",
                message = e.message,
                cause = e
            )
        }
        return result
    }

}