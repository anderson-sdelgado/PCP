package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pcp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioEquipSegRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioPassagRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcp.presenter.proprio.detalhe.DetalheProprioModel
import br.com.usinasantafe.pcp.utils.FlowApp
import java.text.SimpleDateFormat
import java.util.Locale

interface GetDetalheProprio {
    suspend operator fun invoke(
        id: Int
    ): Result<DetalheProprioModel>
}

class IGetDetalheProprio(
    private val movEquipProprioRepository: MovEquipProprioRepository,
    private val movEquipProprioEquipSegRepository: MovEquipProprioEquipSegRepository,
    private val movEquipProprioPassagRepository: MovEquipProprioPassagRepository,
    private val equipRepository: EquipRepository,
    private val colabRepository: ColabRepository
) : GetDetalheProprio {

    override suspend fun invoke(
        id: Int
    ): Result<DetalheProprioModel> {
        try {
            val resultGet = movEquipProprioRepository.get(id)
            if (resultGet.isFailure) {
                val e = resultGet.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetDetalheProprio",
                    message = e.message,
                    cause = e
                )
            }
            val mov = resultGet.getOrNull()!!
            val dthr = SimpleDateFormat(
                "dd/MM/yyyy HH:mm",
                Locale("pt", "BR")
            ).format(mov.dthrMovEquipProprio)
            val tipoMov = if (mov.tipoMovEquipProprio!!.ordinal == 0) "ENTRADA" else "SAÍDA"
            val resultNro = equipRepository.getDescr(mov.idEquipMovEquipProprio!!)
            if (resultNro.isFailure) {
                val e = resultNro.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetDetalheProprio",
                    message = e.message,
                    cause = e
                )
            }
            val veiculo = resultNro.getOrNull()!!
            val resultEquipSegList =
                movEquipProprioEquipSegRepository.list(FlowApp.CHANGE, mov.idMovEquipProprio!!)
            if (resultEquipSegList.isFailure) {
                val e = resultEquipSegList.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetDetalheProprio",
                    message = e.message,
                    cause = e
                )
            }
            val equipSegList = resultEquipSegList.getOrNull()!!
            var veicSeg = ""
            for (equipSeg in equipSegList) {
                val resultNroEquipSeg = equipRepository.getNro(equipSeg.idEquip!!)
                if (resultNroEquipSeg.isFailure) {
                    val e = resultNroEquipSeg.exceptionOrNull()!!
                    return resultFailure(
                        context = "IGetDetalheProprio",
                        message = e.message,
                        cause = e
                    )
                }
                val nroEquipSeg = resultNroEquipSeg.getOrNull()!!
                veicSeg += "$nroEquipSeg - "
            }
            val resultGetNome = colabRepository.getNome(mov.matricColabMovEquipProprio!!)
            if (resultGetNome.isFailure) {
                val e = resultGetNome.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetDetalheProprio",
                    message = e.message,
                    cause = e
                )
            }
            val nome = resultGetNome.getOrNull()!!
            val motorista = "${mov.matricColabMovEquipProprio!!} - $nome"
            val resultPassagList =
                movEquipProprioPassagRepository.list(
                    FlowApp.CHANGE,
                    mov.idMovEquipProprio!!
                )
            if (resultPassagList.isFailure) {
                val e = resultPassagList.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetDetalheProprio",
                    message = e.message,
                    cause = e
                )
            }
            val passagList = resultPassagList.getOrNull()!!
            var passageiro = ""
            for (passag in passagList) {
                val resultGetNomePassag = colabRepository.getNome(passag.matricColab!!)
                if (resultGetNomePassag.isFailure) {
                    val e = resultGetNomePassag.exceptionOrNull()!!
                    return resultFailure(
                        context = "IGetDetalheProprio",
                        message = e.message,
                        cause = e
                    )
                }
                passageiro += "${passag.matricColab!!} - ${resultGetNomePassag.getOrNull()!!}; "
            }

            val destino = "${mov.destinoMovEquipProprio}"
            val descrNotaFiscal =
                if (mov.notaFiscalMovEquipProprio == null) "" else mov.notaFiscalMovEquipProprio
            val notaFiscal = "$descrNotaFiscal"
            val observ =
                if (mov.observMovEquipProprio.isNullOrEmpty()) "" else mov.observMovEquipProprio
            return Result.success(
                DetalheProprioModel(
                    dthr = dthr,
                    tipoMov = tipoMov,
                    veiculo = veiculo,
                    veiculoSec = veicSeg,
                    motorista = motorista,
                    passageiro = passageiro,
                    destino = destino,
                    notaFiscal = notaFiscal,
                    observ = observ
                )
            )
        } catch (e: Exception) {
            return resultFailure(
                context = "IGetDetalheProprio",
                message = "-",
                cause = e
            )
        }
    }

}