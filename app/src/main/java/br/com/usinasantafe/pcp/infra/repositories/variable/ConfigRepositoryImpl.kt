package br.com.usinasantafe.pcp.infra.repositories.variable

import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.domain.entities.variable.Config
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.datasource.webservice.variable.ConfigWebServiceDatasource
import br.com.usinasantafe.pcp.infra.models.webservice.toConfigWebServiceModel
import br.com.usinasantafe.pcp.infra.models.webservice.toConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ConfigRepositoryImpl @Inject constructor (
    private val configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource,
    private val configWebServiceDatasource: ConfigWebServiceDatasource,
) : ConfigRepository {

    override suspend fun hasConfig(): Boolean {
        return configSharedPreferencesDatasource.hasConfig()
    }

    override suspend fun getConfig(): Config {
        return configSharedPreferencesDatasource.getConfig()
    }

    override suspend fun saveConfig(config: Config) {
        configSharedPreferencesDatasource.saveConfig(config)
    }

    override suspend fun recoverToken(config: Config): Flow<Result<Config>> = flow {
        configWebServiceDatasource.recoverToken(config.toConfigWebServiceModel())
            .catch { exception -> emit(Result.failure(exception)) }
            .collect { result ->
                result.fold(
                    onSuccess = {
                        emit(Result.success(it.toConfig()))
                    },
                    onFailure = { exception -> emit(Result.failure(exception)) }
                )
            }
    }

    override suspend fun setStatusSendConfig(statusSend: StatusSend) {
        configSharedPreferencesDatasource.setStatusSend(statusSend)
    }

    override suspend fun clearAllDataConfig() {
        configSharedPreferencesDatasource.clearAllDataConfig()
    }

}