package br.com.usinasantafe.pcp.domain.usecases.chaveequip

import br.com.usinasantafe.pcp.domain.entities.variable.MovChaveEquip
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveEquipRepository
import br.com.usinasantafe.pcp.utils.StatusData
import br.com.usinasantafe.pcp.utils.StatusForeigner
import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.utils.TypeMovKey
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.Date
import kotlin.test.assertEquals

class IStartRemoveMovChaveEquipTest {

    private val movChaveEquipRepository = mock<MovChaveEquipRepository>()
    private val usecase = IStartRemoveMovChaveEquip(
        movChaveEquipRepository = movChaveEquipRepository
    )

    @Test
    fun `Check return failure if have error in MovChaveRepository get`() =
        runTest {
            whenever(
                movChaveEquipRepository.get(1)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IStartRemoveMovChaveEquip -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in MovChaveEquipRepository start`() =
        runTest {
            val entity = MovChaveEquip(
                idMovChaveEquip = 1,
                matricVigiaMovChaveEquip = 19035,
                idLocalMovChaveEquip = 1,
                tipoMovChaveEquip = TypeMovKey.RECEIPT,
                dthrMovChaveEquip = Date(),
                matricColabMovChaveEquip = 19759,
                idEquipMovChaveEquip = 1,
                observMovChaveEquip = "OBSERVACAO TESTE",
                statusMovChaveEquip = StatusData.OPEN,
                statusSendMovChaveEquip = StatusSend.SEND,
                statusForeignerMovChaveEquip = StatusForeigner.INSIDE
            )
            whenever(
                movChaveEquipRepository.get(1)
            ).thenReturn(
                Result.success(entity)
            )
            entity.observMovChaveEquip = null
            entity.tipoMovChaveEquip = TypeMovKey.REMOVE
            entity.dthrMovChaveEquip = Date()
            entity.statusForeignerMovChaveEquip = StatusForeigner.OUTSIDE
            whenever(
                movChaveEquipRepository.start(entity)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IStartRemoveMovChaveEquip -> Unknown Error"
            )
        }

    @Test
    fun `Check return correct if function execute successfully`() =
        runTest {
            val entity = MovChaveEquip(
                idMovChaveEquip = 1,
                matricVigiaMovChaveEquip = 19035,
                idLocalMovChaveEquip = 1,
                tipoMovChaveEquip = TypeMovKey.RECEIPT,
                dthrMovChaveEquip = Date(),
                matricColabMovChaveEquip = 19759,
                idEquipMovChaveEquip = 1,
                observMovChaveEquip = "OBSERVACAO TESTE",
                statusMovChaveEquip = StatusData.OPEN,
                statusSendMovChaveEquip = StatusSend.SEND,
                statusForeignerMovChaveEquip = StatusForeigner.INSIDE
            )
            whenever(
                movChaveEquipRepository.get(1)
            ).thenReturn(
                Result.success(entity)
            )
            entity.observMovChaveEquip = null
            entity.tipoMovChaveEquip = TypeMovKey.REMOVE
            entity.dthrMovChaveEquip = Date()
            entity.statusForeignerMovChaveEquip = StatusForeigner.OUTSIDE
            whenever(
                movChaveEquipRepository.start(entity)
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }

}