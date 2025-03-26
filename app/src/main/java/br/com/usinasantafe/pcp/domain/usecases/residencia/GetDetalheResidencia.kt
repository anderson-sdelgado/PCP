package br.com.usinasantafe.pcp.domain.usecases.residencia

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcp.presenter.residencia.detalhe.DetalheResidenciaModel
import java.text.SimpleDateFormat
import java.util.Locale

interface GetDetalheResidencia {
    suspend operator fun invoke(
        id: Int
    ): Result<DetalheResidenciaModel>
}

class IGetDetalheResidencia(
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository
) : GetDetalheResidencia {

    override suspend fun invoke(
        id: Int
    ): Result<DetalheResidenciaModel> {
        try {
            val resultGet = movEquipResidenciaRepository.get(id)
            if (resultGet.isFailure) {
                val e = resultGet.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetDetalheResidencia",
                    message = e.message,
                    cause = e
                )
            }
            val mov = resultGet.getOrNull()!!
            val dthr = SimpleDateFormat(
                "dd/MM/yyyy HH:mm",
                Locale("pt", "BR")
            ).format(mov.dthrMovEquipResidencia)
            val tipoMov = if (mov.tipoMovEquipResidencia!!.ordinal == 0) "ENTRADA" else "SAÍDA"
            return Result.success(
                DetalheResidenciaModel(
                    id = id,
                    dthr = dthr,
                    tipoMov = tipoMov,
                    veiculo = mov.veiculoMovEquipResidencia!!,
                    placa = mov.placaMovEquipResidencia!!,
                    motorista = mov.motoristaMovEquipResidencia!!,
                    observ = mov.observMovEquipResidencia
                )
            )
        } catch (e: Exception){
            return resultFailure(
                context = "IGetDetalheResidencia",
                message = "-",
                cause = e
            )
        }

    }

}