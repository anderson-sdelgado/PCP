package br.com.usinasantafe.pcp.infra.repositories.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipResidencia
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcp.infra.datasource.retrofit.variable.MovEquipResidenciaRetrofitDatasource
import br.com.usinasantafe.pcp.infra.datasource.room.variable.MovEquipResidenciaRoomDatasource
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.MovEquipResidenciaSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.entityToRetrofitModelOutput
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.retrofitModelInputToEntity
import br.com.usinasantafe.pcp.infra.models.room.variable.entityToRoomModel
import br.com.usinasantafe.pcp.infra.models.room.variable.roomModelToEntity
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.entityToSharedPreferencesModel
import br.com.usinasantafe.pcp.utils.FlowApp

class IMovEquipResidenciaRepository(
    private val movEquipResidenciaSharedPreferencesDatasource: MovEquipResidenciaSharedPreferencesDatasource,
    private val movEquipResidenciaRoomDatasource: MovEquipResidenciaRoomDatasource,
    private val movEquipResidenciaRetrofitDatasource: MovEquipResidenciaRetrofitDatasource
) : MovEquipResidenciaRepository {

    override suspend fun checkOpen(): Result<Boolean> {
        val result = movEquipResidenciaRoomDatasource.checkOpen()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMovEquipResidenciaRepository.checkOpen",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun checkSend(): Result<Boolean> {
        val result = movEquipResidenciaRoomDatasource.checkSend()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMovEquipResidenciaRepository.checkSend",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun delete(id: Int): Result<Boolean> {
        try {
            val result = movEquipResidenciaRoomDatasource.get(id)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipResidenciaRepository.delete",
                    message = e.message,
                    cause = e
                )
            }
            val model = result.getOrNull()!!
            return movEquipResidenciaRoomDatasource.delete(model)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipResidenciaRepository.delete",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun get(id: Int): Result<MovEquipResidencia> {
        try {
            val result = movEquipResidenciaRoomDatasource.get(id)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipResidenciaRepository.get",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(
                result.getOrNull()!!.roomModelToEntity()
            )
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipResidenciaRepository.get",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getMotorista(id: Int): Result<String> {
        try {
            val result = movEquipResidenciaRoomDatasource.get(id)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipResidenciaRepository.getMotorista",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(
                result.getOrNull()!!.roomModelToEntity().motoristaMovEquipResidencia!!
            )
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipResidenciaRepository.getMotorista",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getObserv(id: Int): Result<String?> {
        try {
            val result = movEquipResidenciaRoomDatasource.get(id)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipResidenciaRepository.getObserv",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(
                result.getOrNull()!!.roomModelToEntity().observMovEquipResidencia
            )
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipResidenciaRepository.getObserv",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getPlaca(id: Int): Result<String> {
        try {
            val result = movEquipResidenciaRoomDatasource.get(id)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipResidenciaRepository.getPlaca",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(
                result.getOrNull()!!.roomModelToEntity().placaMovEquipResidencia!!
            )
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipResidenciaRepository.getPlaca",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getVeiculo(id: Int): Result<String> {
        try {
            val result = movEquipResidenciaRoomDatasource.get(id)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipResidenciaRepository.getVeiculo",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(
                result.getOrNull()!!.roomModelToEntity().veiculoMovEquipResidencia!!
            )
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipResidenciaRepository.getVeiculo",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun listOpen(): Result<List<MovEquipResidencia>> {
        try {
            val result = movEquipResidenciaRoomDatasource.listOpen()
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipResidenciaRepository.listOpen",
                    message = e.message,
                    cause = e
                )
            }
            val list = result.getOrNull()!!.map {
                it.roomModelToEntity()
            }
            return Result.success(list)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipResidenciaRepository.listOpen",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun listInside(): Result<List<MovEquipResidencia>> {
        try {
            val result = movEquipResidenciaRoomDatasource.listInside()
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipResidenciaRepository.listInside",
                    message = e.message,
                    cause = e
                )
            }
            val list = result.getOrNull()!!.map {
                it.roomModelToEntity()
            }
            return Result.success(list)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipResidenciaRepository.listInside",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun listSend(): Result<List<MovEquipResidencia>> {
        try {
            val result =
                movEquipResidenciaRoomDatasource.listSend()
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipResidenciaRepository.listSend",
                    message = e.message,
                    cause = e
                )
            }
            val listSend = result.getOrNull()!!.map {
                it.roomModelToEntity()
            }
            return Result.success(listSend)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipResidenciaRepository.listSend",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun listSent(): Result<List<MovEquipResidencia>> {
        try {
            val result =
                movEquipResidenciaRoomDatasource.listSent()
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipResidenciaRepository.listSent",
                    message = e.message,
                    cause = e
                )
            }
            val listSend = result.getOrNull()!!.map {
                it.roomModelToEntity()
            }
            return Result.success(listSend)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipResidenciaRepository.listSent",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun save(
        matricVigia: Int,
        idLocal: Int
    ): Result<Int> {
        try {
            val resultGetMov = movEquipResidenciaSharedPreferencesDatasource.get()
            if (resultGetMov.isFailure) {
                val e = resultGetMov.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipResidenciaRepository.save",
                    message = e.message,
                    cause = e
                )
            }
            val movEquipResidenciaRoomModel =
                resultGetMov.getOrNull()!!.entityToSharedPreferencesModel()
                    .entityToRoomModel(
                        matricVigia = matricVigia,
                        idLocal = idLocal
                    )
            val resultSave = movEquipResidenciaRoomDatasource.save(movEquipResidenciaRoomModel)
            if (resultSave.isFailure) {
                val e = resultSave.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipResidenciaRepository.save",
                    message = e.message,
                    cause = e
                )
            }
            val id = resultSave.getOrNull()!!.toInt()
            if (id == 0) {
                return resultFailure(
                    context = "IMovEquipResidenciaRepository.save",
                    message = "-",
                    cause = Exception("Id is 0")
                )
            }
            val resultClear = movEquipResidenciaSharedPreferencesDatasource.clear()
            if (resultClear.isFailure) {
                val e = resultClear.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipResidenciaRepository.save",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(id)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipResidenciaRepository.save",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun send(
        list: List<MovEquipResidencia>,
        number: Long,
        token: String
    ): Result<List<MovEquipResidencia>> {
        try {
            val result = movEquipResidenciaRetrofitDatasource.send(
                list = list.map { it.entityToRetrofitModelOutput(number) },
                token = token
            )
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipResidenciaRepository.send",
                    message = e.message,
                    cause = e
                )
            }
            val listInput = result.getOrNull()!!
            val resultList = listInput.map {
                it.retrofitModelInputToEntity()
            }
            return Result.success(resultList)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipResidenciaRepository.send",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setClose(id: Int): Result<Boolean> {
        val result = movEquipResidenciaRoomDatasource.setClose(id)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMovEquipResidenciaRepository.setClose",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun setMotorista(
        motorista: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        try {
            val result =
                when (flowApp) {
                    FlowApp.ADD -> movEquipResidenciaSharedPreferencesDatasource.setMotorista(motorista)
                    FlowApp.CHANGE -> movEquipResidenciaRoomDatasource.setMotorista(motorista, id)
                }
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipResidenciaRepository.setMotorista",
                    message = e.message,
                    cause = e
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipResidenciaRepository.setMotorista",
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
                    FlowApp.ADD -> movEquipResidenciaSharedPreferencesDatasource.setObserv(observ)
                    FlowApp.CHANGE -> movEquipResidenciaRoomDatasource.setObserv(observ, id)
                }
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipResidenciaRepository.setObserv",
                    message = e.message,
                    cause = e
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipResidenciaRepository.setObserv",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setOutside(id: Int): Result<Boolean> {
        val result = movEquipResidenciaRoomDatasource.setOutside(id)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMovEquipResidenciaRepository.start",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun setPlaca(
        placa: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        try {
            val result =
                when (flowApp) {
                    FlowApp.ADD -> movEquipResidenciaSharedPreferencesDatasource.setPlaca(placa)
                    FlowApp.CHANGE -> movEquipResidenciaRoomDatasource.setPlaca(placa, id)
                }
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipResidenciaRepository.setPlaca",
                    message = e.message,
                    cause = e
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipResidenciaRepository.setPlaca",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setVeiculo(
        veiculo: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        try {
            val result =
                when (flowApp) {
                    FlowApp.ADD -> movEquipResidenciaSharedPreferencesDatasource.setVeiculo(veiculo)
                    FlowApp.CHANGE -> movEquipResidenciaRoomDatasource.setVeiculo(veiculo, id)
                }
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipResidenciaRepository.setVeiculo",
                    message = e.message,
                    cause = e
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipResidenciaRepository.setVeiculo",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setSent(list: List<MovEquipResidencia>): Result<Boolean> {
        try {
            for (entity in list) {
                val result =
                    movEquipResidenciaRoomDatasource.setSent(entity.idMovEquipResidencia!!)
                if (result.isFailure) {
                    val e = result.exceptionOrNull()!!
                    return resultFailure(
                        context = "IMovEquipResidenciaRepository.setSent",
                        message = e.message,
                        cause = e
                    )
                }
            }
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipResidenciaRepository.setSent",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun start(): Result<Boolean> {
        val result = movEquipResidenciaSharedPreferencesDatasource.start()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMovEquipResidenciaRepository.start(INSIDE)",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun start(movEquipResidencia: MovEquipResidencia): Result<Boolean> {
        try {
            val movEquipResidenciaSharedPreferencesModel =
                movEquipResidencia.entityToSharedPreferencesModel()
            val result = movEquipResidenciaSharedPreferencesDatasource.start(
                movEquipResidenciaSharedPreferencesModel
            )
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipResidenciaRepository.start(OUTSIDE)",
                    message = e.message,
                    cause = e
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipResidenciaRepository.start",
                message = "-",
                cause = e
            )
        }
    }

}