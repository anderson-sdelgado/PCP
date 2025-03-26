package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.entities.stable.Colab
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioPassagRepository
import br.com.usinasantafe.pcp.utils.FlowApp

interface GetPassagColabList {
    suspend operator fun invoke(
        flowApp: FlowApp,
        id: Int
    ): Result<List<Colab>>
}

class IGetPassagColabList(
    private val movEquipProprioPassagRepository: MovEquipProprioPassagRepository,
    private val colabRepository: ColabRepository
) : GetPassagColabList {

    override suspend fun invoke(
        flowApp: FlowApp,
        id: Int
    ): Result<List<Colab>> {
        try {
            val resultList = movEquipProprioPassagRepository.list(flowApp, id)
            if (resultList.isFailure) {
                val e = resultList.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetPassagColabList",
                    message = e.message,
                    cause = e
                )
            }
            val passagList = resultList.getOrNull()!!
            val passagColabList = passagList.map {
                val resultNomeColab = colabRepository.getNome(it.matricColab!!)
                if (resultNomeColab.isFailure) {
                    val e = resultNomeColab.exceptionOrNull()!!
                    return resultFailure(
                        context = "IGetPassagColabList",
                        message = e.message,
                        cause = e
                    )
                }
                Colab(
                    matricColab = it.matricColab!!,
                    nomeColab = resultNomeColab.getOrNull()!!
                )
            }
            return Result.success(passagColabList)
        } catch (e: Exception) {
            return resultFailure(
                context = "IGetPassagColabList",
                message = "-",
                cause = e
            )
        }
    }

}