package br.com.usinasantafe.pcp.domain.usecases.config

import br.com.usinasantafe.pcp.domain.entities.variable.Config
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.lib.Errors
import br.com.usinasantafe.pcp.lib.LevelUpdate
import br.com.usinasantafe.pcp.utils.UiStatusStateUpdate
import br.com.usinasantafe.pcp.utils.emitProgress
import br.com.usinasantafe.pcp.utils.flowCallUpdate
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.tryCatch
import com.google.common.primitives.UnsignedInts.toLong
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface UpdateConfig {
    suspend operator fun invoke(
        number: String,
        password: String,
        version: String,
        sizeAll: Float,
        count: Float = 1f
    ): Flow<UiStatusStateUpdate>
}

class IUpdateConfig @Inject constructor(
    private val configRepository: ConfigRepository,
): UpdateConfig {

    override suspend fun invoke(
        number: String,
        password: String,
        version: String,
        sizeAll: Float,
        count: Float
    ): Flow<UiStatusStateUpdate> = flow {
        flowCallUpdate(getClassAndMethod(), Errors.TOKEN) {

            emitProgress(count, sizeAll, LevelUpdate.GET_TOKEN)
            val numberLong = tryCatch(::toLong.name) {
                number.toLong()
            }
            val entity = Config(numberLong, password, version)
            val config = configRepository.send(entity).getOrThrow()
            entity.idServ = config.idServ

            emitProgress(count, sizeAll, LevelUpdate.SAVE_TOKEN)
            configRepository.save(entity).getOrThrow()

            emitProgress(count, sizeAll, LevelUpdate.FINISH_UPDATE_INITIAL)
        }
    }

}