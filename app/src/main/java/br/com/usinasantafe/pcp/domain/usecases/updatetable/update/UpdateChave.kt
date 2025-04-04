package br.com.usinasantafe.pcp.domain.usecases.updatetable.update

import br.com.usinasantafe.pcp.domain.entities.ResultUpdate
import br.com.usinasantafe.pcp.domain.usecases.updatetable.cleantable.CleanChave
import br.com.usinasantafe.pcp.domain.usecases.updatetable.getserver.GetServerChave
import br.com.usinasantafe.pcp.domain.usecases.updatetable.savetable.SaveChave
import br.com.usinasantafe.pcp.utils.Errors
import br.com.usinasantafe.pcp.utils.TB_CHAVE
import br.com.usinasantafe.pcp.utils.updatePercentage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface UpdateChave {
    suspend operator fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<ResultUpdate>
}

class IUpdateChave(
    private val cleanChave: CleanChave,
    private val getServerChave: GetServerChave,
    private val saveChave: SaveChave,
): UpdateChave {

    override suspend fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<ResultUpdate> = flow {
        var pos = 0f
        emit(
            ResultUpdate(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela $TB_CHAVE do Web Service",
                currentProgress = updatePercentage(++pos, count, sizeAll)
            )
        )
        val resultRecover = getServerChave()
        if (resultRecover.isFailure) {
            val error = resultRecover.exceptionOrNull()!!
            val failure =
                "IUpdateChave -> ${error.message!!.trim()} -> ${error.cause.toString()}"
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
                msgProgress = "Limpando a tabela $TB_CHAVE",
                currentProgress = updatePercentage(++pos, count, sizeAll)
            )
        )
        val resultClean = cleanChave()
        if (resultClean.isFailure) {
            val error = resultClean.exceptionOrNull()!!
            val failure = "IUpdateChave -> ${error.message} -> ${error.cause.toString()}"
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
                msgProgress = "Salvando dados na tabela $TB_CHAVE",
                currentProgress = updatePercentage(++pos, count, sizeAll)
            )
        )
        val list = resultRecover.getOrNull()!!
        val resultSave = saveChave(list)
        if (resultSave.isFailure) {
            val error = resultSave.exceptionOrNull()!!
            val failure = "IUpdateChave -> ${error.message} -> ${error.cause.toString()}"
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