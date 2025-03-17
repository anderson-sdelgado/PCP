package br.com.usinasantafe.pcp.infra.repositories.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovChave
import br.com.usinasantafe.pcp.domain.errors.RepositoryException
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
        return movChaveRoomDatasource.checkOpen()
    }

    override suspend fun checkSend(): Result<Boolean> {
        return movChaveRoomDatasource.checkSend()
    }

    override suspend fun get(id: Int): Result<MovChave> {
        try {
            val resultGet = movChaveRoomDatasource.get(id)
            if(resultGet.isFailure){
                return Result.failure(
                    resultGet.exceptionOrNull()!!
                )
            }
            val entity = resultGet.getOrNull()!!.roomModelToEntity()
            return Result.success(entity)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "IMovChaveRepository.get",
                    cause = e
                )
            )
        }
    }

    override suspend fun getMatricColab(id: Int): Result<Int> {
        try {
            val resultGet = movChaveRoomDatasource.get(id)
            if(resultGet.isFailure){
                return Result.failure(
                    resultGet.exceptionOrNull()!!
                )
            }
            val entity = resultGet.getOrNull()!!.roomModelToEntity()
            return Result.success(entity.matricColabMovChave!!)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "IMovChaveRepository.getMatricColab",
                    cause = e
                )
            )
        }
    }

    override suspend fun getObserv(id: Int): Result<String?> {
        try {
            val resultGet = movChaveRoomDatasource.get(id)
            if(resultGet.isFailure){
                return Result.failure(
                    resultGet.exceptionOrNull()!!
                )
            }
            val entity = resultGet.getOrNull()!!.roomModelToEntity()
            return Result.success(entity.observMovChave)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "IMovChaveRepository.getObserv",
                    cause = e
                )
            )
        }
    }

    override suspend fun listInside(): Result<List<MovChave>> {
        try {
            val resultList = movChaveRoomDatasource.listInside()
            if(resultList.isFailure){
                return Result.failure(
                    resultList.exceptionOrNull()!!
                )
            }
            val entityList = resultList.getOrNull()!!.map {
                it.roomModelToEntity()
            }
            return Result.success(entityList)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "IMovChaveRepository.listInside",
                    cause = e
                )
            )
        }
    }

    override suspend fun listOpen(): Result<List<MovChave>> {
        try {
            val resultList = movChaveRoomDatasource.listOpen()
            if(resultList.isFailure){
                return Result.failure(
                    resultList.exceptionOrNull()!!
                )
            }
            val entityList = resultList.getOrNull()!!.map {
                it.roomModelToEntity()
            }
            return Result.success(entityList)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "IMovChaveRepository.listOpen",
                    cause = e
                )
            )
        }
    }

    override suspend fun listSend(): Result<List<MovChave>> {
        try {
            val resultListSend = movChaveRoomDatasource.listSend()
            if(resultListSend.isFailure){
                return Result.failure(
                    resultListSend.exceptionOrNull()!!
                )
            }
            val entityListSend = resultListSend.getOrNull()!!.map {
                it.roomModelToEntity()
            }
            return Result.success(entityListSend)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "IMovChaveRepository.listSend",
                    cause = e
                )
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
            if (resultGetMov.isFailure)
                return Result.failure(resultGetMov.exceptionOrNull()!!)
            val roomModel = resultGetMov.getOrNull()!!
                .entityToSharedPreferencesModel()
                .entityToRoomModel(
                    matricVigia = matricVigia,
                    idLocal = idLocal,
                    uuid = uuid
                )
            val resultSave = movChaveRoomDatasource.save(roomModel)
            if (resultSave.isFailure)
                return Result.failure(resultSave.exceptionOrNull()!!)
            val id = resultSave.getOrNull()!!.toInt()
            if (id == 0) {
                return Result.failure(
                    RepositoryException(
                        function = "IMovChaveRepository.save",
                        cause = Exception("Id is 0")
                    )
                )
            }
            val resultClear = movChaveSharedPreferencesDatasource.clear()
            if (resultClear.isFailure)
                return Result.failure(resultClear.exceptionOrNull()!!)
            return Result.success(id)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "IMovChaveRepository.save",
                    cause = e
                )
            )
        }
    }

    override suspend fun send(
        list: List<MovChave>,
        number: Long,
        token: String
    ): Result<List<MovChave>> {
        try {
            val resultList = movChaveRetrofitDatasource.send(
                list = list.map {
                    it.entityToRetrofitModelOutput(number)
                },
                token = token
            )
            if (resultList.isFailure)
                return Result.failure(resultList.exceptionOrNull()!!)
            val retrofitModelInputList = resultList.getOrNull()!!
            val entityInputList = retrofitModelInputList.map {
                it.retrofitModelInputToEntity()
            }
            return Result.success(entityInputList)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "IMovChaveRepository.send",
                    cause = e
                )
            )
        }
    }

    override suspend fun setClose(id: Int): Result<Boolean> {
        return movChaveRoomDatasource.setClose(id)
    }

    override suspend fun setIdChave(
        idChave: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        return try {
            when (flowApp) {
                FlowApp.ADD -> movChaveSharedPreferencesDatasource.setIdChave(idChave)
                FlowApp.CHANGE -> movChaveRoomDatasource.setIdChave(idChave, id)
            }
        } catch (e: Exception) {
            Result.failure(
                RepositoryException(
                    function = "IMovChaveRepository.setIdChave",
                    cause = e
                )
            )
        }
    }

    override suspend fun setMatricColab(
        matricColab: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        return try {
            when (flowApp) {
                FlowApp.ADD -> movChaveSharedPreferencesDatasource.setMatricColab(matricColab)
                FlowApp.CHANGE -> movChaveRoomDatasource.setMatricColab(matricColab, id)
            }
        } catch (e: Exception) {
            Result.failure(
                RepositoryException(
                    function = "IMovChaveRepository.setMatricColab",
                    cause = e
                )
            )
        }
    }

    override suspend fun setObserv(
        observ: String?,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        return try {
            when (flowApp) {
                FlowApp.ADD -> movChaveSharedPreferencesDatasource.setObserv(observ)
                FlowApp.CHANGE -> movChaveRoomDatasource.setObserv(observ, id)
            }
        } catch (e: Exception) {
            Result.failure(
                RepositoryException(
                    function = "IMovChaveRepository.setObserv",
                    cause = e
                )
            )
        }
    }

    override suspend fun setOutside(id: Int): Result<Boolean> {
        return movChaveRoomDatasource.setOutside(id)
    }

    override suspend fun setSent(list: List<MovChave>): Result<Boolean> {
        try {
            for (entity in list) {
                val result = movChaveRoomDatasource.setSent(entity.idMovChave!!)
                if (result.isFailure)
                    return Result.failure(result.exceptionOrNull()!!)
            }
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "IMovChaveRepository.setSent",
                    cause = e
                )
            )
        }
    }

    override suspend fun start(): Result<Boolean> {
        return movChaveSharedPreferencesDatasource.start()
    }

    override suspend fun start(movChave: MovChave): Result<Boolean> {
        try {
            val sharedPreferenceModel = movChave.entityToSharedPreferencesModel()
            return movChaveSharedPreferencesDatasource.start(sharedPreferenceModel)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "IMovChaveRepository.start",
                    cause = e
                )
            )
        }
    }

}