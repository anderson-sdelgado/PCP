package br.com.usinasantafe.pcp.presenter.view.veiculoVisitTerc.cpf

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.update.UpdateTableTerceiro
import br.com.usinasantafe.pcp.domain.usecases.update.UpdateTableVisitante
import br.com.usinasantafe.pcp.domain.usecases.veiculoVisitTerc.CheckCpfVisitTerc
import br.com.usinasantafe.pcp.domain.usecases.veiculoVisitTerc.GetCpfVisitTerc
import br.com.usinasantafe.pcp.domain.usecases.veiculoVisitTerc.GetTitleCpfVisitTerc
import br.com.usinasantafe.pcp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcp.presenter.Args.TYPE_OCUPANTE_ARGS
import br.com.usinasantafe.pcp.presenter.theme.clearTextField
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.lib.Errors
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.lib.TypeButton
import br.com.usinasantafe.pcp.lib.TypeOcupante
import br.com.usinasantafe.pcp.utils.UiStateWithStatusUpdate
import br.com.usinasantafe.pcp.utils.UiStatusStateUpdate
import br.com.usinasantafe.pcp.utils.executeUpdateSteps
import br.com.usinasantafe.pcp.utils.onFailureUpdate
import br.com.usinasantafe.pcp.utils.onSuccessUpdateCheckAccess
import br.com.usinasantafe.pcp.utils.sizeUpdate
import br.com.usinasantafe.pcp.utils.withFailure
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class CpfVisitTercState(
    val flowApp: FlowApp = FlowApp.ADD,
    val typeOcupante: TypeOcupante = TypeOcupante.MOTORISTA,
    val id: Int = 0,
    val title: String = "",
    val text: String = "",
    override val status: UiStatusStateUpdate = UiStatusStateUpdate()
) : UiStateWithStatusUpdate<CpfVisitTercState> {

    override fun copyWithStatus(status: UiStatusStateUpdate): CpfVisitTercState =
        copy(status = status)

}

@HiltViewModel
class CpfVisitTercViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getTitleCpfVisitTerc: GetTitleCpfVisitTerc,
    private val checkCpfVisitTerc: CheckCpfVisitTerc,
    private val getCpfVisitTerc: GetCpfVisitTerc,
    private val updateTableTerceiro: UpdateTableTerceiro,
    private val updateTableVisitante: UpdateTableVisitante,
) : ViewModel() {

    private val flowAppArgs: Int = savedStateHandle[FLOW_APP_ARGS]!!
    private val typeOcupanteArgs: Int = savedStateHandle[TYPE_OCUPANTE_ARGS]!!
    private val idArgs: Int = savedStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(CpfVisitTercState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: CpfVisitTercState.() -> CpfVisitTercState) {
        _uiState.update(block)
    }

    init {
        val flowApp = FlowApp.entries[flowAppArgs]
        val typeOcupante = TypeOcupante.entries[typeOcupanteArgs]
        updateState { copy(flowApp = flowApp, typeOcupante = typeOcupante, id = idArgs) }
    }

    fun onCloseDialog() = updateState { copy(status = status.copy(flagDialog = false, flagFailure = false)) }

    fun recover() = viewModelScope.launch {

        data class Data (
            val title: String = "",
            val text: String = ""
        )

        runCatching {
            val title = getTitleCpfVisitTerc(state.flowApp, state.id).getOrThrow()
            val check = (state.flowApp == FlowApp.CHANGE) && (state.typeOcupante == TypeOcupante.MOTORISTA)
            val text = if(check) { getCpfVisitTerc(state.id).getOrThrow() } else ""
            Data(title, text)
        }
            .onSuccess { updateState { copy(title = it.title, text = it.text) } }
            .onFailureUpdate(::updateState)
    }

    fun onTextField(text: String, typeButton: TypeButton) {
        when (typeButton) {
            TypeButton.NUMERIC -> updateState { copy(text = inputCPF(text)) }
            TypeButton.CLEAN -> updateState { copy(text = clearTextField(this.text)) }
            TypeButton.OK -> set()
            TypeButton.UPDATE -> {
                viewModelScope.launch { updateAllDatabase().collect { _uiState.value = it } }
            }
        }
    }

    private fun set() = viewModelScope.launch {
        runCatching {
            if(state.text.isEmpty()) {
                updateState { withFailure( Errors.FIELD_EMPTY) }
                return@launch
            }
            checkCpfVisitTerc(state.text, state.flowApp, state.id).getOrThrow()
        }
            .onSuccessUpdateCheckAccess(::updateState)
            .onFailureUpdate( ::updateState)
    }

    suspend fun updateAllDatabase(): Flow<CpfVisitTercState> =
        executeUpdateSteps(
            steps = listUpdate(),
            getState = { _uiState.value },
            getStatus = { it.status },
            copyStateWithStatus = { state, status -> state.copy(status = status) },
            classAndMethod = getClassAndMethod(),
        )

    suspend fun listUpdate() : List<Flow<UiStatusStateUpdate>> {
        val sizeAll = sizeUpdate(2f)
        val list = mutableListOf<Flow<UiStatusStateUpdate>>()
        var count = 0f
        list.add(updateTableTerceiro(sizeAll, ++count))
        list.add(updateTableVisitante(sizeAll, ++count))
        return list
    }

    private fun inputCPF(char: String): String {
        val currentText = state.text
        if (currentText.length >= 14) return currentText
        return when (currentText.length) {
            3, 7 -> "$currentText.$char"
            11   -> "$currentText-$char"
            else -> currentText + char
        }
    }

}