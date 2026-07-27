package br.com.usinasantafe.pcp.domain.usecases.updateTable

import br.com.usinasantafe.pcp.domain.entities.ResultUpdate
import br.com.usinasantafe.pcp.domain.repositories.stable.LocalTrabRepository
import br.com.usinasantafe.pcp.domain.usecases.common.GetToken
import br.com.usinasantafe.pcp.domain.usecases.updateTable.cleantable.CleanLocalTrab
import br.com.usinasantafe.pcp.domain.usecases.updateTable.getserver.GetServerLocalTrab
import br.com.usinasantafe.pcp.domain.usecases.updateTable.savetable.SaveLocalTrab
import br.com.usinasantafe.pcp.lib.Errors
import br.com.usinasantafe.pcp.lib.LevelUpdate
import br.com.usinasantafe.pcp.lib.TB_CHAVE
import br.com.usinasantafe.pcp.lib.TB_LOCAL_TRAB
import br.com.usinasantafe.pcp.utils.UiStatusStateUpdate
import br.com.usinasantafe.pcp.utils.emitProgress
import br.com.usinasantafe.pcp.utils.flowCall
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.updatePercentage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface UpdateTableLocalTrab {
    suspend operator fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<UiStatusStateUpdate>
}

class IUpdateTableLocalTrab @Inject constructor(
    private val getToken: GetToken,
    private val localTrabRepository: LocalTrabRepository
): UpdateTableLocalTrab {

    override suspend fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<UiStatusStateUpdate> = flow {
        flowCall(getClassAndMethod()) {

            emitProgress(count, sizeAll, LevelUpdate.RECOVERY, TB_LOCAL_TRAB)
            val token = getToken().getOrThrow()
            val entityList = localTrabRepository.listAll(token).getOrThrow()

            emitProgress(count, sizeAll, LevelUpdate.CLEAN, TB_LOCAL_TRAB)
            localTrabRepository.deleteAll().getOrThrow()

            emitProgress(count, sizeAll, LevelUpdate.SAVE, TB_LOCAL_TRAB)
            localTrabRepository.addAll(entityList).getOrThrow()

        }

    }

}