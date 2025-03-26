package br.com.usinasantafe.pcp.domain.usecases.residencia

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcp.presenter.residencia.model.MovEquipResidenciaModel
import br.com.usinasantafe.pcp.utils.TypeMovEquip
import java.text.SimpleDateFormat
import java.util.Locale

interface GetMovEquipResidenciaOpenList {
    suspend operator fun invoke(): Result<List<MovEquipResidenciaModel>>
}

class IGetMovEquipResidenciaOpenList(
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository
): GetMovEquipResidenciaOpenList {

    override suspend fun invoke(): Result<List<MovEquipResidenciaModel>> {
        try {
            val resultList = movEquipResidenciaRepository.listOpen()
            if (resultList.isFailure) {
                val e = resultList.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetMovEquipResidenciaOpenList",
                    message = e.message,
                    cause = e
                )
            }
            val list = resultList.getOrNull()!!
            val modelList = list.map {
                MovEquipResidenciaModel(
                    id = it.idMovEquipResidencia!!,
                    dthr = SimpleDateFormat(
                        "dd/MM/yyyy HH:mm",
                        Locale("pt", "BR")
                    ).format(it.dthrMovEquipResidencia),
                    veiculo = it.veiculoMovEquipResidencia!!,
                    placa = it.placaMovEquipResidencia!!,
                    motorista = it.motoristaMovEquipResidencia!!,
                    tipoMov = if (it.tipoMovEquipResidencia == TypeMovEquip.INPUT) "ENTRADA" else "SAIDA",
                )
            }
            return Result.success(modelList)
        } catch (e: Exception) {
            return resultFailure(
                context = "IGetMovEquipResidenciaOpenList",
                message = "-",
                cause = e
            )
        }
    }

}