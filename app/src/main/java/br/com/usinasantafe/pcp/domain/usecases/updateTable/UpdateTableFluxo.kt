package br.com.usinasantafe.pcp.domain.usecases.updateTable

import br.com.usinasantafe.pcp.domain.repositories.stable.FluxoRepository
import br.com.usinasantafe.pcp.domain.usecases.common.GetToken
import br.com.usinasantafe.pcp.lib.LevelUpdate
import br.com.usinasantafe.pcp.lib.TB_FLUXO
import br.com.usinasantafe.pcp.utils.UiStatusStateUpdate
import br.com.usinasantafe.pcp.utils.emitProgress
import br.com.usinasantafe.pcp.utils.flowCall
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface UpdateTableFluxo {
    suspend operator fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<UiStatusStateUpdate>
}

class IUpdateTableFluxo @Inject constructor(
    private val getToken: GetToken,
    private val fluxoRepository: FluxoRepository
): UpdateTableFluxo {

    override suspend fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<UiStatusStateUpdate> = flow {
        flowCall(getClassAndMethod()) {

            emitProgress(count, sizeAll, LevelUpdate.RECOVERY, TB_FLUXO)
            val token = getToken().getOrThrow()
            val entityList = fluxoRepository.listAll(token).getOrThrow()

            emitProgress(count, sizeAll, LevelUpdate.CLEAN, TB_FLUXO)
            fluxoRepository.deleteAll().getOrThrow()

            emitProgress(count, sizeAll, LevelUpdate.SAVE, TB_FLUXO)
            fluxoRepository.addAll(entityList).getOrThrow()

        }

    }

}