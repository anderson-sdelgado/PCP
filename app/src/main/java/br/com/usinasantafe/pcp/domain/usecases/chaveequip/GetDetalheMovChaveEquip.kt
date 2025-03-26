package br.com.usinasantafe.pcp.domain.usecases.chaveequip

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pcp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveEquipRepository
import br.com.usinasantafe.pcp.presenter.chaveequip.detalhe.DetalheChaveEquipModel
import br.com.usinasantafe.pcp.utils.TypeMovKey
import java.text.SimpleDateFormat
import java.util.Locale

interface GetDetalheMovChaveEquip {
    suspend operator fun invoke(id: Int): Result<DetalheChaveEquipModel>
}

class IGetDetalheMovChaveEquip(
    private val movChaveEquipRepository: MovChaveEquipRepository,
    private val colabRepository: ColabRepository,
    private val equipRepository: EquipRepository
): GetDetalheMovChaveEquip {

    override suspend fun invoke(id: Int): Result<DetalheChaveEquipModel> {
        try {
            val resultMovChave = movChaveEquipRepository.get(id)
            if (resultMovChave.isFailure) {
                val e = resultMovChave.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetDetalheMovChaveEquip",
                    message = e.message,
                    cause = e
                )
            }
            val entity = resultMovChave.getOrNull()!!
            val resultNomeColab = colabRepository.getNome(entity.matricColabMovChaveEquip!!)
            if (resultNomeColab.isFailure) {
                val e = resultNomeColab.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetDetalheMovChaveEquip",
                    message = e.message,
                    cause = e
                )
            }
            val nomeColab = resultNomeColab.getOrNull()!!
            val resultGetEquip = equipRepository.getDescr(entity.idEquipMovChaveEquip!!)
            if (resultGetEquip.isFailure) {
                val e = resultGetEquip.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetDetalheMovChaveEquip",
                    message = e.message,
                    cause = e
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