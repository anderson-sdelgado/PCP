package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.utils.TypeVisitTerc
import br.com.usinasantafe.pcp.domain.repositories.stable.TerceiroRepository
import br.com.usinasantafe.pcp.domain.repositories.stable.VisitanteRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcp.presenter.visitterc.detalhemov.DetalheMovEquipVisitTercModel
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

interface RecoverDetalheMovEquipVisitTerc {
    suspend operator fun invoke(pos: Int): DetalheMovEquipVisitTercModel
}

class RecoverDetalheMovEquipVisitTercImpl @Inject constructor(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
    private val movEquipVisitTercPassagRepository: MovEquipVisitTercPassagRepository,
    private val terceiroRepository: TerceiroRepository,
    private val visitanteRepository: VisitanteRepository,
): RecoverDetalheMovEquipVisitTerc {

    override suspend fun invoke(pos: Int): DetalheMovEquipVisitTercModel {
        val mov = movEquipVisitTercRepository.listMovEquipVisitTercOpen()[pos]
        val dthr = "DATA/HORA: ${SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt", "BR")).format(mov.dthrMovEquipVisitTerc)}"
        val tipoMov = if (mov.tipoMovEquipVisitTerc!!.ordinal == 0) "ENTRADA" else "SAÍDA"
        val veiculo = "VEÍCULO: ${mov.veiculoMovEquipVisitTerc}"
        val placa = "PLACA: ${mov.placaMovEquipVisitTerc}"
        val tipoVisitTerc = if (mov.tipoVisitTercMovEquipVisitTerc!!.ordinal == 0) "VISITANTE" else "TERCEIRO"
        val motorista = when(mov.tipoVisitTercMovEquipVisitTerc!!){
            TypeVisitTerc.VISITANTE -> {
                val visit = visitanteRepository.getVisitanteId(mov.idVisitTercMovEquipVisitTerc!!)
                "${visit.cpfVisitante} - ${visit.nomeVisitante}"
            }
            TypeVisitTerc.TERCEIRO -> {
                val terc = terceiroRepository.getTerceiroId(mov.idVisitTercMovEquipVisitTerc!!)
                "${terc.cpfTerceiro} - ${terc.nomeTerceiro}"
            }
        }
        var passageiros = "PASSAGEIRO(S): "
        val passagList = movEquipVisitTercPassagRepository.listPassag(mov.idMovEquipVisitTerc!!)
        for (passag in passagList) {
            passageiros += when(mov.tipoVisitTercMovEquipVisitTerc!!){
                TypeVisitTerc.VISITANTE -> {
                    val visit = visitanteRepository.getVisitanteId(passag.idVisitTercMovEquipVisitTercPassag!!)
                    "${visit.cpfVisitante} - ${visit.nomeVisitante} -- "
                }
                TypeVisitTerc.TERCEIRO -> {
                    val terc = terceiroRepository.getTerceiroId(passag.idVisitTercMovEquipVisitTercPassag!!)
                    "${terc.cpfTerceiro} - ${terc.nomeTerceiro} -- "
                }
            }
        }
        val descrDestino = if (mov.destinoMovEquipVisitTerc.isNullOrEmpty()) "" else mov.destinoMovEquipVisitTerc
        val destino = "DESTINO: $descrDestino"
        val descrObserv = if (mov.observMovEquipVisitTerc.isNullOrEmpty()) "" else mov.observMovEquipVisitTerc
        val observ = "OBS.: $descrObserv"
        return DetalheMovEquipVisitTercModel(dthr, tipoMov, veiculo, placa, tipoVisitTerc, motorista, passageiros, destino, observ)
    }

}