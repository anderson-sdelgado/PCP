package br.com.usinasantafe.pcp.presenter.view.veiculoVisitTerc.tipo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.veiculoVisitTerc.SetTipoVisitTerc
import br.com.usinasantafe.pcp.lib.TypeVisitTerc
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class TipoVisitTercState(
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

@HiltViewModel
class TipoVisitTercViewModel @Inject constructor(
    private val setTipoVisitTerc: SetTipoVisitTerc,
) : ViewModel() {

    private val _uiState = MutableStateFlow(TipoVisitTercState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun setTypeVisitTerc(typeVisitTerc: TypeVisitTerc) = viewModelScope.launch {
        val resultSetTypeVisitTerc = setTipoVisitTerc(typeVisitTerc)
        if (resultSetTypeVisitTerc.isFailure) {
            val error = resultSetTypeVisitTerc.exceptionOrNull()!!
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
            )
        }
    }

}