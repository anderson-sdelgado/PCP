package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.entities.variable.Config
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioEquipSegRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioPassagRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ISaveMovEquipProprioTest {

    private val configRepository = mock<ConfigRepository>()
    private val movEquipProprioRepository = mock<MovEquipProprioRepository>()
    private val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
    private val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
    private val startProcessSendData = mock<StartProcessSendData>()
    private val usecase = ISaveMovEquipProprio(
        configRepository,
        movEquipProprioRepository,
        movEquipProprioPassagRepository,
        movEquipProprioEquipSegRepository,
        startProcessSendData
    )

    @Test
    fun `Check return failure if have error in ConfigRepository getConfig`() =
        runTest {
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
                "ISaveMovEquipProprio -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if ConfigRepository getConfig return object Config null`() =
        runTest {
            whenever(
                configRepository.getConfig()
            ).thenReturn(
                Result.success(
                    Config()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISaveMovEquipProprio"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause!!.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun `Check return failure if have error in process save in MovEquipProprioRepository`() =
        runTest {
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
                movEquipProprioRepository.save(
                    matricVigia = 19759,
                    idLocal = 1
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
                "ISaveMovEquipProprio -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if  have error in MovEquipProprioPassagRepository save`() =
        runTest {
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
                movEquipProprioRepository.save(
                    matricVigia = 19759,
                    idLocal = 1
                )
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                movEquipProprioPassagRepository.save(1)
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
                "ISaveMovEquipProprio -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if  have error in MovEquipProprioEquipSegRepository save`() =
        runTest {
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
                movEquipProprioRepository.save(
                    matricVigia = 19759,
                    idLocal = 1
                )
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                movEquipProprioPassagRepository.save(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipProprioEquipSegRepository.save(1)
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
                "ISaveMovEquipProprio -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if SaveMovEquipProprio execute successfully`() =
        runTest {
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
                movEquipProprioRepository.save(
                    matricVigia = 19759,
                    idLocal = 1
                )
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                movEquipProprioPassagRepository.save(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipProprioEquipSegRepository.save(1)
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase()
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