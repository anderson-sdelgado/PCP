package br.com.usinasantafe.pcp.domain.usecases.updatetable.savetable

import br.com.usinasantafe.pcp.domain.entities.stable.Terceiro
import br.com.usinasantafe.pcp.domain.repositories.stable.TerceiroRepository

interface SaveTerceiro {
    suspend operator fun invoke(list: List<Terceiro>): Result<Boolean>
}

class ISaveTerceiro(
    private val terceiroRepository: TerceiroRepository,
): SaveTerceiro {

    override suspend fun invoke(list: List<Terceiro>): Result<Boolean> {
        return terceiroRepository.addAll(list)
    }

}