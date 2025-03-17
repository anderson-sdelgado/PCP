package br.com.usinasantafe.pcp.domain.usecases.background

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.domain.usecases.chave.CheckSendMovChave
import br.com.usinasantafe.pcp.domain.usecases.chave.SendMovChaveList
import br.com.usinasantafe.pcp.domain.usecases.chave.SetStatusSentMovChave
import br.com.usinasantafe.pcp.domain.usecases.chaveequip.CheckSendMovChaveEquip
import br.com.usinasantafe.pcp.domain.usecases.chaveequip.SendMovChaveEquipList
import br.com.usinasantafe.pcp.domain.usecases.chaveequip.SetStatusSentMovChaveEquip
import br.com.usinasantafe.pcp.domain.usecases.common.SetStatusSend
import br.com.usinasantafe.pcp.domain.usecases.proprio.CheckSendMovProprio
import br.com.usinasantafe.pcp.domain.usecases.proprio.SendMovProprioList
import br.com.usinasantafe.pcp.domain.usecases.proprio.SetStatusSentMovProprio
import br.com.usinasantafe.pcp.domain.usecases.residencia.CheckSendMovResidencia
import br.com.usinasantafe.pcp.domain.usecases.residencia.SendMovResidenciaList
import br.com.usinasantafe.pcp.domain.usecases.residencia.SetStatusSentMovResidencia
import br.com.usinasantafe.pcp.domain.usecases.visitterc.CheckSendMovVisitTerc
import br.com.usinasantafe.pcp.domain.usecases.visitterc.SendMovVisitTercList
import br.com.usinasantafe.pcp.domain.usecases.visitterc.SetStatusSentMovVisitTerc
import br.com.usinasantafe.pcp.utils.StatusSend

class ProcessWorkManager(
    context: Context,
    workerParameters: WorkerParameters,
    private val setStatusSend: SetStatusSend,
    private val configRepository: ConfigRepository,
    private val checkSendMovProprio: CheckSendMovProprio,
    private val sendMovProprioList: SendMovProprioList,
    private val setStatusSentMovProprio: SetStatusSentMovProprio,
    private val checkSendMovVisitTerc: CheckSendMovVisitTerc,
    private val sendMovVisitTercList: SendMovVisitTercList,
    private val setStatusSentMovVisitTerc: SetStatusSentMovVisitTerc,
    private val checkSendMovResidencia: CheckSendMovResidencia,
    private val sendMovResidenciaList: SendMovResidenciaList,
    private val setStatusSentResidencia: SetStatusSentMovResidencia,
    private val checkSendMovChave: CheckSendMovChave,
    private val sendMovChaveList: SendMovChaveList,
    private val setStatusSentMovChave: SetStatusSentMovChave,
    private val checkSendMovChaveEquip: CheckSendMovChaveEquip,
    private val sendMovChaveEquipList: SendMovChaveEquipList,
    private val setStatusSentMovChaveEquip: SetStatusSentMovChaveEquip
): CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        val resultCheckProprio = checkSendMovProprio()
        if(resultCheckProprio.isFailure)
            return Result.retry()
        val checkSendProprio = resultCheckProprio.getOrNull()!!
        if(checkSendProprio){
            setStatusSend(StatusSend.SENDING)
            val resultSendProprioList = sendMovProprioList()
            if(resultSendProprioList.isFailure)
                return Result.retry()
            val resultSet = setStatusSentMovProprio(resultSendProprioList.getOrNull()!!)
            if(resultSet.isFailure) {
                setStatusSend(StatusSend.SEND)
                return Result.retry()
            }
        }
        val resultCheckVisitTerc = checkSendMovVisitTerc()
        if(resultCheckVisitTerc.isFailure)
            return Result.retry()
        val checkSendVisitTerc = resultCheckVisitTerc.getOrNull()!!
        if(checkSendVisitTerc){
            setStatusSend(StatusSend.SENDING)
            val resultSendVisitTerc = sendMovVisitTercList()
            if(resultSendVisitTerc.isFailure)
                return Result.retry()
            val resultSetVisitTerc = setStatusSentMovVisitTerc(resultSendVisitTerc.getOrNull()!!)
            if(resultSetVisitTerc.isFailure) {
                setStatusSend(StatusSend.SEND)
                return Result.retry()
            }
        }
        val resultCheckResidencia = checkSendMovResidencia()
        if(resultCheckResidencia.isFailure)
            return Result.retry()
        val checkSendResidencia = resultCheckResidencia.getOrNull()!!
        if(checkSendResidencia){
            setStatusSend(StatusSend.SENDING)
            val resultSendResidencia = sendMovResidenciaList()
            if(resultSendResidencia.isFailure)
                return Result.retry()
            val resultSetResidencia = setStatusSentResidencia(resultSendResidencia.getOrNull()!!)
            if(resultSetResidencia.isFailure) {
                setStatusSend(StatusSend.SEND)
                return Result.retry()
            }
        }
        val resultCheckChave = checkSendMovChave()
        if(resultCheckChave.isFailure)
            return Result.retry()
        val checkSendChave = resultCheckChave.getOrNull()!!
        if(checkSendChave){
            setStatusSend(StatusSend.SENDING)
            val resultSendChave = sendMovChaveList()
            if(resultSendChave.isFailure)
                return Result.retry()
            val resultSetChave = setStatusSentMovChave(resultSendChave.getOrNull()!!)
            if(resultSetChave.isFailure) {
                setStatusSend(StatusSend.SEND)
                return Result.retry()
            }
        }
        val resultCheckChaveEquip = checkSendMovChaveEquip()
        if(resultCheckChaveEquip.isFailure)
            return Result.retry()
        val checkSendChaveEquip = resultCheckChaveEquip.getOrNull()!!
        if(checkSendChaveEquip){
            setStatusSend(StatusSend.SENDING)
            val resultSendChaveEquip = sendMovChaveEquipList()
            if(resultSendChaveEquip.isFailure)
                return Result.retry()
            val resultSetChaveEquip = setStatusSentMovChaveEquip(resultSendChaveEquip.getOrNull()!!)
            if(resultSetChaveEquip.isFailure) {
                setStatusSend(StatusSend.SEND)
                return Result.retry()
            }
        }
        val resulHasCOnfig = configRepository.hasConfig()
        val hasConfig = resulHasCOnfig.getOrNull()!!
        if(hasConfig) {
            setStatusSend(StatusSend.SENT)
        }
        return Result.success()
    }
}