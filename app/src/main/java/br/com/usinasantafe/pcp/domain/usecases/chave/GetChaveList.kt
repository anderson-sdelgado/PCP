package br.com.usinasantafe.pcp.domain.usecases.chave

import br.com.usinasantafe.pcp.domain.repositories.stable.ChaveRepository
import br.com.usinasantafe.pcp.presenter.view.chave.chavelist.ChaveModel
import br.com.usinasantafe.pcp.utils.call
import br.com.usinasantafe.pcp.utils.getClassAndMethod
import javax.inject.Inject

interface GetChaveList {
    suspend operator fun invoke(): Result<List<ChaveModel>>
}

class IGetChaveList @Inject constructor(
    private val chaveRepository: ChaveRepository,
    private val getDescrFullChave: GetDescrFullChave
): GetChaveList {

    override suspend fun invoke(): Result<List<ChaveModel>> =
        call(getClassAndMethod()) {
            val entityList = chaveRepository.listAll().getOrThrow()
            entityList.map {
                val desc = getDescrFullChave(it.idChave).getOrThrow()
                ChaveModel(
                    id = it.idChave,
                    description = desc
                )
            }
        }

}