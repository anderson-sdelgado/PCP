package br.com.usinasantafe.pcp.presenter.view.veiculoVisitTerc.passaglist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.veiculoVisitTerc.CleanPassagVisitTerc
import br.com.usinasantafe.pcp.domain.usecases.veiculoVisitTerc.DeletePassagVisitTerc
import br.com.usinasantafe.pcp.domain.usecases.veiculoVisitTerc.GetPassagVisitTercList
import br.com.usinasantafe.pcp.presenter.Args.FLOW_APP_ARGS
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
import timber.log.Timber
import javax.inject.Inject

data class PassagVisitTercListState(
    val flowApp: FlowApp = FlowApp.ADD,
    val typeOcupante: TypeOcupante = TypeOcupante.MOTORISTA,
    val id: Int = 0,
    val passagList: List<PassagVisitTercModel> = emptyList(),
    val flagClean: Boolean = true,
    val idVisitTercSelected: Int? = null,
    val flagDialogCheck: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

@HiltViewModel
class PassagVisitTercListViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
    private val cleanPassagVisitTerc: CleanPassagVisitTerc,
    private val getPassagVisitTercList: GetPassagVisitTercList,
    private val deletePassagVisitTerc: DeletePassagVisitTerc,
) : ViewModel() {

    private val flowApp: Int = saveStateHandle[FLOW_APP_ARGS]!!
    private val typeOcupante: Int = saveStateHandle[TYPE_OCUPANTE_ARGS]!!
    private val id: Int = saveStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(PassagVisitTercListState())
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

    fun setDelete(idVisitTerc: Int) {
        _uiState.update {
            it.copy(
                idVisitTercSelected = idVisitTerc,
                flagDialogCheck = true
            )
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
            val resultClean = cleanPassagVisitTerc()
            if (resultClean.isFailure) {
                val error = resultClean.exceptionOrNull()!!
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
            _uiState.update {
                it.copy(flagClean = false)
            }
        }
    }

    fun recoverPassag() = viewModelScope.launch {
        val resultRecoverPassag = getPassagVisitTercList(
            flowApp = uiState.value.flowApp,
            id = uiState.value.id
        )
        if(resultRecoverPassag.isFailure){
            val error = resultRecoverPassag.exceptionOrNull()!!
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
        val passagList = resultRecoverPassag.getOrNull()!!
        _uiState.update {
            it.copy(passagList = passagList)
        }
    }

    fun deletePassag() = viewModelScope.launch {
        val resultDeletePassag = deletePassagVisitTerc(
            idVisitTerc = uiState.value.idVisitTercSelected!!,
            flowApp = uiState.value.flowApp,
            id = uiState.value.id
        )
        if(resultDeletePassag.isFailure){
            val error = resultDeletePassag.exceptionOrNull()!!
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
        _uiState.update {
            it.copy(flagDialogCheck = false)
        }
        recoverPassag()
    }

}