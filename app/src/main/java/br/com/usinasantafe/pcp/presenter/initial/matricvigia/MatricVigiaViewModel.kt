package br.com.usinasantafe.pcp.presenter.initial.matricvigia

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.common.CheckMatricColab
import br.com.usinasantafe.pcp.domain.usecases.database.UpdateColab
import br.com.usinasantafe.pcp.domain.usecases.initial.SetMatricVigia
import br.com.usinasantafe.pcp.utils.ResultUpdateDatabase
import br.com.usinasantafe.pcp.utils.StatusUpdate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatricVigiaViewModel @Inject constructor(
    private val checkMatricColab: CheckMatricColab,
    private val setMatricVigia: SetMatricVigia,
    private val updateColab: UpdateColab,
) : ViewModel() {

    private val _uiLiveData =
        MutableLiveData<MatricVigiaFragmentState>()
    val uiLiveData: LiveData<MatricVigiaFragmentState> = _uiLiveData

    private fun checkMatric(checkMatric: Boolean) {
        _uiLiveData.value = MatricVigiaFragmentState.CheckMatric(checkMatric)
    }

    private fun checkSetMatricOperador(checkSetMatricOperador: Boolean) {
        _uiLiveData.value = MatricVigiaFragmentState.CheckSetMatric(checkSetMatricOperador)
    }

    private fun setStatusUpdate(statusUpdate: StatusUpdate) {
        _uiLiveData.value = MatricVigiaFragmentState.FeedbackUpdate(statusUpdate)
    }

    private fun setResultUpdate(resultUpdateDatabase: ResultUpdateDatabase){
        _uiLiveData.value = MatricVigiaFragmentState.SetResultUpdate(resultUpdateDatabase)
    }

    fun checkMatricVigia(matricVigia: String) = viewModelScope.launch {
        checkMatric(checkMatricColab(matricVigia))
    }

    fun checkSetMatricVigia(matricVigia: String) = viewModelScope.launch {
        checkSetMatricOperador(setMatricVigia(matricVigia))
    }

    fun updateDataColab() =
        viewModelScope.launch {
            updateColab()
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

sealed class MatricVigiaFragmentState {
    data class CheckMatric(val checkMatric: Boolean) : MatricVigiaFragmentState()
    data class CheckSetMatric(val checkSetMatric: Boolean) : MatricVigiaFragmentState()
    data class FeedbackUpdate(val statusUpdate: StatusUpdate) : MatricVigiaFragmentState()
    data class SetResultUpdate(val resultUpdateDatabase: ResultUpdateDatabase) : MatricVigiaFragmentState()
}