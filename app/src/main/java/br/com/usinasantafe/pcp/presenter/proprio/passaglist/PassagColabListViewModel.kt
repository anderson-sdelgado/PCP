package br.com.usinasantafe.pcp.presenter.proprio.passaglist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.entities.stable.Colab
import br.com.usinasantafe.pcp.domain.usecases.proprio.CleanPassagColab
import br.com.usinasantafe.pcp.domain.usecases.proprio.DeletePassagColab
import br.com.usinasantafe.pcp.domain.usecases.proprio.GetPassagColabList
import br.com.usinasantafe.pcp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcp.presenter.Args.TYPE_OCUPANTE_ARGS
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeOcupante
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PassagColabListState(
    val flowApp: FlowApp = FlowApp.ADD,
    val typeOcupante: TypeOcupante = TypeOcupante.MOTORISTA,
    val id: Int = 0,
    val passagList: List<Colab> = emptyList(),
    val flagClean: Boolean = true,
    val matricColabSelected: Int? = null,
    val flagDialogCheck: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

class PassagColabListViewModel(
    saveStateHandle: SavedStateHandle,
    private val cleanPassagColab: CleanPassagColab,
    private val getPassagColabList: GetPassagColabList,
    private val deletePassagColab: DeletePassagColab,
) : ViewModel() {

    private val flowApp: Int = saveStateHandle[FLOW_APP_ARGS]!!
    private val typeOcupante: Int = saveStateHandle[TYPE_OCUPANTE_ARGS]!!
    private val id: Int = saveStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(PassagColabListState())
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

    fun setCloseDialogCheck() {
        _uiState.update {
            it.copy(flagDialogCheck = false)
        }
    }

    fun cleanPassag() = viewModelScope.launch {
        if (
            (uiState.value.flowApp == FlowApp.ADD) &&
            (uiState.value.typeOcupante == TypeOcupante.MOTORISTA) &&
            (uiState.value.flagClean)
        ) {
            val resultClean = cleanPassagColab()
            if (resultClean.isFailure) {
                val error = resultClean.exceptionOrNull()!!
                val failure =
                    "${error.message} -> ${error.cause.toString()}"
                _uiState.update {
                    it.copy(
                        flagDialog = true,
                        failure = failure,
                    )
                }
                return@launch
            }
            _uiState.update {
                it.copy(flagClean = false)
            }
        }
    }

    fun recoverPassag() = viewModelScope.launch {
        val resultRecoverPassag =
            getPassagColabList(
                flowApp = uiState.value.flowApp,
                id = uiState.value.id
            )
        if (resultRecoverPassag.isFailure) {
            val error = resultRecoverPassag.exceptionOrNull()!!
            val failure =
                "${error.message} -> ${error.cause.toString()}"
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    failure = failure,
                )
            }
            return@launch
        }
        val passagList = resultRecoverPassag.getOrNull()!!
        _uiState.update {
            it.copy(passagList = passagList)
        }
    }

    fun setDelete(matricColab: Int) {
        _uiState.update {
            it.copy(
                matricColabSelected = matricColab,
                flagDialogCheck = true
            )
        }
    }

    fun deletePassag() = viewModelScope.launch {
        val resultDeletePassag = deletePassagColab(
            matricColab = uiState.value.matricColabSelected!!,
            flowApp = uiState.value.flowApp,
            id = uiState.value.id
        )
        if (resultDeletePassag.isFailure) {
            val error = resultDeletePassag.exceptionOrNull()!!
            val failure =
                "${error.message} -> ${error.cause.toString()}"
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    flagDialogCheck = false,
                    failure = failure,
                )
            }
            return@launch
        }
        _uiState.update {
            it.copy(
                flagDialogCheck = false,
            )
        }
        recoverPassag()
    }

}