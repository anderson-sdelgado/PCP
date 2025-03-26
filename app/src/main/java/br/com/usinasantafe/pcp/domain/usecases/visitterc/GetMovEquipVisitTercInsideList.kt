package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcp.presenter.visitterc.model.MovEquipVisitTercModel
import java.text.SimpleDateFormat
import java.util.Locale

interface GetMovEquipVisitTercInsideList {
    suspend operator fun invoke(): Result<List<MovEquipVisitTercModel>>
}

class IGetMovEquipVisitTercInsideList(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
    private val getMotoristaVisitTerc: GetMotoristaVisitTerc
) : GetMovEquipVisitTercInsideList {

    override suspend fun invoke(): Result<List<MovEquipVisitTercModel>> {
        try {
            val resultList = movEquipVisitTercRepository.listInside()
            if (resultList.isFailure) {
                val e = resultList.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetMovEquipVisitTercInsideList",
                    message = e.message,
                    cause = e
                )
            }
            val list = resultList.getOrNull()!!
            val entityList = list.map {
                val resultMotorista = getMotoristaVisitTerc(
                    typeVisitTerc = it.tipoVisitTercMovEquipVisitTerc!!,
                    idVisitTerc = it.idVisitTercMovEquipVisitTerc!!
                )
                if (resultMotorista.isFailure) {
                    val e = resultMotorista.exceptionOrNull()!!
                    return resultFailure(
                        context = "IGetMovEquipVisitTercInsideList",
                        message = e.message,
                        cause = e
                    )
                }
                val motorista = resultMotorista.getOrNull()!!
                MovEquipVisitTercModel(
                    id = it.idMovEquipVisitTerc!!,
                    dthr = SimpleDateFormat(
                        "dd/MM/yyyy HH:mm",
                        Locale("pt", "BR")
                    ).format(it.dthrMovEquipVisitTerc),
                    veiculo = it.veiculoMovEquipVisitTerc!!,
                    placa = it.placaMovEquipVisitTerc!!,
                    tipoVisitTerc = it.tipoVisitTercMovEquipVisitTerc!!.name,
                    motorista = motorista
                )
            }
            return Result.success(entityList)
        } catch (e: Exception) {
            return resultFailure(
                context = "IGetMovEquipVisitTercInsideList",
                message = "-",
                cause = e
            )
        }
    }

}