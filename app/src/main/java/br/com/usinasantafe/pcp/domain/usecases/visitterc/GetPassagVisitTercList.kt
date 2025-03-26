package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.TerceiroRepository
import br.com.usinasantafe.pcp.domain.repositories.stable.VisitanteRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcp.presenter.visitterc.passaglist.PassagVisitTercModel
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeVisitTerc

interface GetPassagVisitTercList {
    suspend operator fun invoke(
        flowApp: FlowApp,
        id: Int
    ): Result<List<PassagVisitTercModel>>
}

class IGetPassagVisitTercList(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
    private val movEquipVisitTercPassagRepository: MovEquipVisitTercPassagRepository,
    private val terceiroRepository: TerceiroRepository,
    private val visitanteRepository: VisitanteRepository
) : GetPassagVisitTercList {

    override suspend fun invoke(
        flowApp: FlowApp,
        id: Int
    ): Result<List<PassagVisitTercModel>> {
        try {
            val resultList = movEquipVisitTercPassagRepository.list(flowApp, id)
            if (resultList.isFailure) {
                val e = resultList.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetPassagVisitTercList",
                    message = e.message,
                    cause = e
                )
            }
            val passagList = resultList.getOrNull()!!
            if (passagList.isEmpty())
                return Result.success(emptyList())
            val resultTypeVisitTerc = movEquipVisitTercRepository.getTypeVisitTerc(
                flowApp = flowApp,
                id = id
            )
            if (resultTypeVisitTerc.isFailure) {
                val e = resultTypeVisitTerc.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetPassagVisitTercList",
                    message = e.message,
                    cause = e
                )
            }
            val typeVisitTerc = resultTypeVisitTerc.getOrNull()!!
            val passagColabList = passagList.map {
                when (typeVisitTerc) {
                    TypeVisitTerc.VISITANTE -> {
                        val resultCPF =
                            visitanteRepository.getCpf(it.idVisitTerc!!)
                        if (resultCPF.isFailure) {
                            val e = resultCPF.exceptionOrNull()!!
                            return resultFailure(
                                context = "IGetPassagVisitTercList",
                                message = e.message,
                                cause = e
                            )
                        }
                        val cpf = resultCPF.getOrNull()!!
                        val resultNome = visitanteRepository.getNome(cpf)
                        if (resultNome.isFailure) {
                            val e = resultNome.exceptionOrNull()!!
                            return resultFailure(
                                context = "IGetPassagVisitTercList",
                                message = e.message,
                                cause = e
                            )
                        }
                        val nome = resultNome.getOrNull()!!
                        PassagVisitTercModel(
                            id = it.idVisitTerc!!,
                            cpf = cpf,
                            nome = nome
                        )
                    }

                    TypeVisitTerc.TERCEIRO -> {
                        val resultCPF =
                            terceiroRepository.getCpf(it.idVisitTerc!!)
                        if (resultCPF.isFailure) {
                            val e = resultCPF.exceptionOrNull()!!
                            return resultFailure(
                                context = "IGetPassagVisitTercList",
                                message = e.message,
                                cause = e
                            )
                        }
                        val cpf = resultCPF.getOrNull()!!
                        val resultNome = terceiroRepository.getNome(cpf)
                        if (resultNome.isFailure) {
                            val e = resultNome.exceptionOrNull()!!
                            return resultFailure(
                                context = "IGetPassagVisitTercList",
                                message = e.message,
                                cause = e
                            )
                        }
                        val nome = resultNome.getOrNull()!!
                        PassagVisitTercModel(
                            id = it.idVisitTerc!!,
                            cpf = cpf,
                            nome = nome
                        )
                    }
                }
            }
            return Result.success(passagColabList)
        } catch (e: Exception) {
            return resultFailure(
                context = "IGetPassagVisitTercList",
                message = "-",
                cause = e
            )
        }
    }

}