package br.com.usinasantafe.pcp.domain.usecases.database

import br.com.usinasantafe.pcp.utils.ResultUpdateDatabase
import br.com.usinasantafe.pcp.utils.TB_EQUIP
import br.com.usinasantafe.pcp.utils.TEXT_CLEAR_TB
import br.com.usinasantafe.pcp.utils.TEXT_RECEIVE_WS_TB
import br.com.usinasantafe.pcp.utils.TEXT_SAVE_DATA_TB
import br.com.usinasantafe.pcp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.utils.token
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface UpdateEquip {
    suspend operator fun invoke(contador: Int = 0, qtde: Int = 3): Flow<Result<ResultUpdateDatabase>>
}

class UpdateEquipImpl @Inject constructor(
    private val equipRepository: EquipRepository,
    private val configRepository: ConfigRepository,
): UpdateEquip {

    override suspend fun invoke(contador: Int, qtde: Int): Flow<Result<ResultUpdateDatabase>> = flow {
        var contUpdate = contador
        emit(Result.success(ResultUpdateDatabase(++contUpdate, TEXT_CLEAR_TB + TB_EQUIP, qtde)))
        equipRepository.deleteAllEquip()
        emit(Result.success(ResultUpdateDatabase(++contUpdate, TEXT_RECEIVE_WS_TB + TB_EQUIP, qtde)))
        val config = configRepository.getConfig()
        equipRepository.recoverAllEquip(token(nroAparelho = config.nroAparelhoConfig!!, version = config.version!!, idBD = config.idBDConfig!!))
            .collect{ result ->
                result.fold(
                    onSuccess = { list ->
                        emit(Result.success(ResultUpdateDatabase(++contUpdate, TEXT_SAVE_DATA_TB + TB_EQUIP, qtde)))
                        equipRepository.addAllEquip(list)
                    },
                    onFailure = { exception -> emit(Result.failure(Throwable("$exception - $TB_EQUIP"))) }
                )
            }
    }

}