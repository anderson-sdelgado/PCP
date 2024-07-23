package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pcp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioPassagRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioSegRepository
import br.com.usinasantafe.pcp.presenter.proprio.detalhemov.DetalheMovEquipProprioModel
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

interface RecoverDetalheMovEquipProprio {
    suspend operator fun invoke(pos: Int): DetalheMovEquipProprioModel
}

class RecoverDetalheMovEquipProprioImpl @Inject constructor(
    private val movEquipProprioRepository: MovEquipProprioRepository,
    private val movEquipProprioPassagRepository: MovEquipProprioPassagRepository,
    private val movEquipProprioSegRepository: MovEquipProprioSegRepository,
    private val equipRepository: EquipRepository,
    private val colabRepository: ColabRepository,
): RecoverDetalheMovEquipProprio {

    override suspend fun invoke(pos: Int): DetalheMovEquipProprioModel {
        val mov = movEquipProprioRepository.listMovEquipProprioOpen()[pos]
        val dthr = "DATA/HORA: ${SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt", "BR")).format(mov.dthrMovEquipProprio)}"
        val tipoMov = if (mov.tipoMovEquipProprio!!.ordinal == 0) "ENTRADA" else "SAÍDA"
        val veiculo = "VEÍCULO: ${equipRepository.getEquipId(mov.idEquipMovEquipProprio!!).nroEquip}"
        val motorista = "MOTORISTA: ${mov.nroMatricColabMovEquipProprio} - ${colabRepository.getColabMatric(mov.nroMatricColabMovEquipProprio!!).nomeColab}"
        var passageiros = "PASSAGEIRO(S): "
        val passagList = movEquipProprioPassagRepository.listPassag(mov.idMovEquipProprio!!)
        for (passag in passagList) {
            passageiros += "${passag.nroMatricMovEquipProprioPassag} - ${colabRepository.getColabMatric(passag.nroMatricMovEquipProprioPassag!!).nomeColab} - "
        }
        val destino = "DESTINO: ${mov.destinoMovEquipProprio}"
        val equipSegList = movEquipProprioSegRepository.listEquipSeg(mov.idMovEquipProprio!!)
        var equipSeg = "VEÍCULO SECUNDÁRIO: "
        for (equip in equipSegList){
            equipSeg += "${equipRepository.getEquipId(equip.idEquipMovEquipProprioSeg!!).nroEquip} - "
        }
        val descrNotaFiscal = if (mov.nroNotaFiscalMovEquipProprio == null) "" else mov.nroNotaFiscalMovEquipProprio
        val notaFiscal = "NOTAL FISCAL: $descrNotaFiscal"
        val descrObserv = if (mov.observMovEquipProprio.isNullOrEmpty()) "" else mov.observMovEquipProprio
        val observ = "OBS.: $descrObserv"
        return DetalheMovEquipProprioModel(dthr, tipoMov, veiculo, motorista, passageiros, destino, equipSeg, notaFiscal, observ)
    }

}