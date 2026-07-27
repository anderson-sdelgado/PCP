package br.com.usinasantafe.pcp.presenter.view.veiculoProprio.nomecolab

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.common.GetNomeColab
import br.com.usinasantafe.pcp.domain.usecases.veiculoProprio.SetMatricColab
import br.com.usinasantafe.pcp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcp.presenter.Args.MATRIC_COLAB_ARGS
import br.com.usinasantafe.pcp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcp.presenter.Args.TYPE_OCUPANTE_ARGS
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.lib.TypeOcupante
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NomeColabState(
    val flowApp: FlowApp = FlowApp.ADD,
    val typeOcupante: TypeOcupante = TypeOcupante.MOTORISTA,
    val id: Int = 0,
    val matricColab: String = "",
    val nomeColab: String = "",
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

@HiltViewModel
class NomeColabViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
    private val getNomeColab: GetNomeColab,
    private val setMatricColab: SetMatricColab
) : ViewModel() {

    private val matricColab: String = saveStateHandle[MATRIC_COLAB_ARGS]!!
    private val flowApp: Int = saveStateHandle[FLOW_APP_ARGS]!!
    private val typeOcupante: Int = saveStateHandle[TYPE_OCUPANTE_ARGS]!!
    private val id: Int = saveStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(NomeColabState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                matricColab = matricColab,
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

    fun returnNomeColab() = viewModelScope.launch {
        val recoverNome = getNomeColab(uiState.value.matricColab)
        if (recoverNome.isFailure) {
            val error = recoverNome.exceptionOrNull()!!
            val failure = "${getClassAndMethod()} -> ${error.message} - ${error.cause.toString()}"
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    failure = failure,
                )
            }
            return@launch
        }
        val result = recoverNome.getOrNull()!!
        _uiState.update {
            it.copy(
                nomeColab = result
            )
        }
    }

    fun setMatric() = viewModelScope.launch {
        val resultSetMatric = setMatricColab(
            matricColab = uiState.value.matricColab,
            flowApp = uiState.value.flowApp,
            typeOcupante = uiState.value.typeOcupante,
            id = uiState.value.id
        )
        if (resultSetMatric.isFailure){
            val error = resultSetMatric.exceptionOrNull()!!
            val failure = "${getClassAndMethod()} -> ${error.message} - ${error.cause.toString()}"
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    failure = failure,
                )
            }
            return@launch
        }
        _uiState.update {
            it.copy(
                flagDialog = false,
                flagAccess = true
            )
        }
    }

}