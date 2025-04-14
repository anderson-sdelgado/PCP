package br.com.usinasantafe.pcp.presenter.chaveequip.nroequip

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.entities.ResultUpdate
import br.com.usinasantafe.pcp.domain.usecases.chaveequip.GetNroEquipMovChaveEquip
import br.com.usinasantafe.pcp.domain.usecases.chaveequip.SetIdEquipMovChaveEquip
import br.com.usinasantafe.pcp.domain.usecases.common.CheckNroEquip
import br.com.usinasantafe.pcp.domain.usecases.updatetable.update.UpdateEquip
import br.com.usinasantafe.pcp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcp.presenter.chaveequip.matriccolab.resultUpdateToMatricColabChaveEquip
import br.com.usinasantafe.pcp.ui.theme.addTextField
import br.com.usinasantafe.pcp.ui.theme.clearTextField
import br.com.usinasantafe.pcp.utils.Errors
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeButton
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

data class NroEquipChaveEquipState(
    val nroEquip: String = "",
    val flowApp: FlowApp = FlowApp.ADD,
    val id: Int = 0,
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

fun ResultUpdate.resultUpdateToNroEquipChaveEquip(): NroEquipChaveEquipState {
    val fail = if(failure.isNotEmpty()){
        val ret = "NroEquipChaveEquipViewModel.updateAllDatabase -> ${this.failure}"
        Timber.e(ret)
        ret
    } else {
        this.failure
    }
    val msg = if(failure.isNotEmpty()){
        "NroEquipChaveEquipViewModel.updateAllDatabase -> ${this.failure}"
    } else {
        this.msgProgress
    }
    return NroEquipChaveEquipState(
            flagDialog = this.flagDialog,
            flagFailure = this.flagFailure,
            errors = this.errors,
            failure = fail,
            flagProgress = this.flagProgress,
            msgProgress = msg,
            currentProgress = this.currentProgress,
        )
    }

class NroEquipChaveEquipViewModel(
    saveStateHandle: SavedStateHandle,
    private val checkNroEquip: CheckNroEquip,
    private val setIdEquipMovChaveEquip: SetIdEquipMovChaveEquip,
    private val updateEquip: UpdateEquip,
    private val getNroEquipMovChaveEquip: GetNroEquipMovChaveEquip,
) : ViewModel() {

    private val flowApp: Int = saveStateHandle[FLOW_APP_ARGS]!!
    private val id: Int = saveStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(NroEquipChaveEquipState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                flowApp = FlowApp.entries[flowApp],
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
                val nroEquip = addTextField(uiState.value.nroEquip, text)
                _uiState.update {
                    it.copy(nroEquip = nroEquip)
                }
            }

            TypeButton.CLEAN -> {
                val nroEquip = clearTextField(uiState.value.nroEquip)
                _uiState.update {
                    it.copy(nroEquip = nroEquip)
                }
            }

            TypeButton.OK -> {
                if (uiState.value.nroEquip.isEmpty()) {
                    _uiState.update {
                        it.copy(
                            flagDialog = true,
                            flagFailure = true,
                            errors = Errors.FIELDEMPTY
                        )
                    }
                    return
                }
                setNroEquip()
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

    private fun setNroEquip() = viewModelScope.launch {
        val resultCheckEquip = checkNroEquip(uiState.value.nroEquip)
        if (resultCheckEquip.isFailure) {
            val error = resultCheckEquip.exceptionOrNull()!!
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
        val result = resultCheckEquip.getOrNull()!!
        if (result) {
            val resultSetEquip = setIdEquipMovChaveEquip(
                nroEquip = uiState.value.nroEquip,
                flowApp = uiState.value.flowApp,
                id = uiState.value.id
            )
            if (resultSetEquip.isFailure) {
                val error = resultSetEquip.exceptionOrNull()!!
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

    suspend fun updateAllDatabase(): Flow<NroEquipChaveEquipState> = flow {
        val sizeUpdate = 4f
        var configState = NroEquipChaveEquipState()
        updateEquip(sizeUpdate, 1f).collect {
            configState = it.resultUpdateToNroEquipChaveEquip()
            emit(it.resultUpdateToNroEquipChaveEquip())
        }
        if (configState.flagFailure)
            return@flow
        emit(
            NroEquipChaveEquipState(
                flagDialog = true,
                flagProgress = false,
                flagFailure = false,
                msgProgress = "Atualização de dados realizado com sucesso!",
                currentProgress = 1f,
            )
        )
    }

    fun getNroEquip() = viewModelScope.launch {
        if (
            (uiState.value.flowApp == FlowApp.CHANGE) &&
            (uiState.value.checkGetMatricColab)
        ) {
            val resultGetNro = getNroEquipMovChaveEquip(uiState.value.id)
            if (resultGetNro.isFailure) {
                val error = resultGetNro.exceptionOrNull()!!
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
            val nroEquip = resultGetNro.getOrNull()!!
            _uiState.update {
                it.copy(
                    nroEquip = nroEquip,
                    checkGetMatricColab = false
                )
            }
        }
    }
}