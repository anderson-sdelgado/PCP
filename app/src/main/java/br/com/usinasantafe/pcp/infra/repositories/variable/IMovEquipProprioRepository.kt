package br.com.usinasantafe.pcp.infra.repositories.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipProprio
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcp.infra.datasource.room.variable.MovEquipProprioRoomDatasource
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.MovEquipProprioSharedPreferencesDatasource
import br.com.usinasantafe.pcp.infra.datasource.retrofit.variable.MovEquipProprioRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.room.variable.entityToRoomModel
import br.com.usinasantafe.pcp.infra.models.room.variable.roomModelToEntity
import br.com.usinasantafe.pcp.infra.models.sharedpreferences.entityToSharedPreferencesModel
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.entityToRetrofitModelOutput
import br.com.usinasantafe.pcp.infra.models.retrofit.variable.retrofitModelInputToEntity
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeMovEquip

class IMovEquipProprioRepository(
    private val movEquipProprioSharedPreferencesDatasource: MovEquipProprioSharedPreferencesDatasource,
    private val movEquipProprioRoomDatasource: MovEquipProprioRoomDatasource,
    private val movEquipProprioRetrofitDatasource: MovEquipProprioRetrofitDatasource,
) : MovEquipProprioRepository {

    override suspend fun checkOpen(): Result<Boolean> {
        val result = movEquipProprioRoomDatasource.checkOpen()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMovEquipProprioRepository.checkOpen",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun checkSend(): Result<Boolean> {
        val result = movEquipProprioRoomDatasource.checkSend()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMovEquipProprioRepository.checkSend",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun delete(id: Int): Result<Boolean> {
        try {
            val resultGet = movEquipProprioRoomDatasource.get(id)
            if (resultGet.isFailure) {
                val e = resultGet.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipProprioRepository.delete",
                    message = e.message,
                    cause = e
                )
            }
            val model = resultGet.getOrNull()!!
            val resultDelete = movEquipProprioRoomDatasource.delete(model)
            if (resultDelete.isFailure) {
                val e = resultDelete.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipProprioRepository.delete",
                    message = e.message,
                    cause = e
                )
            }
            return resultDelete
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipProprioRepository.delete",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun get(id: Int): Result<MovEquipProprio> {
        try {
            val result = movEquipProprioRoomDatasource.get(id)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipProprioRepository.get",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(result.getOrNull()!!.roomModelToEntity())
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipProprioRepository.delete",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getDestino(id: Int): Result<String> {
        try {
            val result = movEquipProprioRoomDatasource.get(id)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipProprioRepository.getDestino",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(result.getOrNull()!!.roomModelToEntity().destinoMovEquipProprio!!)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipProprioRepository.getDestino",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getTipoMov(): Result<TypeMovEquip> {
        try {
            val result = movEquipProprioSharedPreferencesDatasource.get()
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipProprioRepository.getTipoMov",
                    message = e.message,
                    cause = e
                )
            }
            val type = result.getOrNull()!!.tipoMovEquipProprio
            return Result.success(type)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipProprioRepository.getTipoMov",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getIdEquip(id: Int): Result<Int> {
        try {
            val result = movEquipProprioRoomDatasource.get(id)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipProprioRepository.getIdEquip",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(result.getOrNull()!!.roomModelToEntity().idEquipMovEquipProprio!!)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipProprioRepository.getIdEquip",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getObserv(id: Int): Result<String?> {
        try {
            val result = movEquipProprioRoomDatasource.get(id)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipProprioRepository.getObserv",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(result.getOrNull()!!.roomModelToEntity().observMovEquipProprio)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipProprioRepository.getObserv",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getMatricColab(id: Int): Result<Int> {
        try {
            val result = movEquipProprioRoomDatasource.get(id)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipProprioRepository.getMatricColab",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(result.getOrNull()!!.roomModelToEntity().matricColabMovEquipProprio!!)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipProprioRepository.getMatricColab",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getNotaFiscal(id: Int): Result<Int?> {
        try {
            val result = movEquipProprioRoomDatasource.get(id)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipProprioRepository.getNotaFiscal",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(result.getOrNull()!!.roomModelToEntity().notaFiscalMovEquipProprio)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipProprioRepository.getNotaFiscal",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun listOpen(): Result<List<MovEquipProprio>> {
        try {
            val result = movEquipProprioRoomDatasource.listOpen()
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipProprioRepository.listOpen",
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
                context = "IMovEquipProprioRepository.listOpen",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun listSend(): Result<List<MovEquipProprio>> {
        try {
            val result =
                movEquipProprioRoomDatasource.listSend()
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipProprioRepository.listSend",
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
                context = "IMovEquipProprioRepository.listSend",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun listSent(): Result<List<MovEquipProprio>> {
        try {
            val result =
                movEquipProprioRoomDatasource.listSent()
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipProprioRepository.listSent",
                    message = e.message,
                    cause = e
                )
            }
            val listSent = result.getOrNull()!!.map {
                it.roomModelToEntity()
            }
            return Result.success(listSent)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipProprioRepository.listSent",
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
                movEquipProprioSharedPreferencesDatasource.get()
            if (resultGetMov.isFailure) {
                val e = resultGetMov.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipProprioRepository.save",
                    message = e.message,
                    cause = e
                )
            }
            val movEquipProprioRoomModel =
                resultGetMov.getOrNull()!!.entityToSharedPreferencesModel()
                    .entityToRoomModel(matricVigia, idLocal)
            val resultSave = movEquipProprioRoomDatasource.save(movEquipProprioRoomModel)
            if (resultSave.isFailure) {
                val e = resultSave.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipProprioRepository.save",
                    message = e.message,
                    cause = e
                )
            }
            val id = resultSave.getOrNull()!!.toInt()
            if (id == 0) {
                return resultFailure(
                    context = "IMovEquipProprioRepository.save",
                    message = "-",
                    cause = Exception("Id is 0")
                )
            }
            val resultClear = movEquipProprioSharedPreferencesDatasource.clean()
            if (resultClear.isFailure) {
                val e = resultClear.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipProprioRepository.save",
                    message = e.message,
                    cause = e
                )
            }
            return Result.success(id)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipProprioRepository.save",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun send(
        list: List<MovEquipProprio>,
        number: Long,
        token: String
    ): Result<List<MovEquipProprio>> {
        try {
            val result = movEquipProprioRetrofitDatasource.send(
                list = list.map { it.entityToRetrofitModelOutput(number) },
                token = token
            )
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipProprioRepository.send",
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
                context = "IMovEquipProprioRepository.send",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setClose(id: Int): Result<Boolean> {
        val result = movEquipProprioRoomDatasource.setClose(id)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMovEquipProprioRepository.setClose",
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
                    FlowApp.ADD -> movEquipProprioSharedPreferencesDatasource.setDestino(destino)
                    FlowApp.CHANGE -> movEquipProprioRoomDatasource.setDestino(destino, id)
                }
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipProprioRepository.setDestino",
                    message = e.message,
                    cause = e
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipProprioRepository.setDestino",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setIdEquip(
        idEquip: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        try {
            val result =
                when (flowApp) {
                    FlowApp.ADD -> movEquipProprioSharedPreferencesDatasource.setIdEquip(idEquip)
                    FlowApp.CHANGE -> movEquipProprioRoomDatasource.setIdEquip(idEquip, id)
                }
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipProprioRepository.setIdEquip",
                    message = e.message,
                    cause = e
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipProprioRepository.setIdEquip",
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
                    FlowApp.ADD -> movEquipProprioSharedPreferencesDatasource.setMatricColab(matricColab)
                    FlowApp.CHANGE -> movEquipProprioRoomDatasource.setMatricColab(matricColab, id)
                }
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipProprioRepository.setMatricColab",
                    message = e.message,
                    cause = e
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipProprioRepository.setMatricColab",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setNotaFiscal(
        notaFiscal: Int?,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        try {
            val result =
                when (flowApp) {
                    FlowApp.ADD -> movEquipProprioSharedPreferencesDatasource.setNotaFiscal(notaFiscal)
                    FlowApp.CHANGE -> movEquipProprioRoomDatasource.setNotaFiscal(notaFiscal, id)
                }
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipProprioRepository.setNotaFiscal",
                    message = e.message,
                    cause = e
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipProprioRepository.setNotaFiscal",
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
                    FlowApp.ADD -> movEquipProprioSharedPreferencesDatasource.setObserv(observ)
                    FlowApp.CHANGE -> movEquipProprioRoomDatasource.setObserv(observ, id)
                }
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMovEquipProprioRepository.setObserv",
                    message = e.message,
                    cause = e
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipProprioRepository.setObserv",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setSent(list: List<MovEquipProprio>): Result<Boolean> {
        try {
            for (entity in list) {
                val result =
                    movEquipProprioRoomDatasource.setSent(entity.idMovEquipProprio!!)
                if (result.isFailure) {
                    val e = result.exceptionOrNull()!!
                    return resultFailure(
                        context = "IMovEquipProprioRepository.setSent",
                        message = e.message,
                        cause = e
                    )
                }
            }
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMovEquipProprioRepository.setSent",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setSend(id: Int): Result<Boolean> {
        val result = movEquipProprioRoomDatasource.setSend(id)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMovEquipProprioRepository.setSend",
                message = e.message,
                cause = e
            )
        }
        return result
    }

    override suspend fun start(typeMov: TypeMovEquip): Result<Boolean> {
        val result = movEquipProprioSharedPreferencesDatasource.start(typeMov)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMovEquipProprioRepository.start",
                message = e.message,
                cause = e
            )
        }
        return result
    }

}