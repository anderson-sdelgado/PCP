package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcp.presenter.visitterc.detalhe.DetalheVisitTercModel
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeVisitTerc
import java.text.SimpleDateFormat
import java.util.Locale

interface GetDetalheVisitTerc {
    suspend operator fun invoke(
        id: Int
    ): Result<DetalheVisitTercModel>
}

class IGetDetalheVisitTerc(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
    private val movEquipVisitTercPassagRepository: MovEquipVisitTercPassagRepository,
    private val getMotoristaVisitTerc: GetMotoristaVisitTerc,
) : GetDetalheVisitTerc {

    override suspend fun invoke(
        id: Int
    ): Result<DetalheVisitTercModel> {
        try {
            val resultGet = movEquipVisitTercRepository.get(id)
            if (resultGet.isFailure) {
                val e = resultGet.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetDetalheVisitTerc",
                    message = e.message,
                    cause = e
                )
            }
            val mov = resultGet.getOrNull()!!
            val dthr = SimpleDateFormat(
                "dd/MM/yyyy HH:mm",
                Locale("pt", "BR")
            ).format(mov.dthrMovEquipVisitTerc)
            val tipoMov = if (mov.tipoMovEquipVisitTerc!!.ordinal == 0) "ENTRADA" else "SAÍDA"
            val veiculo = "${mov.veiculoMovEquipVisitTerc}"
            val placa = "${mov.placaMovEquipVisitTerc}"
            val tipoVisitTerc = when (mov.tipoVisitTercMovEquipVisitTerc!!) {
                TypeVisitTerc.VISITANTE -> "VISITANTE"
                TypeVisitTerc.TERCEIRO -> "TERCEIRO"
            }
            val resultGetMotorista = getMotoristaVisitTerc(
                mov.tipoVisitTercMovEquipVisitTerc!!,
                mov.idVisitTercMovEquipVisitTerc!!
            )
            if (resultGetMotorista.isFailure) {
                val e = resultGetMotorista.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetDetalheVisitTerc",
                    message = e.message,
                    cause = e
                )
            }
            val motorista = resultGetMotorista.getOrNull()!!
            val resultPassagList =
                movEquipVisitTercPassagRepository.list(
                    FlowApp.CHANGE,
                    mov.idMovEquipVisitTerc!!
                )
            if (resultPassagList.isFailure) {
                val e = resultPassagList.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetDetalheVisitTerc",
                    message = e.message,
                    cause = e
                )
            }
            val passagList = resultPassagList.getOrNull()!!
            var passageiro = ""
            for (passag in passagList) {
                val resultGetPassag = getMotoristaVisitTerc(
                    mov.tipoVisitTercMovEquipVisitTerc!!,
                    passag.idVisitTerc!!
                )
                if (resultGetPassag.isFailure)
                    return Result.failure(resultGetPassag.exceptionOrNull()!!)
                if(passageiro != ""){
                    passageiro += " "
                }
                passageiro += "${resultGetPassag.getOrNull()!!};"
            }
            val destino =
                if (mov.destinoMovEquipVisitTerc.isNullOrEmpty()) "" else mov.destinoMovEquipVisitTerc
            val observ =
                if (mov.observMovEquipVisitTerc.isNullOrEmpty()) "" else mov.observMovEquipVisitTerc
            return Result.success(
                DetalheVisitTercModel(
                    dthr = dthr,
                    tipoMov = tipoMov,
                    veiculo = veiculo,
                    placa = placa,
                    motorista = motorista,
                    passageiro = passageiro,
                    destino = destino,
                    observ = observ,
                    tipoVisitTerc = tipoVisitTerc
                )
            )
        } catch (e: Exception) {
            return resultFailure(
                context = "IGetDetalheVisitTerc",
                message = "-",
                cause = e
            )
        }
    }

}