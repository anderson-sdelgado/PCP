package br.com.usinasantafe.pcp.presenter.visitterc.movlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.common.GetHeader
import br.com.usinasantafe.pcp.domain.usecases.visitterc.GetMovEquipVisitTercInsideList
import br.com.usinasantafe.pcp.domain.usecases.visitterc.StartInputMovEquipVisitTerc
import br.com.usinasantafe.pcp.presenter.visitterc.model.MovEquipVisitTercModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MovEquipVisitTercListState(
    val movEquipVisitTercModelList: List<MovEquipVisitTercModel> = emptyList(),
    val descrVigia: String = "",
    val descrLocal: String = "",
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

class MovEquipVisitTercListViewModel(
    private val getHeader: GetHeader,
    private val getMovEquipVisitTercInsideList: GetMovEquipVisitTercInsideList,
    private val startInputMovEquipVisitTerc: StartInputMovEquipVisitTerc,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovEquipVisitTercListState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun returnHeader() = viewModelScope.launch {
        val recoverHeader = getHeader()
        if (recoverHeader.isFailure) {
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

    fun recoverMovEquipList() = viewModelScope.launch {
        val resultGetList = getMovEquipVisitTercInsideList()
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
                movEquipVisitTercModelList = result
            )
        }
    }

    fun startMov() = viewModelScope.launch {
        val resultStart = startInputMovEquipVisitTerc()
        if (resultStart.isFailure) {
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

}