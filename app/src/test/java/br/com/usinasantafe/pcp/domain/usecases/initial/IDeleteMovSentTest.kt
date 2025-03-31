package br.com.usinasantafe.pcp.domain.usecases.initial

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipProprio
import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipResidencia
import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipVisitTerc
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioEquipSegRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioPassagRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcp.utils.StatusData
import br.com.usinasantafe.pcp.utils.StatusForeigner
import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.utils.TypeMovEquip
import br.com.usinasantafe.pcp.utils.TypeVisitTerc
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.Date

class IDeleteMovSentTest {

    private val movEquipProprioRepository = mock<MovEquipProprioRepository>()
    private val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
    private val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
    private val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
    private val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
    private val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()

    private val usecase = IDeleteMovSent(
        movEquipProprioRepository,
        movEquipProprioPassagRepository,
        movEquipProprioEquipSegRepository,
        movEquipVisitTercRepository,
        movEquipVisitTercPassagRepository,
        movEquipResidenciaRepository
    )

    @Test
    fun `Check return failure if have error in movEquipProprioRepository listSent without data`() =
        runTest {
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IDeleteMovSent"
            )
        }

    @Test
    fun `Check return failure if have error in movEquipProprioRepository listSent`() =
        runTest {
            whenever(
                movEquipProprioRepository.listSent()
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
                "IDeleteMovSent -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in movEquipProprioRepository delete`() =
        runTest {
            val movProprio1 = MovEquipProprio(
                idMovEquipProprio = 1,
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 10,
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = Date(),
                idEquipMovEquipProprio = 10,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND,
            )
            val movProprio2 = MovEquipProprio(
                idMovEquipProprio = 2,
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 10,
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = Date(1723213270250),
                idEquipMovEquipProprio = 10,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND,
            )
            val movProprioList = listOf(
                movProprio1,
                movProprio2
            )
            whenever(
                movEquipProprioRepository.listSent()
            ).thenReturn(
                Result.success(movProprioList)
            )
            whenever(
                movEquipProprioRepository.delete(2)
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
                "IDeleteMovSent -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in movEquipProprioPassagRepository delete`() =
        runTest {
            val movProprio1 = MovEquipProprio(
                idMovEquipProprio = 1,
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 10,
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = Date(),
                idEquipMovEquipProprio = 10,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND,
            )
            val movProprio2 = MovEquipProprio(
                idMovEquipProprio = 2,
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 10,
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = Date(1723213270250),
                idEquipMovEquipProprio = 10,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND,
            )
            val movProprioList = listOf(
                movProprio1,
                movProprio2
            )
            whenever(
                movEquipProprioRepository.listSent()
            ).thenReturn(
                Result.success(movProprioList)
            )
            whenever(
                movEquipProprioRepository.delete(2)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipProprioPassagRepository.delete(2)
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
                "IDeleteMovSent -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in movEquipProprioEquipSegRepository delete`() =
        runTest {
            val movProprio1 = MovEquipProprio(
                idMovEquipProprio = 1,
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 10,
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = Date(),
                idEquipMovEquipProprio = 10,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND,
            )
            val movProprio2 = MovEquipProprio(
                idMovEquipProprio = 2,
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 10,
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = Date(1723213270250),
                idEquipMovEquipProprio = 10,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND,
            )
            val movProprioList = listOf(
                movProprio1,
                movProprio2
            )
            whenever(
                movEquipProprioRepository.listSent()
            ).thenReturn(
                Result.success(movProprioList)
            )
            whenever(
                movEquipProprioRepository.delete(2)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipProprioPassagRepository.delete(2)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipProprioEquipSegRepository.delete(2)
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
                "IDeleteMovSent -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in movEquipVisitTercRepository listSent without data`() =
        runTest {
            val movProprio1 = MovEquipProprio(
                idMovEquipProprio = 1,
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 10,
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = Date(),
                idEquipMovEquipProprio = 10,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND,
            )
            val movProprio2 = MovEquipProprio(
                idMovEquipProprio = 2,
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 10,
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = Date(1723213270250),
                idEquipMovEquipProprio = 10,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND,
            )
            val movProprioList = listOf(
                movProprio1,
                movProprio2
            )
            whenever(
                movEquipProprioRepository.listSent()
            ).thenReturn(
                Result.success(movProprioList)
            )
            whenever(
                movEquipProprioRepository.delete(2)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipProprioPassagRepository.delete(2)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipProprioEquipSegRepository.delete(2)
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IDeleteMovSent"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun `Check return failure if have error in movEquipVisitTercRepository listSent`() =
        runTest {
            val movProprio1 = MovEquipProprio(
                idMovEquipProprio = 1,
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 10,
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = Date(),
                idEquipMovEquipProprio = 10,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND,
            )
            val movProprio2 = MovEquipProprio(
                idMovEquipProprio = 2,
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 10,
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = Date(1723213270250),
                idEquipMovEquipProprio = 10,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND,
            )
            val movProprioList = listOf(
                movProprio1,
                movProprio2
            )
            whenever(
                movEquipProprioRepository.listSent()
            ).thenReturn(
                Result.success(movProprioList)
            )
            whenever(
                movEquipProprioRepository.delete(2)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipProprioPassagRepository.delete(2)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipProprioEquipSegRepository.delete(2)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipVisitTercRepository.listSent()
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
                "IDeleteMovSent -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in movEquipVisitTercRepository delete`() =
        runTest {
            val movProprio1 = MovEquipProprio(
                idMovEquipProprio = 1,
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 10,
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = Date(),
                idEquipMovEquipProprio = 10,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND,
            )
            val movProprio2 = MovEquipProprio(
                idMovEquipProprio = 2,
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 10,
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = Date(1723213270250),
                idEquipMovEquipProprio = 10,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND,
            )
            val movVisitTerc1 = MovEquipVisitTerc(
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
            val movVisitTerc2 = MovEquipVisitTerc(
                idMovEquipVisitTerc = 2,
                dthrMovEquipVisitTerc = Date(1723213270250),
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                veiculoMovEquipVisitTerc = "GOL",
                placaMovEquipVisitTerc = "AAA-0000",
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.VISITANTE,
                idVisitTercMovEquipVisitTerc = 1,
                destinoMovEquipVisitTerc = "Teste Destino",
                observMovEquipVisitTerc = "Teste Observ"
            )
            val movProprioList = listOf(
                movProprio1,
                movProprio2
            )
            val movVisitTercList = listOf(
                movVisitTerc1,
                movVisitTerc2
            )
            whenever(
                movEquipProprioRepository.listSent()
            ).thenReturn(
                Result.success(movProprioList)
            )
            whenever(
                movEquipProprioRepository.delete(2)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipProprioPassagRepository.delete(2)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipProprioEquipSegRepository.delete(2)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipVisitTercRepository.listSent()
            ).thenReturn(
                Result.success(movVisitTercList)
            )
            whenever(
                movEquipVisitTercRepository.delete(2)
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
                "IDeleteMovSent -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in movEquipVisitTercPassagRepository delete`() =
        runTest {
            val movProprio1 = MovEquipProprio(
                idMovEquipProprio = 1,
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 10,
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = Date(),
                idEquipMovEquipProprio = 10,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND,
            )
            val movProprio2 = MovEquipProprio(
                idMovEquipProprio = 2,
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 10,
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = Date(1723213270250),
                idEquipMovEquipProprio = 10,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND,
            )
            val movVisitTerc1 = MovEquipVisitTerc(
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
            val movVisitTerc2 = MovEquipVisitTerc(
                idMovEquipVisitTerc = 2,
                dthrMovEquipVisitTerc = Date(1723213270250),
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                veiculoMovEquipVisitTerc = "GOL",
                placaMovEquipVisitTerc = "AAA-0000",
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.VISITANTE,
                idVisitTercMovEquipVisitTerc = 1,
                destinoMovEquipVisitTerc = "Teste Destino",
                observMovEquipVisitTerc = "Teste Observ"
            )
            val movProprioList = listOf(
                movProprio1,
                movProprio2
            )
            val movVisitTercList = listOf(
                movVisitTerc1,
                movVisitTerc2
            )
            whenever(
                movEquipProprioRepository.listSent()
            ).thenReturn(
                Result.success(movProprioList)
            )
            whenever(
                movEquipProprioRepository.delete(2)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipProprioPassagRepository.delete(2)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipProprioEquipSegRepository.delete(2)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipVisitTercRepository.listSent()
            ).thenReturn(
                Result.success(movVisitTercList)
            )
            whenever(
                movEquipVisitTercRepository.delete(2)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipVisitTercPassagRepository.delete(2)
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
                "IDeleteMovSent -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in movEquipResidenciaRepository listSent without data`() =
        runTest {
            val movProprio1 = MovEquipProprio(
                idMovEquipProprio = 1,
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 10,
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = Date(),
                idEquipMovEquipProprio = 10,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND,
            )
            val movProprio2 = MovEquipProprio(
                idMovEquipProprio = 2,
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 10,
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = Date(1723213270250),
                idEquipMovEquipProprio = 10,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND,
            )
            val movVisitTerc1 = MovEquipVisitTerc(
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
            val movVisitTerc2 = MovEquipVisitTerc(
                idMovEquipVisitTerc = 2,
                dthrMovEquipVisitTerc = Date(1723213270250),
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                veiculoMovEquipVisitTerc = "GOL",
                placaMovEquipVisitTerc = "AAA-0000",
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.VISITANTE,
                idVisitTercMovEquipVisitTerc = 1,
                destinoMovEquipVisitTerc = "Teste Destino",
                observMovEquipVisitTerc = "Teste Observ"
            )
            val movProprioList = listOf(
                movProprio1,
                movProprio2
            )
            val movVisitTercList = listOf(
                movVisitTerc1,
                movVisitTerc2
            )
            whenever(
                movEquipProprioRepository.listSent()
            ).thenReturn(
                Result.success(movProprioList)
            )
            whenever(
                movEquipProprioRepository.delete(2)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipProprioPassagRepository.delete(2)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipProprioEquipSegRepository.delete(2)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipVisitTercRepository.listSent()
            ).thenReturn(
                Result.success(movVisitTercList)
            )
            whenever(
                movEquipVisitTercRepository.delete(2)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipVisitTercPassagRepository.delete(2)
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IDeleteMovSent"
            )
        }

    @Test
    fun `Check return failure if have error in movEquipResidenciaRepository listSent`() =
        runTest {
            val movProprio1 = MovEquipProprio(
                idMovEquipProprio = 1,
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 10,
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = Date(),
                idEquipMovEquipProprio = 10,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND,
            )
            val movProprio2 = MovEquipProprio(
                idMovEquipProprio = 2,
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 10,
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = Date(1723213270250),
                idEquipMovEquipProprio = 10,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND,
            )
            val movVisitTerc1 = MovEquipVisitTerc(
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
            val movVisitTerc2 = MovEquipVisitTerc(
                idMovEquipVisitTerc = 2,
                dthrMovEquipVisitTerc = Date(1723213270250),
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                veiculoMovEquipVisitTerc = "GOL",
                placaMovEquipVisitTerc = "AAA-0000",
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.VISITANTE,
                idVisitTercMovEquipVisitTerc = 1,
                destinoMovEquipVisitTerc = "Teste Destino",
                observMovEquipVisitTerc = "Teste Observ"
            )
            val movProprioList = listOf(
                movProprio1,
                movProprio2
            )
            val movVisitTercList = listOf(
                movVisitTerc1,
                movVisitTerc2
            )
            whenever(
                movEquipProprioRepository.listSent()
            ).thenReturn(
                Result.success(movProprioList)
            )
            whenever(
                movEquipProprioRepository.delete(2)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipProprioPassagRepository.delete(2)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipProprioEquipSegRepository.delete(2)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipVisitTercRepository.listSent()
            ).thenReturn(
                Result.success(movVisitTercList)
            )
            whenever(
                movEquipVisitTercRepository.delete(2)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipVisitTercPassagRepository.delete(2)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipResidenciaRepository.listSent()
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
                "IDeleteMovSent -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in movEquipResidenciaRepository delete`() =
        runTest {
            val movProprio1 = MovEquipProprio(
                idMovEquipProprio = 1,
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 10,
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = Date(),
                idEquipMovEquipProprio = 10,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND,
            )
            val movProprio2 = MovEquipProprio(
                idMovEquipProprio = 2,
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 10,
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = Date(1723213270250),
                idEquipMovEquipProprio = 10,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND,
            )
            val movVisitTerc1 = MovEquipVisitTerc(
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
            val movVisitTerc2 = MovEquipVisitTerc(
                idMovEquipVisitTerc = 2,
                dthrMovEquipVisitTerc = Date(1723213270250),
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                veiculoMovEquipVisitTerc = "GOL",
                placaMovEquipVisitTerc = "AAA-0000",
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.VISITANTE,
                idVisitTercMovEquipVisitTerc = 1,
                destinoMovEquipVisitTerc = "Teste Destino",
                observMovEquipVisitTerc = "Teste Observ"
            )
            val movResidencia1 = MovEquipResidencia(
                idMovEquipResidencia = 1,
                matricVigiaMovEquipResidencia = 1000,
                idLocalMovEquipResidencia = 1000,
                tipoMovEquipResidencia = TypeMovEquip.INPUT,
                dthrMovEquipResidencia = Date(),
                motoristaMovEquipResidencia = "MOTORISTA TESTE",
                veiculoMovEquipResidencia = "VEICULO TESTE",
                placaMovEquipResidencia = "PLACA TESTE",
                statusMovEquipResidencia = StatusData.OPEN,
                statusSendMovEquipResidencia = StatusSend.SEND,
                statusMovEquipForeignerResidencia = StatusForeigner.INSIDE,
            )
            val movResidencia2 = MovEquipResidencia(
                idMovEquipResidencia = 2,
                matricVigiaMovEquipResidencia = 1000,
                idLocalMovEquipResidencia = 1000,
                tipoMovEquipResidencia = TypeMovEquip.INPUT,
                dthrMovEquipResidencia = Date(1723213270250),
                motoristaMovEquipResidencia = "MOTORISTA TESTE",
                veiculoMovEquipResidencia = "VEICULO TESTE",
                placaMovEquipResidencia = "PLACA TESTE",
                statusMovEquipResidencia = StatusData.OPEN,
                statusSendMovEquipResidencia = StatusSend.SEND,
                statusMovEquipForeignerResidencia = StatusForeigner.INSIDE,
            )
            val movProprioList = listOf(
                movProprio1,
                movProprio2
            )
            val movVisitTercList = listOf(
                movVisitTerc1,
                movVisitTerc2
            )
            val movResidenciaList = listOf(
                movResidencia1,
                movResidencia2
            )
            whenever(
                movEquipProprioRepository.listSent()
            ).thenReturn(
                Result.success(movProprioList)
            )
            whenever(
                movEquipProprioRepository.delete(2)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipProprioPassagRepository.delete(2)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipProprioEquipSegRepository.delete(2)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipVisitTercRepository.listSent()
            ).thenReturn(
                Result.success(movVisitTercList)
            )
            whenever(
                movEquipVisitTercRepository.delete(2)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipVisitTercPassagRepository.delete(2)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipResidenciaRepository.listSent()
            ).thenReturn(
                Result.success(movResidenciaList)
            )
            whenever(
                movEquipResidenciaRepository.delete(2)
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
                "IDeleteMovSent -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if DeleteMovSentImpl execute successfully`() =
        runTest {
            val movProprio1 = MovEquipProprio(
                idMovEquipProprio = 1,
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 10,
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = Date(),
                idEquipMovEquipProprio = 10,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND,
            )
            val movProprio2 = MovEquipProprio(
                idMovEquipProprio = 2,
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 10,
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = Date(1723213270250),
                idEquipMovEquipProprio = 10,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND,
            )
            val movVisitTerc1 = MovEquipVisitTerc(
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
            val movVisitTerc2 = MovEquipVisitTerc(
                idMovEquipVisitTerc = 2,
                dthrMovEquipVisitTerc = Date(1723213270250),
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                veiculoMovEquipVisitTerc = "GOL",
                placaMovEquipVisitTerc = "AAA-0000",
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.VISITANTE,
                idVisitTercMovEquipVisitTerc = 1,
                destinoMovEquipVisitTerc = "Teste Destino",
                observMovEquipVisitTerc = "Teste Observ"
            )
            val movResidencia1 = MovEquipResidencia(
                idMovEquipResidencia = 1,
                matricVigiaMovEquipResidencia = 1000,
                idLocalMovEquipResidencia = 1000,
                tipoMovEquipResidencia = TypeMovEquip.INPUT,
                dthrMovEquipResidencia = Date(),
                motoristaMovEquipResidencia = "MOTORISTA TESTE",
                veiculoMovEquipResidencia = "VEICULO TESTE",
                placaMovEquipResidencia = "PLACA TESTE",
                statusMovEquipResidencia = StatusData.OPEN,
                statusSendMovEquipResidencia = StatusSend.SEND,
                statusMovEquipForeignerResidencia = StatusForeigner.INSIDE,
            )
            val movResidencia2 = MovEquipResidencia(
                idMovEquipResidencia = 2,
                matricVigiaMovEquipResidencia = 1000,
                idLocalMovEquipResidencia = 1000,
                tipoMovEquipResidencia = TypeMovEquip.INPUT,
                dthrMovEquipResidencia = Date(1723213270250),
                motoristaMovEquipResidencia = "MOTORISTA TESTE",
                veiculoMovEquipResidencia = "VEICULO TESTE",
                placaMovEquipResidencia = "PLACA TESTE",
                statusMovEquipResidencia = StatusData.OPEN,
                statusSendMovEquipResidencia = StatusSend.SEND,
                statusMovEquipForeignerResidencia = StatusForeigner.INSIDE,
            )
            val movProprioList = listOf(
                movProprio1,
                movProprio2
            )
            val movVisitTercList = listOf(
                movVisitTerc1,
                movVisitTerc2
            )
            val movResidenciaList = listOf(
                movResidencia1,
                movResidencia2
            )
            whenever(
                movEquipProprioRepository.listSent()
            ).thenReturn(
                Result.success(movProprioList)
            )
            whenever(
                movEquipProprioRepository.delete(2)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipProprioPassagRepository.delete(2)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipProprioEquipSegRepository.delete(2)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipVisitTercRepository.listSent()
            ).thenReturn(
                Result.success(movVisitTercList)
            )
            whenever(
                movEquipVisitTercRepository.delete(2)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipVisitTercPassagRepository.delete(2)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipResidenciaRepository.listSent()
            ).thenReturn(
                Result.success(movResidenciaList)
            )
            whenever(
                movEquipResidenciaRepository.delete(2)
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

    @Test
    fun `Check return true if DeleteMovSentImpl execute successfully without data`() =
        runTest {
            whenever(
                movEquipProprioRepository.listSent()
            ).thenReturn(
                Result.success(emptyList())
            )
            whenever(
                movEquipVisitTercRepository.listSent()
            ).thenReturn(
                Result.success(emptyList())
            )
            whenever(
                movEquipResidenciaRepository.listSent()
            ).thenReturn(
                Result.success(emptyList())
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