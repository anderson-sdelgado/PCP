package br.com.usinasantafe.pcp.domain.usecases.chave

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveRepository
import br.com.usinasantafe.pcp.presenter.chave.model.ControleChaveModel
import java.text.SimpleDateFormat
import java.util.Locale

interface GetMovChaveInsideList {
    suspend operator fun invoke(): Result<List<ControleChaveModel>>
}

class IGetMovChaveInsideList(
    private val movChaveRepository: MovChaveRepository,
    private val colabRepository: ColabRepository,
    private val getDescrFullChave: GetDescrFullChave,
) : GetMovChaveInsideList {

    override suspend fun invoke(): Result<List<ControleChaveModel>> {
        try {
            val resultList = movChaveRepository.listInside()
            if (resultList.isFailure) {
                val e = resultList.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetMovChaveInsideList",
                    message = e.message,
                    cause = e
                )
            }
            val entityList = resultList.getOrNull()!!.map {
                val resultNomeColab = colabRepository.getNome(it.matricColabMovChave!!)
                if (resultNomeColab.isFailure) {
                    val e = resultNomeColab.exceptionOrNull()!!
                    return resultFailure(
                        context = "IGetMovChaveInsideList",
                        message = e.message,
                        cause = e
                    )
                }
                val nomeColab = resultNomeColab.getOrNull()!!
                val resultGetDescrFullChave = getDescrFullChave(it.idChaveMovChave!!)
                if (resultGetDescrFullChave.isFailure) {
                    val e = resultGetDescrFullChave.exceptionOrNull()!!
                    return resultFailure(
                        context = "IGetMovChaveInsideList",
                        message = e.message,
                        cause = e
                    )
                }
                val descrFullChave = resultGetDescrFullChave.getOrNull()!!
                ControleChaveModel(
                    id = it.idMovChave!!,
                    dthr = SimpleDateFormat(
                        "dd/MM/yyyy HH:mm",
                        Locale("pt", "BR")
                    ).format(it.dthrMovChave),
                    chave = descrFullChave,
                    colab = "${it.matricColabMovChave!!} - $nomeColab"
                )
            }
            return Result.success(entityList)
        } catch (e: Exception) {
            return resultFailure(
                context = "IGetMovChaveInsideList",
                message = "-",
                cause = e
            )
        }
    }

}