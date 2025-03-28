package br.com.usinasantafe.pcp.presenter.proprio.movlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.common.GetHeader
import br.com.usinasantafe.pcp.domain.usecases.proprio.CloseAllMovProprio
import br.com.usinasantafe.pcp.domain.usecases.proprio.GetMovEquipProprioOpenList
import br.com.usinasantafe.pcp.domain.usecases.proprio.StartMovEquipProprio
import br.com.usinasantafe.pcp.utils.TypeMovEquip
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MovEquipProprioListState(
    val descrVigia: String = "",
    val descrLocal: String = "",
    val movEquipProprioModelList: List<MovEquipProprioModel> = emptyList(),
    val flagDialogCheck: Boolean = false,
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val flagCloseAllMov: Boolean = false,
    val failure: String = "",
)

class MovEquipProprioListViewModel(
    private val getHeader: GetHeader,
    private val startMovEquipProprio: StartMovEquipProprio,
    private val getMovEquipProprioOpenList: GetMovEquipProprioOpenList,
    private val closeAllMovProprio: CloseAllMovProprio,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovEquipProprioListState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun setDialogCheck(flagDialogCheck: Boolean) {
        _uiState.update {
            it.copy(flagDialogCheck = flagDialogCheck)
        }
    }

    fun returnHeader() = viewModelScope.launch {
        val recoverHeader = getHeader()
        if(recoverHeader.isFailure){
            val error = recoverHeader.exceptionOrNull()!!
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
        val result = recoverHeader.getOrNull()!!
        _uiState.update {
            it.copy(
                descrVigia = result.descrVigia,
                descrLocal = result.descrLocal,
            )
        }
    }

    fun recoverMovEquipOpenList() = viewModelScope.launch {
        val resultGetList = getMovEquipProprioOpenList()
        if (resultGetList.isFailure) {
            val error = resultGetList.exceptionOrNull()!!
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
        val result = resultGetList.getOrNull()!!
        _uiState.update {
            it.copy(
                movEquipProprioModelList = result
            )
        }
    }

    fun startMov(typeMov: TypeMovEquip) = viewModelScope.launch {
        val resultStart = startMovEquipProprio(typeMov = typeMov)
        if(resultStart.isFailure){
            val error = resultStart.exceptionOrNull()!!
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
            it.copy(
                flagAccess = true,
                flagDialog = false,
            )
        }
    }


    fun closeAllMov() = viewModelScope.launch {
        val resultCloseAllMov = closeAllMovProprio()
        if(resultCloseAllMov.isFailure) {
            val error = resultCloseAllMov.exceptionOrNull()!!
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
            it.copy(
                flagCloseAllMov = true,
            )
        }
    }

}