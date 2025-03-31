package br.com.usinasantafe.pcp.domain.usecases.residencia

import br.com.usinasantafe.pcp.domain.entities.variable.Config
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcp.utils.TypeMovEquip
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ISaveMovEquipResidenciaTest {

    private val configRepository = mock<ConfigRepository>()
    private val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
    private val startProcessSendData = mock<StartProcessSendData>()
    private val usecase = ISaveMovEquipResidencia(
        configRepository,
        movEquipResidenciaRepository,
        startProcessSendData,
    )

    @Test
    fun `Check return failure if have error in ConfigRepository OutsideMovResidencia`() =
        runTest {
            whenever(
                movEquipResidenciaRepository.setOutside(1)
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
                "ISaveMovEquipResidencia -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in ConfigRepository GetConfig`() =
        runTest {
            whenever(
                movEquipResidenciaRepository.setOutside(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                configRepository.getConfig()
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
                "ISaveMovEquipResidencia -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipResidenciaRepository Save`() =
        runTest {
            whenever(
                movEquipResidenciaRepository.setOutside(1)
            ).thenReturn(
                Result.success(true)
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
                movEquipResidenciaRepository.save(19759, 1)
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
                "ISaveMovEquipResidencia -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if SaveMovEquipResidenciaImpl execute successfully`() =
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
                movEquipResidenciaRepository.save(19759, 1)
            ).thenReturn(
                Result.success(1)
            )
            val usecase = ISaveMovEquipResidencia(
                configRepository,
                movEquipResidenciaRepository,
                startProcessSendData
            )
            val result = usecase(
                typeMov = TypeMovEquip.OUTPUT,
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