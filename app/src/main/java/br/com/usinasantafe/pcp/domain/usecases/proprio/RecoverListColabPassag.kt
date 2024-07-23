package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.utils.TypeAddOcupante
import br.com.usinasantafe.pcp.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioPassagRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import javax.inject.Inject

interface RecoverListColabPassag {
    suspend operator fun invoke(typeAddOcupante: TypeAddOcupante, pos: Int): List<String>
}

class RecoverListColabPassagImpl @Inject constructor(
    private val movEquipProprioRepository: MovEquipProprioRepository,
    private val movEquipProprioPassagRepository: MovEquipProprioPassagRepository,
    private val colabRepository: ColabRepository,
): RecoverListColabPassag {

    override suspend fun invoke(typeAddOcupante: TypeAddOcupante, pos: Int): List<String> {
        return when(typeAddOcupante){
            TypeAddOcupante.ADDMOTORISTA,
            TypeAddOcupante.ADDPASSAGEIRO -> {
                movEquipProprioPassagRepository.listPassag().map {
                    val colab = colabRepository.getColabMatric(it.nroMatricMovEquipProprioPassag!!)
                    return@map "${colab.matricColab} - ${colab.nomeColab}"
                }
            }
            TypeAddOcupante.CHANGEMOTORISTA,
            TypeAddOcupante.CHANGEPASSAGEIRO -> {
                val movEquip = movEquipProprioRepository.listMovEquipProprioOpen()[pos]
                movEquipProprioPassagRepository.listPassag(movEquip.idMovEquipProprio!!).map {
                    val colab = colabRepository.getColabMatric(it.nroMatricMovEquipProprioPassag!!)
                    return@map "${colab.matricColab} - ${colab.nomeColab}"
                }
            }
        }
    }

}