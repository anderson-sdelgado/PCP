package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.entities.variable.Config
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcp.utils.TypeMovEquip
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class SaveMovEquipVisitTercImplTest {

    @Test
    fun `Chech return failure if have error in ConfigRepository CloseMovVisitTerc`() =
        runTest {
            val configRepository = mock<ConfigRepository>()
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(
                movEquipVisitTercRepository.setOutside(1)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val usecase = ISaveMovEquipVisitTerc(
                configRepository,
                movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository,
                startProcessSendData,
            )
            val result = usecase(
                typeMov = TypeMovEquip.OUTPUT,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Usecase -> CloseMovVisitTerc"
            )
        }

    @Test
    fun `Chech return failure if have error in ConfigRepository getConfig`() =
        runTest {
            val configRepository = mock<ConfigRepository>()
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(
                configRepository.getConfig()
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val usecase = ISaveMovEquipVisitTerc(
                configRepository,
                movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository,
                startProcessSendData
            )
            val result = usecase(
                typeMov = TypeMovEquip.OUTPUT,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> ConfigRepository.getConfig"
            )
        }

    @Test
    fun `Chech return failure if have error in MovEquipVisitTercRepository Save`() =
        runTest {
            val configRepository = mock<ConfigRepository>()
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
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
                movEquipVisitTercRepository.save(19759, 1)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val usecase = ISaveMovEquipVisitTerc(
                configRepository,
                movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository,
                startProcessSendData
            )
            val result = usecase(
                typeMov = TypeMovEquip.OUTPUT,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipVisitTercRepository.save"
            )
        }

    @Test
    fun `Chech return failure if have error in MovEquipVisitTercPassagRepository Save`() =
        runTest {
            val configRepository = mock<ConfigRepository>()
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
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
                movEquipVisitTercRepository.save(19759, 1)
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                movEquipVisitTercPassagRepository.save(1)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val usecase = ISaveMovEquipVisitTerc(
                configRepository,
                movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository,
                startProcessSendData
            )
            val result = usecase(
                typeMov = TypeMovEquip.OUTPUT,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipVisitTercPassagRepository.save"
            )
        }

    @Test
    fun `Chech return true if SaveMovEquipVisitTercImpl execute successfully`() =
        runTest {
            val configRepository = mock<ConfigRepository>()
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
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
                movEquipVisitTercRepository.save(19759, 1)
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                movEquipVisitTercPassagRepository.save(1)
            ).thenReturn(
                Result.success(true)
            )
            val usecase = ISaveMovEquipVisitTerc(
                configRepository,
                movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository,
                startProcessSendData
            )
            val result = usecase(
                typeMov = TypeMovEquip.OUTPUT,
                id = 0
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }
}