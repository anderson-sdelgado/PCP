package br.com.usinasantafe.pcp.presenter.proprio.veiculo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.database.UpdateEquip
import br.com.usinasantafe.pcp.domain.usecases.proprio.CheckNroEquip
import br.com.usinasantafe.pcp.domain.usecases.proprio.SetNroEquip
import br.com.usinasantafe.pcp.utils.ResultUpdateDatabase
import br.com.usinasantafe.pcp.utils.StatusUpdate
import br.com.usinasantafe.pcp.utils.TypeAddEquip
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VeiculoProprioViewModel @Inject constructor(
    private val checkNroEquip: CheckNroEquip,
    private val setNroEquip: SetNroEquip,
    private val updateEquip: UpdateEquip,
) : ViewModel() {

    private val _uiLiveData = MutableLiveData<VeiculoProprioFragmentState>()
    val uiLiveData: LiveData<VeiculoProprioFragmentState> = _uiLiveData

    private fun checkVeiculo(checkMatric: Boolean) {
        _uiLiveData.value = VeiculoProprioFragmentState.CheckEquip(checkMatric)
    }

    private fun setCheckNroVeiculo(checkSetMatricOperador: Boolean) {
        _uiLiveData.value = VeiculoProprioFragmentState.CheckSetVeicEquipSeg(checkSetMatricOperador)
    }

    private fun setStatusUpdate(statusUpdate: StatusUpdate) {
        _uiLiveData.value = VeiculoProprioFragmentState.FeedbackUpdate(statusUpdate)
    }

    private fun setResultUpdate(resultUpdateDatabase: ResultUpdateDatabase){
        _uiLiveData.value = VeiculoProprioFragmentState.SetResultUpdate(resultUpdateDatabase)
    }

    fun checkNroVeiculo(nroEquip: String) = viewModelScope.launch {
        checkVeiculo(checkNroEquip(nroEquip))
    }

    fun setNroVeiculo(nroEquip: String, typeAddEquip: TypeAddEquip, pos: Int) = viewModelScope.launch {
        setCheckNroVeiculo(setNroEquip(nroEquip, typeAddEquip, pos))
    }

    fun updateDataEquip() =
        viewModelScope.launch {
            updateEquip()
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

sealed class VeiculoProprioFragmentState {
    data class CheckEquip(val checkNroEquip: Boolean) : VeiculoProprioFragmentState()
    data class CheckSetVeicEquipSeg(val checkSetNroVeiculo: Boolean) : VeiculoProprioFragmentState()
    data class FeedbackUpdate(val statusUpdate: StatusUpdate) : VeiculoProprioFragmentState()
    data class SetResultUpdate(val resultUpdateDatabase: ResultUpdateDatabase) : VeiculoProprioFragmentState()
}