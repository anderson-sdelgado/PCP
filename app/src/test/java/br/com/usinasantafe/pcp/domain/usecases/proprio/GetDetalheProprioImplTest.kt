package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipProprio
import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipProprioEquipSeg
import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipProprioPassag
import br.com.usinasantafe.pcp.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pcp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioEquipSegRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioPassagRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.StatusData
import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.utils.TypeMovEquip
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.Date

class GetDetalheProprioImplTest {

    @Test
    fun `Check return failure if have error in MovEquipProprioRepository get`() = runTest {
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
        val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
        val equipRepository = mock<EquipRepository>()
        val colabRepository = mock<ColabRepository>()
        whenever(
            movEquipProprioRepository.get(1)
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val usecase = IGetDetalheProprio(
            movEquipProprioRepository,
            movEquipProprioEquipSegRepository,
            movEquipProprioPassagRepository,
            equipRepository,
            colabRepository
        )
        val result = usecase(1)
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Repository -> MovEquipProprioRepository.get"
        )
    }

    @Test
    fun `Check return failure if have error in EquipRepository getNro`() = runTest {
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
        val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
        val equipRepository = mock<EquipRepository>()
        val colabRepository = mock<ColabRepository>()
        whenever(movEquipProprioRepository.get(1)).thenReturn(
            Result.success(
                MovEquipProprio(
                    idMovEquipProprio = 1,
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
            )
        )
        whenever(
            equipRepository.getNro(10)
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val usecase = IGetDetalheProprio(
            movEquipProprioRepository,
            movEquipProprioEquipSegRepository,
            movEquipProprioPassagRepository,
            equipRepository,
            colabRepository
        )
        val result = usecase(1)
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Repository -> EquipRepository.getNro"
        )
    }

    @Test
    fun `Check return failure if have error in MovEquipProprioEquipSegRepository list`() = runTest {
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
        val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
        val equipRepository = mock<EquipRepository>()
        val colabRepository = mock<ColabRepository>()
        whenever(
            movEquipProprioRepository.get(1)
        ).thenReturn(
            Result.success(
                MovEquipProprio(
                    idMovEquipProprio = 1,
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
            )
        )
        whenever(
            equipRepository.getNro(10)
        ).thenReturn(
            Result.success(100)
        )
        whenever(
            movEquipProprioEquipSegRepository.list(
                flowApp = FlowApp.CHANGE,
                id = 1
            )
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val usecase = IGetDetalheProprio(
            movEquipProprioRepository,
            movEquipProprioEquipSegRepository,
            movEquipProprioPassagRepository,
            equipRepository,
            colabRepository
        )
        val result = usecase(1)
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Repository -> MovEquipProprioEquipSegRepository.list"
        )
    }

    @Test
    fun `Check return failure if have error in EquipRepository getNro EquipSeg`() = runTest {
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
        val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
        val equipRepository = mock<EquipRepository>()
        val colabRepository = mock<ColabRepository>()
        whenever(
            movEquipProprioRepository.get(1)
        ).thenReturn(
            Result.success(
                MovEquipProprio(
                    idMovEquipProprio = 1,
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
                    movEquipProprioEquipSegList = listOf(
                        MovEquipProprioEquipSeg(
                            idMovEquipProprioEquipSeg = 1,
                            idMovEquipProprio = 1,
                            idEquip = 100
                        )
                    ),
                    movEquipProprioPassagList = listOf(
                        MovEquipProprioPassag(
                            idMovEquipProprioPassag = 1,
                            idMovEquipProprio = 1,
                            matricColab = 19035
                        )
                    )
                )
            )
        )
        whenever(
            equipRepository.getNro(10)
        ).thenReturn(
            Result.success(100)
        )
        whenever(
            movEquipProprioEquipSegRepository.list(
                flowApp = FlowApp.CHANGE,
                id = 1
            )
        ).thenReturn(
            Result.success(
                listOf(
                    MovEquipProprioEquipSeg(
                        idMovEquipProprioEquipSeg = 1,
                        idMovEquipProprio = 1,
                        idEquip = 100
                    )
                )
            )
        )
        whenever(
            equipRepository.getNro(
                idEquip = 100
            )
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val usecase = IGetDetalheProprio(
            movEquipProprioRepository,
            movEquipProprioEquipSegRepository,
            movEquipProprioPassagRepository,
            equipRepository,
            colabRepository
        )
        val result = usecase(1)
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Repository -> EquipRepository.getNro"
        )
    }

    @Test
    fun `Check return failure if have error in ColabRepository getNome`() = runTest {
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
        val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
        val equipRepository = mock<EquipRepository>()
        val colabRepository = mock<ColabRepository>()
        whenever(
            movEquipProprioRepository.get(1)
        ).thenReturn(
            Result.success(
                MovEquipProprio(
                    idMovEquipProprio = 1,
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
                    movEquipProprioEquipSegList = listOf(
                        MovEquipProprioEquipSeg(
                            idMovEquipProprioEquipSeg = 1,
                            idMovEquipProprio = 1,
                            idEquip = 100
                        )
                    ),
                    movEquipProprioPassagList = listOf(
                        MovEquipProprioPassag(
                            idMovEquipProprioPassag = 1,
                            idMovEquipProprio = 1,
                            matricColab = 19035
                        )
                    )
                )
            )
        )
        whenever(
            equipRepository.getNro(10)
        ).thenReturn(
            Result.success(100)
        )
        whenever(
            movEquipProprioEquipSegRepository.list(
                flowApp = FlowApp.CHANGE,
                id = 1
            )
        ).thenReturn(
            Result.success(
                listOf(
                    MovEquipProprioEquipSeg(
                        idMovEquipProprioEquipSeg = 1,
                        idMovEquipProprio = 1,
                        idEquip = 100
                    )
                )
            )
        )
        whenever(
            equipRepository.getNro(idEquip = 100)
        ).thenReturn(
            Result.success(200)
        )
        whenever(
            colabRepository.getNome(19759)
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val usecase = IGetDetalheProprio(
            movEquipProprioRepository,
            movEquipProprioEquipSegRepository,
            movEquipProprioPassagRepository,
            equipRepository,
            colabRepository
        )
        val result = usecase(1)
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Repository -> ColabRepository.getNome"
        )
    }

    @Test
    fun `Check return failure if have error in MovEquipProprioPassagRepository list`() = runTest {
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
        val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
        val equipRepository = mock<EquipRepository>()
        val colabRepository = mock<ColabRepository>()
        whenever(
            movEquipProprioRepository.get(1)
        ).thenReturn(
            Result.success(
                MovEquipProprio(
                    idMovEquipProprio = 1,
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
                    movEquipProprioEquipSegList = listOf(
                        MovEquipProprioEquipSeg(
                            idMovEquipProprioEquipSeg = 1,
                            idMovEquipProprio = 1,
                            idEquip = 100
                        )
                    ),
                    movEquipProprioPassagList = listOf(
                        MovEquipProprioPassag(
                            idMovEquipProprioPassag = 1,
                            idMovEquipProprio = 1,
                            matricColab = 19035
                        )
                    )
                )
            )
        )
        whenever(
            equipRepository.getNro(10)
        ).thenReturn(
            Result.success(100)
        )
        whenever(
            movEquipProprioEquipSegRepository.list(
                flowApp = FlowApp.CHANGE,
                id = 1
            )
        ).thenReturn(
            Result.success(
                listOf(
                    MovEquipProprioEquipSeg(
                        idMovEquipProprioEquipSeg = 1,
                        idMovEquipProprio = 1,
                        idEquip = 100
                    )
                )
            )
        )
        whenever(
            equipRepository.getNro(100)
        ).thenReturn(
            Result.success(200)
        )
        whenever(
            colabRepository.getNome(19759)
        ).thenReturn(
            Result.success("ANDERSON DA SILVA DELGADO")
        )
        whenever(
            movEquipProprioPassagRepository.list(
                flowApp = FlowApp.CHANGE,
                id = 1
            )
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val usecase = IGetDetalheProprio(
            movEquipProprioRepository,
            movEquipProprioEquipSegRepository,
            movEquipProprioPassagRepository,
            equipRepository,
            colabRepository
        )
        val result = usecase(1)
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Repository -> MovEquipProprioPassagRepository.list"
        )
    }

    @Test
    fun `Check return failure if have error in ColabRepository getNome Passag`() = runTest {
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
        val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
        val equipRepository = mock<EquipRepository>()
        val colabRepository = mock<ColabRepository>()
        whenever(
            movEquipProprioRepository.get(1)
        ).thenReturn(
            Result.success(
                MovEquipProprio(
                    idMovEquipProprio = 1,
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
                    movEquipProprioEquipSegList = listOf(
                        MovEquipProprioEquipSeg(
                            idMovEquipProprioEquipSeg = 1,
                            idMovEquipProprio = 1,
                            idEquip = 100
                        )
                    ),
                    movEquipProprioPassagList = listOf(
                        MovEquipProprioPassag(
                            idMovEquipProprioPassag = 1,
                            idMovEquipProprio = 1,
                            matricColab = 19035
                        )
                    )
                )
            )
        )
        whenever(
            equipRepository.getNro(10)
        ).thenReturn(
            Result.success(100)
        )
        whenever(
            movEquipProprioEquipSegRepository.list(
                flowApp = FlowApp.CHANGE,
                id = 1
            )
        ).thenReturn(
            Result.success(
                listOf(
                    MovEquipProprioEquipSeg(
                        idMovEquipProprioEquipSeg = 1,
                        idMovEquipProprio = 1,
                        idEquip = 100
                    )
                )
            )
        )
        whenever(
            equipRepository.getNro(100)
        ).thenReturn(
            Result.success(200)
        )
        whenever(
            colabRepository.getNome(19759)
        ).thenReturn(
            Result.success("ANDERSON DA SILVA DELGADO")
        )
        whenever(
            movEquipProprioPassagRepository.list(
                flowApp = FlowApp.CHANGE,
                id = 1
            )
        ).thenReturn(
            Result.success(
                listOf(
                    MovEquipProprioPassag(
                        idMovEquipProprioPassag = 1,
                        idMovEquipProprio = 1,
                        matricColab = 19035
                    )
                )
            )
        )
        whenever(
            colabRepository.getNome(19035)
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val usecase = IGetDetalheProprio(
            movEquipProprioRepository,
            movEquipProprioEquipSegRepository,
            movEquipProprioPassagRepository,
            equipRepository,
            colabRepository
        )
        val result = usecase(1)
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Repository -> ColabRepository.getNome"
        )
    }

    @Test
    fun `Check return model if RecoverDetalheProprio execute with success`() = runTest {
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
        val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
        val equipRepository = mock<EquipRepository>()
        val colabRepository = mock<ColabRepository>()
        whenever(
            movEquipProprioRepository.get(1)
        ).thenReturn(
            Result.success(
                MovEquipProprio(
                    idMovEquipProprio = 1,
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
                    movEquipProprioEquipSegList = listOf(
                        MovEquipProprioEquipSeg(
                            idMovEquipProprioEquipSeg = 1,
                            idMovEquipProprio = 1,
                            idEquip = 100
                        )
                    ),
                    movEquipProprioPassagList = listOf(
                        MovEquipProprioPassag(
                            idMovEquipProprioPassag = 1,
                            idMovEquipProprio = 1,
                            matricColab = 19035
                        )
                    )
                )
            )
        )
        whenever(
            equipRepository.getNro(10)
        ).thenReturn(
            Result.success(100)
        )
        whenever(
            movEquipProprioEquipSegRepository.list(
                flowApp = FlowApp.CHANGE,
                id = 1
            )
        ).thenReturn(
            Result.success(
                listOf(
                    MovEquipProprioEquipSeg(
                        idMovEquipProprioEquipSeg = 1,
                        idMovEquipProprio = 1,
                        idEquip = 100
                    )
                )
            )
        )
        whenever(
            equipRepository.getNro(100)
        ).thenReturn(
            Result.success(200)
        )
        whenever(
            colabRepository.getNome(19759)
        ).thenReturn(
            Result.success("ANDERSON DA SILVA DELGADO")
        )
        whenever(
            movEquipProprioPassagRepository.list(
                flowApp = FlowApp.CHANGE,
                id = 1
            )
        ).thenReturn(
            Result.success(
                listOf(
                    MovEquipProprioPassag(
                        idMovEquipProprioPassag = 1,
                        idMovEquipProprio = 1,
                        matricColab = 19035
                    )
                )
            )
        )
        whenever(
            colabRepository.getNome(19035)
        ).thenReturn(
            Result.success("JOSE DONIZETE")
        )
        val usecase = IGetDetalheProprio(
            movEquipProprioRepository,
            movEquipProprioEquipSegRepository,
            movEquipProprioPassagRepository,
            equipRepository,
            colabRepository
        )
        val result = usecase(1)
        assertTrue(result.isSuccess)
        val model = result.getOrNull()!!
        assertEquals(model.dthr, "09/08/2024 11:21")
        assertEquals(model.veiculoSec, "200 - ")
    }

    @Test
    fun `Check return model if Equip Seg is empty`() = runTest {
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
        val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
        val equipRepository = mock<EquipRepository>()
        val colabRepository = mock<ColabRepository>()
        whenever(
            movEquipProprioRepository.get(1)
        ).thenReturn(
            Result.success(
                MovEquipProprio(
                    idMovEquipProprio = 1,
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
                    movEquipProprioEquipSegList = listOf(
                        MovEquipProprioEquipSeg(
                            idMovEquipProprioEquipSeg = 1,
                            idMovEquipProprio = 1,
                            idEquip = 100
                        )
                    ),
                    movEquipProprioPassagList = listOf(
                        MovEquipProprioPassag(
                            idMovEquipProprioPassag = 1,
                            idMovEquipProprio = 1,
                            matricColab = 19035
                        )
                    )
                )
            )
        )
        whenever(
            equipRepository.getNro(10)
        ).thenReturn(
            Result.success(100)
        )
        whenever(
            movEquipProprioEquipSegRepository.list(
                flowApp = FlowApp.CHANGE,
                id = 1
            )
        ).thenReturn(
            Result.success(
                listOf()
            )
        )
        whenever(
            colabRepository.getNome(19759)
        ).thenReturn(
            Result.success("ANDERSON DA SILVA DELGADO")
        )
        whenever(
            movEquipProprioPassagRepository.list(
                flowApp = FlowApp.CHANGE,
                id = 1
            )
        ).thenReturn(
            Result.success(
                listOf(
                    MovEquipProprioPassag(
                        idMovEquipProprioPassag = 1,
                        idMovEquipProprio = 1,
                        matricColab = 19035
                    )
                )
            )
        )
        whenever(
            colabRepository.getNome(19035)
        ).thenReturn(
            Result.success("JOSE DONIZETE")
        )
        val usecase = IGetDetalheProprio(
            movEquipProprioRepository,
            movEquipProprioEquipSegRepository,
            movEquipProprioPassagRepository,
            equipRepository,
            colabRepository
        )
        val result = usecase(1)
        assertTrue(result.isSuccess)
        val model = result.getOrNull()!!
        assertEquals(model.dthr, "09/08/2024 11:21")
        assertEquals(model.veiculoSec, "")
    }
}