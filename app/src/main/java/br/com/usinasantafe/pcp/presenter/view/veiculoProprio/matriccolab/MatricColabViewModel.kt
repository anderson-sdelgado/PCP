package br.com.usinasantafe.pcp.presenter.view.veiculoProprio.matriccolab

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.entities.ResultUpdate
import br.com.usinasantafe.pcp.domain.usecases.common.CheckMatricColab
import br.com.usinasantafe.pcp.domain.usecases.veiculoProprio.GetMatricColab
import br.com.usinasantafe.pcp.domain.usecases.updateTable.update.UpdateTableColab
import br.com.usinasantafe.pcp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcp.presenter.Args.TYPE_OCUPANTE_ARGS
import br.com.usinasantafe.pcp.presenter.theme.addTextField
import br.com.usinasantafe.pcp.presenter.theme.clearTextField
import br.com.usinasantafe.pcp.lib.Errors
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.lib.TypeButton
import br.com.usinasantafe.pcp.lib.TypeOcupante
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

data class MatricColabState(
    val flowApp: FlowApp = FlowApp.ADD,
    val id: Int = 0,
    val matricColab: String = "",
    val typeOcupante: TypeOcupante = TypeOcupante.MOTORISTA,
    val checkGetMatricColab: Boolean = true,
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
    val flagFailure: Boolean = false,
    val errors: Errors = Errors.FIELD_EMPTY,
    val flagProgress: Boolean = false,
    val msgProgress: String = "",
    val currentProgress: Float = 0.0f,
)

fun ResultUpdate.resultUpdateToMatricColab(): MatricColabState {
    val fail = if(failure.isNotEmpty()){
        val ret = "MatricColabViewModel.updateAllDatabase -> ${this.failure}"
        Timber.e(ret)
        ret
    } else {
        this.failure
    }
    val msg = if(failure.isNotEmpty()){
        "MatricColabViewModel.updateAllDatabase -> ${this.failure}"
    } else {
        this.msgProgress
    }
    return MatricColabState(
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
class MatricColabViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
    private val checkMatricColab: CheckMatricColab,
    private val updateTableColab: UpdateTableColab,
    private val getMatricColab: GetMatricColab,
) : ViewModel() {

    private val flowApp: Int = saveStateHandle[FLOW_APP_ARGS]!!
    private val typeOcupante: Int = saveStateHandle[TYPE_OCUPANTE_ARGS]!!
    private val id: Int = saveStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(MatricColabState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                flowApp = FlowApp.entries[flowApp],
                typeOcupante = TypeOcupante.entries[typeOcupante],
                id = id
            )
        }
    }

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun setTextField(
        text: String,
        typeButton: TypeButton
    ) {
        when (typeButton) {
            TypeButton.NUMERIC -> {
                val matricColab = addTextField(uiState.value.matricColab, text)
                _uiState.update {
                    it.copy(matricColab = matricColab)
                }
            }

            TypeButton.CLEAN -> {
                val matricColab = clearTextField(uiState.value.matricColab)
                _uiState.update {
                    it.copy(matricColab = matricColab)
                }
            }

            TypeButton.OK -> {
                if (uiState.value.matricColab.isEmpty()) {
                    val failure = "${getClassAndMethod()} -> Campo vazio!"
                    Timber.e(failure)
                    _uiState.update {
                        it.copy(
                            flagDialog = true,
                            flagFailure = true,
                            errors = Errors.FIELD_EMPTY
                        )
                    }
                    return
                }
                setMatricColab()
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

    fun getMatricColab() = viewModelScope.launch {
        if (
            (uiState.value.flowApp == FlowApp.CHANGE) &&
            (uiState.value.typeOcupante == TypeOcupante.MOTORISTA) &&
            (uiState.value.checkGetMatricColab)
        ) {
            val resultGetMatric = getMatricColab(uiState.value.id)
            if (resultGetMatric.isFailure) {
                val error = resultGetMatric.exceptionOrNull()!!
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
            val matricColab = resultGetMatric.getOrNull()!!
            _uiState.update {
                it.copy(
                    matricColab = matricColab,
                    checkGetMatricColab = false
                )
            }
        }
    }

    private fun setMatricColab() = viewModelScope.launch {
        val resultCheckMatric = checkMatricColab(uiState.value.matricColab)
        if (resultCheckMatric.isFailure) {
            val error = resultCheckMatric.exceptionOrNull()!!
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
        val result = resultCheckMatric.getOrNull()!!
        _uiState.update {
            it.copy(
                flagAccess = result,
                flagDialog = !result,
                flagFailure = !result,
                errors = Errors.INVALID,
            )
        }
    }

    suspend fun updateAllDatabase(): Flow<MatricColabState> = flow {
        val sizeUpdate = 4f
        var state = MatricColabState()
        updateTableColab(sizeUpdate, 1f).collect {
            state = it.resultUpdateToMatricColab()
            emit(it.resultUpdateToMatricColab())
        }
        if (state.flagFailure) {
            val failure = "${getClassAndMethod()} -> ${state.failure}"
            Timber.e(failure)
            return@flow
        }
        emit(
            MatricColabState(
                flagDialog = true,
                flagProgress = false,
                flagFailure = false,
                msgProgress = "Atualização de dados realizado com sucesso!",
                currentProgress = 1f,
            )
        )
    }

}