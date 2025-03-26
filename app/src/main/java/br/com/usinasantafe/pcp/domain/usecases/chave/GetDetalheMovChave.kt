package br.com.usinasantafe.pcp.domain.usecases.chave

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveRepository
import br.com.usinasantafe.pcp.presenter.chave.detalhe.DetalheChaveModel
import br.com.usinasantafe.pcp.utils.TypeMovKey
import java.text.SimpleDateFormat
import java.util.Locale

interface GetDetalheMovChave {
    suspend operator fun invoke(id: Int): Result<DetalheChaveModel>
}

class IGetDetalheMovChave(
    private val movChaveRepository: MovChaveRepository,
    private val colabRepository: ColabRepository,
    private val getDescrFullChave: GetDescrFullChave
): GetDetalheMovChave {

    override suspend fun invoke(id: Int): Result<DetalheChaveModel> {
        try {
            val resultMovChave = movChaveRepository.get(id)
            if (resultMovChave.isFailure) {
                val e = resultMovChave.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetDetalheMovChave",
                    message = e.message,
                    cause = e
                )
            }
            val entity = resultMovChave.getOrNull()!!
            val resultNomeColab = colabRepository.getNome(entity.matricColabMovChave!!)
            if (resultNomeColab.isFailure) {
                val e = resultNomeColab.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetDetalheMovChave",
                    message = e.message,
                    cause = e
                )
            }
            val nomeColab = resultNomeColab.getOrNull()!!
            val resultGetDescrFullChave = getDescrFullChave(entity.idChaveMovChave!!)
            if (resultGetDescrFullChave.isFailure) {
                val e = resultGetDescrFullChave.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetDetalheMovChave",
                    message = e.message,
                    cause = e
                )
            }
            val descrFullChave = resultGetDescrFullChave.getOrNull()!!
            return Result.success(
                DetalheChaveModel(
                    dthr = SimpleDateFormat(
                        "dd/MM/yyyy HH:mm",
                        Locale("pt", "BR")
                    ).format(entity.dthrMovChave),
                    tipoMov = when (entity.tipoMovChave!!) {
                        TypeMovKey.REMOVE -> "RETIRADA"
                        TypeMovKey.RECEIPT -> "DEVOLUÇÃO"
                    },
                    chave = descrFullChave,
                    colab = "${entity.matricColabMovChave} - $nomeColab",
                    observ = entity.observMovChave
                )
            )
        } catch (e: Exception) {
            return resultFailure(
                context = "IGetDetalheMovChave",
                message = "-",
                cause = e
            )
        }
    }

}