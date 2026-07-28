package br.com.usinasantafe.pcp.domain.usecases.chaveEquip

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pcp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveEquipRepository
import br.com.usinasantafe.pcp.presenter.view.chaveEquip.detalhe.DetalheChaveEquipModel
import br.com.usinasantafe.pcp.lib.TypeMovKey
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

interface GetDetalheMovChaveEquip {
    suspend operator fun invoke(id: Int): Result<DetalheChaveEquipModel>
}

class IGetDetalheMovChaveEquip @Inject constructor(
    private val movChaveEquipRepository: MovChaveEquipRepository,
    private val colabRepository: ColabRepository,
    private val equipRepository: EquipRepository
): GetDetalheMovChaveEquip {

    override suspend fun invoke(id: Int): Result<DetalheChaveEquipModel> {
        try {
            val resultMovChave = movChaveEquipRepository.getById(id)
            if (resultMovChave.isFailure) {
                val e = resultMovChave.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetDetalheMovChaveEquip",
                    message = e.message,
                    cause = e.cause
                )
            }
            val entity = resultMovChave.getOrNull()!!
            val resultNomeColab = colabRepository.getNomeByMatric(entity.matricColabMovChaveEquip!!)
            if (resultNomeColab.isFailure) {
                val e = resultNomeColab.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetDetalheMovChaveEquip",
                    message = e.message,
                    cause = e.cause
                )
            }
            val nomeColab = resultNomeColab.getOrNull()!!
            val resultGetEquip = equipRepository.getDescrById(entity.idEquipMovChaveEquip!!)
            if (resultGetEquip.isFailure) {
                val e = resultGetEquip.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetDetalheMovChaveEquip",
                    message = e.message,
                    cause = e.cause
                )
            }
            val descrEquip = resultGetEquip.getOrNull()!!
            return Result.success(
                DetalheChaveEquipModel(
                    dthr = SimpleDateFormat(
                        "dd/MM/yyyy HH:mm",
                        Locale("pt", "BR")
                    ).format(entity.dthrMovChaveEquip),
                    tipoMov = when (entity.tipoMovChaveEquip!!) {
                        TypeMovKey.REMOVE -> "RETIRADA"
                        TypeMovKey.RECEIPT -> "DEVOLUÇÃO"
                    },
                    equip = descrEquip,
                    colab = "${entity.matricColabMovChaveEquip} - $nomeColab",
                    observ = entity.observMovChaveEquip
                )
            )
        } catch (e: Exception) {
            return resultFailure(
                context = "IGetDetalheMovChaveEquip",
                message = "-",
                cause = e
            )
        }
    }

}