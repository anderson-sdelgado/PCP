package br.com.usinasantafe.pcp.domain.usecases.updatetable.getserver

import br.com.usinasantafe.pcp.domain.entities.stable.Equip
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcp.domain.usecases.common.GetToken

interface GetServerEquip {
    suspend operator fun invoke(): Result<List<Equip>>
}

class IGetServerEquip(
    private val getToken: GetToken,
    private val equipRepository: EquipRepository
): GetServerEquip {

    override suspend fun invoke(): Result<List<Equip>> {
        try {
            val resultToken = getToken()
            if (resultToken.isFailure) {
                val e = resultToken.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetServerEquip",
                    message = e.message,
                    cause = e
                )
            }
            val token = resultToken.getOrNull()!!
            val resultRecoverAll = equipRepository.recoverAll(token)
            if (resultRecoverAll.isFailure) {
                val e = resultRecoverAll.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetServerEquip",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(resultRecoverAll.getOrNull()!!)
        } catch (e: Exception) {
            return resultFailure(
                context = "IGetServerEquip",
                message = "-",
                cause = e
            )
        }
    }

}
