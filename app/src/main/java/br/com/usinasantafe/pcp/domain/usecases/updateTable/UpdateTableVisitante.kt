package br.com.usinasantafe.pcp.domain.usecases.updateTable

import br.com.usinasantafe.pcp.domain.repositories.stable.VisitanteRepository
import br.com.usinasantafe.pcp.domain.usecases.common.GetToken
import br.com.usinasantafe.pcp.lib.LevelUpdate
import br.com.usinasantafe.pcp.lib.TB_VISITANTE
import br.com.usinasantafe.pcp.utils.UiStatusStateUpdate
import br.com.usinasantafe.pcp.utils.emitProgress
import br.com.usinasantafe.pcp.utils.flowCall
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface UpdateTableVisitante {
    suspend operator fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<UiStatusStateUpdate>
}

class IUpdateTableVisitante @Inject constructor(
    private val getToken: GetToken,
    private val visitanteRepository: VisitanteRepository
): UpdateTableVisitante {

    override suspend fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<UiStatusStateUpdate> = flow {
        flowCall(getClassAndMethod()) {

            emitProgress(count, sizeAll, LevelUpdate.RECOVERY, TB_VISITANTE)
            val token = getToken().getOrThrow()
            val entityList = visitanteRepository.listAll(token).getOrThrow()

            emitProgress(count, sizeAll, LevelUpdate.CLEAN, TB_VISITANTE)
            visitanteRepository.deleteAll().getOrThrow()

            emitProgress(count, sizeAll, LevelUpdate.SAVE, TB_VISITANTE)
            visitanteRepository.addAll(entityList).getOrThrow()

        }
    }

}