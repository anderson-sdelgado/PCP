package br.com.usinasantafe.pcp.presenter.visitterc.cpf

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.database.UpdateVisitTerc
import br.com.usinasantafe.pcp.domain.usecases.visitterc.CheckCPFVisitTerc
import br.com.usinasantafe.pcp.utils.ResultUpdateDatabase
import br.com.usinasantafe.pcp.utils.StatusUpdate
import br.com.usinasantafe.pcp.utils.TypeAddOcupante
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CPFVisitTercViewModel @Inject constructor(
    private val checkCPFVisitTerc: CheckCPFVisitTerc,
    private val updateVisitTerc: UpdateVisitTerc,
) : ViewModel() {

    private val _uiLiveData = MutableLiveData<CPFVisitTercFragmentState>()
    val uiLiveData: LiveData<CPFVisitTercFragmentState> = _uiLiveData

    private fun checkCPF(checkCPF: Boolean) {
        _uiLiveData.value = CPFVisitTercFragmentState.CheckCPF(checkCPF)
    }

    private fun setStatusUpdate(statusUpdate: StatusUpdate) {
        _uiLiveData.value = CPFVisitTercFragmentState.FeedbackUpdate(statusUpdate)
    }

    private fun setResultUpdate(resultUpdateDatabase: ResultUpdateDatabase){
        _uiLiveData.value = CPFVisitTercFragmentState.SetResultUpdate(resultUpdateDatabase)
    }

    fun checkCPFVisitanteTerceiro(cpf: String, typeAddOcupante: TypeAddOcupante, pos: Int) = viewModelScope.launch {
        checkCPF(checkCPFVisitTerc(cpf, typeAddOcupante, pos))
    }

    fun updateDataVisitTerc() =
        viewModelScope.launch {
            updateVisitTerc()
                .catch { catch ->
                    setResultUpdate(ResultUpdateDatabase(1, "Erro: $catch", 100, 100))
                    setStatusUpdate(StatusUpdate.FAILURE)
                }
                .collect { result ->
                    result.fold(
                        onSuccess = { resultUpdateDatabase ->
                            setResultUpdate(resultUpdateDatabase)
                            if (resultUpdateDatabase.percentage == 100) {
                                setStatusUpdate(StatusUpdate.UPDATED)
                            }
                        },
                        onFailure = { catch ->
                            setResultUpdate(ResultUpdateDatabase(100, "Erro: $catch", 100))
                            setStatusUpdate(StatusUpdate.FAILURE)
                        })
                }
        }

}

sealed class CPFVisitTercFragmentState {
    data class CheckCPF(val checkCPF: Boolean) : CPFVisitTercFragmentState()
    data class FeedbackUpdate(val statusUpdate: StatusUpdate) : CPFVisitTercFragmentState()
    data class SetResultUpdate(val resultUpdateDatabase: ResultUpdateDatabase) : CPFVisitTercFragmentState()
}