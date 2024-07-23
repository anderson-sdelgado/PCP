package br.com.usinasantafe.pcp.presenter.visitterc.tipo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.visitterc.SetTipoVisitTerc
import br.com.usinasantafe.pcp.utils.TypeVisitTerc
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TipoVisitTercViewModel @Inject constructor(
    private val setTipoVisitTerc: SetTipoVisitTerc,
) : ViewModel() {

    private val _uiLiveData = MutableLiveData<TipoVisitTercFragmentState>()
    val uiLiveData: LiveData<TipoVisitTercFragmentState> = _uiLiveData

    private fun checkSetTipo(check: Boolean) {
        _uiLiveData.value = TipoVisitTercFragmentState.CheckSetTipo(check)
    }

    fun setTipo(typeVisitTerc: TypeVisitTerc) = viewModelScope.launch {
        checkSetTipo(setTipoVisitTerc(typeVisitTerc))
    }

}

sealed class TipoVisitTercFragmentState {
    data class CheckSetTipo(val check: Boolean) : TipoVisitTercFragmentState()
}