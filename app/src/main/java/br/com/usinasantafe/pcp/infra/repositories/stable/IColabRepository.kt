package br.com.usinasantafe.pcp.infra.repositories.stable

import br.com.usinasantafe.pcp.domain.entities.stable.Colab
import br.com.usinasantafe.pcp.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pcp.infra.datasource.room.stable.ColabRoomDatasource
import br.com.usinasantafe.pcp.infra.datasource.retrofit.stable.ColabRetrofitDatasource
import br.com.usinasantafe.pcp.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.pcp.infra.models.room.stable.entityToRoomModel
import br.com.usinasantafe.pcp.utils.EmptyResult
import br.com.usinasantafe.pcp.utils.call
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import javax.inject.Inject

class IColabRepository @Inject constructor(
    private val colabRoomDatasource: ColabRoomDatasource,
    private val colabRetrofitDatasource: ColabRetrofitDatasource
): ColabRepository {

    override suspend fun addAll(list: List<Colab>): EmptyResult =
        call(getClassAndMethod()) {
            val colabModelList = list.map { it.entityToRoomModel() }
            colabRoomDatasource.addAll(colabModelList).getOrThrow()
        }

    override suspend fun hasMatric(matric: Int): Result<Boolean> =
        call(getClassAndMethod()) {
            colabRoomDatasource.hasMatric(matric).getOrThrow()
        }

    override suspend fun deleteAll(): EmptyResult =
        call(getClassAndMethod()) {
            colabRoomDatasource.deleteAll().getOrThrow()
        }

    override suspend fun getNomeByMatric(matric: Int): Result<String> =
        call(getClassAndMethod()) {
            colabRoomDatasource.getNomeByMatric(matric).getOrThrow()
        }

    override suspend fun listAll(token: String): Result<List<Colab>> =
        call(getClassAndMethod()) {
            val modelList = colabRetrofitDatasource.listAll(token).getOrThrow()
            modelList.map { it.retrofitModelToEntity() }
        }

}