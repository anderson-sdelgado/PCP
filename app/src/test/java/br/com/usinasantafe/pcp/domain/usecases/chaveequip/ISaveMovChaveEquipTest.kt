package br.com.usinasantafe.pcp.domain.usecases.chaveequip

import br.com.usinasantafe.pcp.domain.entities.variable.Config
import br.com.usinasantafe.pcp.domain.entities.variable.MovChaveEquip
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveEquipRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcp.utils.TypeMovKey
import br.com.usinasantafe.pcp.utils.UUIDProvider
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class ISaveMovChaveEquipTest {

    private val uuidProvider = mock<UUIDProvider>()
    private val configRepository = mock<ConfigRepository>()
    private val movChaveEquipRepository = mock<MovChaveEquipRepository>()
    private val startProcessSendData = mock<StartProcessSendData>()
    private val usecase = ISaveMovChaveEquip(
        configRepository = configRepository,
        movChaveEquipRepository = movChaveEquipRepository,
        startProcessSendData = startProcessSendData,
        uuidProvider = uuidProvider
    )

    @Test
    fun `Check return failure if have error in ConfigRepository getConfig - RECEIPT`() =
        runTest {
            whenever(
                configRepository.getConfig()
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(
                typeMov = TypeMovKey.RECEIPT,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISaveMovChaveEquip -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in MovChaveEquipRepository save - RECEIPT`() =
        runTest {
            whenever(
                uuidProvider.uuid()
            ).thenReturn(
                "UUID"
            )
            whenever(
                configRepository.getConfig()
            ).thenReturn(
                Result.success(
                    Config(
                        matricVigia = 19759,
                        idLocal = 1
                    )
                )
            )
            whenever(
                movChaveEquipRepository.save(
                    matricVigia = 19759,
                    idLocal = 1,
                    uuid = "UUID"
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(
                typeMov = TypeMovKey.RECEIPT,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISaveMovChaveEquip -> Unknown Error"
            )
        }

    @Test
    fun `Check return correct if function execute successfully - RECEIPT`() =
        runTest {
            whenever(
                uuidProvider.uuid()
            ).thenReturn(
                "UUID"
            )
            whenever(
                configRepository.getConfig()
            ).thenReturn(
                Result.success(
                    Config(
                        matricVigia = 19759,
                        idLocal = 1
                    )
                )
            )
            whenever(
                movChaveEquipRepository.save(
                    matricVigia = 19759,
                    idLocal = 1,
                    uuid = "UUID"
                )
            ).thenReturn(
                Result.success(1)
            )
            val result = usecase(
                typeMov = TypeMovKey.RECEIPT,
                id = 1
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }

    @Test
    fun `Check return failure if have error in SetStatusOutsideMovChave - REMOVE`() =
        runTest {
            whenever(
                movChaveEquipRepository.setOutside(1)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(
                typeMov = TypeMovKey.REMOVE,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISaveMovChaveEquip -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in MovChaveEquipRepository get - REMOVE`() =
        runTest {
            whenever(
                movChaveEquipRepository.setOutside(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movChaveEquipRepository.get(1)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(
                typeMov = TypeMovKey.REMOVE,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISaveMovChaveEquip -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in ConfigRepository getConfig - REMOVE`() =
        runTest {
            whenever(
                movChaveEquipRepository.setOutside(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movChaveEquipRepository.get(1)
            ).thenReturn(
                Result.success(
                    MovChaveEquip(
                        uuidMainMovChaveEquip = "UUID"
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
            val result = usecase(
                typeMov = TypeMovKey.REMOVE,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISaveMovChaveEquip -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in MovChaveRepository save - REMOVE`() =
        runTest {
            whenever(
                movChaveEquipRepository.setOutside(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movChaveEquipRepository.get(1)
            ).thenReturn(
                Result.success(
                    MovChaveEquip(
                        uuidMainMovChaveEquip = "UUID"
                    )
                )
            )
            whenever(
                configRepository.getConfig()
            ).thenReturn(
                Result.success(
                    Config(
                        matricVigia = 19759,
                        idLocal = 1
                    )
                )
            )
            whenever(
                movChaveEquipRepository.save(
                    matricVigia = 19759,
                    idLocal = 1,
                    uuid = "UUID"
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(
                typeMov = TypeMovKey.REMOVE,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISaveMovChaveEquip -> Unknown Error"
            )
        }

    @Test
    fun `Check return correct if function execute successfully - REMOVE`() =
        runTest {
            whenever(
                movChaveEquipRepository.setOutside(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movChaveEquipRepository.get(1)
            ).thenReturn(
                Result.success(
                    MovChaveEquip(
                        uuidMainMovChaveEquip = "UUID"
                    )
                )
            )
            whenever(
                configRepository.getConfig()
            ).thenReturn(
                Result.success(
                    Config(
                        matricVigia = 19759,
                        idLocal = 1
                    )
                )
            )
            whenever(
                movChaveEquipRepository.save(
                    matricVigia = 19759,
                    idLocal = 1,
                    uuid = "UUID"
                )
            ).thenReturn(
                Result.success(1)
            )
            val result = usecase(
                typeMov = TypeMovKey.REMOVE,
                id = 1
            )
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