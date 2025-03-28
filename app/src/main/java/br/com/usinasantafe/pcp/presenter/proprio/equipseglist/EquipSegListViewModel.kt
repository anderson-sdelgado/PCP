package br.com.usinasantafe.pcp.presenter.proprio.equipseglist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.entities.stable.Equip
import br.com.usinasantafe.pcp.domain.usecases.proprio.CleanEquipSeg
import br.com.usinasantafe.pcp.domain.usecases.proprio.DeleteEquipSeg
import br.com.usinasantafe.pcp.domain.usecases.proprio.GetEquipSegList
import br.com.usinasantafe.pcp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcp.presenter.Args.TYPE_EQUIP_ARGS
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeEquip
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class EquipSegListState(
    val equipSegList: List<Equip> = emptyList(),
    val flagClean: Boolean = true,
    val flowApp: FlowApp = FlowApp.ADD,
    val typeEquip: TypeEquip = TypeEquip.VEICULO,
    val id: Int = 0,
    val idEquipSelected: Int? = null,
    val flagDialogCheck: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

class EquipSegListViewModel(
    saveStateHandle: SavedStateHandle,
    private val cleanEquipSeg: CleanEquipSeg,
    private val getEquipSegList: GetEquipSegList,
    private val deleteEquipSeg: DeleteEquipSeg,
) : ViewModel() {

    private val flowApp: Int = saveStateHandle[FLOW_APP_ARGS]!!
    private val typeEquip: Int = saveStateHandle[TYPE_EQUIP_ARGS]!!
    private val id: Int = saveStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(EquipSegListState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                flowApp = FlowApp.entries[flowApp],
                typeEquip = TypeEquip.entries[typeEquip],
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

    fun cleanVeicSeg() = viewModelScope.launch {
        if (
            (uiState.value.flowApp == FlowApp.ADD) &&
            (uiState.value.typeEquip == TypeEquip.VEICULO) &&
            (uiState.value.flagClean)
        ) {
            val resultClean = cleanEquipSeg()
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


    fun recoverVeicSeg() = viewModelScope.launch {
        val resultRecoverEquipSeg = getEquipSegList(
            flowApp = _uiState.value.flowApp,
            id = _uiState.value.id
        )
        if (resultRecoverEquipSeg.isFailure) {
            val error = resultRecoverEquipSeg.exceptionOrNull()!!
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
        val equipSegList = resultRecoverEquipSeg.getOrNull()!!
        _uiState.update {
            it.copy(equipSegList = equipSegList)
        }
    }


    fun setDelete(idEquip: Int) {
        _uiState.update {
            it.copy(
                idEquipSelected = idEquip,
                flagDialogCheck = true
            )
        }
    }

    fun deleteVeicSeg() = viewModelScope.launch {
        val resultDeletePassag = deleteEquipSeg(
            idEquip = _uiState.value.idEquipSelected!!,
            flowApp = _uiState.value.flowApp,
            id = _uiState.value.id
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
        recoverVeicSeg()
    }

}