package br.com.usinasantafe.pcp.infra.repositories.variable

import br.com.usinasantafe.pcp.domain.entities.variable.Config
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.infra.datasource.retrofit.variable.ConfigRetrofitDatasource
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.entityToRetrofitModel
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.entityToSharedPreferencesModel
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.sharedPreferencesModelToEntity
import br.com.usinasantafe.pcp.lib.FlagUpdate
import br.com.usinasantafe.pcp.lib.StatusSend
import br.com.usinasantafe.pcp.utils.EmptyResult
import br.com.usinasantafe.pcp.utils.call
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.required
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IConfigRepository @Inject constructor(
    private val configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource,
    private val configRetrofitDatasource: ConfigRetrofitDatasource,
) : ConfigRepository {

    override suspend fun hasConfig(): Result<Boolean> =
        call(getClassAndMethod()) {
            configSharedPreferencesDatasource.has().getOrThrow()
        }

    override suspend fun save(entity: Config): EmptyResult =
        call(getClassAndMethod()) {
            val model = entity.entityToSharedPreferencesModel()
            configSharedPreferencesDatasource.save(model).getOrThrow()
        }

    override suspend fun getPassword(): Result<String> =
        call(getClassAndMethod()) {
            configSharedPreferencesDatasource.get().getOrThrow()::password.required()
        }

    override suspend fun getFlagUpdate(): Result<FlagUpdate> =
        call(getClassAndMethod()) {
            configSharedPreferencesDatasource.get().getOrThrow()::flagUpdate.required()
        }

    override suspend fun getMatricVigia(): Result<Int> =
        call(getClassAndMethod()) {
            configSharedPreferencesDatasource.get().getOrThrow()::matricVigia.required()
        }

    override suspend fun clean(): EmptyResult =
        call(getClassAndMethod()) {
            configSharedPreferencesDatasource.clean().getOrThrow()
        }

    override suspend fun getConfig(): Result<Config> =
        call(getClassAndMethod()) {
            val model = configSharedPreferencesDatasource.get().getOrThrow()
            model.sharedPreferencesModelToEntity()
        }

    override suspend fun send(entity: Config): Result<Config> =
        call(getClassAndMethod()) {
            val model = entity.entityToRetrofitModel()
            val configRetrofitModel = configRetrofitDatasource.recoverToken(model).getOrThrow()
            entity.idServ = configRetrofitModel.idServ
            entity
        }

    override suspend fun setFlagUpdate(flagUpdate: FlagUpdate): EmptyResult =
        call(getClassAndMethod()) {
            configSharedPreferencesDatasource.setFlagUpdate(flagUpdate).getOrThrow()
        }

    override suspend fun setIdLocal(idLocal: Int): EmptyResult =
        call(getClassAndMethod()) {
            configSharedPreferencesDatasource.setIdLocal(idLocal).getOrThrow()
        }

    override suspend fun setMatricVigia(matric: Int): EmptyResult =
        call(getClassAndMethod()) {
            configSharedPreferencesDatasource.setMatricVigia(matric).getOrThrow()
        }

    override suspend fun setStatusSend(statusSend: StatusSend): EmptyResult =
        call(getClassAndMethod()) {
            configSharedPreferencesDatasource.setStatusSend(statusSend).getOrThrow()
        }

    override fun getStatusSend(): Flow<StatusSend> {
        configSharedPreferencesDatasource.getStatusSend()
    }

}