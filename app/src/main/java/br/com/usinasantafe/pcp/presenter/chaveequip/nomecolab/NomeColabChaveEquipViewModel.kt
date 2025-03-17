package br.com.usinasantafe.pcp.presenter.chaveequip.nomecolab

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.chaveequip.SetMatricColabMovChaveEquip
import br.com.usinasantafe.pcp.domain.usecases.chaveequip.StartRemoveMovChaveEquip
import br.com.usinasantafe.pcp.domain.usecases.common.GetNomeColab
import br.com.usinasantafe.pcp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcp.presenter.Args.MATRIC_COLAB_ARGS
import br.com.usinasantafe.pcp.presenter.Args.TYPE_MOV_ARGS
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeMovKey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class NomeColabChaveEquipState(
    val flowApp: FlowApp = FlowApp.ADD,
    val typeMov: TypeMovKey = TypeMovKey.RECEIPT,
    val id: Int = 0,
    val matricColab: String = "",
    val nomeColab: String = "",
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

class NomeColabChaveEquipViewModel(
    saveStateHandle: SavedStateHandle,
    private val getNomeColab: GetNomeColab,
    private val setMatricColabMovChaveEquip: SetMatricColabMovChaveEquip,
    private val startRemoveMovChaveEquip: StartRemoveMovChaveEquip
) : ViewModel() {

    private val matricColab: String = saveStateHandle[MATRIC_COLAB_ARGS]!!
    private val flowApp: Int = saveStateHandle[FLOW_APP_ARGS]!!
    private val typeMov: Int = saveStateHandle[TYPE_MOV_ARGS]!!
    private val id: Int = saveStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(NomeColabChaveEquipState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                matricColab = matricColab,
                typeMov = TypeMovKey.entries[typeMov],
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

    fun returnNomeColab() = viewModelScope.launch {
        val recoverNome = getNomeColab(uiState.value.matricColab)
        if (recoverNome.isFailure) {
            val error = recoverNome.exceptionOrNull()!!
            val failure =
                "NomeColabChaveEquipViemModel.returnNomeColab -> GetNomeColab -> ${error.message} -> ${error.cause.toString()}"
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

    fun setMatricColab() = viewModelScope.launch {
        if (
            (uiState.value.typeMov == TypeMovKey.REMOVE) &&
            (uiState.value.flowApp == FlowApp.ADD)
        ) {
            val resultStart = startRemoveMovChaveEquip(uiState.value.id)
            if(resultStart.isFailure) {
                val error = resultStart.exceptionOrNull()!!
                val failure = "NomeColabChaveEquipViemModel.setMatricColab -> StartRemoveMovChaveEquip -> ${error.message} -> ${error.cause.toString()}"
                _uiState.update {
                    it.copy(
                        flagDialog = true,
                        failure = failure,
                    )
                }
                return@launch
            }
        }
        val resultSetMatric = setMatricColabMovChaveEquip(
            matricColab = uiState.value.matricColab,
            flowApp = uiState.value.flowApp,
            id = uiState.value.id
        )
        if (resultSetMatric.isFailure){
            val error = resultSetMatric.exceptionOrNull()!!
            val failure =
                "NomeColabChaveEquipViemModel.setMatricColab -> SetMatricColabMovChaveEquip -> ${error.message} -> ${error.cause.toString()}"
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