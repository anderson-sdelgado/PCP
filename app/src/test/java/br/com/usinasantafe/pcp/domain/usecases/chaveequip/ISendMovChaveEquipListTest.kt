package br.com.usinasantafe.pcp.domain.usecases.chaveequip

import br.com.usinasantafe.pcp.domain.entities.variable.Config
import br.com.usinasantafe.pcp.domain.entities.variable.MovChaveEquip
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveEquipRepository
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

class ISendMovChaveEquipListTest {

    private val movChaveEquipRepository = mock<MovChaveEquipRepository>()
    private val configRepository = mock<ConfigRepository>()
    private val usecase = ISendMovChaveEquipList(
        movChaveEquipRepository,
        configRepository
    )

    @Test
    fun `Check return failure if have error in MovChaveRepository listSend`() =
        runTest {
            whenever(
                movChaveEquipRepository.listSend()
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
                "ISendMovChaveEquipList -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in ConfigRepository getConfig`() =
        runTest {
            val entity = MovChaveEquip(
                idMovChaveEquip = 1,
                dthrMovChaveEquip = Date(1723213270250),
                tipoMovChaveEquip = TypeMovKey.RECEIPT,
                matricVigiaMovChaveEquip = 19759,
                idLocalMovChaveEquip = 1,
                idEquipMovChaveEquip = 1,
                matricColabMovChaveEquip = 19035,
                observMovChaveEquip = "OBSERV",
                statusMovChaveEquip = StatusData.OPEN,
                statusSendMovChaveEquip = StatusSend.SEND,
                statusForeignerMovChaveEquip = StatusForeigner.INSIDE,
                uuidMainMovChaveEquip = "UUID"
            )
            whenever(
                movChaveEquipRepository.listSend()
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
                "ISendMovChaveEquipList -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in MovChaveRepository send`() =
        runTest {
            val entity = MovChaveEquip(
                idMovChaveEquip = 1,
                dthrMovChaveEquip = Date(1723213270250),
                tipoMovChaveEquip = TypeMovKey.RECEIPT,
                matricVigiaMovChaveEquip = 19759,
                idLocalMovChaveEquip = 1,
                idEquipMovChaveEquip = 1,
                matricColabMovChaveEquip = 19035,
                observMovChaveEquip = "OBSERV",
                statusMovChaveEquip = StatusData.OPEN,
                statusSendMovChaveEquip = StatusSend.SEND,
                statusForeignerMovChaveEquip = StatusForeigner.INSIDE,
                uuidMainMovChaveEquip = "UUID"
            )
            whenever(
                movChaveEquipRepository.listSend()
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
                movChaveEquipRepository.send(
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
                "ISendMovChaveEquipList -> Unknown Error"
            )
        }

    @Test
    fun `Check return correct if function execute successfully`() =
        runTest {
            val entity = MovChaveEquip(
                idMovChaveEquip = 1,
                dthrMovChaveEquip = Date(1723213270250),
                tipoMovChaveEquip = TypeMovKey.RECEIPT,
                matricVigiaMovChaveEquip = 19759,
                idLocalMovChaveEquip = 1,
                idEquipMovChaveEquip = 1,
                matricColabMovChaveEquip = 19035,
                observMovChaveEquip = "OBSERV",
                statusMovChaveEquip = StatusData.OPEN,
                statusSendMovChaveEquip = StatusSend.SEND,
                statusForeignerMovChaveEquip = StatusForeigner.INSIDE,
                uuidMainMovChaveEquip = "UUID"
            )
            val listReturn = listOf(
                MovChaveEquip(
                    idMovChaveEquip = 1,
                )
            )
            whenever(
                movChaveEquipRepository.listSend()
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
                movChaveEquipRepository.send(
                    list = listOf(entity),
                    number = 16997417840,
                    token = token(
                        number = 16997417840,
                        version = "1.00",
                        idBD = 1
                    )
                )
            ).thenReturn(
                Result.success(
                    listReturn
                )
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
                entityResult.idMovChaveEquip,
                1
            )
        }
}