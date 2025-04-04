package br.com.usinasantafe.pcp.domain.usecases.chave

import br.com.usinasantafe.pcp.domain.entities.variable.MovChave
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveRepository
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

class IStartReceiptMovChaveTest {

    private val movChaveRepository = mock<MovChaveRepository>()
    private val usecase = IStartReceiptMovChave(
        movChaveRepository = movChaveRepository
    )

    @Test
    fun `Check return failure if have error in MovChaveRepository get`() =
        runTest {
            whenever(
                movChaveRepository.get(1)
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
                "IStartReceiptMovChave -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in MovChaveRepository start`() =
        runTest {
            val entity = MovChave(
                idMovChave = 1,
                matricVigiaMovChave = 19035,
                idLocalMovChave = 1,
                tipoMovChave = TypeMovKey.REMOVE,
                dthrMovChave = Date(),
                matricColabMovChave = 19759,
                idChaveMovChave = 1,
                observMovChave = "OBSERVACAO TESTE",
                statusMovChave = StatusData.OPEN,
                statusSendMovChave = StatusSend.SEND,
                statusForeignerMovChave = StatusForeigner.INSIDE
            )
            whenever(
                movChaveRepository.get(1)
            ).thenReturn(
                Result.success(entity)
            )
            entity.observMovChave = null
            entity.tipoMovChave = TypeMovKey.RECEIPT
            entity.dthrMovChave = Date()
            entity.statusForeignerMovChave = StatusForeigner.OUTSIDE
            whenever(
                movChaveRepository.start(entity)
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
                "IStartReceiptMovChave -> Unknown Error"
            )
        }

    @Test
    fun `Check return correct if function execute successfully`() =
        runTest {
            val entity = MovChave(
                idMovChave = 1,
                matricVigiaMovChave = 19035,
                idLocalMovChave = 1,
                tipoMovChave = TypeMovKey.REMOVE,
                dthrMovChave = Date(),
                matricColabMovChave = 19759,
                idChaveMovChave = 1,
                observMovChave = "OBSERVACAO TESTE",
                statusMovChave = StatusData.OPEN,
                statusSendMovChave = StatusSend.SEND,
                statusForeignerMovChave = StatusForeigner.INSIDE
            )
            whenever(
                movChaveRepository.get(1)
            ).thenReturn(
                Result.success(entity)
            )
            entity.observMovChave = null
            entity.tipoMovChave = TypeMovKey.RECEIPT
            entity.dthrMovChave = Date()
            entity.statusForeignerMovChave = StatusForeigner.OUTSIDE
            whenever(
                movChaveRepository.start(entity)
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