package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.entities.stable.Equip
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioEquipSegRepository
import br.com.usinasantafe.pcp.utils.FlowApp

interface GetEquipSegList {
    suspend operator fun invoke(
        flowApp: FlowApp,
        id: Int
    ): Result<List<Equip>>
}

class IGetEquipSegList(
    private val movEquipProprioEquipSegRepository: MovEquipProprioEquipSegRepository,
    private val equipRepository: EquipRepository
) : GetEquipSegList {

    override suspend fun invoke(
        flowApp: FlowApp,
        id: Int
    ): Result<List<Equip>> {
        try {
            val resultList = movEquipProprioEquipSegRepository.list(
                flowApp = flowApp,
                id = id
            )
            if (resultList.isFailure) {
                val e = resultList.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetEquipSegList",
                    message = e.message,
                    cause = e
                )
            }
            val equipList = resultList.getOrNull()!!
            val equipSegList = equipList.map {
                val resultNroEquip = equipRepository.get(it.idEquip!!)
                if (resultNroEquip.isFailure) {
                    val e = resultNroEquip.exceptionOrNull()!!
                    return resultFailure(
                        context = "IGetEquipSegList",
                        message = e.message,
                        cause = e
                    )
                }
                return@map resultNroEquip.getOrNull()!!
            }
            return Result.success(equipSegList)
        } catch (e: Exception) {
            return resultFailure(
                context = "IGetEquipSegList",
                message = "-",
                cause = e
            )
        }
    }

}