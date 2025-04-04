package br.com.usinasantafe.pcp.domain.usecases.updatetable.update

import br.com.usinasantafe.pcp.domain.entities.ResultUpdate
import br.com.usinasantafe.pcp.domain.usecases.updatetable.cleantable.CleanLocal
import br.com.usinasantafe.pcp.domain.usecases.updatetable.getserver.GetServerLocal
import br.com.usinasantafe.pcp.domain.usecases.updatetable.savetable.SaveLocal
import br.com.usinasantafe.pcp.utils.Errors
import br.com.usinasantafe.pcp.utils.TB_LOCAL
import br.com.usinasantafe.pcp.utils.updatePercentage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface UpdateLocal {
    suspend operator fun invoke(sizeAll: Float, count: Float): Flow<ResultUpdate>
}

class IUpdateLocal(
    private val cleanLocal: CleanLocal,
    private val getServerLocal: GetServerLocal,
    private val saveLocal: SaveLocal,
): UpdateLocal {

    override suspend fun invoke(sizeAll: Float, count: Float): Flow<ResultUpdate> = flow {
        var pos = 0f
        emit(
            ResultUpdate(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela $TB_LOCAL do Web Service",
                currentProgress = updatePercentage(++pos, count, sizeAll)
            )
        )
        val resultRecover = getServerLocal()
        if (resultRecover.isFailure) {
            val error = resultRecover.exceptionOrNull()!!
            val failure =
                "IUpdateLocal -> ${error.message} -> ${error.cause.toString()}"
            emit(
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    msgProgress = failure,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
        emit(
            ResultUpdate(
                flagProgress = true,
                msgProgress = "Limpando a tabela $TB_LOCAL",
                currentProgress = updatePercentage(++pos, count, sizeAll)
            )
        )
        val resultClean = cleanLocal()
        if (resultClean.isFailure) {
            val error = resultClean.exceptionOrNull()!!
            val failure = "IUpdateLocal -> ${error.message} -> ${error.cause.toString()}"
            emit(
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    msgProgress = failure,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
        emit(
            ResultUpdate(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela $TB_LOCAL",
                currentProgress = updatePercentage(++pos, count, sizeAll)
            )
        )
        val list = resultRecover.getOrNull()!!
        val resultSave = saveLocal(list)
        if (resultSave.isFailure) {
            val error = resultSave.exceptionOrNull()!!
            val failure = "IUpdateLocal -> ${error.message} -> ${error.cause.toString()}"
            emit(
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    msgProgress = failure,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
    }

}