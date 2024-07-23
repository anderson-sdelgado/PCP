package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.utils.TypeAddOcupante
import br.com.usinasantafe.pcp.utils.TypeVisitTerc
import br.com.usinasantafe.pcp.domain.repositories.stable.TerceiroRepository
import br.com.usinasantafe.pcp.domain.repositories.stable.VisitanteRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import javax.inject.Inject

interface RecoverListPassagVisitTerc {
    suspend operator fun invoke(typeAddOcupante: TypeAddOcupante, pos: Int): List<String>
}

class RecoverListPassagVisitTercImpl @Inject constructor(
    private val terceiroRepository: TerceiroRepository,
    private val visitanteRepository: VisitanteRepository,
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
    private val movEquipVisitTercPassagRepository: MovEquipVisitTercPassagRepository,
) : RecoverListPassagVisitTerc {

    override suspend fun invoke(typeAddOcupante: TypeAddOcupante, pos: Int): List<String> {
        return when (typeAddOcupante) {
            TypeAddOcupante.ADDMOTORISTA,
            TypeAddOcupante.ADDPASSAGEIRO -> {
                movEquipVisitTercPassagRepository.listPassag().map {
                    return@map getNomeVisitTerc(it.idVisitTercMovEquipVisitTercPassag!!, movEquipVisitTercRepository.getTipoVisitTercMovEquipVisitTerc())
                }
            }

            TypeAddOcupante.CHANGEMOTORISTA,
            TypeAddOcupante.CHANGEPASSAGEIRO -> {
                val movEquip = movEquipVisitTercRepository.listMovEquipVisitTercOpen()[pos]
                movEquipVisitTercPassagRepository.listPassag(movEquip.idMovEquipVisitTerc!!).map {
                    return@map getNomeVisitTerc(it.idVisitTercMovEquipVisitTercPassag!!, movEquip.tipoVisitTercMovEquipVisitTerc!!)
                }
            }
        }

    }

    private suspend fun getNomeVisitTerc(id: Long, typeVisitTerc: TypeVisitTerc): String {
        return when(typeVisitTerc) {
            TypeVisitTerc.VISITANTE -> {
                val visitante =
                    visitanteRepository.getVisitanteId(id)
                "${visitante.cpfVisitante} - ${visitante.nomeVisitante}"
            }

            TypeVisitTerc.TERCEIRO -> {
                val terceiro =
                    terceiroRepository.getTerceiroId(id)
                "${terceiro.cpfTerceiro} - ${terceiro.nomeTerceiro}"
            }
        }
    }

}