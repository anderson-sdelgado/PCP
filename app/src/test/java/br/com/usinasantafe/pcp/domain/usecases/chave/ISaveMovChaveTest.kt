package br.com.usinasantafe.pcp.domain.usecases.chave

import br.com.usinasantafe.pcp.domain.entities.variable.Config
import br.com.usinasantafe.pcp.domain.entities.variable.MovChave
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcp.utils.TypeMovKey
import br.com.usinasantafe.pcp.utils.UUIDProvider
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class ISaveMovChaveTest {

    private val uuidProvider = mock<UUIDProvider>()
    private val configRepository = mock<ConfigRepository>()
    private val movChaveRepository = mock<MovChaveRepository>()
    private val startProcessSendData = mock<StartProcessSendData>()
    private val usecase = ISaveMovChave(
        configRepository = configRepository,
        movChaveRepository = movChaveRepository,
        startProcessSendData = startProcessSendData,
        uuidProvider = uuidProvider
    )

    @Test
    fun `Check return failure if have error in ConfigRepository getConfig - REMOVE`() =
        runTest {
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
                "ISaveMovChave -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in MovChaveRepository save - REMOVE`() =
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
                movChaveRepository.save(
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
                "ISaveMovChave -> Unknown Error"
            )
        }

    @Test
    fun `Check return correct if function execute successfully - REMOVE`() =
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
                movChaveRepository.save(
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

    @Test
    fun `Check return failure if have error in SetStatusOutsideMovChave - RECEIPT`() =
        runTest {
            whenever(
                movChaveRepository.setOutside(1)
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
                "ISaveMovChave -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in MovChaveRepository get - RECEIPT`() =
        runTest {
            whenever(
                movChaveRepository.setOutside(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movChaveRepository.get(1)
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
                "ISaveMovChave -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in ConfigRepository getConfig - RECEIPT`() =
        runTest {
            whenever(
                movChaveRepository.setOutside(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movChaveRepository.get(1)
            ).thenReturn(
                Result.success(
                    MovChave(
                        uuidMainMovChave = "UUID"
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
                typeMov = TypeMovKey.RECEIPT,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISaveMovChave -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in MovChaveRepository save - RECEIPT`() =
        runTest {
            whenever(
                movChaveRepository.setOutside(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movChaveRepository.get(1)
            ).thenReturn(
                Result.success(
                    MovChave(
                        uuidMainMovChave = "UUID"
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
                movChaveRepository.save(
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
                "ISaveMovChave -> Unknown Error"
            )
        }

    @Test
    fun `Check return correct if function execute successfully - RECEIPT`() =
        runTest {
            whenever(
                movChaveRepository.setOutside(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movChaveRepository.get(1)
            ).thenReturn(
                Result.success(
                    MovChave(
                        uuidMainMovChave = "UUID"
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
                movChaveRepository.save(
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

}