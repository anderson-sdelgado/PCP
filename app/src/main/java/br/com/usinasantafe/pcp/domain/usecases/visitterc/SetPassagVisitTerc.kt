package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.utils.TypeAddOcupante
import br.com.usinasantafe.pcp.utils.TypeVisitTerc
import br.com.usinasantafe.pcp.domain.repositories.stable.TerceiroRepository
import br.com.usinasantafe.pcp.domain.repositories.stable.VisitanteRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import javax.inject.Inject

interface SetPassagVisitTerc {
    suspend operator fun invoke(cpf: String, typeAddOcupante: TypeAddOcupante, pos: Int): Boolean
}

class SetPassagVisitTercImpl @Inject constructor(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
    private val movEquipVisitTercPassagRepository: MovEquipVisitTercPassagRepository,
    private val visitanteRepository: VisitanteRepository,
    private val terceiroRepository: TerceiroRepository,
): SetPassagVisitTerc {

    override suspend fun invoke(cpf: String, typeAddOcupante: TypeAddOcupante, pos: Int): Boolean {
        return when(typeAddOcupante) {
            TypeAddOcupante.ADDMOTORISTA -> {
                val idVisitTerc = getIdVisitTerc(cpf, movEquipVisitTercRepository.getTipoVisitTercMovEquipVisitTerc())
                movEquipVisitTercRepository.setIdVisitTercMovEquipVisitTerc(idVisitTerc)
            }
            TypeAddOcupante.ADDPASSAGEIRO -> {
                val idVisitTerc = getIdVisitTerc(cpf, movEquipVisitTercRepository.getTipoVisitTercMovEquipVisitTerc())
                movEquipVisitTercPassagRepository.addPassag(idVisitTerc)
            }
            TypeAddOcupante.CHANGEMOTORISTA -> {
                val movEquip = movEquipVisitTercRepository.listMovEquipVisitTercOpen()[pos]
                val idVisitTerc = getIdVisitTerc(cpf, movEquip.tipoVisitTercMovEquipVisitTerc!!)
                movEquipVisitTercRepository.updateMotoristaMovEquipVisitTerc(idVisitTerc, movEquip)
            }
            TypeAddOcupante.CHANGEPASSAGEIRO -> {
                val movEquip = movEquipVisitTercRepository.listMovEquipVisitTercOpen()[pos]
                val idVisitTerc = getIdVisitTerc(cpf, movEquip.tipoVisitTercMovEquipVisitTerc!!)
                val result = movEquipVisitTercPassagRepository.addPassag(idVisitTerc, movEquip.idMovEquipVisitTerc!!)
                if(result)
                    movEquipVisitTercRepository.setStatusSendMov(movEquip)
                return result
            }
        }
    }

    private suspend fun getIdVisitTerc(cpf: String, typeVisitTerc: TypeVisitTerc): Long{
        return when(typeVisitTerc){
            TypeVisitTerc.VISITANTE -> visitanteRepository.getVisitanteCPF(cpf).idVisitante
            TypeVisitTerc.TERCEIRO -> terceiroRepository.getTerceiroCPF(cpf).idBDTerceiro
        }
    }

}