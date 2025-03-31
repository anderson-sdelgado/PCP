package br.com.usinasantafe.pcp.infra.repositories.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovChaveEquip
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveEquipRepository
import br.com.usinasantafe.pcp.infra.datasource.retrofit.variable.MovChaveEquipRetrofitDatasource
import br.com.usinasantafe.pcp.infra.datasource.room.variable.MovChaveEquipRoomDatasource
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.MovChaveEquipSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.entityToRetrofitModelOutput
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.retrofitModelInputToEntity
import br.com.usinasantafe.pcp.infra.models.room.variable.entityToRoomModel
import br.com.usinasantafe.pcp.infra.models.room.variable.roomModelToEntity
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.entityToSharedPreferencesModel
import br.com.usinasantafe.pcp.utils.FlowApp

class IMovChaveEquipRepository(
    private val movChaveEquipRoomDatasource: MovChaveEquipRoomDatasource,
    private val movChaveEquipSharedPreferencesDatasource: MovChaveEquipSharedPreferencesDatasource,
    private val movChaveEquipRetrofitDatasource: MovChaveEquipRetrofitDatasource
): MovChaveEquipRepository {

    override suspend fun checkOpen(): Result<Boolean> {
        val result = movChaveEquipRoomDatasource.checkOpen()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMovChaveEquipRepository.checkOpen",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun checkSend(): Result<Boolean> {
        val result = movChaveEquipRoomDatasource.checkSend()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMovChaveEquipRepository.checkSend",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun get(id: Int): Result<MovChaveEquip> {
        try {
            val result = movChaveEquipRoomDatasource.get(id)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovChaveEquipRepository.get",
                    message = e.message,
                    cause = e
                )
            }
            val entity = result.getOrNull()!!.roomModelToEntity()
            return Result.success(entity)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovChaveEquipRepository.get",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getMatricColab(id: Int): Result<Int> {
        try {
            val result = movChaveEquipRoomDatasource.get(id)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovChaveEquipRepository.getMatricColab",
                    message = e.message,
                    cause = e
                )
            }
            val entity = result.getOrNull()!!.roomModelToEntity()
            return Result.success(entity.matricColabMovChaveEquip!!)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovChaveEquipRepository.getMatricColab",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getIdEquip(id: Int): Result<Int> {
        try {
            val result = movChaveEquipRoomDatasource.get(id)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovChaveEquipRepository.getIdEquip",
                    message = e.message,
                    cause = e
                )
            }
            val entity = result.getOrNull()!!.roomModelToEntity()
            return Result.success(entity.idEquipMovChaveEquip!!)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovChaveEquipRepository.getIdEquip",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getObserv(id: Int): Result<String?> {
        try {
            val result = movChaveEquipRoomDatasource.get(id)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovChaveEquipRepository.getObserv",
                    message = e.message,
                    cause = e
                )
            }
            val entity = result.getOrNull()!!.roomModelToEntity()
            return Result.success(entity.observMovChaveEquip)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovChaveEquipRepository.getObserv",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun listInside(): Result<List<MovChaveEquip>> {
        try {
            val result = movChaveEquipRoomDatasource.listInside()
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovChaveEquipRepository.listInside",
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
                context = "IMovChaveEquipRepository.listInside",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun listOpen(): Result<List<MovChaveEquip>> {
        try {
            val result = movChaveEquipRoomDatasource.listOpen()
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovChaveEquipRepository.listOpen",
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
                context = "IMovChaveEquipRepository.listOpen",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun listSend(): Result<List<MovChaveEquip>> {
        try {
            val result = movChaveEquipRoomDatasource.listSend()
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovChaveEquipRepository.listSend",
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
                context = "IMovChaveEquipRepository.listSend",
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
            val resultGetMov = movChaveEquipSharedPreferencesDatasource.get()
            if (resultGetMov.isFailure) {
                val e = resultGetMov.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovChaveEquipRepository.save",
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
            val resultSave = movChaveEquipRoomDatasource.save(roomModel)
            if (resultSave.isFailure) {
                val e = resultSave.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovChaveEquipRepository.save",
                    message = e.message,
                    cause = e
                )
            }
            val id = resultSave.getOrNull()!!.toInt()
            if (id == 0) {
                return resultFailure(
                    context = "IMovChaveEquipRepository.save",
                    message = "-",
                    cause = Exception("Id is 0")
                )
            }
            val resultClean = movChaveEquipSharedPreferencesDatasource.clean()
            if (resultClean.isFailure) {
                val e = resultClean.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovChaveEquipRepository.save",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(id)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovChaveEquipRepository.save",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun send(
        list: List<MovChaveEquip>,
        number: Long,
        token: String
    ): Result<List<MovChaveEquip>> {
        try {
            val result = movChaveEquipRetrofitDatasource.send(
                list = list.map {
                    it.entityToRetrofitModelOutput(number)
                },
                token = token
            )
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovChaveEquipRepository.send",
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
                context = "IMovChaveEquipRepository.send",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setClose(id: Int): Result<Boolean> {
        val result = movChaveEquipRoomDatasource.setClose(id)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMovChaveEquipRepository.setClose",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun setIdEquip(
        idEquip: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        try {
            val result =
                when (flowApp) {
                    FlowApp.ADD -> movChaveEquipSharedPreferencesDatasource.setIdEquip(idEquip)
                    FlowApp.CHANGE -> movChaveEquipRoomDatasource.setIdEquip(idEquip, id)
                }
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovChaveEquipRepository.setIdEquip",
                    message = e.message,
                    cause = e
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovChaveEquipRepository.setIdEquip",
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
                    FlowApp.ADD -> movChaveEquipSharedPreferencesDatasource.setMatricColab(matricColab)
                    FlowApp.CHANGE -> movChaveEquipRoomDatasource.setMatricColab(matricColab, id)
                }
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovChaveEquipRepository.setMatricColab",
                    message = e.message,
                    cause = e
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovChaveEquipRepository.setMatricColab",
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
                    FlowApp.ADD -> movChaveEquipSharedPreferencesDatasource.setObserv(observ)
                    FlowApp.CHANGE -> movChaveEquipRoomDatasource.setObserv(observ, id)
                }
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovChaveEquipRepository.setObserv",
                    message = e.message,
                    cause = e
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovChaveEquipRepository.setObserv",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setOutside(id: Int): Result<Boolean> {
        val result = movChaveEquipRoomDatasource.setOutside(id)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMovChaveEquipRepository.setOutside",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun setSent(list: List<MovChaveEquip>): Result<Boolean> {
        try {
            for (entity in list) {
                val result = movChaveEquipRoomDatasource.setSent(entity.idMovChaveEquip!!)
                if (result.isFailure) {
                    val e = result.exceptionOrNull()!!
                    return resultFailure(
                        context = "IMovChaveEquipRepository.setSent",
                        message = e.message,
                        cause = e
                    )
                }
            }
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovChaveEquipRepository.setSent",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun start(): Result<Boolean> {
        val result = movChaveEquipSharedPreferencesDatasource.start()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMovChaveEquipRepository.start(INSIDE)",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun start(movChaveEquip: MovChaveEquip): Result<Boolean> {
        try {
            val sharedPreferenceModel = movChaveEquip.entityToSharedPreferencesModel()
            val result = movChaveEquipSharedPreferencesDatasource.start(sharedPreferenceModel)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovChaveEquipRepository.start(OUTSIDE)",
                    message = e.message,
                    cause = e
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovChaveEquipRepository.start(OUTSIDE)",
                message = "-",
                cause = e
            )
        }
    }
}