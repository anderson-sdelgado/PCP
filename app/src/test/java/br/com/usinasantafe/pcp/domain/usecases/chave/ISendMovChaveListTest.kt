package br.com.usinasantafe.pcp.domain.usecases.chave

import br.com.usinasantafe.pcp.domain.entities.variable.Config
import br.com.usinasantafe.pcp.domain.entities.variable.MovChave
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveRepository
import br.com.usinasantafe.pcp.utils.StatusData
import br.com.usinasantafe.pcp.utils.StatusForeigner
import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.utils.TypeMovKey
import br.com.usinasantafe.pcp.utils.token
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.Date
import kotlin.test.assertEquals

class ISendMovChaveListTest {

    private val movChaveRepository = mock<MovChaveRepository>()
    private val configRepository = mock<ConfigRepository>()
    private val usecase = ISendMovChaveList(
        movChaveRepository,
        configRepository
    )

    @Test
    fun `Check return failure if have error in MovChaveRepository listSend`() =
        runTest {
            whenever(
                movChaveRepository.listSend()
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovChaveRepository.listSend"
            )
        }

    @Test
    fun `Check return failure if have error in ConfigRepository getConfig`() =
        runTest {
            val entity = MovChave(
                idMovChave = 1,
                matricVigiaMovChave = 19759,
                dthrMovChave = Date(1723213270250),
                tipoMovChave = TypeMovKey.REMOVE,
                idLocalMovChave = 1,
                idChaveMovChave = 1,
                matricColabMovChave = 19035,
                statusMovChave = StatusData.OPEN,
                statusSendMovChave = StatusSend.SEND,
                statusForeignerMovChave = StatusForeigner.INSIDE,
                observMovChave = "Teste",
                uuidMainMovChave = "UUID"
            )
            whenever(
                movChaveRepository.listSend()
            ).thenReturn(
                Result.success(
                    listOf(
                        entity
                    )
                )
            )
            whenever(
                configRepository.getConfig()
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> ConfigRepository.getConfig"
            )
        }

    @Test
    fun `Check return failure if have error in MovChaveRepository send`() =
        runTest {
            val entity = MovChave(
                idMovChave = 1,
                matricVigiaMovChave = 19759,
                dthrMovChave = Date(1723213270250),
                tipoMovChave = TypeMovKey.REMOVE,
                idLocalMovChave = 1,
                idChaveMovChave = 1,
                matricColabMovChave = 19035,
                statusMovChave = StatusData.OPEN,
                statusSendMovChave = StatusSend.SEND,
                statusForeignerMovChave = StatusForeigner.INSIDE,
                observMovChave = "Teste",
                uuidMainMovChave = "UUID"
            )
            whenever(
                movChaveRepository.listSend()
            ).thenReturn(
                Result.success(
                    listOf(
                        entity
                    )
                )
            )
            whenever(
                configRepository.getConfig()
            ).thenReturn(
                Result.success(
                    Config(
                        number = 16997417840,
                        version = "1.00",
                        idBD = 1
                    )
                )
            )
            whenever(
                movChaveRepository.send(
                    list = listOf(entity),
                    number = 16997417840,
                    token = token(
                        number = 16997417840,
                        version = "1.00",
                        idBD = 1
                    )
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovChaveRepository.send"
            )
        }

    @Test
    fun `Check return correct if function execute successfully`() =
        runTest {
            val entity = MovChave(
                idMovChave = 1,
                matricVigiaMovChave = 19759,
                dthrMovChave = Date(1723213270250),
                tipoMovChave = TypeMovKey.REMOVE,
                idLocalMovChave = 1,
                idChaveMovChave = 1,
                matricColabMovChave = 19035,
                statusMovChave = StatusData.OPEN,
                statusSendMovChave = StatusSend.SEND,
                statusForeignerMovChave = StatusForeigner.INSIDE,
                observMovChave = "Teste",
                uuidMainMovChave = "UUID"
            )
            val listReturn = listOf(
                MovChave(
                    idMovChave = 1,
                )
            )
            whenever(
                movChaveRepository.listSend()
            ).thenReturn(
                Result.success(
                    listOf(
                        entity
                    )
                )
            )
            whenever(
                configRepository.getConfig()
            ).thenReturn(
                Result.success(
                    Config(
                        number = 16997417840,
                        version = "1.00",
                        idBD = 1
                    )
                )
            )
            whenever(
                movChaveRepository.send(
                    list = listOf(entity),
                    number = 16997417840,
                    token = token(
                        number = 16997417840,
                        version = "1.00",
                        idBD = 1
                    )
                )
            ).thenReturn(
                Result.success(listReturn)
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            val listResult = result.getOrNull()!!
            assertEquals(
                listResult.size,
                1
            )
            val entityResult = listResult[0]
            assertEquals(
                entityResult.idMovChave,
                1
            )
        }
}