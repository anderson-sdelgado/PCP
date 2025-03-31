package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipVisitTerc
import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipVisitTercPassag
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeMovEquip
import br.com.usinasantafe.pcp.utils.TypeVisitTerc
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.Date

class GetDetalheVisitTercImplTest {

    private val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
    private val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
    private val getMotoristaVisitTerc = mock<GetMotoristaVisitTerc>()
    private val usecase = IGetDetalheVisitTerc(
        movEquipVisitTercRepository = movEquipVisitTercRepository,
        movEquipVisitTercPassagRepository = movEquipVisitTercPassagRepository,
        getMotoristaVisitTerc = getMotoristaVisitTerc
    )

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository Get`() = runTest {
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
            "IGetDetalheVisitTerc -> Unknown Error"
        )
    }

    @Test
    fun `Check return failure if have error in Motorista VisitanteRepository Get`() = runTest {
        whenever(
            movEquipVisitTercRepository.get(1)
        ).thenReturn(
            Result.success(
                MovEquipVisitTerc(
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
            )
        )
        whenever(
            getMotoristaVisitTerc(
                typeVisitTerc = TypeVisitTerc.VISITANTE,
                idVisitTerc = 1
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
            "IGetDetalheVisitTerc -> Unknown Error"
        )
    }

    @Test
    fun `Check return failure if have error in Motorista TerceiroRepository Get`() = runTest {
        whenever(
            movEquipVisitTercRepository.get(1)
        ).thenReturn(
            Result.success(
                MovEquipVisitTerc(
                    idMovEquipVisitTerc = 1,
                    dthrMovEquipVisitTerc = Date(),
                    tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                    veiculoMovEquipVisitTerc = "GOL",
                    placaMovEquipVisitTerc = "AAA-0000",
                    tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                    idVisitTercMovEquipVisitTerc = 1,
                    destinoMovEquipVisitTerc = "Teste Destino",
                    observMovEquipVisitTerc = "Teste Observ"
                )
            )
        )
        whenever(
            getMotoristaVisitTerc(
                typeVisitTerc = TypeVisitTerc.TERCEIRO,
                idVisitTerc = 1
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
            "IGetDetalheVisitTerc -> Unknown Error"
        )
    }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercPassagRepository List`() =
        runTest {
            whenever(
                movEquipVisitTercRepository.get(1)
            ).thenReturn(
                Result.success(
                    MovEquipVisitTerc(
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
                )
            )
            whenever(
                getMotoristaVisitTerc(
                    typeVisitTerc = TypeVisitTerc.VISITANTE,
                    idVisitTerc = 1
                )
            ).thenReturn(
                Result.success(
                    "123.456.789-00 - Anderson"
                )
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
                "IGetDetalheVisitTerc -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in  Passageiro VisitanteRepository Get`() = runTest {
        whenever(
            movEquipVisitTercRepository.get(1)
        ).thenReturn(
            Result.success(
                MovEquipVisitTerc(
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
            )
        )
        whenever(
            getMotoristaVisitTerc(
                typeVisitTerc = TypeVisitTerc.VISITANTE,
                idVisitTerc = 1
            )
        ).thenReturn(
            Result.success(
                "123.456.789-00 - Anderson"
            )
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
                        idMovEquipVisitTerc = 1,
                        idVisitTerc = 10
                    )
                )
            )
        )
        whenever(
            getMotoristaVisitTerc(
                typeVisitTerc = TypeVisitTerc.VISITANTE,
                idVisitTerc = 10
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
            "IGetDetalheVisitTerc -> Unknown Error"
        )
    }

    @Test
    fun `Check return failure if have error in  Passageiro TerceiroRepository Get`() = runTest {
        whenever(
            movEquipVisitTercRepository.get(1)
        ).thenReturn(
            Result.success(
                MovEquipVisitTerc(
                    idMovEquipVisitTerc = 1,
                    dthrMovEquipVisitTerc = Date(),
                    tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                    veiculoMovEquipVisitTerc = "GOL",
                    placaMovEquipVisitTerc = "AAA-0000",
                    tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                    idVisitTercMovEquipVisitTerc = 1,
                    destinoMovEquipVisitTerc = "Teste Destino",
                    observMovEquipVisitTerc = "Teste Observ"
                )
            )
        )
        whenever(
            getMotoristaVisitTerc(
                typeVisitTerc = TypeVisitTerc.TERCEIRO,
                idVisitTerc = 1
            )
        ).thenReturn(
            Result.success(
                "123.456.789-00 - Anderson"
            )
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
                        idMovEquipVisitTerc = 1,
                        idVisitTerc = 10
                    )
                )
            )
        )
        whenever(
            getMotoristaVisitTerc(
                typeVisitTerc = TypeVisitTerc.TERCEIRO,
                idVisitTerc = 10
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
            "IGetDetalheVisitTerc -> Unknown Error"
        )
    }

    @Test
    fun `Check return detalhe if GetDetalheVisitTercImpl execute correctly Visitante`() = runTest {
        whenever(
            movEquipVisitTercRepository.get(1)
        ).thenReturn(
            Result.success(
                MovEquipVisitTerc(
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
            )
        )
        whenever(
            getMotoristaVisitTerc(
                typeVisitTerc = TypeVisitTerc.VISITANTE,
                idVisitTerc = 1
            )
        ).thenReturn(
            Result.success(
                "123.456.789-00 - Anderson"
            )
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
                        idMovEquipVisitTerc = 1,
                        idVisitTerc = 10
                    )
                )
            )
        )
        whenever(
            getMotoristaVisitTerc(
                typeVisitTerc = TypeVisitTerc.VISITANTE,
                idVisitTerc = 10
            )
        ).thenReturn(
            Result.success(
                "123.456.789-01 - Passageiro"
            )
        )
        val result = usecase(1)
        assertTrue(result.isSuccess)
        val detalhe = result.getOrNull()!!
        assertEquals(
            detalhe.tipoMov,
            "ENTRADA"
        )
        assertEquals(
            detalhe.veiculo,
            "GOL"
        )
        assertEquals(
            detalhe.placa,
            "AAA-0000"
        )
        assertEquals(
            detalhe.tipoVisitTerc,
            "VISITANTE"
        )
        assertEquals(
            detalhe.motorista,
            "123.456.789-00 - Anderson"
        )
        assertEquals(
            detalhe.passageiro,
            "123.456.789-01 - Passageiro;"
        )
        assertEquals(
            detalhe.destino,
            "Teste Destino"
        )
        assertEquals(
            detalhe.observ,
            "Teste Observ"
        )
    }

    @Test
    fun `Check return detalhe if GetDetalheVisitTercImpl execute correctly Terceiro`() = runTest {
        whenever(
            movEquipVisitTercRepository.get(1)
        ).thenReturn(
            Result.success(
                MovEquipVisitTerc(
                    idMovEquipVisitTerc = 1,
                    dthrMovEquipVisitTerc = Date(),
                    tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                    veiculoMovEquipVisitTerc = "GOL",
                    placaMovEquipVisitTerc = "AAA-0000",
                    tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                    idVisitTercMovEquipVisitTerc = 1,
                    destinoMovEquipVisitTerc = "Teste Destino",
                    observMovEquipVisitTerc = "Teste Observ"
                )
            )
        )
        whenever(
            getMotoristaVisitTerc(
                typeVisitTerc = TypeVisitTerc.TERCEIRO,
                idVisitTerc = 1
            )
        ).thenReturn(
            Result.success(
                "123.456.789-00 - Anderson"
            )
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
                        idMovEquipVisitTerc = 1,
                        idVisitTerc = 10
                    )
                )
            )
        )
        whenever(
            getMotoristaVisitTerc(
                typeVisitTerc = TypeVisitTerc.TERCEIRO,
                idVisitTerc = 10
            )
        ).thenReturn(
            Result.success(
                "123.456.789-01 - Passageiro"
            )
        )
        val result = usecase(1)
        assertTrue(result.isSuccess)
        val detalhe = result.getOrNull()!!
        assertEquals(
            detalhe.tipoMov,
            "ENTRADA"
        )
        assertEquals(
            detalhe.veiculo,
            "GOL"
        )
        assertEquals(
            detalhe.placa,
            "AAA-0000"
        )
        assertEquals(
            detalhe.tipoVisitTerc,
            "TERCEIRO"
        )
        assertEquals(
            detalhe.motorista,
            "123.456.789-00 - Anderson"
        )
        assertEquals(
            detalhe.passageiro,
            "123.456.789-01 - Passageiro;"
        )
        assertEquals(
            detalhe.destino,
            "Teste Destino"
        )
        assertEquals(
            detalhe.observ,
            "Teste Observ"
        )
    }
}