package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.repositories.stable.ColabRepository
import javax.inject.Inject

interface RecoverNomeColab {
    suspend operator fun invoke(matricColab: String): String
}

class RecoverNomeColabImpl @Inject constructor (
    private val colabRepository: ColabRepository,
): RecoverNomeColab {

    override suspend fun invoke(matricColab: String): String {
        return colabRepository.getColabMatric(matricColab.toLong()).nomeColab
    }

}