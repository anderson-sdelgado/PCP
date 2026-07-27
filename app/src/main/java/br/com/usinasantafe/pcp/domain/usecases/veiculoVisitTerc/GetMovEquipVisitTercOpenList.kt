package br.com.usinasantafe.pcp.domain.usecases.veiculoVisitTerc

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcp.presenter.view.veiculoVisitTerc.model.MovEquipVisitTercModel
import br.com.usinasantafe.pcp.lib.TypeMovEquip
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

interface GetMovEquipVisitTercOpenList {
    suspend operator fun invoke(): Result<List<MovEquipVisitTercModel>>
}

class IGetMovEquipVisitTercOpenList @Inject constructor(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
    private val getMotoristaVisitTerc: GetMotoristaVisitTerc
) : GetMovEquipVisitTercOpenList {

    override suspend fun invoke(): Result<List<MovEquipVisitTercModel>> {
        try {
            val resultList = movEquipVisitTercRepository.listOpen()
            if (resultList.isFailure) {
                val e = resultList.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetMovEquipVisitTercOpenList",
                    message = e.message,
                    cause = e.cause
                )
            }
            val list = resultList.getOrNull()!!
            val modelList = list.map {
                val resultMotorista = getMotoristaVisitTerc(
                    typeVisitTerc = it.tipoVisitTercMovEquipVisitTerc!!,
                    idVisitTerc = it.idVisitTercMovEquipVisitTerc!!
                )
                if (resultMotorista.isFailure) {
                    val e = resultMotorista.exceptionOrNull()!!
                    return resultFailure(
                        context = "IGetMovEquipVisitTercOpenList",
                        message = e.message,
                                cause = e.cause
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
                    motorista = motorista,
                    tipoMov = if (it.tipoMovEquipVisitTerc == TypeMovEquip.INPUT) "ENTRADA" else "SAIDA",
                )
            }
            return Result.success(modelList)
        } catch (e: Exception) {
            return resultFailure(
                context = "IGetMovEquipVisitTercOpenList",
                message = "-",
                cause = e
            )
        }
    }

}