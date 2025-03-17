package br.com.usinasantafe.pcp.infra.repositories.variable

import br.com.usinasantafe.pcp.domain.entities.variable.MovChaveEquip
import br.com.usinasantafe.pcp.domain.errors.RepositoryException
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
        return movChaveEquipRoomDatasource.checkOpen()
    }

    override suspend fun checkSend(): Result<Boolean> {
        return movChaveEquipRoomDatasource.checkSend()
    }

    override suspend fun get(id: Int): Result<MovChaveEquip> {
        try {
            val resultGet = movChaveEquipRoomDatasource.get(id)
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
                    function = "IMovChaveEquipRepository.get",
                    cause = e
                )
            )
        }
    }

    override suspend fun getMatricColab(id: Int): Result<Int> {
        try {
            val resultGet = movChaveEquipRoomDatasource.get(id)
            if(resultGet.isFailure){
                return Result.failure(
                    resultGet.exceptionOrNull()!!
                )
            }
            val entity = resultGet.getOrNull()!!.roomModelToEntity()
            return Result.success(entity.matricColabMovChaveEquip!!)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "IMovChaveEquipRepository.getMatricColab",
                    cause = e
                )
            )
        }
    }

    override suspend fun getIdEquip(id: Int): Result<Int> {
        try {
            val resultGet = movChaveEquipRoomDatasource.get(id)
            if(resultGet.isFailure){
                return Result.failure(
                    resultGet.exceptionOrNull()!!
                )
            }
            val entity = resultGet.getOrNull()!!.roomModelToEntity()
            return Result.success(entity.idEquipMovChaveEquip!!)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "IMovChaveEquipRepository.getIdEquip",
                    cause = e
                )
            )
        }
    }

    override suspend fun getObserv(id: Int): Result<String?> {
        try {
            val resultGet = movChaveEquipRoomDatasource.get(id)
            if(resultGet.isFailure){
                return Result.failure(
                    resultGet.exceptionOrNull()!!
                )
            }
            val entity = resultGet.getOrNull()!!.roomModelToEntity()
            return Result.success(entity.observMovChaveEquip)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "IMovChaveEquipRepository.getObserv",
                    cause = e
                )
            )
        }
    }

    override suspend fun listInside(): Result<List<MovChaveEquip>> {
        try {
            val resultList = movChaveEquipRoomDatasource.listInside()
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
                    function = "IMovChaveEquipRepository.listInside",
                    cause = e
                )
            )
        }
    }

    override suspend fun listOpen(): Result<List<MovChaveEquip>> {
        try {
            val resultList = movChaveEquipRoomDatasource.listOpen()
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
                    function = "IMovChaveEquipRepository.listOpen",
                    cause = e
                )
            )
        }
    }

    override suspend fun listSend(): Result<List<MovChaveEquip>> {
        try {
            val resultList = movChaveEquipRoomDatasource.listSend()
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
                    function = "IMovChaveEquipRepository.listSend",
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
            val resultGetMov = movChaveEquipSharedPreferencesDatasource.get()
            if (resultGetMov.isFailure)
                return Result.failure(resultGetMov.exceptionOrNull()!!)
            val roomModel = resultGetMov.getOrNull()!!
                .entityToSharedPreferencesModel()
                .entityToRoomModel(
                    matricVigia = matricVigia,
                    idLocal = idLocal,
                    uuid = uuid
                )
            val resultSave = movChaveEquipRoomDatasource.save(roomModel)
            if (resultSave.isFailure)
                return Result.failure(resultSave.exceptionOrNull()!!)
            val id = resultSave.getOrNull()!!.toInt()
            if (id == 0) {
                return Result.failure(
                    RepositoryException(
                        function = "IMovChaveEquipRepository.save",
                        cause = Exception("Id is 0")
                    )
                )
            }
            val resultClear = movChaveEquipSharedPreferencesDatasource.clear()
            if (resultClear.isFailure)
                return Result.failure(resultClear.exceptionOrNull()!!)
            return Result.success(id)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "IMovChaveEquipRepository.save",
                    cause = e
                )
            )
        }
    }

    override suspend fun send(
        list: List<MovChaveEquip>,
        number: Long,
        token: String
    ): Result<List<MovChaveEquip>> {
        try {
            val resultList = movChaveEquipRetrofitDatasource.send(
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
                    function = "IMovChaveEquipRepository.send",
                    cause = e
                )
            )
        }
    }

    override suspend fun setClose(id: Int): Result<Boolean> {
        return movChaveEquipRoomDatasource.setClose(id)
    }

    override suspend fun setIdEquip(idEquip: Int, flowApp: FlowApp, id: Int): Result<Boolean> {
        return try {
            when (flowApp) {
                FlowApp.ADD -> movChaveEquipSharedPreferencesDatasource.setIdEquip(idEquip)
                FlowApp.CHANGE -> movChaveEquipRoomDatasource.setIdEquip(idEquip, id)
            }
        } catch (e: Exception) {
            Result.failure(
                RepositoryException(
                    function = "IMovChaveEquipRepository.setIdEquip",
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
                FlowApp.ADD -> movChaveEquipSharedPreferencesDatasource.setMatricColab(matricColab)
                FlowApp.CHANGE -> movChaveEquipRoomDatasource.setMatricColab(matricColab, id)
            }
        } catch (e: Exception) {
            Result.failure(
                RepositoryException(
                    function = "IMovChaveEquipRepository.setMatricColab",
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
                FlowApp.ADD -> movChaveEquipSharedPreferencesDatasource.setObserv(observ)
                FlowApp.CHANGE -> movChaveEquipRoomDatasource.setObserv(observ, id)
            }
        } catch (e: Exception) {
            Result.failure(
                RepositoryException(
                    function = "IMovChaveEquipRepository.setObserv",
                    cause = e
                )
            )
        }
    }

    override suspend fun setOutside(id: Int): Result<Boolean> {
        return movChaveEquipRoomDatasource.setOutside(id)
    }

    override suspend fun setSent(list: List<MovChaveEquip>): Result<Boolean> {
        try {
            for (entity in list) {
                val result = movChaveEquipRoomDatasource.setSent(entity.idMovChaveEquip!!)
                if (result.isFailure)
                    return Result.failure(result.exceptionOrNull()!!)
            }
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "IMovChaveEquipRepository.setSent",
                    cause = e
                )
            )
        }
    }

    override suspend fun start(): Result<Boolean> {
        return movChaveEquipSharedPreferencesDatasource.start()
    }

    override suspend fun start(movChaveEquip: MovChaveEquip): Result<Boolean> {
        try {
            val sharedPreferenceModel = movChaveEquip.entityToSharedPreferencesModel()
            return movChaveEquipSharedPreferencesDatasource.start(sharedPreferenceModel)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "IMovChaveEquipRepository.start",
                    cause = e
                )
            )
        }
    }
}