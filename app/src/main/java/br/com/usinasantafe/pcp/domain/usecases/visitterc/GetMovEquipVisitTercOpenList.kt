package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.errors.UsecaseException
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcp.presenter.visitterc.model.MovEquipVisitTercModel
import br.com.usinasantafe.pcp.utils.TypeMovEquip
import java.text.SimpleDateFormat
import java.util.Locale

interface GetMovEquipVisitTercOpenList {
    suspend operator fun invoke(): Result<List<MovEquipVisitTercModel>>
}

class IGetMovEquipVisitTercOpenList(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
    private val getMotoristaVisitTerc: GetMotoristaVisitTerc
) : GetMovEquipVisitTercOpenList {

    override suspend fun invoke(): Result<List<MovEquipVisitTercModel>> {
        try {
            val resultList = movEquipVisitTercRepository.listOpen()
            if (resultList.isFailure)
                return Result.failure(resultList.exceptionOrNull()!!)
            val list = resultList.getOrNull()!!
            val modelList = list.map {
                val resultMotorista = getMotoristaVisitTerc(
                    typeVisitTerc = it.tipoVisitTercMovEquipVisitTerc!!,
                    idVisitTerc = it.idVisitTercMovEquipVisitTerc!!
                )
                if (resultMotorista.isFailure)
                    return Result.failure(resultMotorista.exceptionOrNull()!!)
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
            return Result.failure(
                UsecaseException(
                    function = "GetMovEquipVisitTercOpenListImpl",
                    cause = e
                )
            )
        }
    }

}