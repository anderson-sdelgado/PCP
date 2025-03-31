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

    private val configRepository = mock<ConfigRepository>()
    private val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
    private val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
    private val startProcessSendData = mock<StartProcessSendData>()
    private val usecase = ISaveMovEquipVisitTerc(
        configRepository,
        movEquipVisitTercRepository,
        movEquipVisitTercPassagRepository,
        startProcessSendData,
    )

    @Test
    fun `Chech return failure if have error in ConfigRepository CloseMovVisitTerc`() =
        runTest {
            whenever(
                movEquipVisitTercRepository.setOutside(1)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(
                typeMov = TypeMovEquip.OUTPUT,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISaveMovEquipVisitTerc -> Unknown Error"
            )
        }

    @Test
    fun `Chech return failure if have error in ConfigRepository getConfig`() =
        runTest {
            whenever(
                configRepository.getConfig()
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(
                typeMov = TypeMovEquip.OUTPUT,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISaveMovEquipVisitTerc -> Unknown Error"
            )
        }

    @Test
    fun `Chech return failure if have error in MovEquipVisitTercRepository Save`() =
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
                movEquipVisitTercRepository.save(19759, 1)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(
                typeMov = TypeMovEquip.OUTPUT,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISaveMovEquipVisitTerc -> Unknown Error"
            )
        }

    @Test
    fun `Chech return failure if have error in MovEquipVisitTercPassagRepository Save`() =
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
            val result = usecase(
                typeMov = TypeMovEquip.OUTPUT,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISaveMovEquipVisitTerc -> Unknown Error"
            )
        }

    @Test
    fun `Chech return true if SaveMovEquipVisitTercImpl execute successfully`() =
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
                movEquipVisitTercRepository.save(19759, 1)
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                movEquipVisitTercPassagRepository.save(1)
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(
                typeMov = TypeMovEquip.OUTPUT,
                id = 0
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.isSuccess,
                true
            )
        }
}