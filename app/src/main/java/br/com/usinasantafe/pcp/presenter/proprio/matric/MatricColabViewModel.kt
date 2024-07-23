package br.com.usinasantafe.pcp.presenter.proprio.matric

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.common.CheckMatricColab
import br.com.usinasantafe.pcp.domain.usecases.database.UpdateColab
import br.com.usinasantafe.pcp.utils.ResultUpdateDatabase
import br.com.usinasantafe.pcp.utils.StatusUpdate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatricColabViewModel @Inject constructor(
    private val checkMatricColab: CheckMatricColab,
    private val updateColab: UpdateColab,
) : ViewModel() {

    private val _uiLiveData = MutableLiveData<MatricColabFragmentState>()
    val uiLiveData: LiveData<MatricColabFragmentState> = _uiLiveData

    private fun checkMatric(checkMatric: Boolean) {
        _uiLiveData.value = MatricColabFragmentState.CheckMatric(checkMatric)
    }

    private fun setStatusUpdate(statusUpdate: StatusUpdate) {
        _uiLiveData.value = MatricColabFragmentState.FeedbackUpdate(statusUpdate)
    }

    private fun setResultUpdate(resultUpdateDatabase: ResultUpdateDatabase){
        _uiLiveData.value = MatricColabFragmentState.SetResultUpdate(resultUpdateDatabase)
    }

    fun checkMatricColaborador(matricVigia: String) = viewModelScope.launch {
        checkMatric(checkMatricColab(matricVigia))
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

sealed class MatricColabFragmentState {
    data class CheckMatric(val checkMatric: Boolean) : MatricColabFragmentState()
    data class FeedbackUpdate(val statusUpdate: StatusUpdate) : MatricColabFragmentState()
    data class SetResultUpdate(val resultUpdateDatabase: ResultUpdateDatabase) : MatricColabFragmentState()
}