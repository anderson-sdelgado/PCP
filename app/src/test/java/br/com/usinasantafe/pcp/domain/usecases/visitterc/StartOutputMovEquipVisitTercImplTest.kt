package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipVisitTerc
import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipVisitTercPassag
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.StatusForeigner
import br.com.usinasantafe.pcp.utils.TypeMovEquip
import br.com.usinasantafe.pcp.utils.TypeVisitTerc
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.Date

class StartOutputMovEquipVisitTercImplTest {

    private val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
    private val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()

    private val usecase = IStartOutputMovEquipVisitTerc(
        movEquipVisitTercRepository,
        movEquipVisitTercPassagRepository
    )

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository get`() =
        runTest {
            whenever(
                movEquipVisitTercRepository.get(1)
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
                "IStartOutputMovEquipVisitTerc -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository start`() =
        runTest {
            val movEquipVisitTerc = MovEquipVisitTerc(
                idMovEquipVisitTerc = 1,
                dthrMovEquipVisitTerc = Date(),
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                veiculoMovEquipVisitTerc = "GOL",
                placaMovEquipVisitTerc = "AAA-0000",
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.VISITANTE,
                idVisitTercMovEquipVisitTerc = 1,
                destinoMovEquipVisitTerc = "Teste Destino",
                observMovEquipVisitTerc = "Teste Observ"
            )
            whenever(
                movEquipVisitTercRepository.get(1)
            ).thenReturn(
                Result.success(
                    movEquipVisitTerc
                )
            )
            movEquipVisitTerc.tipoMovEquipVisitTerc = TypeMovEquip.OUTPUT
            movEquipVisitTerc.dthrMovEquipVisitTerc = Date()
            movEquipVisitTerc.destinoMovEquipVisitTerc = null
            movEquipVisitTerc.statusMovEquipForeignerVisitTerc = StatusForeigner.OUTSIDE
            whenever(
                movEquipVisitTercRepository.start(movEquipVisitTerc)
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
                "IStartOutputMovEquipVisitTerc -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in movEquipVisitTercPassagRepository list`() =
        runTest {
            val movEquipVisitTerc = MovEquipVisitTerc(
                idMovEquipVisitTerc = 1,
                dthrMovEquipVisitTerc = Date(),
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                veiculoMovEquipVisitTerc = "GOL",
                placaMovEquipVisitTerc = "AAA-0000",
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.VISITANTE,
                idVisitTercMovEquipVisitTerc = 1,
                destinoMovEquipVisitTerc = "Teste Destino",
                observMovEquipVisitTerc = "Teste Observ"
            )
            whenever(
                movEquipVisitTercRepository.get(1)
            ).thenReturn(
                Result.success(
                    movEquipVisitTerc
                )
            )
            movEquipVisitTerc.tipoMovEquipVisitTerc = TypeMovEquip.OUTPUT
            movEquipVisitTerc.dthrMovEquipVisitTerc = Date()
            movEquipVisitTerc.destinoMovEquipVisitTerc = null
            movEquipVisitTerc.statusMovEquipForeignerVisitTerc = StatusForeigner.OUTSIDE
            whenever(
                movEquipVisitTercRepository.start(movEquipVisitTerc)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipVisitTercPassagRepository.list(
                    FlowApp.CHANGE,
                    1
                )
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
                "IStartOutputMovEquipVisitTerc -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if StartOutputMovEquipVisitTerc execute successfully and list empty`() =
        runTest {
            val movEquipVisitTerc = MovEquipVisitTerc(
                idMovEquipVisitTerc = 1,
                dthrMovEquipVisitTerc = Date(),
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                veiculoMovEquipVisitTerc = "GOL",
                placaMovEquipVisitTerc = "AAA-0000",
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.VISITANTE,
                idVisitTercMovEquipVisitTerc = 1,
                destinoMovEquipVisitTerc = "Teste Destino",
                observMovEquipVisitTerc = "Teste Observ"
            )
            whenever(
                movEquipVisitTercRepository.get(1)
            ).thenReturn(
                Result.success(
                    movEquipVisitTerc
                )
            )
            movEquipVisitTerc.tipoMovEquipVisitTerc = TypeMovEquip.OUTPUT
            movEquipVisitTerc.dthrMovEquipVisitTerc = Date()
            movEquipVisitTerc.destinoMovEquipVisitTerc = null
            movEquipVisitTerc.statusMovEquipForeignerVisitTerc = StatusForeigner.OUTSIDE
            whenever(
                movEquipVisitTercRepository.start(movEquipVisitTerc)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipVisitTercPassagRepository.list(
                    FlowApp.CHANGE,
                    1
                )
            ).thenReturn(
                Result.success(
                    emptyList()
                )
            )
            val result = usecase(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.isSuccess,
                true
            )
        }

    @Test
    fun `Check return failure if have error in movEquipVisitTercPassagRepository add`() =
        runTest {
            val movEquipVisitTerc = MovEquipVisitTerc(
                idMovEquipVisitTerc = 1,
                dthrMovEquipVisitTerc = Date(),
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                veiculoMovEquipVisitTerc = "GOL",
                placaMovEquipVisitTerc = "AAA-0000",
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.VISITANTE,
                idVisitTercMovEquipVisitTerc = 1,
                destinoMovEquipVisitTerc = "Teste Destino",
                observMovEquipVisitTerc = "Teste Observ"
            )
            whenever(
                movEquipVisitTercRepository.get(1)
            ).thenReturn(
                Result.success(
                    movEquipVisitTerc
                )
            )
            movEquipVisitTerc.tipoMovEquipVisitTerc = TypeMovEquip.OUTPUT
            movEquipVisitTerc.dthrMovEquipVisitTerc = Date()
            movEquipVisitTerc.destinoMovEquipVisitTerc = null
            movEquipVisitTerc.statusMovEquipForeignerVisitTerc = StatusForeigner.OUTSIDE
            whenever(
                movEquipVisitTercRepository.start(movEquipVisitTerc)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipVisitTercPassagRepository.list(
                    FlowApp.CHANGE,
                    1
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        MovEquipVisitTercPassag(
                            idMovEquipVisitTercPassag = 1,
                            idVisitTerc = 10,
                            idMovEquipVisitTerc = 1
                        )
                    )
                )
            )
            whenever(
                movEquipVisitTercPassagRepository.add(
                    idVisitTerc = 10,
                    flowApp = FlowApp.ADD,
                    id = 0
                )
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
                "IStartOutputMovEquipVisitTerc -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if StartOutputMovEquipVisitTerc execute successfully`() =
        runTest {
            val movEquipVisitTerc = MovEquipVisitTerc(
                idMovEquipVisitTerc = 1,
                dthrMovEquipVisitTerc = Date(),
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                veiculoMovEquipVisitTerc = "GOL",
                placaMovEquipVisitTerc = "AAA-0000",
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.VISITANTE,
                idVisitTercMovEquipVisitTerc = 1,
                destinoMovEquipVisitTerc = "Teste Destino",
                observMovEquipVisitTerc = "Teste Observ"
            )
            whenever(
                movEquipVisitTercRepository.get(1)
            ).thenReturn(
                Result.success(
                    movEquipVisitTerc
                )
            )
            movEquipVisitTerc.tipoMovEquipVisitTerc = TypeMovEquip.OUTPUT
            movEquipVisitTerc.dthrMovEquipVisitTerc = Date()
            movEquipVisitTerc.destinoMovEquipVisitTerc = null
            movEquipVisitTerc.statusMovEquipForeignerVisitTerc = StatusForeigner.OUTSIDE
            whenever(
                movEquipVisitTercRepository.start(movEquipVisitTerc)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipVisitTercPassagRepository.list(
                    FlowApp.CHANGE,
                    1
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        MovEquipVisitTercPassag(
                            idMovEquipVisitTercPassag = 1,
                            idVisitTerc = 10,
                            idMovEquipVisitTerc = 1
                        )
                    )
                )
            )
            whenever(
                movEquipVisitTercPassagRepository.add(
                    idVisitTerc = 10,
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(1)
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