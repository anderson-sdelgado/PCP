package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.utils.TypeAddEquip
import br.com.usinasantafe.pcp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioSegRepository
import javax.inject.Inject

interface SetNroEquip {
    suspend operator fun invoke(nroEquip: String, typeAddEquip: TypeAddEquip, pos: Int): Boolean
}

class SetNroEquipImpl @Inject constructor(
    private val movEquipProprioRepository: MovEquipProprioRepository,
    private val movEquipProprioSegRepository: MovEquipProprioSegRepository,
    private val equipRepository: EquipRepository,
): SetNroEquip {

    override suspend fun invoke(nroEquip: String, typeAddEquip: TypeAddEquip, pos: Int): Boolean {
        return try {
            val idEquip = equipRepository.getEquipNro(nroEquip.toLong()).idEquip
            when(typeAddEquip){
                TypeAddEquip.ADDVEICULO -> movEquipProprioRepository.setVeiculoMovEquipProprio(idEquip)
                TypeAddEquip.ADDVEICULOSEG -> movEquipProprioSegRepository.addEquipSeg(idEquip)
                TypeAddEquip.CHANGEVEICULO -> {
                    val movEquip = movEquipProprioRepository.listMovEquipProprioOpen()[pos]
                    movEquipProprioRepository.updateVeiculoMovEquipProprio(idEquip, movEquip)
                }
                TypeAddEquip.CHANGEVEICULOSEG -> {
                    val movEquip = movEquipProprioRepository.listMovEquipProprioOpen()[pos]
                    val result = movEquipProprioSegRepository.addEquipSeg(idEquip, movEquip.idMovEquipProprio!!)
                    if(result)
                        movEquipProprioRepository.updateStatusSendMovEquipProprio(movEquip)
                    return result
                }
            }
        } catch (exception: Exception) {
            false
        }
    }

}