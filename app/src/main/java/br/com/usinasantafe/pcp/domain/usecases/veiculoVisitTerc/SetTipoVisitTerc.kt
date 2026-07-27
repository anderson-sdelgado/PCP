package br.com.usinasantafe.pcp.domain.usecases.veiculoVisitTerc

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcp.lib.TypeVisitTerc
import javax.inject.Inject

interface SetTipoVisitTerc {
    suspend operator fun invoke(typeVisitTerc: TypeVisitTerc): Result<Boolean>
}

class ISetTipoVisitTerc @Inject constructor(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository
): SetTipoVisitTerc {

    override suspend fun invoke(typeVisitTerc: TypeVisitTerc): Result<Boolean> {
        val result = movEquipVisitTercRepository.setTipoVisitTerc(typeVisitTerc)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ISetTipoVisitTerc",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

}