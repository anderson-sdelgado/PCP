package br.com.usinasantafe.pcp.presenter.view.initial.local

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.entities.ResultUpdate
import br.com.usinasantafe.pcp.domain.entities.stable.Local
import br.com.usinasantafe.pcp.domain.usecases.config.SetIdLocalConfig
import br.com.usinasantafe.pcp.domain.usecases.initial.GetLocalList
import br.com.usinasantafe.pcp.domain.usecases.updateTable.update.UpdateTableLocal
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.lib.Errors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class LocalState(
    val locals: List<Local> = emptyList(),
    val flagDialog: Boolean = false,
    val failure: String = "",
    val flagAccess: Boolean = false,
    val flagFailure: Boolean = false,
    val errors: Errors = Errors.FIELD_EMPTY,
    val flagProgress: Boolean = false,
    val msgProgress: String = "",
    val currentProgress: Float = 0.0f,
)

fun ResultUpdate.resultUpdateToLocal(): LocalState {
    val fail = if(failure.isNotEmpty()){
        val ret = "LocalViewModel.updateAllDatabase -> ${this.failure}"
        Timber.e(ret)
        ret
    } else {
        this.failure
    }
    val msg = if(failure.isNotEmpty()){
        "LocalViewModel.updateAllDatabase -> ${this.failure}"
    } else {
        this.msgProgress
    }
    return LocalState(
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
class LocalViewModel @Inject constructor(
    private val getLocalList: GetLocalList,
    private val setIdLocalConfig: SetIdLocalConfig,
    private val updateTableLocal: UpdateTableLocal,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LocalState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun localList() = viewModelScope.launch {
        val resultRecoverLocals = getLocalList()
        if (resultRecoverLocals.isFailure) {
            val error = resultRecoverLocals.exceptionOrNull()!!
            val failure = "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                )
            }
            return@launch
        }
        val result = resultRecoverLocals.getOrNull()!!
        _uiState.update {
            it.copy(
                locals = result
            )
        }
    }

    fun setIdLocal(id: Int) = viewModelScope.launch {
        val resultSetIdLocalConfig = setIdLocalConfig(id)
        if (resultSetIdLocalConfig.isFailure) {
            val error = resultSetIdLocalConfig.exceptionOrNull()!!
            val failure = "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    failure = failure,
                )
            }
            return@launch
        }
        val result = resultSetIdLocalConfig.getOrNull()!!
        _uiState.update {
            it.copy(
                flagAccess = result,
                flagDialog = !result,
            )
        }
    }

    fun updateDatabase() = viewModelScope.launch {
        viewModelScope.launch {
            updateAllDatabase().collect { stateUpdate ->
                _uiState.value = stateUpdate
            }
        }
    }

    suspend fun updateAllDatabase(): Flow<LocalState> = flow {
        val sizeUpdate = 4f
        var state = LocalState()
        updateTableLocal(sizeUpdate, 1f).collect {
            state = it.resultUpdateToLocal()
            emit(
                it.resultUpdateToLocal()
            )
        }
        if (state.flagFailure)
            return@flow
        emit(
            LocalState(
                flagDialog = true,
                flagProgress = false,
                flagFailure = false,
                msgProgress = "Atualização de dados realizado com sucesso!",
                currentProgress = 1f,
            )
        )
    }

}