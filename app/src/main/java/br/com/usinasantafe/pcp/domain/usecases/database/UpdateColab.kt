package br.com.usinasantafe.pcp.domain.usecases.database

import br.com.usinasantafe.pcp.utils.ResultUpdateDatabase
import br.com.usinasantafe.pcp.utils.TB_COLAB
import br.com.usinasantafe.pcp.utils.TEXT_CLEAR_TB
import br.com.usinasantafe.pcp.utils.TEXT_RECEIVE_WS_TB
import br.com.usinasantafe.pcp.utils.TEXT_SAVE_DATA_TB
import br.com.usinasantafe.pcp.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.utils.token
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface UpdateColab {
    suspend operator fun invoke(contador: Int = 0, qtde: Int = 3): Flow<Result<ResultUpdateDatabase>>
}

class UpdateColabImpl @Inject constructor(
    private val colabRepository: ColabRepository,
    private val configRepository: ConfigRepository,
): UpdateColab {

    override suspend fun invoke(contador: Int, qtde: Int): Flow<Result<ResultUpdateDatabase>> = flow {
        var contUpdate = contador
        emit(Result.success(ResultUpdateDatabase(++contUpdate, TEXT_CLEAR_TB + TB_COLAB, qtde)))
        colabRepository.deleteAll()
        emit(Result.success(ResultUpdateDatabase(++contUpdate, TEXT_RECEIVE_WS_TB + TB_COLAB, qtde)))
        val config = configRepository.getConfig()
        colabRepository.recoverAll(token(nroAparelho = config.nroAparelhoConfig!!, version = config.version!!, idBD = config.idBDConfig!!))
            .collect{ result ->
                result.fold(
                    onSuccess = { list ->
                        emit(Result.success(ResultUpdateDatabase(++contUpdate, TEXT_SAVE_DATA_TB + TB_COLAB, qtde)))
                        colabRepository.addAll(list)
                    },
                    onFailure = { exception -> emit(Result.failure(Throwable("$exception - $TB_COLAB") )) }
                )
            }
    }

}