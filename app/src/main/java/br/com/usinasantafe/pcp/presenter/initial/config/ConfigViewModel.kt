package br.com.usinasantafe.pcp.presenter.initial.config

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcp.domain.usecases.database.UpdateAllDataBase
import br.com.usinasantafe.pcp.domain.usecases.initial.InitialConfig
import br.com.usinasantafe.pcp.domain.usecases.initial.RecoverConfig
import br.com.usinasantafe.pcp.utils.StatusRecover
import br.com.usinasantafe.pcp.utils.StatusUpdate
import br.com.usinasantafe.pcp.utils.WEB_RETURN_CLEAR_EQUIP
import br.com.usinasantafe.pcp.utils.ResultUpdateDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfigViewModel @Inject constructor(
    private val recoverConfig: RecoverConfig,
    private val updateAllDataBase: UpdateAllDataBase,
    private val initialConfig: InitialConfig,
) : ViewModel() {

    private val _uiLiveData = MutableLiveData<ConfigFragmentState>()
    val uiLiveData: LiveData<ConfigFragmentState> = _uiLiveData

    private fun setLoadingDataBase(statusUpdate: StatusUpdate) {
        _uiLiveData.value = ConfigFragmentState.FeedbackLoadingDataBase(statusUpdate)
    }

    private fun setLoadingToken(statusRecover: StatusRecover) {
        _uiLiveData.value = ConfigFragmentState.FeedbackLoadingToken(statusRecover)
    }

    private fun setConfig(config: ConfigModel) {
        _uiLiveData.value = ConfigFragmentState.RecoverConfig(config)
    }

    private fun setResultUpdate(resultUpdateDatabase: ResultUpdateDatabase){
        _uiLiveData.value = ConfigFragmentState.SetResultUpdate(resultUpdateDatabase)
    }

    fun recoverDataConfig() = viewModelScope.launch {
        recoverConfig()?.let { setConfig(it) }
    }

    fun saveDataConfig(nroAparelho: String, senha: String, version: String) =
        viewModelScope.launch {
            initialConfig(nroAparelho, senha, version)
                .catch { catch ->
                    setResultUpdate(ResultUpdateDatabase(100, "Erro: $catch", 100))
                    setLoadingToken(StatusRecover.FAILURE)
                }
                .collect { result ->
                    result.fold(
                    onSuccess = { resultUpdateDatabase ->
                        setResultUpdate(resultUpdateDatabase)
                        if (resultUpdateDatabase.percentage == 100) {
                            if (resultUpdateDatabase.describe == WEB_RETURN_CLEAR_EQUIP) {
                                setLoadingToken(StatusRecover.EMPTY)
                            } else {
                                setLoadingToken(StatusRecover.SUCCESS)
                            }
                        }
                    },
                    onFailure = { catch ->
                        setResultUpdate(ResultUpdateDatabase(100, "Erro: $catch", 100))
                        setLoadingToken(StatusRecover.FAILURE)
                    })
                }
        }

    fun updateDataBaseInitial() =
        viewModelScope.launch {
            updateAllDataBase()
                .catch { catch ->
                    setResultUpdate(ResultUpdateDatabase(100, "Erro: $catch", 100))
                    setLoadingDataBase(StatusUpdate.FAILURE)
                }
                .collect { result ->
                    result.fold(
                        onSuccess = { resultUpdateDatabase ->
                            setResultUpdate(resultUpdateDatabase)
                            if (resultUpdateDatabase.percentage == 100) {
                                setLoadingDataBase(StatusUpdate.UPDATED)
                            }
                        },
                        onFailure = { catch ->
                            setResultUpdate(ResultUpdateDatabase(100, "Erro: $catch", 100))
                            setLoadingDataBase(StatusUpdate.FAILURE)
                            return@collect cancel()
                        }
                    )
                }
        }
}

sealed class ConfigFragmentState {
    data class RecoverConfig(val config: ConfigModel) : ConfigFragmentState()
    data class FeedbackLoadingDataBase(val statusUpdateDataBase: StatusUpdate) :
        ConfigFragmentState()
    data class FeedbackLoadingToken(val statusToken: StatusRecover) : ConfigFragmentState()
    data class IsCheckUpdate(val isCheckUpdate: Boolean) : ConfigFragmentState()
    data class SetResultUpdate(val resultUpdateDatabase: ResultUpdateDatabase) : ConfigFragmentState()
}