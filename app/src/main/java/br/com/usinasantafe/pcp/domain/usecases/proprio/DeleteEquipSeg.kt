package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.utils.TypeAddEquip
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioSegRepository
import javax.inject.Inject

interface DeleteEquipSeg {
    suspend operator fun invoke(posList: Int, typeAddEquip: TypeAddEquip, pos: Int): Boolean
}

class DeleteEquipSegImpl @Inject constructor(
    private val movEquipProprioRepository: MovEquipProprioRepository,
    private val movEquipProprioSegRepository: MovEquipProprioSegRepository,
) : DeleteEquipSeg {

    override suspend fun invoke(posList: Int, typeAddEquip: TypeAddEquip, pos: Int): Boolean {
        return when (typeAddEquip) {
            TypeAddEquip.ADDVEICULO,
            TypeAddEquip.ADDVEICULOSEG -> movEquipProprioSegRepository.deleteEquipSeg(posList)
            TypeAddEquip.CHANGEVEICULO,
            TypeAddEquip.CHANGEVEICULOSEG -> {
                val movEquip = movEquipProprioRepository.listMovEquipProprioOpen()[pos]
                val result = movEquipProprioSegRepository.deleteEquipSeg(posList, movEquip.idMovEquipProprio!!)
                if(result)
                    movEquipProprioRepository.updateStatusSendMovEquipProprio(movEquip)
                return result
            }
        }
    }

}