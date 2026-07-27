package br.com.usinasantafe.pcp.domain.usecases.updateTable

import br.com.usinasantafe.pcp.domain.repositories.stable.TerceiroRepository
import br.com.usinasantafe.pcp.domain.usecases.common.GetToken
import br.com.usinasantafe.pcp.lib.LevelUpdate
import br.com.usinasantafe.pcp.lib.TB_TERCEIRO
import br.com.usinasantafe.pcp.utils.UiStatusStateUpdate
import br.com.usinasantafe.pcp.utils.emitProgress
import br.com.usinasantafe.pcp.utils.flowCall
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface UpdateTableTerceiro {
    suspend operator fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<UiStatusStateUpdate>
}

class IUpdateTableTerceiro @Inject constructor(
    private val getToken: GetToken,
    private val terceiroRepository: TerceiroRepository
): UpdateTableTerceiro {

    override suspend fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<UiStatusStateUpdate> = flow {
        flowCall(getClassAndMethod()) {

            emitProgress(count, sizeAll, LevelUpdate.RECOVERY, TB_TERCEIRO)
            val token = getToken().getOrThrow()
            val entityList = terceiroRepository.listAll(token).getOrThrow()

            emitProgress(count, sizeAll, LevelUpdate.CLEAN, TB_TERCEIRO)
            terceiroRepository.deleteAll().getOrThrow()

            emitProgress(count, sizeAll, LevelUpdate.SAVE, TB_TERCEIRO)
            terceiroRepository.addAll(entityList).getOrThrow()

        }
    }

}