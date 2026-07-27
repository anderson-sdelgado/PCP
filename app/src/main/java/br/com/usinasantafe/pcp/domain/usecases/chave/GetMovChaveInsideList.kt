package br.com.usinasantafe.pcp.domain.usecases.chave

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveRepository
import br.com.usinasantafe.pcp.presenter.view.chave.model.ControleChaveModel
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

interface GetMovChaveInsideList {
    suspend operator fun invoke(): Result<List<ControleChaveModel>>
}

class IGetMovChaveInsideList @Inject constructor(
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
                    cause = e.cause
                )
            }
            val entityList = resultList.getOrNull()!!.map {
                val resultNomeColab = colabRepository.getNome(it.matricColabMovChave!!)
                if (resultNomeColab.isFailure) {
                    val e = resultNomeColab.exceptionOrNull()!!
                    return resultFailure(
                        context = "IGetMovChaveInsideList",
                        message = e.message,
                        cause = e.cause
                    )
                }
                val nomeColab = resultNomeColab.getOrNull()!!
                val resultGetDescrFullChave = getDescrFullChave(it.idChaveMovChave!!)
                if (resultGetDescrFullChave.isFailure) {
                    val e = resultGetDescrFullChave.exceptionOrNull()!!
                    return resultFailure(
                        context = "IGetMovChaveInsideList",
                        message = e.message,
                        cause = e.cause
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