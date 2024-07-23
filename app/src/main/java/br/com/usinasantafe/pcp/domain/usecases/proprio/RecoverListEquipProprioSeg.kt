package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.utils.TypeAddEquip
import br.com.usinasantafe.pcp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioSegRepository
import javax.inject.Inject

interface RecoverListEquipProprioSeg {
    suspend operator fun invoke(typeAddEquip: TypeAddEquip, pos: Int): List<String>
}

class RecoverListEquipProprioSegImpl @Inject constructor(
    private val movEquipProprioRepository: MovEquipProprioRepository,
    private val movEquipProprioSegRepository: MovEquipProprioSegRepository,
    private val equipRepository: EquipRepository,
) : RecoverListEquipProprioSeg {

    override suspend fun invoke(typeAddEquip: TypeAddEquip, pos: Int): List<String> {
        return when (typeAddEquip) {
            TypeAddEquip.ADDVEICULO,
            TypeAddEquip.ADDVEICULOSEG -> movEquipProprioSegRepository.listEquipSeg()
                .map { equipRepository.getEquipId(it.idEquipMovEquipProprioSeg!!).nroEquip.toString() }
            TypeAddEquip.CHANGEVEICULO,
            TypeAddEquip.CHANGEVEICULOSEG -> {
                val movEquip = movEquipProprioRepository.listMovEquipProprioOpen()[pos]
                movEquipProprioSegRepository.listEquipSeg(movEquip.idMovEquipProprio!!)
                    .map { equipRepository.getEquipId(it.idEquipMovEquipProprioSeg!!).nroEquip.toString() }
            }
        }
    }

}