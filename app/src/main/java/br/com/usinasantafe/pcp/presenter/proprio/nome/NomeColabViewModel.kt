package br.com.usinasantafe.pcp.presenter.proprio.nome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.proprio.RecoverNomeColab
import br.com.usinasantafe.pcp.domain.usecases.proprio.SetMatricMotoristaPassag
import br.com.usinasantafe.pcp.utils.TypeAddOcupante
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NomeColabViewModel @Inject constructor(
    private val recoverNomeColab: RecoverNomeColab,
    private val setMatricMotoristaPassag: SetMatricMotoristaPassag,
): ViewModel() {

    private val _uiLiveData = MutableLiveData<NomeColabFragmentState>()
    val uiLiveData: LiveData<NomeColabFragmentState> = _uiLiveData

    private fun checkSetMatricColab(checkSetMatricOperador: Boolean) {
        _uiLiveData.value = NomeColabFragmentState.CheckSetMatric(checkSetMatricOperador)
    }

    private fun setNomeVigia(nome: String) {
        _uiLiveData.value = NomeColabFragmentState.GetNomeColab(nome)
    }

    fun recoverDataNome(matricColab: String) = viewModelScope.launch {
        setNomeVigia(recoverNomeColab(matricColab))
    }

    fun setMatricMotorista(matricVigia: String, typeAddOcupante: TypeAddOcupante, pos: Int) = viewModelScope.launch {
        checkSetMatricColab(setMatricMotoristaPassag(matricVigia, typeAddOcupante, pos))
    }

}

sealed class NomeColabFragmentState {
    data class GetNomeColab(val nomeColab: String) : NomeColabFragmentState()
    data class CheckSetMatric(val checkSetMatric: Boolean) : NomeColabFragmentState()
}