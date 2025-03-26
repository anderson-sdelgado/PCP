package br.com.usinasantafe.pcp.domain.usecases.chaveequip

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pcp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveEquipRepository
import br.com.usinasantafe.pcp.presenter.chaveequip.model.ControleChaveEquipModel
import br.com.usinasantafe.pcp.utils.TypeMovKey
import java.text.SimpleDateFormat
import java.util.Locale

interface GetMovChaveEquipOpenList {
    suspend operator fun invoke(): Result<List<ControleChaveEquipModel>>
}

class IGetMovChaveEquipOpenList(
    private val movChaveEquipRepository: MovChaveEquipRepository,
    private val colabRepository: ColabRepository,
    private val equipRepository: EquipRepository
): GetMovChaveEquipOpenList {

    override suspend fun invoke(): Result<List<ControleChaveEquipModel>> {
        try{
            val resultList = movChaveEquipRepository.listOpen()
            if (resultList.isFailure) {
                val e = resultList.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetMovChaveEquipOpenList",
                    message = e.message,
                    cause = e
                )
            }
            val entityList = resultList.getOrNull()!!.map {
                val resultNomeColab = colabRepository.getNome(it.matricColabMovChaveEquip!!)
                if (resultNomeColab.isFailure) {
                    val e = resultNomeColab.exceptionOrNull()!!
                    return resultFailure(
                        context = "IGetMovChaveEquipOpenList",
                        message = e.message,
                        cause = e
                    )
                }
                val nomeColab = resultNomeColab.getOrNull()!!
                val resultGetEquip = equipRepository.getDescr(it.idEquipMovChaveEquip!!)
                if (resultGetEquip.isFailure) {
                    val e = resultGetEquip.exceptionOrNull()!!
                    return resultFailure(
                        context = "IGetMovChaveEquipOpenList",
                        message = e.message,
                        cause = e
                    )
                }
                val descrEquip = resultGetEquip.getOrNull()!!
                ControleChaveEquipModel(
                    id = it.idMovChaveEquip!!,
                    dthr = SimpleDateFormat(
                        "dd/MM/yyyy HH:mm",
                        Locale("pt", "BR")
                    ).format(it.dthrMovChaveEquip),
                    tipoMov = when (it.tipoMovChaveEquip!!) {
                        TypeMovKey.REMOVE -> "RETIRADA"
                        TypeMovKey.RECEIPT -> "RECEBIMENTO"
                    },
                    equip = descrEquip,
                    colab = "${it.matricColabMovChaveEquip} - $nomeColab"
                )
            }
            return Result.success(entityList)
        } catch (e: Exception) {
            return resultFailure(
                context = "IGetMovChaveEquipOpenList",
                message = "-",
                cause = e
            )
        }
    }

}