package br.com.usinasantafe.pcp.infra.repositories.variable

import br.com.usinasantafe.pcp.domain.entities.variable.Config
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.infra.datasource.retrofit.variable.ConfigRetrofitDatasource
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.entityToRetrofitModel
import br.com.usinasantafe.pcp.utils.FlagUpdate
import br.com.usinasantafe.pcp.utils.StatusSend

class IConfigRepository(
    private val configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource,
    private val configRetrofitDatasource: ConfigRetrofitDatasource,
) : ConfigRepository {

    override suspend fun hasConfig(): Result<Boolean> {
        val result = configSharedPreferencesDatasource.has()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IConfigRepository.hasConfig",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun saveInitial(
        number: Long,
        password: String,
        version: String,
        idBD: Int
    ): Result<Boolean> {
        val config = Config(
            number = number,
            password = password,
            version = version,
            idBD = idBD,
            statusSend = StatusSend.SENT
        )
        val result = configSharedPreferencesDatasource.save(config)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IConfigRepository.saveInitial",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun getPassword(): Result<String> {
        try {
            val result = configSharedPreferencesDatasource.get()
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IConfigRepository.getPassword",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(result.getOrNull()!!.password!!)
        } catch (e: Exception) {
            return resultFailure(
                context = "IConfigRepository.getPassword",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getFlagUpdate(): Result<FlagUpdate> {
        try {
            val result = configSharedPreferencesDatasource.get()
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IConfigRepository.getFlagUpdate",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(result.getOrNull()!!.flagUpdate)
        } catch (e: Exception) {
            return resultFailure(
                context = "IConfigRepository.getFlagUpdate",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getMatricVigia(): Result<Int> {
        try {
            val result = configSharedPreferencesDatasource.get()
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IConfigRepository.getMatricVigia",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(result.getOrNull()!!.matricVigia!!)
        } catch (e: Exception) {
            return resultFailure(
                context = "IConfigRepository.getMatricVigia",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun clean(): Result<Boolean> {
        val result = configSharedPreferencesDatasource.clean()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IConfigRepository.clean",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun getConfig(): Result<Config> {
        val result = configSharedPreferencesDatasource.get()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IConfigRepository.getConfig",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun send(config: Config): Result<Int> {
        try {
            val result = configRetrofitDatasource.recoverToken(config.entityToRetrofitModel())
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IConfigRepository.send",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(result.getOrNull()!!.idBD)
        } catch (e: Exception) {
            return resultFailure(
                context = "IConfigRepository.send",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setFlagUpdate(flagUpdate: FlagUpdate): Result<Boolean> {
        try {
            val resultConfig = configSharedPreferencesDatasource.get()
            if (resultConfig.isFailure) {
                val e = resultConfig.exceptionOrNull()!!
                return resultFailure(
                    context = "IConfigRepository.setFlagUpdate",
                    message = e.message,
                    cause = e
                )
            }
            val config = resultConfig.getOrNull()!!
            config.flagUpdate = flagUpdate
            val resultSave = configSharedPreferencesDatasource.save(config)
            if (resultSave.isFailure) {
                val e = resultSave.exceptionOrNull()!!
                return resultFailure(
                    context = "IConfigRepository.setFlagUpdate",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IConfigRepository.setFlagUpdate",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setIdLocal(idLocal: Int): Result<Boolean> {
        try {
            val resultConfig = configSharedPreferencesDatasource.get()
            if (resultConfig.isFailure) {
                val e = resultConfig.exceptionOrNull()!!
                return resultFailure(
                    context = "IConfigRepository.setIdLocal",
                    message = e.message,
                    cause = e
                )
            }
            val config = resultConfig.getOrNull()!!
            config.idLocal = idLocal
            val resultSave = configSharedPreferencesDatasource.save(config)
            if (resultSave.isFailure) {
                val e = resultSave.exceptionOrNull()!!
                return resultFailure(
                    context = "IConfigRepository.setIdLocal",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IConfigRepository.setIdLocal",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setMatricVigia(matric: Int): Result<Boolean> {
        try {
            val resultConfig = configSharedPreferencesDatasource.get()
            if (resultConfig.isFailure) {
                val e = resultConfig.exceptionOrNull()!!
                return resultFailure(
                    context = "IConfigRepository.setIdLocal",
                    message = e.message,
                    cause = e
                )
            }
            val config = resultConfig.getOrNull()!!
            config.matricVigia = matric
            val resultSave = configSharedPreferencesDatasource.save(config)
            if (resultSave.isFailure) {
                val e = resultSave.exceptionOrNull()!!
                return resultFailure(
                    context = "IConfigRepository.setMatricVigia",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IConfigRepository.setMatricVigia",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setStatusSend(statusSend: StatusSend): Result<Boolean> {
        try {
            val resultConfig = configSharedPreferencesDatasource.get()
            if (resultConfig.isFailure) {
                val e = resultConfig.exceptionOrNull()!!
                return resultFailure(
                    context = "IConfigRepository.setStatusSend",
                    message = e.message,
                    cause = e
                )
            }
            val config = resultConfig.getOrNull()!!
            config.statusSend = statusSend
            val resultSave = configSharedPreferencesDatasource.save(config)
            if (resultSave.isFailure) {
                val e = resultSave.exceptionOrNull()!!
                return resultFailure(
                    context = "IConfigRepository.setStatusSend",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IConfigRepository.setStatusSend",
                message = "-",
                cause = e
            )
        }
    }

}