package br.com.usinasantafe.pcp.domain.usecases.veiculoProprio

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pcp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcp.presenter.view.veiculoProprio.movlist.MovEquipProprioModel
import br.com.usinasantafe.pcp.lib.TypeMovEquip
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

interface GetMovEquipProprioOpenList {
    suspend operator fun invoke(): Result<List<MovEquipProprioModel>>
}

class IGetMovEquipProprioOpenList @Inject constructor(
    private val movEquipProprioRepository: MovEquipProprioRepository,
    private val equipRepository: EquipRepository,
    private val colabRepository: ColabRepository
): GetMovEquipProprioOpenList {

    override suspend fun invoke(): Result<List<MovEquipProprioModel>> {
        try {
            val resultList = movEquipProprioRepository.listOpen()
            if (resultList.isFailure) {
                val e = resultList.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetMovEquipProprioOpenList",
                    message = e.message,
                    cause = e.cause
                )
            }
            val list = resultList.getOrNull()!!
            val modelList = list.map {
                val resultNro = equipRepository.getDescr(it.idEquipMovEquipProprio!!)
                if (resultNro.isFailure) {
                    val e = resultNro.exceptionOrNull()!!
                    return resultFailure(
                        context = "IGetMovEquipProprioOpenList",
                        message = e.message,
                                cause = e.cause
                            )
                }
                val descrEquip = resultNro.getOrNull()!!
                val resultGetNome = colabRepository.getNome(it.matricColabMovEquipProprio!!)
                if (resultGetNome.isFailure) {
                    val e = resultGetNome.exceptionOrNull()!!
                    return resultFailure(
                        context = "IGetMovEquipProprioOpenList",
                        message = e.message,
                                cause = e.cause
                            )
                }
                val nomeColab = resultGetNome.getOrNull()!!
                MovEquipProprioModel(
                    id = it.idMovEquipProprio!!,
                    dthr = SimpleDateFormat(
                        "dd/MM/yyyy HH:mm",
                        Locale("pt", "BR")
                    ).format(it.dthrMovEquipProprio),
                    typeMov = if (it.tipoMovEquipProprio == TypeMovEquip.INPUT) "ENTRADA" else "SAIDA",
                    equip = descrEquip,
                    colab = "${it.matricColabMovEquipProprio!!} - $nomeColab"
                )
            }
            return Result.success(modelList)
        } catch (e: Exception){
            return resultFailure(
                context = "IGetMovEquipProprioOpenList",
                message = "-",
                cause = e
            )
        }
    }

}