package br.com.usinasantafe.pcp.domain.usecases.veiculoProprio

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioEquipSegRepository
import br.com.usinasantafe.pcp.lib.FlowApp
import javax.inject.Inject

interface DeleteEquipSeg {
    suspend operator fun invoke(
        idEquip: Int,
        flowApp: FlowApp,
        id: Int,
    ): Result<Boolean>
}

class IDeleteEquipSeg @Inject constructor(
    private val movEquipProprioEquipSegRepository: MovEquipProprioEquipSegRepository
) : DeleteEquipSeg {

    override suspend fun invoke(
        idEquip: Int,
        flowApp: FlowApp,
        id: Int,
    ): Result<Boolean> {
        val result = movEquipProprioEquipSegRepository.delete(
            idEquip = idEquip,
            flowApp = flowApp,
            id = id
        )
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IDeleteEquipSeg",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

}