package br.com.usinasantafe.pcp.domain.usecases.updatetable.update

import br.com.usinasantafe.pcp.domain.entities.ResultUpdate
import br.com.usinasantafe.pcp.domain.usecases.updatetable.cleantable.CleanFluxo
import br.com.usinasantafe.pcp.domain.usecases.updatetable.getserver.GetServerFluxo
import br.com.usinasantafe.pcp.domain.usecases.updatetable.savetable.SaveFluxo
import br.com.usinasantafe.pcp.utils.Errors
import br.com.usinasantafe.pcp.utils.TB_FLUXO
import br.com.usinasantafe.pcp.utils.updatePercentage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface UpdateFluxo {
    suspend operator fun invoke(sizeAll: Float, count: Float): Flow<ResultUpdate>
}

class IUpdateFluxo(
    private val cleanFluxo: CleanFluxo,
    private val getServerFluxo: GetServerFluxo,
    private val saveFluxo: SaveFluxo,
): UpdateFluxo {

    override suspend fun invoke(sizeAll: Float, count: Float): Flow<ResultUpdate> = flow {
        var pos = 0f
        emit(
            ResultUpdate(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela $TB_FLUXO do Web Service",
                currentProgress = updatePercentage(++pos, count, sizeAll)
            )
        )
        val resultRecover = getServerFluxo()
        if (resultRecover.isFailure) {
            val error = resultRecover.exceptionOrNull()!!
            val failure =
                "IUpdateFluxo -> ${error.message} -> ${error.cause.toString()}"
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
                msgProgress = "Limpando a tabela $TB_FLUXO",
                currentProgress = updatePercentage(++pos, count, sizeAll)
            )
        )
        val resultClean = cleanFluxo()
        if (resultClean.isFailure) {
            val error = resultClean.exceptionOrNull()!!
            val failure = "IUpdateFluxo -> ${error.message} -> ${error.cause.toString()}"
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
                msgProgress = "Salvando dados na tabela $TB_FLUXO",
                currentProgress = updatePercentage(++pos, count, sizeAll)
            )
        )
        val list = resultRecover.getOrNull()!!
        val resultSave = saveFluxo(list)
        if (resultSave.isFailure) {
            val error = resultSave.exceptionOrNull()!!
            val failure = "IUpdateFluxo -> ${error.message} -> ${error.cause.toString()}"
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