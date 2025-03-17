package br.com.usinasantafe.pcp.presenter.chaveequip.matriccolab

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.entities.ResultUpdate
import br.com.usinasantafe.pcp.domain.usecases.chaveequip.GetMatricColabMovChaveEquip
import br.com.usinasantafe.pcp.domain.usecases.common.CheckMatricColab
import br.com.usinasantafe.pcp.domain.usecases.updatetable.update.UpdateColab
import br.com.usinasantafe.pcp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcp.presenter.Args.TYPE_MOV_ARGS
import br.com.usinasantafe.pcp.ui.theme.addTextField
import br.com.usinasantafe.pcp.ui.theme.clearTextField
import br.com.usinasantafe.pcp.utils.Errors
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeButton
import br.com.usinasantafe.pcp.utils.TypeMovKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MatricColabChaveEquipState(
    val flowApp: FlowApp = FlowApp.ADD,
    val typeMov: TypeMovKey = TypeMovKey.RECEIPT,
    val id: Int = 0,
    val matricColab: String = "",
    val checkGetMatricColab: Boolean = true,
    val flagAccess: Boolean = false,
    val flagFailure: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
    val errors: Errors = Errors.FIELDEMPTY,
    val flagProgress: Boolean = false,
    val msgProgress: String = "",
    val currentProgress: Float = 0.0f,
)

fun ResultUpdate.resultUpdateToMatricColabChaveEquip(): MatricColabChaveEquipState {
    return with(this){
        MatricColabChaveEquipState(
            flagDialog = this.flagDialog,
            flagFailure = this.flagFailure,
            errors = this.errors,
            failure = this.failure,
            flagProgress = this.flagProgress,
            msgProgress = this.msgProgress,
            currentProgress = this.currentProgress,
        )
    }
}

class MatricColabChaveEquipViewModel(
    saveStateHandle: SavedStateHandle,
    private val updateColab: UpdateColab,
    private val checkMatricColab: CheckMatricColab,
    private val getMatricColabMovChaveEquip: GetMatricColabMovChaveEquip
) : ViewModel() {

    private val flowApp: Int = saveStateHandle[FLOW_APP_ARGS]!!
    private val typeMov: Int = saveStateHandle[TYPE_MOV_ARGS]!!
    private val id: Int = saveStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(MatricColabChaveEquipState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                flowApp = FlowApp.entries[flowApp],
                typeMov = TypeMovKey.entries[typeMov],
                id = id
            )
        }
    }

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun getMatricColab() = viewModelScope.launch {
        if (
            (uiState.value.flowApp == FlowApp.CHANGE) &&
            (uiState.value.checkGetMatricColab)
        ) {
            val resultGetMatric = getMatricColabMovChaveEquip(uiState.value.id)
            if (resultGetMatric.isFailure) {
                val error = resultGetMatric.exceptionOrNull()!!
                val failure =
                    "MatricColabChaveEquipViewModel.getMatricColab -> GetMatricColabMovChaveEquip -> ${error.message} -> ${error.cause.toString()}"
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
                    _uiState.update {
                        it.copy(
                            flagDialog = true,
                            flagFailure = true,
                            errors = Errors.FIELDEMPTY
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

    private fun setMatricColab() = viewModelScope.launch {
        val resultCheckMatric = checkMatricColab(uiState.value.matricColab)
        if (resultCheckMatric.isFailure) {
            val error = resultCheckMatric.exceptionOrNull()!!
            val failure =
                "MatricColabChaveEquipViewModel.setMatricColab -> CheckMatricColab -> ${error.message} -> ${error.cause.toString()}"
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

    suspend fun updateAllDatabase(): Flow<MatricColabChaveEquipState> = flow {
        val sizeUpdate = 4f
        var state = MatricColabChaveEquipState()
        updateColab(sizeUpdate, 1f).collect {
            state = it.resultUpdateToMatricColabChaveEquip()
            emit(it.resultUpdateToMatricColabChaveEquip())
        }
        if (state.flagFailure)
            return@flow
        emit(
            MatricColabChaveEquipState(
                flagDialog = true,
                flagProgress = false,
                flagFailure = false,
                msgProgress = "Atualização de dados realizado com sucesso!",
                currentProgress = 1f,
            )
        )
    }

}