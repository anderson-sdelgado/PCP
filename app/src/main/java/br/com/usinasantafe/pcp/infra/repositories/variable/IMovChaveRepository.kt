package br.com.usinasantafe.pcp.infra.repositories.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovChave
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveRepository
import br.com.usinasantafe.pcp.infra.datasource.retrofit.variable.MovChaveRetrofitDatasource
import br.com.usinasantafe.pcp.infra.datasource.room.variable.MovChaveRoomDatasource
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.MovChaveSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.entityToRetrofitModelOutput
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.retrofitModelInputToEntity
import br.com.usinasantafe.pcp.infra.models.room.variable.entityToRoomModel
import br.com.usinasantafe.pcp.infra.models.room.variable.roomModelToEntity
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.entityToSharedPreferencesModel
import br.com.usinasantafe.pcp.utils.FlowApp

class IMovChaveRepository(
    private val movChaveRoomDatasource: MovChaveRoomDatasource,
    private val movChaveSharedPreferencesDatasource: MovChaveSharedPreferencesDatasource,
    private val movChaveRetrofitDatasource: MovChaveRetrofitDatasource
): MovChaveRepository {

    override suspend fun checkOpen(): Result<Boolean> {
        val result = movChaveRoomDatasource.checkOpen()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMovChaveRepository.checkOpen",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun checkSend(): Result<Boolean> {
        val result = movChaveRoomDatasource.checkSend()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMovChaveRepository.checkSend",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun get(id: Int): Result<MovChave> {
        try {
            val result = movChaveRoomDatasource.get(id)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovChaveRepository.get",
                    message = e.message,
                    cause = e
                )
            }
            val entity = result.getOrNull()!!.roomModelToEntity()
            return Result.success(entity)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovChaveRepository.get",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getMatricColab(id: Int): Result<Int> {
        try {
            val result = movChaveRoomDatasource.get(id)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovChaveRepository.getMatricColab",
                    message = e.message,
                    cause = e
                )
            }
            val entity = result.getOrNull()!!.roomModelToEntity()
            return Result.success(entity.matricColabMovChave!!)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovChaveRepository.getMatricColab",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getObserv(id: Int): Result<String?> {
        try {
            val result = movChaveRoomDatasource.get(id)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovChaveRepository.getObserv",
                    message = e.message,
                    cause = e
                )
            }
            val entity = result.getOrNull()!!.roomModelToEntity()
            return Result.success(entity.observMovChave)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovChaveRepository.getObserv",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun listInside(): Result<List<MovChave>> {
        try {
            val result = movChaveRoomDatasource.listInside()
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovChaveRepository.listInside",
                    message = e.message,
                    cause = e
                )
            }
            val entityList = result.getOrNull()!!.map {
                it.roomModelToEntity()
            }
            return Result.success(entityList)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovChaveRepository.listInside",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun listOpen(): Result<List<MovChave>> {
        try {
            val result = movChaveRoomDatasource.listOpen()
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovChaveRepository.listOpen",
                    message = e.message,
                    cause = e
                )
            }
            val entityList = result.getOrNull()!!.map {
                it.roomModelToEntity()
            }
            return Result.success(entityList)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovChaveRepository.listOpen",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun listSend(): Result<List<MovChave>> {
        try {
            val result = movChaveRoomDatasource.listSend()
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovChaveRepository.listSend",
                    message = e.message,
                    cause = e
                )
            }
            val entityListSend = result.getOrNull()!!.map {
                it.roomModelToEntity()
            }
            return Result.success(entityListSend)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovChaveRepository.listSend",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun save(
        matricVigia: Int,
        idLocal: Int,
        uuid: String
    ): Result<Int> {
        try {
            val resultGetMov = movChaveSharedPreferencesDatasource.get()
            if (resultGetMov.isFailure) {
                val e = resultGetMov.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovChaveRepository.save",
                    message = e.message,
                    cause = e
                )
            }
            val roomModel = resultGetMov.getOrNull()!!
                .entityToSharedPreferencesModel()
                .entityToRoomModel(
                    matricVigia = matricVigia,
                    idLocal = idLocal,
                    uuid = uuid
                )
            val resultSave = movChaveRoomDatasource.save(roomModel)
            if (resultSave.isFailure) {
                val e = resultSave.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovChaveRepository.save",
                    message = e.message,
                    cause = e
                )
            }
            val id = resultSave.getOrNull()!!.toInt()
            if (id == 0) {
                return resultFailure(
                    context = "IMovChaveRepository.save",
                    message = "-",
                    cause = Exception("Id is 0")
                )
            }
            val resultClean = movChaveSharedPreferencesDatasource.clean()
            if (resultClean.isFailure) {
                val e = resultClean.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovChaveRepository.save",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(id)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovChaveRepository.save",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun send(
        list: List<MovChave>,
        number: Long,
        token: String
    ): Result<List<MovChave>> {
        try {
            val result = movChaveRetrofitDatasource.send(
                list = list.map {
                    it.entityToRetrofitModelOutput(number)
                },
                token = token
            )
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovChaveRepository.send",
                    message = e.message,
                    cause = e
                )
            }
            val retrofitModelInputList = result.getOrNull()!!
            val entityInputList = retrofitModelInputList.map {
                it.retrofitModelInputToEntity()
            }
            return Result.success(entityInputList)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovChaveRepository.send",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setClose(id: Int): Result<Boolean> {
        val result = movChaveRoomDatasource.setClose(id)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMovChaveRepository.setClose",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun setIdChave(
        idChave: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        try {
            val result =
                when (flowApp) {
                    FlowApp.ADD -> movChaveSharedPreferencesDatasource.setIdChave(idChave)
                    FlowApp.CHANGE -> movChaveRoomDatasource.setIdChave(idChave, id)
                }
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovChaveRepository.setIdChave",
                    message = e.message,
                    cause = e
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovChaveRepository.setIdChave",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setMatricColab(
        matricColab: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        try {
            val result =
                when (flowApp) {
                    FlowApp.ADD -> movChaveSharedPreferencesDatasource.setMatricColab(matricColab)
                    FlowApp.CHANGE -> movChaveRoomDatasource.setMatricColab(matricColab, id)
                }
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovChaveRepository.setMatricColab",
                    message = e.message,
                    cause = e
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovChaveRepository.setMatricColab",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setObserv(
        observ: String?,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        try {
            val result =
                when (flowApp) {
                    FlowApp.ADD -> movChaveSharedPreferencesDatasource.setObserv(observ)
                    FlowApp.CHANGE -> movChaveRoomDatasource.setObserv(observ, id)
                }
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovChaveRepository.setObserv",
                    message = e.message,
                    cause = e
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovChaveRepository.setObserv",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setOutside(id: Int): Result<Boolean> {
        val result = movChaveRoomDatasource.setOutside(id)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMovChaveRepository.setOutside",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun setSent(list: List<MovChave>): Result<Boolean> {
        try {
            for (entity in list) {
                val result = movChaveRoomDatasource.setSent(entity.idMovChave!!)
                if (result.isFailure) {
                    val e = result.exceptionOrNull()!!
                    return resultFailure(
                        context = "IMovChaveRepository.setSent",
                        message = e.message,
                        cause = e
                    )
                }
            }
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovChaveRepository.setSent",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun start(): Result<Boolean> {
        val result = movChaveSharedPreferencesDatasource.start()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMovChaveRepository.start(INSIDE)",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun start(movChave: MovChave): Result<Boolean> {
        try {
            val sharedPreferenceModel = movChave.entityToSharedPreferencesModel()
            val result = movChaveSharedPreferencesDatasource.start(sharedPreferenceModel)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovChaveRepository.start(OUTSIDE)",
                    message = e.message,
                    cause = e
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovChaveRepository.start(OUTSIDE)",
                message = "-",
                cause = e
            )
        }
    }

}