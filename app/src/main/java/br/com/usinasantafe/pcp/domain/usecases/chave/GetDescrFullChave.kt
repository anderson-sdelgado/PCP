package br.com.usinasantafe.pcp.domain.usecases.chave

import br.com.usinasantafe.pcp.domain.repositories.stable.ChaveRepository
import br.com.usinasantafe.pcp.domain.repositories.stable.LocalTrabRepository
import br.com.usinasantafe.pcp.utils.call
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import javax.inject.Inject

interface GetDescrFullChave {
    suspend operator fun invoke(id: Int): Result<String>
}

class IGetDescrFullChave @Inject constructor(
    private val chaveRepository: ChaveRepository,
    private val localTrabRepository: LocalTrabRepository,
): GetDescrFullChave {

    override suspend fun invoke(id: Int): Result<String> =
        call(getClassAndMethod()) {
            val entity = chaveRepository.get(id).getOrThrow()
            val desc = localTrabRepository.getDescrById(entity.idLocalTrab).getOrThrow()
            "${entity.descrChave} - $desc"
        }

}