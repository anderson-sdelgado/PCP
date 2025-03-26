package br.com.usinasantafe.pcp.domain.usecases.residencia

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcp.presenter.residencia.model.MovEquipResidenciaModel
import java.text.SimpleDateFormat
import java.util.Locale

interface GetMovEquipResidenciaInsideList {
    suspend operator fun invoke(): Result<List<MovEquipResidenciaModel>>
}

class IGetMovEquipResidenciaInsideList(
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository
): GetMovEquipResidenciaInsideList {

    override suspend fun invoke(): Result<List<MovEquipResidenciaModel>> {
        try {
            val resultList = movEquipResidenciaRepository.listInside()
            if (resultList.isFailure) {
                val e = resultList.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetMovEquipResidenciaInsideList",
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
                    motorista = it.motoristaMovEquipResidencia!!
                )
            }
            return Result.success(modelList)
        } catch (e: Exception) {
            return resultFailure(
                context = "IGetMovEquipResidenciaInsideList",
                message = "-",
                cause = e
            )
        }
    }

}