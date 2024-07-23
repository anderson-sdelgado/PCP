package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipProprio
import br.com.usinasantafe.pcp.infra.models.webservice.entityToMovEquipProprioWebServiceModel
import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.utils.TypeMov
import org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.util.Date

@RunWith(MockitoJUnitRunner::class)
class SendDataMovEquipProprioImplTest {

    @Test
    fun `Checar informacoes de envio`() {
        val movEquipProprio = MovEquipProprio(
            idMovEquipProprio = 1,
            nroMatricColabMovEquipProprio = 19759,
            idLocalMovEquipProprio = 1,
            tipoMovEquipProprio = TypeMov.INPUT,
            dthrMovEquipProprio = Date(1716894098389),
            idEquipMovEquipProprio = 303,
            nroMatricVigiaMovEquipProprio = 19035,
            destinoMovEquipProprio = "teste",
            statusSendMovEquipProprio = StatusSend.SEND
        )
        val movEquipProprioModel = movEquipProprio.entityToMovEquipProprioWebServiceModel(16997417840)
        println(movEquipProprioModel)
    }
}