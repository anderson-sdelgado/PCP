package br.com.usinasantafe.pcp.presenter.initial.menuinicial

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.common.GetStatusSendConfig
import br.com.usinasantafe.pcp.domain.usecases.initial.CheckAcessApont
import br.com.usinasantafe.pcp.utils.StatusSend
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuInicialViewModel @Inject constructor(
    private val checkAcessApont: CheckAcessApont,
    private val getStateSendConfig: GetStatusSendConfig,
) : ViewModel() {

    private val _uiLiveData = MutableLiveData<MenuInicialFragmentState>()
    val uiLiveData: LiveData<MenuInicialFragmentState> = _uiLiveData

    private fun checkAccess(acessApont: Boolean){
        _uiLiveData.value = MenuInicialFragmentState.HasAcessApont(acessApont)
    }

    private fun setStatusSend(statusSend: StatusSend){
        _uiLiveData.value = MenuInicialFragmentState.SetStatusSend(statusSend)
    }

    fun checkAccessApont() = viewModelScope.launch {
        checkAccess(checkAcessApont())
    }

    fun stateSend() = viewModelScope.launch {
        getStateSendConfig()
            .collect { result ->
                setStatusSend(result)
            }
    }

}

sealed class MenuInicialFragmentState {
    data class HasAcessApont(val hasAcessApont: Boolean) : MenuInicialFragmentState()
    data class SetStatusSend(val statusSend: StatusSend): MenuInicialFragmentState()
}