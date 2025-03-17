package br.com.usinasantafe.pcp.domain.usecases.updatetable.cleantable

import br.com.usinasantafe.pcp.domain.repositories.stable.TerceiroRepository

interface CleanTerceiro {
    suspend operator fun invoke(): Result<Boolean>
}

class ICleanTerceiro(
    private val terceiroRepository: TerceiroRepository
): CleanTerceiro {

    override suspend fun invoke(): Result<Boolean> {
        return terceiroRepository.deleteAll()
    }

}