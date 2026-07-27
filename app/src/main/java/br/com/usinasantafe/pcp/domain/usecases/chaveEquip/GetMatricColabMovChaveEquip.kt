package br.com.usinasantafe.pcp.domain.usecases.chaveEquip

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveEquipRepository
import javax.inject.Inject

interface GetMatricColabMovChaveEquip {
    suspend operator fun invoke(id: Int): Result<String>
}

class IGetMatricColabMovChaveEquip @Inject constructor(
    private val movChaveEquipRepository: MovChaveEquipRepository
): GetMatricColabMovChaveEquip {

    override suspend fun invoke(id: Int): Result<String> {
        try {
            val resultGetMatricColab = movChaveEquipRepository.getMatricColab(id)
            if (resultGetMatricColab.isFailure) {
                val e = resultGetMatricColab.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetMatricColabMovChaveEquip",
                    message = e.message,
                    cause = e.cause
                )
            }
            val matricColab = resultGetMatricColab.getOrNull()!!
            return Result.success(matricColab.toString())
        } catch (e: Exception) {
            return resultFailure(
                context = "IGetMatricColabMovChaveEquip",
                message = "-",
                cause = e
            )
        }
    }

}