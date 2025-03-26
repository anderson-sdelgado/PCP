package br.com.usinasantafe.pcp.infra.repositories.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipVisitTerc
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcp.infra.datasource.retrofit.variable.MovEquipVisitTercRetrofitDatasource
import br.com.usinasantafe.pcp.infra.datasource.room.variable.MovEquipVisitTercRoomDatasource
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.MovEquipVisitTercSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.entityToRetrofitModelOutput
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.retrofitModelInputToEntity
import br.com.usinasantafe.pcp.infra.models.room.variable.entityToRoomModel
import br.com.usinasantafe.pcp.infra.models.room.variable.roomModelToEntity
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.entityToSharedPreferencesModel
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeVisitTerc

class IMovEquipVisitTercRepository(
    private val movEquipVisitTercSharedPreferencesDatasource: MovEquipVisitTercSharedPreferencesDatasource,
    private val movEquipVisitTercRoomDatasource: MovEquipVisitTercRoomDatasource,
    private val movEquipVisitTercRetrofitDatasource: MovEquipVisitTercRetrofitDatasource,
) : MovEquipVisitTercRepository {

    override suspend fun checkOpen(): Result<Boolean> {
        val result = movEquipVisitTercRoomDatasource.checkOpen()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMovEquipVisitTercRepository.checkOpen",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun checkSend(): Result<Boolean> {
        val result = movEquipVisitTercRoomDatasource.checkSend()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMovEquipVisitTercRepository.checkOpen",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun delete(id: Int): Result<Boolean> {
        try {
            val result = movEquipVisitTercRoomDatasource.get(id)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipVisitTercRepository.delete",
                    message = e.message,
                    cause = e
                )
            }
            val model = result.getOrNull()!!
            return movEquipVisitTercRoomDatasource.delete(model)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipVisitTercRepository.delete",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun get(id: Int): Result<MovEquipVisitTerc> {
        try {
            val result = movEquipVisitTercRoomDatasource.get(id)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipVisitTercRepository.get",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(result.getOrNull()!!.roomModelToEntity())
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipVisitTercRepository.get",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getDestino(id: Int): Result<String> {
        try {
            val result = movEquipVisitTercRoomDatasource.get(id)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipVisitTercRepository.getDestino",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(
                result.getOrNull()!!.roomModelToEntity().destinoMovEquipVisitTerc!!
            )
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipVisitTercRepository.getDestino",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getIdVisitTerc(id: Int): Result<Int> {
        try {
            val result = movEquipVisitTercRoomDatasource.get(id)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipVisitTercRepository.getIdVisitTerc",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(
                result.getOrNull()!!.roomModelToEntity().idVisitTercMovEquipVisitTerc!!
            )
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipVisitTercRepository.getIdVisitTerc",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getObserv(id: Int): Result<String?> {
        try {
            val result = movEquipVisitTercRoomDatasource.get(id)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipVisitTercRepository.getObserv",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(
                result.getOrNull()!!.roomModelToEntity().observMovEquipVisitTerc
            )
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipVisitTercRepository.getObserv",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getPlaca(id: Int): Result<String> {
        try {
            val result = movEquipVisitTercRoomDatasource.get(id)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipVisitTercRepository.getPlaca",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(
                result.getOrNull()!!.roomModelToEntity().placaMovEquipVisitTerc!!
            )
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipVisitTercRepository.getPlaca",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getTypeVisitTerc(
        flowApp: FlowApp,
        id: Int
    ): Result<TypeVisitTerc> {
        when (flowApp) {
            FlowApp.ADD -> {
                val result = movEquipVisitTercSharedPreferencesDatasource.get()
                if (result.isFailure) {
                    val e = result.exceptionOrNull()!!
                    return resultFailure(
                        context = "IMovEquipVisitTercRepository.getTypeVisitTerc",
                        message = e.message,
                        cause = e
                    )
                }
                return Result.success(result.getOrNull()!!.tipoVisitTercMovEquipVisitTerc!!)
            }

            FlowApp.CHANGE -> {
                val result = movEquipVisitTercRoomDatasource.get(id)
                if (result.isFailure) {
                    val e = result.exceptionOrNull()!!
                    return resultFailure(
                        context = "IMovEquipVisitTercRepository.getTypeVisitTerc",
                        message = e.message,
                        cause = e
                    )
                }
                return Result.success(result.getOrNull()!!.tipoVisitTercMovEquipVisitTerc)
            }
        }
    }

    override suspend fun getVeiculo(id: Int): Result<String> {
        try {
            val result = movEquipVisitTercRoomDatasource.get(id)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipVisitTercRepository.getVeiculo",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(
                result.getOrNull()!!.roomModelToEntity().veiculoMovEquipVisitTerc!!
            )
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipVisitTercRepository.getVeiculo",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun listOpen(): Result<List<MovEquipVisitTerc>> {
        try {
            val result = movEquipVisitTercRoomDatasource.listOpen()
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipVisitTercRepository.listOpen",
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
                context = "IMovEquipVisitTercRepository.listOpen",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun listInside(): Result<List<MovEquipVisitTerc>> {
        try {
            val result =
                movEquipVisitTercRoomDatasource.listInside()
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipVisitTercRepository.listInside",
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
                context = "IMovEquipVisitTercRepository.listInside",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun listSend(): Result<List<MovEquipVisitTerc>> {
        try {
            val result =
                movEquipVisitTercRoomDatasource.listSend()
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipVisitTercRepository.listSend",
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
                context = "IMovEquipVisitTercRepository.listSend",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun listSent(): Result<List<MovEquipVisitTerc>> {
        try {
            val result =
                movEquipVisitTercRoomDatasource.listSent()
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipVisitTercRepository.listSent",
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
                context = "IMovEquipVisitTercRepository.listSent",
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
            val resultGetMov =
                movEquipVisitTercSharedPreferencesDatasource.get()
            if (resultGetMov.isFailure) {
                val e = resultGetMov.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipVisitTercRepository.save",
                    message = e.message,
                    cause = e
                )
            }
            val movEquipVisitTercRoomModel =
                resultGetMov.getOrNull()!!.entityToSharedPreferencesModel()
                    .entityToRoomModel(matricVigia, idLocal)
            val resultSave = movEquipVisitTercRoomDatasource.save(movEquipVisitTercRoomModel)
            if (resultSave.isFailure) {
                val e = resultSave.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipVisitTercRepository.save",
                    message = e.message,
                    cause = e
                )
            }
            val id = resultSave.getOrNull()!!.toInt()
            if (id == 0) {
                return resultFailure(
                    context = "IMovEquipVisitTercRepository.save",
                    message = "-",
                    cause = Exception("Id is 0")
                )
            }
            val resultClear = movEquipVisitTercSharedPreferencesDatasource.clear()
            if (resultClear.isFailure) {
                val e = resultClear.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipVisitTercRepository.save",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(id)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipVisitTercRepository.save",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun send(
        list: List<MovEquipVisitTerc>,
        number: Long,
        token: String
    ): Result<List<MovEquipVisitTerc>> {
        try {
            val result = movEquipVisitTercRetrofitDatasource.send(
                list = list.map { it.entityToRetrofitModelOutput(number) },
                token = token
            )
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipVisitTercRepository.send",
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
                context = "IMovEquipVisitTercRepository.send",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setClose(id: Int): Result<Boolean> {
        val result = movEquipVisitTercRoomDatasource.setClose(id)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMovEquipVisitTercRepository.setClose",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun setDestino(
        destino: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        try {
            val result =
                when (flowApp) {
                    FlowApp.ADD -> movEquipVisitTercSharedPreferencesDatasource.setDestino(destino)
                    FlowApp.CHANGE -> movEquipVisitTercRoomDatasource.setDestino(destino, id)
                }
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipVisitTercRepository.setClose",
                    message = e.message,
                    cause = e
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipVisitTercRepository.setClose",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setIdVisitTerc(
        idVisitTerc: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        try {
            val result =
                when (flowApp) {
                    FlowApp.ADD -> movEquipVisitTercSharedPreferencesDatasource.setIdVisitTerc(
                        idVisitTerc
                    )

                    FlowApp.CHANGE -> movEquipVisitTercRoomDatasource.setIdVisitTerc(idVisitTerc, id)
                }
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipVisitTercRepository.setIdVisitTerc",
                    message = e.message,
                    cause = e
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipVisitTercRepository.setIdVisitTerc",
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
                FlowApp.ADD -> movEquipVisitTercSharedPreferencesDatasource.setObserv(observ)
                FlowApp.CHANGE -> movEquipVisitTercRoomDatasource.setObserv(observ, id)
            }
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipVisitTercRepository.setObserv",
                    message = e.message,
                    cause = e
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipVisitTercRepository.setObserv",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setOutside(id: Int): Result<Boolean> {
        val result = movEquipVisitTercRoomDatasource.setOutside(id)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMovEquipVisitTercRepository.setOutside",
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
                    FlowApp.ADD -> movEquipVisitTercSharedPreferencesDatasource.setPlaca(placa)
                    FlowApp.CHANGE -> movEquipVisitTercRoomDatasource.setPlaca(placa, id)
                }
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipVisitTercRepository.setPlaca",
                    message = e.message,
                    cause = e
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipVisitTercRepository.setPlaca",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setTipoVisitTerc(typeVisitTerc: TypeVisitTerc): Result<Boolean> {
        val result = movEquipVisitTercSharedPreferencesDatasource.setTipoVisitTerc(typeVisitTerc)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMovEquipVisitTercRepository.setTipoVisitTerc",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun setVeiculo(
        veiculo: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        try {
            val result =
                when (flowApp) {
                    FlowApp.ADD -> movEquipVisitTercSharedPreferencesDatasource.setVeiculo(veiculo)
                    FlowApp.CHANGE -> movEquipVisitTercRoomDatasource.setVeiculo(veiculo, id)
                }
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipVisitTercRepository.setVeiculo",
                    message = e.message,
                    cause = e
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipVisitTercRepository.setVeiculo",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setSent(list: List<MovEquipVisitTerc>): Result<Boolean> {
        try {
            for (entity in list) {
                val result =
                    movEquipVisitTercRoomDatasource.setSent(entity.idMovEquipVisitTerc!!)
                if (result.isFailure) {
                    val e = result.exceptionOrNull()!!
                    return resultFailure(
                        context = "IMovEquipVisitTercRepository.setSent",
                        message = e.message,
                        cause = e
                    )
                }
            }
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipVisitTercRepository.setSent",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setSend(id: Int): Result<Boolean> {
        val result = movEquipVisitTercRoomDatasource.setSend(id)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMovEquipVisitTercRepository.setSend",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun start(): Result<Boolean> {
        val result = movEquipVisitTercSharedPreferencesDatasource.start()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMovEquipVisitTercRepository.start(INSIDE)",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun start(movEquipVisitTerc: MovEquipVisitTerc): Result<Boolean> {
        try {
            val movEquipVisitTercSharedPreferencesModel =
                movEquipVisitTerc.entityToSharedPreferencesModel()
            val result = movEquipVisitTercSharedPreferencesDatasource.start(
                movEquipVisitTercSharedPreferencesModel
            )
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipVisitTercRepository.start(OUTSIDE)",
                    message = e.message,
                    cause = e
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipVisitTercRepository.start(OUTSIDE)",
                message = "-",
                cause = e
            )
        }
    }


}