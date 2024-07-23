package br.com.usinasantafe.pcp.presenter.initial.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.database.UpdateLocal
import br.com.usinasantafe.pcp.domain.usecases.initial.RecoverListLocal
import br.com.usinasantafe.pcp.domain.usecases.initial.SetLocal
import br.com.usinasantafe.pcp.utils.ResultUpdateDatabase
import br.com.usinasantafe.pcp.utils.StatusUpdate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalListViewModel @Inject constructor (
    private val recoverListLocal: RecoverListLocal,
    private val updateLocal: UpdateLocal,
    private val setLocal: SetLocal,
): ViewModel() {

    private val _uiLiveData = MutableLiveData<LocalListFragmentState>()
    val uiLiveData: LiveData<LocalListFragmentState> = _uiLiveData

    private fun setListLocal(localList: List<String>) {
        _uiLiveData.value = LocalListFragmentState.ListLocal(localList)
    }

    private fun checkSetLocal(check: Boolean) {
        _uiLiveData.value = LocalListFragmentState.CheckSetLocal(check)
    }

    private fun setStatusUpdate(statusUpdate: StatusUpdate) {
        _uiLiveData.value = LocalListFragmentState.FeedbackUpdate(statusUpdate)
    }

    private fun setResultUpdate(resultUpdateDatabase: ResultUpdateDatabase) {
        _uiLiveData.value = LocalListFragmentState.SetResultUpdate(resultUpdateDatabase)
    }

    fun setPosLocal(pos: Int) = viewModelScope.launch {
        checkSetLocal(setLocal(pos))
    }

    fun recoverLocals() = viewModelScope.launch {
        setListLocal(recoverListLocal())
    }

    fun updateDataLocal() =
        viewModelScope.launch {
            updateLocal()
                .catch { catch ->
                    setResultUpdate(ResultUpdateDatabase(1, "Erro: $catch", 100, 100))
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

sealed class LocalListFragmentState {
    data class ListLocal(val localList: List<String>) : LocalListFragmentState()
    data class CheckSetLocal(val check: Boolean) : LocalListFragmentState()
    data class FeedbackUpdate(val statusUpdate: StatusUpdate) : LocalListFragmentState()
    data class SetResultUpdate(val resultUpdateDatabase: ResultUpdateDatabase) :
        LocalListFragmentState()
}