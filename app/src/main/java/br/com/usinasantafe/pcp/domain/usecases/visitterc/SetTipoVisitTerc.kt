package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcp.utils.TypeVisitTerc

interface SetTipoVisitTerc {
    suspend operator fun invoke(typeVisitTerc: TypeVisitTerc): Result<Boolean>
}

class ISetTipoVisitTerc(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository
): SetTipoVisitTerc {

    override suspend fun invoke(typeVisitTerc: TypeVisitTerc): Result<Boolean> {
        val result = movEquipVisitTercRepository.setTipoVisitTerc(typeVisitTerc)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ISetTipoVisitTerc",
                message = e.message,
                cause = e
            )
        }
        return result
    }

}