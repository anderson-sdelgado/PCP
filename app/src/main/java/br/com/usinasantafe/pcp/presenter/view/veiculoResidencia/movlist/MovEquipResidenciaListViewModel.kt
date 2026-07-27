package br.com.usinasantafe.pcp.presenter.view.veiculoResidencia.movlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.common.GetHeader
import br.com.usinasantafe.pcp.domain.usecases.veiculoResidencia.GetMovEquipResidenciaInsideList
import br.com.usinasantafe.pcp.domain.usecases.veiculoResidencia.StartInputMovEquipResidencia
import br.com.usinasantafe.pcp.presenter.view.veiculoResidencia.model.MovEquipResidenciaModel
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class MovEquipResidenciaListState(
    val descrVigia: String = "",
    val descrLocal: String = "",
    val movEquipResidenciaModelList: List<MovEquipResidenciaModel> = emptyList(),
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

@HiltViewModel
class MovEquipResidenciaListViewModel @Inject constructor(
    private val getHeader: GetHeader,
    private val getMovEquipResidenciaInsideList: GetMovEquipResidenciaInsideList,
    private val startINputMovEquipResidencia: StartInputMovEquipResidencia,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovEquipResidenciaListState())
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
        val result = recoverHeader.getOrNull()!!
        _uiState.update {
            it.copy(
                descrVigia = result.descrVigia,
                descrLocal = result.descrLocal,
            )
        }
    }

    fun startMov() = viewModelScope.launch {
        val resultStart = startINputMovEquipResidencia()
        if (resultStart.isFailure) {
            val error = resultStart.exceptionOrNull()!!
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
            it.copy(
                flagAccess = true,
                flagDialog = false,
            )
        }
    }

    fun recoverMovEquipList() = viewModelScope.launch {
        val resultGetList = getMovEquipResidenciaInsideList()
        if (resultGetList.isFailure) {
            val error = resultGetList.exceptionOrNull()!!
            val failure = "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
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
                movEquipResidenciaModelList = result
            )
        }
    }
}