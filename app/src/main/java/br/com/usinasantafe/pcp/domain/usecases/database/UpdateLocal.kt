package br.com.usinasantafe.pcp.domain.usecases.database

import br.com.usinasantafe.pcp.utils.ResultUpdateDatabase
import br.com.usinasantafe.pcp.utils.TB_LOCAL
import br.com.usinasantafe.pcp.utils.TEXT_CLEAR_TB
import br.com.usinasantafe.pcp.utils.TEXT_RECEIVE_WS_TB
import br.com.usinasantafe.pcp.utils.TEXT_SAVE_DATA_TB
import br.com.usinasantafe.pcp.domain.repositories.stable.LocalRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.utils.token
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface UpdateLocal {
    suspend operator fun invoke(contador: Int = 0, qtde: Int = 3): Flow<Result<ResultUpdateDatabase>>
}

class UpdateLocalImpl @Inject constructor(
    private val localRepository: LocalRepository,
    private val configRepository: ConfigRepository,
): UpdateLocal {

    override suspend fun invoke(contador: Int, qtde: Int): Flow<Result<ResultUpdateDatabase>> = flow {
        var contUpdate = contador
        emit(Result.success(ResultUpdateDatabase(++contUpdate, TEXT_CLEAR_TB + TB_LOCAL, qtde)))
        localRepository.deleteAllLocal()
        emit(Result.success(ResultUpdateDatabase(++contUpdate, TEXT_RECEIVE_WS_TB + TB_LOCAL, qtde)))
        val config = configRepository.getConfig()
        localRepository.recoverAllLocal(token(nroAparelho = config.nroAparelhoConfig!!, version = config.version!!, idBD = config.idBDConfig!!))
            .collect{ result ->
                result.fold(
                    onSuccess = { list ->
                        emit(Result.success(ResultUpdateDatabase(++contUpdate, TEXT_SAVE_DATA_TB + TB_LOCAL, qtde)))
                        localRepository.addAllLocal(list)
                    },
                    onFailure = { exception -> emit(Result.failure(Throwable("$exception - $TB_LOCAL"))) }
                )
            }
    }

}