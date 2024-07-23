package br.com.usinasantafe.pcp.presenter.proprio.notafiscal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.proprio.SetNotaFiscalProprio
import br.com.usinasantafe.pcp.utils.FlowApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotaFiscalProprioViewModel @Inject constructor(
    private val setNotaFiscalProprio: SetNotaFiscalProprio,
) : ViewModel() {

    private val _uiLiveData = MutableLiveData<NotaFiscalProprioFragmentState>()
    val uiLiveData: LiveData<NotaFiscalProprioFragmentState> = _uiLiveData

    private fun checkSetNotaFiscal(check: Boolean) {
        _uiLiveData.value = NotaFiscalProprioFragmentState.CheckSetNotaFiscal(check)
    }

    fun setNotaFiscal(notaFiscal: String, flowApp: FlowApp, pos: Int) = viewModelScope.launch {
        checkSetNotaFiscal(setNotaFiscalProprio(notaFiscal, flowApp, pos))
    }

}

sealed class NotaFiscalProprioFragmentState {
    data class CheckSetNotaFiscal(val check: Boolean) : NotaFiscalProprioFragmentState()
}