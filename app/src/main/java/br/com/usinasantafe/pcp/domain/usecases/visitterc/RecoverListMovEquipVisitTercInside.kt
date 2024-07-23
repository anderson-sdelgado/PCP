package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.utils.TypeVisitTerc
import br.com.usinasantafe.pcp.domain.repositories.stable.TerceiroRepository
import br.com.usinasantafe.pcp.domain.repositories.stable.VisitanteRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcp.presenter.visitterc.movequip.MovEquipVisitTercModel
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

interface RecoverListMovEquipVisitTercInside {
    suspend operator fun invoke(): List<MovEquipVisitTercModel>
}

class RecoverListMovEquipVisitTercInsideImpl @Inject constructor(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
    private val visitanteRepository: VisitanteRepository,
    private val terceiroRepository: TerceiroRepository,
): RecoverListMovEquipVisitTercInside {

    override suspend fun invoke(): List<MovEquipVisitTercModel> {
        return movEquipVisitTercRepository.listMovEquipVisitTercInside().map { movEquipVisitTerc ->
            val dthr = "DATA/HORA: ${SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt", "BR")).format(movEquipVisitTerc.dthrMovEquipVisitTerc)}"
            val motorista = if(movEquipVisitTerc.tipoVisitTercMovEquipVisitTerc == TypeVisitTerc.TERCEIRO){
                val terceiro = terceiroRepository.getTerceiroId(movEquipVisitTerc.idVisitTercMovEquipVisitTerc!!)
                "TERCEIRO: ${terceiro.cpfTerceiro} - ${terceiro.nomeTerceiro}"
            } else {
                val visitante = visitanteRepository.getVisitanteId(movEquipVisitTerc.idVisitTercMovEquipVisitTerc!!)
                "VISITANTE: ${visitante.cpfVisitante} - ${visitante.nomeVisitante}"
            }
            val veiculo = "VEÍCULO: ${movEquipVisitTerc.veiculoMovEquipVisitTerc!!}"
            val placa = "PLACA: ${movEquipVisitTerc.placaMovEquipVisitTerc!!}"
            return@map MovEquipVisitTercModel(dthr, motorista, veiculo, placa)
        }
    }

}