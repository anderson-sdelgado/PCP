package br.com.usinasantafe.pcp.domain.usecases.updateTable

import br.com.usinasantafe.pcp.domain.repositories.stable.ChaveRepository
import br.com.usinasantafe.pcp.domain.usecases.common.GetToken
import br.com.usinasantafe.pcp.lib.LevelUpdate
import br.com.usinasantafe.pcp.lib.TB_CHAVE
import br.com.usinasantafe.pcp.utils.UiStatusStateUpdate
import br.com.usinasantafe.pcp.utils.emitProgress
import br.com.usinasantafe.pcp.utils.flowCall
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface UpdateTableChave {
    suspend operator fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<UiStatusStateUpdate>
}

class IUpdateTableChave @Inject constructor(
    private val getToken: GetToken,
    private val chaveRepository: ChaveRepository
): UpdateTableChave {

    override suspend fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<UiStatusStateUpdate> = flow {
        flowCall(getClassAndMethod()) {

            emitProgress(count, sizeAll, LevelUpdate.RECOVERY, TB_CHAVE)
            val token = getToken().getOrThrow()
            val entityList = chaveRepository.listAll(token).getOrThrow()

            emitProgress(count, sizeAll, LevelUpdate.CLEAN, TB_CHAVE)
            chaveRepository.deleteAll().getOrThrow()

            emitProgress(count, sizeAll, LevelUpdate.SAVE, TB_CHAVE)
            chaveRepository.addAll(entityList).getOrThrow()
        }
    }

}