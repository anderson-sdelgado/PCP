package br.com.usinasantafe.pcp.domain.usecases.updateTable

import br.com.usinasantafe.pcp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcp.domain.usecases.common.GetToken
import br.com.usinasantafe.pcp.lib.LevelUpdate
import br.com.usinasantafe.pcp.lib.TB_EQUIP
import br.com.usinasantafe.pcp.utils.UiStatusStateUpdate
import br.com.usinasantafe.pcp.utils.emitProgress
import br.com.usinasantafe.pcp.utils.flowCall
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface UpdateTableEquip {
    suspend operator fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<UiStatusStateUpdate>
}

class IUpdateTableEquip @Inject constructor(
    private val getToken: GetToken,
    private val equipRepository: EquipRepository
): UpdateTableEquip {

    override suspend fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<UiStatusStateUpdate> = flow {
        flowCall(getClassAndMethod()) {

            emitProgress(count, sizeAll, LevelUpdate.RECOVERY, TB_EQUIP)
            val token = getToken().getOrThrow()
            val entityList = equipRepository.listAll(token).getOrThrow()

            emitProgress(count, sizeAll, LevelUpdate.CLEAN, TB_EQUIP)
            equipRepository.deleteAll().getOrThrow()

            emitProgress(count, sizeAll, LevelUpdate.SAVE, TB_EQUIP)
            equipRepository.addAll(entityList).getOrThrow()

        }
    }

}