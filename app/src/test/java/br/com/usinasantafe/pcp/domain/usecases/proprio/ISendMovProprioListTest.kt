package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.entities.variable.Config
import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipProprio
import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipProprioEquipSeg
import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipProprioPassag
import br.com.usinasantafe.pcp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioEquipSegRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioPassagRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.StatusData
import br.com.usinasantafe.pcp.utils.StatusSend
import br.com.usinasantafe.pcp.utils.TypeMovEquip
import br.com.usinasantafe.pcp.utils.token
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.Date

class ISendMovProprioListTest {

    private val movEquipProprioRepository = mock<MovEquipProprioRepository>()
    private val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
    private val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
    private val configRepository = mock<ConfigRepository>()
    private val usecase = ISendMovProprioList(
        movEquipProprioRepository,
        movEquipProprioEquipSegRepository,
        movEquipProprioPassagRepository,
        configRepository
    )

    @Test
    fun `Check return failure if have error in MovEquipProprioRepository listSend`() =
        runTest {
            whenever(
                movEquipProprioRepository.listSend()
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
                "ISendMovProprioList -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipProprioEquipSegRepository list`() =
        runTest {
            whenever(
                movEquipProprioRepository.listSend()
            ).thenReturn(
                Result.success(
                    listOf(
                        MovEquipProprio(
                            idMovEquipProprio = 1,
                            matricVigiaMovEquipProprio = 19759,
                            idLocalMovEquipProprio = 1,
                            tipoMovEquipProprio = TypeMovEquip.INPUT,
                            dthrMovEquipProprio = Date(1723213270250),
                            idEquipMovEquipProprio = 1,
                            matricColabMovEquipProprio = 19759,
                            destinoMovEquipProprio = "TESTE DESTINO",
                            notaFiscalMovEquipProprio = 123456789,
                            observMovEquipProprio = "TESTE OBSERV",
                            statusMovEquipProprio = StatusData.OPEN,
                            statusSendMovEquipProprio = StatusSend.SEND
                        )
                    )
                )
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
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISendMovProprioList -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipProprioPassagRepository list`() =
        runTest {
            whenever(
                movEquipProprioRepository.listSend()
            ).thenReturn(
                Result.success(
                    listOf(
                        MovEquipProprio(
                            idMovEquipProprio = 1,
                            matricVigiaMovEquipProprio = 19759,
                            idLocalMovEquipProprio = 1,
                            tipoMovEquipProprio = TypeMovEquip.INPUT,
                            dthrMovEquipProprio = Date(1723213270250),
                            idEquipMovEquipProprio = 1,
                            matricColabMovEquipProprio = 19759,
                            destinoMovEquipProprio = "TESTE DESTINO",
                            notaFiscalMovEquipProprio = 123456789,
                            observMovEquipProprio = "TESTE OBSERV",
                            statusMovEquipProprio = StatusData.OPEN,
                            statusSendMovEquipProprio = StatusSend.SEND
                        )
                    )
                )
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
                            idEquip = 1
                        )
                    )
                )
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
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISendMovProprioList -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in ConfigRepository getConfig`() =
        runTest {
            whenever(
                movEquipProprioRepository.listSend()
            ).thenReturn(
                Result.success(
                    listOf(
                        MovEquipProprio(
                            idMovEquipProprio = 1,
                            matricVigiaMovEquipProprio = 19759,
                            idLocalMovEquipProprio = 1,
                            tipoMovEquipProprio = TypeMovEquip.INPUT,
                            dthrMovEquipProprio = Date(1723213270250),
                            idEquipMovEquipProprio = 1,
                            matricColabMovEquipProprio = 19759,
                            destinoMovEquipProprio = "TESTE DESTINO",
                            notaFiscalMovEquipProprio = 123456789,
                            observMovEquipProprio = "TESTE OBSERV",
                            statusMovEquipProprio = StatusData.OPEN,
                            statusSendMovEquipProprio = StatusSend.SEND
                        )
                    )
                )
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
                            idEquip = 1
                        )
                    )
                )
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
                "ISendMovProprioList -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipProprioRepository send`() = runTest {
        val movEquipProprio = MovEquipProprio(
            idMovEquipProprio = 1,
            matricVigiaMovEquipProprio = 19759,
            idLocalMovEquipProprio = 1,
            tipoMovEquipProprio = TypeMovEquip.INPUT,
            dthrMovEquipProprio = Date(1723213270250),
            idEquipMovEquipProprio = 1,
            matricColabMovEquipProprio = 19759,
            destinoMovEquipProprio = "TESTE DESTINO",
            notaFiscalMovEquipProprio = 123456789,
            observMovEquipProprio = "TESTE OBSERV",
            statusMovEquipProprio = StatusData.OPEN,
            statusSendMovEquipProprio = StatusSend.SEND
        )
        val movEquipProprioEquipSeg = MovEquipProprioEquipSeg(
            idMovEquipProprioEquipSeg = 1,
            idMovEquipProprio = 1,
            idEquip = 1
        )
        val movEquipProprioPassag = MovEquipProprioPassag(
            idMovEquipProprioPassag = 1,
            idMovEquipProprio = 1,
            matricColab = 19035
        )
        val listSend = listOf(
            movEquipProprio
        )
        val listSendFull = listSend.map {
            it.movEquipProprioEquipSegList = listOf(movEquipProprioEquipSeg)
            it.movEquipProprioPassagList = listOf(movEquipProprioPassag)
            return@map it
        }
        whenever(
            movEquipProprioRepository.listSend()
        ).thenReturn(
            Result.success(
                listOf(
                    movEquipProprio
                )
            )
        )
        whenever(
            movEquipProprioEquipSegRepository.list(
                flowApp = FlowApp.CHANGE,
                id = 1
            )
        ).thenReturn(
            Result.success(
                listOf(
                    movEquipProprioEquipSeg
                )
            )
        )
        whenever(
            movEquipProprioPassagRepository.list(
                flowApp = FlowApp.CHANGE,
                id = 1
            )
        ).thenReturn(
            Result.success(
                listOf(
                    movEquipProprioPassag
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
            movEquipProprioRepository.send(
                list = listSendFull,
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
            "ISendMovProprioList -> Unknown Error"
        )
    }

    @Test
    fun `Check return list if SendMovProprio execute successfully`() = runTest {
        val movEquipProprio = MovEquipProprio(
            idMovEquipProprio = 1,
            matricVigiaMovEquipProprio = 19759,
            idLocalMovEquipProprio = 1,
            tipoMovEquipProprio = TypeMovEquip.INPUT,
            dthrMovEquipProprio = Date(1723213270250),
            idEquipMovEquipProprio = 1,
            matricColabMovEquipProprio = 19759,
            destinoMovEquipProprio = "TESTE DESTINO",
            notaFiscalMovEquipProprio = 123456789,
            observMovEquipProprio = "TESTE OBSERV",
            statusMovEquipProprio = StatusData.OPEN,
            statusSendMovEquipProprio = StatusSend.SEND
        )
        val movEquipProprioEquipSeg = MovEquipProprioEquipSeg(
            idMovEquipProprioEquipSeg = 1,
            idMovEquipProprio = 1,
            idEquip = 1
        )
        val movEquipProprioPassag = MovEquipProprioPassag(
            idMovEquipProprioPassag = 1,
            idMovEquipProprio = 1,
            matricColab = 19035
        )
        val listSend = listOf(
            movEquipProprio
        )
        val listSendFull = listSend.map {
            it.movEquipProprioEquipSegList = listOf(movEquipProprioEquipSeg)
            it.movEquipProprioPassagList = listOf(movEquipProprioPassag)
            return@map it
        }
        whenever(
            movEquipProprioRepository.listSend()
        ).thenReturn(
            Result.success(
                listOf(
                    movEquipProprio
                )
            )
        )
        whenever(
            movEquipProprioEquipSegRepository.list(
                flowApp = FlowApp.CHANGE,
                id = 1
            )
        ).thenReturn(
            Result.success(
                listOf(
                    movEquipProprioEquipSeg
                )
            )
        )
        whenever(
            movEquipProprioPassagRepository.list(
                flowApp = FlowApp.CHANGE,
                id = 1
            )
        ).thenReturn(
            Result.success(
                listOf(
                    movEquipProprioPassag
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
            movEquipProprioRepository.send(
                list = listSendFull,
                number = 16997417840,
                token = token(
                    number = 16997417840,
                    version = "1.00",
                    idBD = 1
                )
            )
        ).thenReturn(
            Result.success(
                listOf(
                    MovEquipProprio(
                        idMovEquipProprio = 1
                    )
                )
            )
        )
        val result = usecase()
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!![0].idMovEquipProprio, 1
        )
    }

}

