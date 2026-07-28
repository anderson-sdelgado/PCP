package br.com.usinasantafe.pcp.external.room.datasource.stable

import br.com.usinasantafe.pcp.external.room.dao.stable.TerceiroDao
import br.com.usinasantafe.pcp.infra.datasource.room.stable.TerceiroRoomDatasource
import br.com.usinasantafe.pcp.infra.models.room.stable.TerceiroRoomModel
import br.com.usinasantafe.pcp.utils.EmptyResult
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import br.com.usinasantafe.pcp.utils.result
import javax.inject.Inject

class ITerceiroRoomDatasource @Inject constructor(
    private val terceiroDao: TerceiroDao
): TerceiroRoomDatasource {

    override suspend fun addAll(list: List<TerceiroRoomModel>): EmptyResult =
        result(getClassAndMethod()) {
            terceiroDao.insertAll(list)
        }

    override suspend fun hasCpf(cpf: String): Result<Boolean> =
        result(getClassAndMethod()) {
            terceiroDao.has(cpf)
        }

    override suspend fun deleteAll(): EmptyResult =
        result(getClassAndMethod()) {
            terceiroDao.deleteAll()
        }

    override suspend fun getById(id: Int): Result<TerceiroRoomModel> =
        result(getClassAndMethod()) {
            terceiroDao.getById(id)
        }

    override suspend fun getByCpf(cpf: String): Result<List<TerceiroRoomModel>> =
        result(getClassAndMethod()) {
            terceiroDao.getByCpf(cpf)
        }
}