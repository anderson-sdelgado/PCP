package br.com.usinasantafe.pcp.presenter.view.initial.matricvigia

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.entities.ResultUpdate
import br.com.usinasantafe.pcp.domain.usecases.common.CheckMatricColab
import br.com.usinasantafe.pcp.domain.usecases.config.SetMatricVigiaConfig
import br.com.usinasantafe.pcp.domain.usecases.updateTable.update.UpdateTableColab
import br.com.usinasantafe.pcp.presenter.theme.addTextField
import br.com.usinasantafe.pcp.presenter.theme.clearTextField
import br.com.usinasantafe.pcp.lib.Errors
import br.com.usinasantafe.pcp.lib.TypeButton
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class MatricVigiaState(
    val matricVigia: String = "",
    val flagAccess: Boolean = false,
    val flagFailure: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
    val errors: Errors = Errors.FIELD_EMPTY,
    val flagProgress: Boolean = false,
    val msgProgress: String = "",
    val currentProgress: Float = 0.0f,
)

fun ResultUpdate.resultUpdateToMatricVigia(): MatricVigiaState {
    val fail = if(failure.isNotEmpty()){
        val ret = "MatricVigiaViewModel.updateAllDatabase -> ${this.failure}"
        Timber.e(ret)
        ret
    } else {
        this.failure
    }
    val msg = if(failure.isNotEmpty()){
        "MatricVigiaViewModel.updateAllDatabase -> ${this.failure}"
    } else {
        this.msgProgress
    }
    return MatricVigiaState(
            flagDialog = this.flagDialog,
            flagFailure = this.flagFailure,
            errors = this.errors,
            failure = fail,
            flagProgress = this.flagProgress,
            msgProgress = msg,
            currentProgress = this.currentProgress,
        )
    }

@HiltViewModel
class MatricVigiaViewModel @Inject constructor(
    private val checkMatricColab: CheckMatricColab,
    private val setMatricVigiaConfig: SetMatricVigiaConfig,
    private val updateTableColab: UpdateTableColab,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MatricVigiaState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(
                flagDialog = false
            )
        }
    }

    fun setTextField(
        text: String,
        typeButton: TypeButton
    ){
        when(typeButton){
            TypeButton.NUMERIC -> {
                val matricVigia = addTextField(uiState.value.matricVigia, text)
                _uiState.update {
                    it.copy(matricVigia = matricVigia)
                }
            }
            TypeButton.CLEAN -> {
                val matricVigia = clearTextField(uiState.value.matricVigia)
                _uiState.update {
                    it.copy(matricVigia = matricVigia)
                }
            }
            TypeButton.OK -> {
                if (uiState.value.matricVigia.isEmpty()) {
                    _uiState.update {
                        it.copy(
                            flagDialog = true,
                            flagFailure = true,
                            errors = Errors.FIELD_EMPTY
                        )
                    }
                    return
                }
                setMatricVigia()
            }
            TypeButton.UPDATE -> {
                viewModelScope.launch {
                    updateAllDatabase().collect { stateUpdate ->
                        _uiState.value = stateUpdate
                    }
                }
            }
        }
    }

    private fun setMatricVigia() = viewModelScope.launch {
        val resultCheck = checkMatricColab(uiState.value.matricVigia)
        if(resultCheck.isFailure){
            val error = resultCheck.exceptionOrNull()!!
            val failure = "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    flagFailure = true,
                    errors = Errors.EXCEPTION,
                    failure = failure,
                )
            }
            return@launch
        }
        val result = resultCheck.getOrNull()!!
        if(result) {
            val resultSet = setMatricVigiaConfig(uiState.value.matricVigia)
            if (resultSet.isFailure) {
                val error = resultSet.exceptionOrNull()!!
                val failure =
                    "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
                Timber.e(failure)
                _uiState.update {
                    it.copy(
                        flagDialog = true,
                        flagFailure = true,
                        errors = Errors.EXCEPTION,
                        failure = failure,
                    )
                }
                return@launch
            }
        }
        _uiState.update {
            it.copy(
                flagAccess = result,
                flagDialog = !result,
                flagFailure = !result,
                errors = Errors.INVALID,
            )
        }
    }

    suspend fun updateAllDatabase(): Flow<MatricVigiaState> = flow {
        val sizeUpdate = 4f
        var configState = MatricVigiaState()
        updateTableColab(sizeUpdate, 1f).collect{
            configState = it.resultUpdateToMatricVigia()
            emit(it.resultUpdateToMatricVigia())
        }
        if(configState.flagFailure)
            return@flow
        emit(
            MatricVigiaState(
                flagDialog = true,
                flagProgress = false,
                flagFailure = false,
                msgProgress = "Atualização de dados realizado com sucesso!",
                currentProgress = 1f,
            )
        )
    }

}