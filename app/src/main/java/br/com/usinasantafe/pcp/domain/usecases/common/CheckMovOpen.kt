package br.com.usinasantafe.pcp.domain.usecases.common

import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcp.utils.StatusData
import javax.inject.Inject

interface CheckMovOpen {
    suspend operator fun invoke(): Boolean
}

class CheckMovOpenImpl @Inject constructor(
    private val configRepository: ConfigRepository,
): CheckMovOpen {
    override suspend fun invoke(): Boolean {
        return when(configRepository.getConfig().statusApont){
            StatusData.OPEN -> false
            StatusData.CLOSE -> true
        }
    }
}